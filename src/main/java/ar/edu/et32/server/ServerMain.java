package ar.edu.et32.server;

/**
 * Lanzador sencillo para levantar el servidor sin argumentos extras.
 */
public class ServerMain {

    public static void main(String[] args) {
        int puerto = args.length >= 1 ? parseEntero(args[0], 5001) : 5001;
        System.out.println("Servidor escuchando en puerto " + puerto);
        new Server(puerto).iniciarServidor();
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
