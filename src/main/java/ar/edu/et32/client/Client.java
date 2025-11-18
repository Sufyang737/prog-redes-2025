package ar.edu.et32.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Cliente de consola que se conecta al servidor y permite interactuar con la partida.
 */
public class Client {

    private final String host;
    private final int puerto;

    public Client(String host, int puerto) {
        this.host = host;
        this.puerto = puerto;
    }

    public void conectarConServidor() {
        try (Socket socket = new Socket(host, puerto);
             BufferedReader servidorIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter servidorOut = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader consola = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Conectado al servidor " + host + ":" + puerto);
            escucharMensajes(servidorIn, servidorOut, consola);
        } catch (IOException e) {
            System.err.println("No se pudo conectar con el servidor: " + e.getMessage());
        }
    }

    private void escucharMensajes(BufferedReader servidorIn,
                                   PrintWriter servidorOut,
                                   BufferedReader consola) throws IOException {
        String linea;
        boolean turnoActivo = false;
        while ((linea = servidorIn.readLine()) != null) {
            if (linea.startsWith("SOLICITAR_NOMBRE")) {
                System.out.print("Ingresa tu nombre: ");
                String nombre = consola.readLine();
                servidorOut.println(nombre == null ? "" : nombre);
            } else if (linea.startsWith("ASIGNADO:")) {
                System.out.println("Servidor: Tu símbolo es " + linea.substring("ASIGNADO:".length()));
            } else if (linea.startsWith("INFO:")) {
                System.out.println(linea.substring("INFO:".length()));
            } else if (linea.equals("TABLERO")) {
                recibirEstadoJuego(servidorIn);
            } else if (linea.startsWith("ESTADO:")) {
                System.out.println("Estado de la partida: " + linea.substring("ESTADO:".length()));
            } else if (linea.equals("ES_TU_TURNO")) {
                turnoActivo = true;
                realizarMovimiento(consola, servidorOut);
            } else if (linea.equals("ESPERA_TURNO")) {
                turnoActivo = false;
                System.out.println("Esperando la jugada del oponente...");
            } else if (linea.startsWith("MOVIMIENTO_INVALIDO")) {
                System.out.println(linea.replace("MOVIMIENTO_INVALIDO:", "Movimiento inválido: "));
                if (turnoActivo) {
                    realizarMovimiento(consola, servidorOut);
                }
            } else if (linea.equals("FIN_PARTIDA")) {
                System.out.println("La partida ha finalizado. Gracias por jugar.");
                break;
            } else {
                System.out.println("Mensaje desconocido del servidor: " + linea);
            }
        }
    }

    private void realizarMovimiento(BufferedReader consola, PrintWriter servidorOut) throws IOException {
        System.out.print("Ingresa posicion (fila,col): ");
        String movimiento = consola.readLine();
        if (movimiento == null || movimiento.isBlank()) {
            movimiento = "-1,-1";
        }
        servidorOut.println(movimiento.trim());
    }

    private void recibirEstadoJuego(BufferedReader servidorIn) throws IOException {
        String linea;
        while ((linea = servidorIn.readLine()) != null) {
            if (linea.equals("FIN_TABLERO")) {
                break;
            }
            System.out.println(linea);
        }
    }

    public static void main(String[] args) {
        String host = "127.0.0.1";
        int puerto = 5001;
        if (args.length >= 1) {
            host = args[0];
        }
        if (args.length >= 2) {
            puerto = Integer.parseInt(args[1]);
        }
        Client client = new Client(host, puerto);
        client.conectarConServidor();
    }
}
