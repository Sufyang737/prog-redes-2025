package ar.edu.et32;

import ar.edu.et32.client.Client;
import ar.edu.et32.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Punto de entrada para iniciar el servidor o un cliente desde un único comando.
 */
public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            args = solicitarArgumentosInteractivos();
        }

        if (args.length == 0) {
            mostrarUso();
            return;
        }

        String modo = args[0].toLowerCase();
        switch (modo) {
            case "server" -> iniciarServidor(args);
            case "client" -> iniciarCliente(args);
            default -> {
                System.err.println("Modo desconocido: " + args[0]);
                mostrarUso();
            }
        }
    }

    private static void iniciarServidor(String[] args) {
        int puerto = args.length >= 2 ? parseEntero(args[1], 5001) : 5001;
        System.out.println("Levantando servidor en el puerto " + puerto);
        new Server(puerto).iniciarServidor();
    }

    private static void iniciarCliente(String[] args) {
        String host = args.length >= 2 ? args[1] : "127.0.0.1";
        int puerto = args.length >= 3 ? parseEntero(args[2], 5001) : 5001;
        System.out.println("Conectando cliente a " + host + ":" + puerto);
        new Client(host, puerto).conectarConServidor();
    }

    private static int parseEntero(String valor, int porDefecto) {
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            System.err.println("Valor inválido: " + valor + ". Se usará " + porDefecto);
            return porDefecto;
        }
    }

    private static void mostrarUso() {
        System.out.println("Uso: java ar.edu.et32.Main <server|client> [host] [puerto]");
        System.out.println("Ejemplos:");
        System.out.println("  Servidor (puerto por defecto 5001): java ar.edu.et32.Main server [puerto]");
        System.out.println("  Cliente: java ar.edu.et32.Main client [host] [puerto]");
    }

    private static String[] solicitarArgumentosInteractivos() {
        System.out.println("Seleccioná qué querés iniciar (server/client). Dejando vacío se cancela.");
        try (BufferedReader consola = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("Modo: ");
            String modo = consola.readLine();
            if (modo == null || modo.isBlank()) {
                return new String[0];
            }
            modo = modo.trim().toLowerCase();
            if (modo.equals("server")) {
                System.out.print("Puerto [5001]: ");
                String puerto = consola.readLine();
                if (puerto == null || puerto.isBlank()) {
                    return new String[]{"server"};
                }
                return new String[]{"server", puerto.trim()};
            } else if (modo.equals("client")) {
                System.out.print("Host [127.0.0.1]: ");
                String host = consola.readLine();
                if (host == null || host.isBlank()) {
                    host = "127.0.0.1";
                } else {
                    host = host.trim();
                }
                System.out.print("Puerto [5001]: ");
                String puerto = consola.readLine();
                if (puerto == null || puerto.isBlank()) {
                    return new String[]{"client", host, "5001"};
                }
                return new String[]{"client", host, puerto.trim()};
            } else {
                System.err.println("Entrada no reconocida.");
                return new String[0];
            }
        } catch (IOException e) {
            System.err.println("No se pudo leer de la consola: " + e.getMessage());
            return new String[0];
        }
    }
}
