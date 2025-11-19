package ar.edu.et32.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {

    static final String HOST_DEFAULT = "127.0.0.1";
    static final String HOST_PROFESOR = "130.10.1.54";
    static final int PUERTO_DEFAULT = 5000;

    public static void main(String[] args) {
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Ingrese IP (enter para 127.0.0.1, escribir profe para 130.10.1.54): ");
            String hostIngresado = teclado.readLine();
            if (hostIngresado == null || hostIngresado.isBlank()) {
                hostIngresado = HOST_DEFAULT;
            } else if ("profe".equalsIgnoreCase(hostIngresado.trim())) {
                hostIngresado = HOST_PROFESOR;
            }
            System.out.print("Ingrese puerto (enter para 5000): ");
            String puertoIngresado = teclado.readLine();
            int puerto = puertoIngresado == null || puertoIngresado.isBlank() ? PUERTO_DEFAULT : Integer.parseInt(puertoIngresado.trim());

            String host = hostIngresado;
            Socket socket = new Socket(host, puerto);
            BufferedReader delServidor = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter alServidor = new PrintWriter(socket.getOutputStream(), true);

            Thread escucha = new Thread(() -> {
                try {
                    String mensaje;
                    while ((mensaje = delServidor.readLine()) != null) {
                        System.out.println("[SERVIDOR] " + mensaje);
                    }
                } catch (IOException e) {
                    System.out.println("Fin de la conexión: " + e.getMessage());
                }
            });
            escucha.setDaemon(true);
            escucha.start();

            System.out.println("Conectado a " + host + ":" + puerto + ". Use /hi, /logout o ID.");
            System.out.println("Comandos: /hi para bienvenida, /logout para salir, ID para el código.");
            while (true) {
                String linea = teclado.readLine();
                if (linea == null) {
                    break;
                }
                alServidor.println(linea);
                if ("/logout".equals(linea.trim())) {
                    break;
                }
            }

            socket.close();
        } catch (Exception e) {
            System.out.println("No se pudo conectar: " + e.getMessage());
        }
    }
}
