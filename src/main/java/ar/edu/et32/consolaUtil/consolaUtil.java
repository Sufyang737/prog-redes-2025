package ar.edu.et32.consolaUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class consolaUtil {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RED = "\u001B[31m";

    public enum TipoNumero {
        ENTERO, FLOTANTE, NO_ES_NUMERO
    }

    public String leerTexto(String mensaje) {
        System.out.print(ANSI_CYAN + mensaje + ANSI_RESET);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return reader.readLine();
        } catch (IOException e) {
            System.err.println("Error de entrada/salida: " + e.getMessage());
            return "";
        }
    }

    public TipoNumero validarNumero(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return TipoNumero.NO_ES_NUMERO;
        }
        try {
            Integer.parseInt(texto.trim());
            return TipoNumero.ENTERO;
        } catch (NumberFormatException e1) {
            try {
                Float.parseFloat(texto.trim());
                return TipoNumero.FLOTANTE;
            } catch (NumberFormatException e2) {
                return TipoNumero.NO_ES_NUMERO;
            }
        }
    }

    public void mostrarMenu() {
        System.out.println(ANSI_BLUE + "\n===================================");
        System.out.println("   SISTEMA DE GESTIÓN DE INVENTARIO");
        System.out.println("===================================" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "1. Agregar Producto");
        System.out.println("2. Mostrar Inventario");
        System.out.println("3. Editar Producto");
        System.out.println("4. Eliminar Producto");
        System.out.println("5. Salir" + ANSI_RESET);
    }
}
