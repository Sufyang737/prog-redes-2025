package ar.edu.et32.procesadorDeDatos;

// --- IMPORTACIÓN AÑADIDA aqui estaba tu error! ---
import ar.edu.et32.configuration.Configuracion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ProcesadorDeDatos {

    /**
     * Procesa los datos del vector según las reglas del Punto 2.
     * @param vector El vector de enteros a procesar.
     */
    public void procesarDatosPunto2(int[] vector) {
        // Se corrigió "Configuration" por "Configuracion"
        try (BufferedWriter writerResultados = new BufferedWriter(new FileWriter(Configuracion.ARCHIVO_RESULTADOS));
             BufferedWriter writerErrores = new BufferedWriter(new FileWriter(Configuracion.ARCHIVO_ERRORES))) {

            writerResultados.write("--- Resultados del Punto 2 (Vector) ---\n");
            writerErrores.write("--- Errores del Punto 2 (Vector) ---\n");

            for (int i = 0; i < vector.length - 1; i++) {
                int numero1 = vector[i];
                int numero2 = vector[i + 1];
                int divisor = numero2 - 3;

                try {
                    if (divisor == 0) throw new ArithmeticException("División por cero.");

                    double resultado = (double) numero1 / divisor;
                    String linea = String.format("%d / (%d - 3) = %.2f", numero1, numero2, resultado);
                    writerResultados.write(linea);
                    writerResultados.newLine();

                } catch (ArithmeticException e) {
                    String errorMsg = String.format("%d / (%d - 3) = Error: %s", numero1, numero2, e.getMessage());
                    writerErrores.write(errorMsg);
                    writerErrores.newLine();
                }
            }

            String errorFinal = String.format("%d / (siguiente - 3) = Error: No hay un 'siguiente número' para el último elemento.", vector[vector.length - 1]);
            writerErrores.write(errorFinal);
            writerErrores.newLine();

        } catch (IOException e) {
            System.err.println("Error al escribir en los archivos de salida del Punto 2: " + e.getMessage());
        }
    }

    /**
     * Procesa datos del vector y del archivo de datos según las reglas del Punto 3.
     * @param vector El vector de enteros a procesar.
     */
    public void procesarDatosPunto3(int[] vector) {
        // Se corrigió "Configuracion" aquí también.
        try (BufferedWriter writerResultados = new BufferedWriter(new FileWriter(Configuracion.ARCHIVO_RESULTADOS, true));
             BufferedWriter writerErrores = new BufferedWriter(new FileWriter(Configuracion.ARCHIVO_ERRORES, true))) {

            writerResultados.write("\n--- Resultados del Punto 3 (Vector y Archivo) ---\n");
            writerErrores.write("\n--- Errores del Punto 3 (Vector y Archivo) ---\n");

            // 1. Procesar datos del vector
            writerResultados.write("-- Desde el Vector --\n");
            for (int numero : vector) {
                double resultado = (double) numero / 3.0;
                writerResultados.write(String.format("%d / 3 = %.2f", numero, resultado));
                writerResultados.newLine();
            }

            writerResultados.write("\n-- Desde el Archivo '" + Configuracion.ARCHIVO_DATOS + "' --\n");
            File archivoEntrada = new File(Configuracion.ARCHIVO_DATOS);
            if (!archivoEntrada.exists()) {
                writerErrores.write("Error: El archivo '" + Configuracion.ARCHIVO_DATOS + "' no fue encontrado.\n");
                return;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(archivoEntrada))) {
                String lineaLeida;
                while ((lineaLeida = reader.readLine()) != null) {
                    try {
                        int numero = Integer.parseInt(lineaLeida.trim());
                        double resultado = (double) numero / 3.0;
                        writerResultados.write(String.format("%d / 3 = %.2f", numero, resultado));
                        writerResultados.newLine();
                    } catch (NumberFormatException e) {
                        writerErrores.write(String.format("'%s' / 3 = Error: La línea no es un número válido.\n", lineaLeida));
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Error al procesar los datos para el Punto 3: " + e.getMessage());
        }
    }
}
