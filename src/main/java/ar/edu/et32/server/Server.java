package ar.edu.et32.server;

import ar.edu.et32.model.Board;
import ar.edu.et32.model.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Servidor que acepta clientes y coordina partidas del juego en red.
 */
public class Server {

    private final int puerto;
    private final List<Socket> salaDeEspera = new ArrayList<>();
    private final ExecutorService partidas = Executors.newCachedThreadPool();

    public Server(int puerto) {
        this.puerto = puerto;
    }

    public void iniciarServidor() {
        System.out.println("Iniciando servidor en el puerto " + puerto);
        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            while (true) {
                Socket cliente = serverSocket.accept();
                System.out.println("Cliente conectado desde " + cliente.getInetAddress());
                agregarCliente(cliente);
            }
        } catch (IOException e) {
            System.err.println("Error al iniciar o ejecutar el servidor: " + e.getMessage());
        } finally {
            partidas.shutdownNow();
        }
    }

    private void agregarCliente(Socket cliente) {
        synchronized (salaDeEspera) {
            salaDeEspera.add(cliente);
            if (salaDeEspera.size() >= 2) {
                Socket jugador1 = salaDeEspera.remove(0);
                Socket jugador2 = salaDeEspera.remove(0);
                partidas.execute(new GameSession(jugador1, jugador2));
            } else {
                try {
                    PrintWriter out = new PrintWriter(cliente.getOutputStream(), true);
                    out.println("INFO:Esperando a otro jugador...");
                } catch (IOException e) {
                    System.err.println("No se pudo notificar al jugador en espera: " + e.getMessage());
                }
            }
        }
    }

    private static class GameSession implements Runnable {
        private final PlayerConnection jugadorX;
        private final PlayerConnection jugadorO;
        private final Board board = new Board();

        private GameSession(Socket socketJugador1, Socket socketJugador2) {
            this.jugadorX = new PlayerConnection(socketJugador1, "X");
            this.jugadorO = new PlayerConnection(socketJugador2, "O");
        }

        @Override
        public void run() {
            try {
                gestionarPartida();
            } catch (IOException e) {
                System.err.println("Error durante la partida: " + e.getMessage());
            } finally {
                jugadorX.cerrar();
                jugadorO.cerrar();
            }
        }

        private void gestionarPartida() throws IOException {
            solicitarNombre(jugadorX);
            solicitarNombre(jugadorO);

            jugadorX.enviar("INFO:Comienza la partida. Tu símbolo es X");
            jugadorO.enviar("INFO:Comienza la partida. Tu símbolo es O");

            Player jugadorLogicoX = new Player(jugadorX.getNombre(), jugadorX.getSimbolo());
            Player jugadorLogicoO = new Player(jugadorO.getNombre(), jugadorO.getSimbolo());

            boolean turnoDeX = true;
            notificarEstadoJuego("Partida iniciada", "CONTINUA");

            while (true) {
                PlayerConnection actual = turnoDeX ? jugadorX : jugadorO;
                PlayerConnection esperando = turnoDeX ? jugadorO : jugadorX;
                Player jugadorLogico = turnoDeX ? jugadorLogicoX : jugadorLogicoO;

                actual.enviar("ES_TU_TURNO");
                actual.enviar("INFO:Ingresa tu movimiento con el formato fila,col usando valores 0-2");
                esperando.enviar("ESPERA_TURNO");

                boolean movimientoExitoso = false;
                while (!movimientoExitoso) {
                    String entrada = actual.recibir();
                    if (entrada == null) {
                        throw new SocketException("El jugador se desconectó");
                    }

                    int[] movimiento = parsearMovimiento(entrada.trim());
                    if (movimiento == null) {
                        actual.enviar("MOVIMIENTO_INVALIDO:Formato incorrecto. Usa fila,col");
                        continue;
                    }

                    if (!validarMovimiento(movimiento[0], movimiento[1])) {
                        actual.enviar("MOVIMIENTO_INVALIDO:Coordenadas fuera de rango");
                        continue;
                    }

                    if (!jugadorLogico.makeMove(board, movimiento[0], movimiento[1])) {
                        actual.enviar("MOVIMIENTO_INVALIDO:La casilla ya está ocupada");
                        continue;
                    }

                    movimientoExitoso = true;
                    String descripcion = "Movimiento de " + jugadorLogico.getName() +
                            " (" + jugadorLogico.getSymbol() + ") en " + movimiento[0] + "," + movimiento[1];
                    notificarEstadoJuego(descripcion, "CONTINUA");
                }

                String ganador = board.getWinner();
                if (ganador != null) {
                    String descripcion = "Jugador " + jugadorLogico.getName() + " gana";
                    notificarEstadoJuego(descripcion, "GANA:" + ganador);
                    finalizarPartida();
                    return;
                }

                if (board.isDraw()) {
                    notificarEstadoJuego("No hay más movimientos disponibles", "EMPATE");
                    finalizarPartida();
                    return;
                }

                turnoDeX = !turnoDeX;
            }
        }

        private void finalizarPartida() {
            jugadorX.enviar("FIN_PARTIDA");
            jugadorO.enviar("FIN_PARTIDA");
        }

        private void solicitarNombre(PlayerConnection jugador) throws IOException {
            jugador.enviar("SOLICITAR_NOMBRE");
            String nombre = jugador.recibir();
            if (nombre == null || nombre.isBlank()) {
                nombre = "Jugador" + jugador.getSimbolo();
            }
            jugador.setNombre(nombre.trim());
            jugador.enviar("ASIGNADO:" + jugador.getSimbolo());
        }

        private boolean validarMovimiento(int fila, int col) {
            return fila >= 0 && fila < 3 && col >= 0 && col < 3;
        }

        private void notificarEstadoJuego(String descripcion, String estado) {
            String tableroActual = board.renderBoard();
            for (PlayerConnection jugador : List.of(jugadorX, jugadorO)) {
                jugador.enviar("INFO:" + descripcion);
                jugador.enviar("TABLERO");
                for (String linea : tableroActual.split("\n")) {
                    jugador.enviar(linea);
                }
                jugador.enviar("FIN_TABLERO");
                jugador.enviar("ESTADO:" + estado);
            }
        }

        private int[] parsearMovimiento(String entrada) {
            String[] partes = entrada.split(",");
            if (partes.length != 2) {
                return null;
            }
            try {
                int fila = Integer.parseInt(partes[0].trim());
                int col = Integer.parseInt(partes[1].trim());
                return new int[]{fila, col};
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }

    private static class PlayerConnection {
        private final Socket socket;
        private final BufferedReader in;
        private final PrintWriter out;
        private final String simbolo;
        private String nombre;

        private PlayerConnection(Socket socket, String simbolo) {
            this.socket = socket;
            this.simbolo = simbolo;
            try {
                this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.out = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                throw new IllegalStateException("No se pudo crear el canal de comunicación", e);
            }
        }

        public void enviar(String mensaje) {
            out.println(mensaje);
        }

        public String recibir() throws IOException {
            return in.readLine();
        }

        public String getSimbolo() {
            return simbolo;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public void cerrar() {
            try {
                socket.close();
            } catch (IOException ignored) {
            }
        }
    }

    public static void main(String[] args) {
        int puerto = 5001;
        if (args.length > 0) {
            puerto = Integer.parseInt(args[0]);
        }
        Server server = new Server(puerto);
        server.iniciarServidor();
    }
}
