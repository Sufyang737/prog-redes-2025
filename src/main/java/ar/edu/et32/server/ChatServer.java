package ar.edu.et32.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;


public class ChatServer {

    static final String MENSAJE_BIENVENIDA = "BIENVENIDO AL SERVIDOR LOCAL";
    static final Vector<ClientWorker> CLIENTES = new Vector<>();

    public static void main(String[] args) {
        int puerto = 5000;
        if (args.length > 0) {
            try {
                puerto = Integer.parseInt(args[0]);
            } catch (NumberFormatException ignored) {
            }
        }

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(puerto);
            System.out.println("Servidor local escuchando en puerto " + puerto);
            while (true) {
                Socket socket = serverSocket.accept();
                ClientWorker worker = new ClientWorker(socket);
                CLIENTES.add(worker);
                worker.start();
            }
        } catch (IOException e) {
            System.out.println("Error del servidor: " + e.getMessage());
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    static class ClientWorker extends Thread {
        Socket socket;
        BufferedReader entrada;
        PrintWriter salida;

        ClientWorker(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                salida = new PrintWriter(socket.getOutputStream(), true);
                salida.println(MENSAJE_BIENVENIDA);
                while (true) {
                    String texto = entrada.readLine();
                    if (texto == null) {
                        break;
                    }
                    texto = texto.trim();
                    if (texto.length() == 0) {
                        continue;
                    }
                    if (texto.startsWith("/")) {
                        if ("/hi".equals(texto)) {
                            salida.println(MENSAJE_BIENVENIDA);
                        } else if ("/logout".equals(texto)) {
                            break;
                        } else {
                            salida.println("COMANDO DESCONOCIDO");
                        }
                    } else {
                        for (ClientWorker c : CLIENTES) {
                            if (c.salida != null) {
                                c.salida.println(texto);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Cliente caído: " + e.getMessage());
            } finally {
                try {
                    if (entrada != null) entrada.close();
                } catch (IOException ignored) {}
                if (salida != null) salida.close();
                try {
                    socket.close();
                } catch (IOException ignored) {}
                CLIENTES.remove(this);
            }
        }
    }
}
