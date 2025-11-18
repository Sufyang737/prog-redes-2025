package ar.edu.et32.client;

/**
 * Lanzador sencillo que se conecta al servidor usando valores por defecto.
 */
public class ClientMain {

    public static void main(String[] args) {
        String host = args.length >= 1 ? args[0] : "127.0.0.1";
        int puerto = args.length >= 2 ? parseEntero(args[1], 5001) : 5001;
        System.out.println("Cliente conectándose a " + host + ":" + puerto);
        new Client(host, puerto).conectarConServidor();
    }

    private static int parseEntero(String valor, int porDefecto) {
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            System.err.println("Puerto inválido, se usa " + porDefecto);
            return porDefecto;
        }
    }
}
