package ar.edu.et32.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class InputUtils {

    private static final PrintStream out = System.out;

    public static String leerLineaSystemIn(String prompt) {
        out.print(prompt);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int byteLeido;
        try {

            while ((byteLeido = System.in.read()) != -1) {
                if (byteLeido == '\n') {
                    break;
                }
                if (byteLeido != '\r') {
                    baos.write(byteLeido);
                }
            }
            if (byteLeido == -1 && baos.size() == 0) return null;
        } catch (IOException e) {
            out.println("Error de E/S al leer de System.in: " + e.getMessage());
            return null;
        }
        return baos.toString();
    }

    public static int leerEnteroValidadoSystemIn(String prompt, Integer min, Integer max) {
        int numero = 0;
        boolean valido = false;
        while (!valido) {
            String linea = leerLineaSystemIn(prompt);
            if (linea == null || linea.trim().isEmpty()) {
                out.println("Entrada vacía o error de lectura. Intente de nuevo.");
                continue;
            }
            try {
                numero = Integer.parseInt(linea.trim());
                if ((min == null || numero >= min) && (max == null || numero <= max)) {
                    valido = true;
                } else {
                    out.print("Número fuera de rango. ");
                    if (min != null) out.print("Mínimo: " + min + ". ");
                    if (max != null) out.print("Máximo: " + max + ". ");
                    out.println("Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                out.println("Entrada inválida. '" + linea.trim() + "' no es un número entero válido. Intente de nuevo.");
            }
        }
        return numero;
    }

    public static double leerDoubleValidadoSystemIn(String prompt, Double min, Double max) {
        double numero = 0.0;
        boolean valido = false;
        while (!valido) {
            String linea = leerLineaSystemIn(prompt);
            if (linea == null || linea.trim().isEmpty()) {
                out.println("Entrada vacía o error de lectura. Intente de nuevo.");
                continue;
            }
            try {
                numero = Double.parseDouble(linea.trim().replace(',', '.'));
                if ((min == null || numero >= min) && (max == null || numero <= max)) {
                    valido = true;
                } else {
                    out.print("Número fuera de rango. ");
                    if (min != null) out.print("Mínimo: " + min + ". ");
                    if (max != null) out.print("Máximo: " + max + ". ");
                    out.println("Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                out.println("Entrada inválida. '" + linea.trim() + "' no es un número decimal válido. Intente de nuevo.");
            }
        }
        return numero;
    }

    public static String leerStringNoVacioSystemIn(String prompt) {
        String entrada = "";
        boolean valido = false;
        while(!valido) {
            entrada = leerLineaSystemIn(prompt);
            if (entrada == null) {
                out.println("Fallo la lectura de entrada.");
                System.exit(1);
            }
            entrada = entrada.trim();
            if (!entrada.isEmpty()) {
                valido = true;
            } else {
                out.println("La entrada no puede estar vacía. Intente de nuevo.");
            }
        }
        return entrada;
    }

    private static BufferedReader brGrupo2;

    private static void inicializarBufferedReaderParaGrupo2() {
        if (brGrupo2 == null) {
            brGrupo2 = new BufferedReader(new InputStreamReader(System.in));
        }
    }

    public static String leerLineaBufferedReader(String prompt) {
        inicializarBufferedReaderParaGrupo2();
        out.print(prompt);
        try {
            return brGrupo2.readLine();
        } catch (IOException e) {
            out.println("Error de E/S al leer con BufferedReader: " + e.getMessage());
            return null;
        }
    }

    public static int leerEnteroValidadoBufferedReader(String prompt, Integer min, Integer max) {
        int numero = 0;
        boolean valido = false;
        while (!valido) {
            String linea = leerLineaBufferedReader(prompt);
            if (linea == null || linea.trim().isEmpty()) {
                out.println("Entrada vacía, fin de entrada o error de lectura. Intente de nuevo.");
                if(linea == null) System.exit(1);
                continue;
            }
            try {
                numero = Integer.parseInt(linea.trim());
                if ((min == null || numero >= min) && (max == null || numero <= max)) {
                    valido = true;
                } else {
                    out.print("Número fuera de rango. ");
                    if (min != null) out.print("Mínimo: " + min + ". ");
                    if (max != null) out.print("Máximo: " + max + ". ");
                    out.println("Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                out.println("Entrada inválida. '" + linea.trim() + "' no es un número entero válido. Intente de nuevo.");
            }
        }
        return numero;
    }

    public static double leerDoubleValidadoBufferedReader(String prompt, Double min, Double max) {
        double numero = 0.0;
        boolean valido = false;
        while (!valido) {
            String linea = leerLineaBufferedReader(prompt);
            if (linea == null || linea.trim().isEmpty()) {
                out.println("Entrada vacía, fin de entrada o error de lectura. Intente de nuevo.");
                if(linea == null) System.exit(1);
                continue;
            }
            try {
                numero = Double.parseDouble(linea.trim().replace(',', '.'));
                if ((min == null || numero >= min) && (max == null || numero <= max)) {
                    valido = true;
                } else {
                    out.print("Número fuera de rango. ");
                    if (min != null) out.print("Mínimo: " + min + ". ");
                    if (max != null) out.print("Máximo: " + max + ". ");
                    out.println("Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                out.println("Entrada inválida. '" + linea.trim() + "' no es un número decimal válido. Intente de nuevo.");
            }
        }
        return numero;
    }

    public static String leerStringNoVacioBufferedReader(String prompt) {
        String entrada = "";
        boolean valido = false;
        while(!valido) {
            entrada = leerLineaBufferedReader(prompt);
            if (entrada == null) {
                out.println("Fallo la lectura de entrada (EOF).");
                System.exit(1);
            }
            entrada = entrada.trim();
            if (!entrada.isEmpty()) {
                valido = true;
            } else {
                out.println("La entrada no puede estar vacía. Intente de nuevo.");
            }
        }
        return entrada;
    }

    public static void cerrarBufferedReader() {
        try {
            if (brGrupo2 != null) {
                brGrupo2.close();
            }
        } catch (IOException e) {
            out.println("Advertencia: Error al cerrar BufferedReader: " + e.getMessage());
        }
    }
}