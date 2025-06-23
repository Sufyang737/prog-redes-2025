package ar.edu.et32.manejadorDeDatos;

import ar.edu.et32.configuration.Configuracion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Se especializa en leer datos desde la consola y guardarlos
 * donde corresponda (vector o archivo).
 * Cumple con la lógica del Punto 1.
 */
public class ManejadorDeDatos {

    /**
     * Pide al usuario números por consola y los almacena en un vector.
     * @return Un array de enteros con los números ingresados.
     */
    public int[] ingresarDatosParaVector() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int[] vector = new int[Configuracion.TAMANIO_VECTOR]; //<- Esta línea ahora funcionará
        System.out.println("\n> Ingrese " + Configuracion.TAMANIO_VECTOR + " números para guardar en memoria volátil (vector):");
        System.out.println("(Recuerde ingresar al menos dos veces el número '0')");

        for (int i = 0; i < vector.length; i++) {
            while (true) {
                try {
                    System.out.print("  Ingrese el número " + (i + 1) + ": ");
                    String linea = reader.readLine();
                    vector[i] = Integer.parseInt(linea.trim());
                    break;
                } catch (NumberFormatException e) {
                    System.err.println("  Error: El valor ingresado no es un número entero válido. Intente de nuevo.");
                } catch (IOException e) {
                    System.err.println("  Error de entrada/salida: " + e.getMessage());
                }
            }
        }
        System.out.println("-> Vector cargado correctamente.");
        return vector;
    }

    /**
     * Pide al usuario números por consola y los guarda en un archivo de texto.
     */
    public void ingresarDatosParaArchivo() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\n> Ingrese " + Configuracion.CANTIDAD_DATOS_ARCHIVO + " números para guardar en memoria no-volátil (archivo):");
        System.out.println("(Recuerde ingresar al menos dos veces el número '0')");

        try (FileWriter fw = new FileWriter(Configuracion.ARCHIVO_DATOS);
             BufferedWriter bw = new BufferedWriter(fw)) {

            for (int i = 0; i < Configuracion.CANTIDAD_DATOS_ARCHIVO; i++) {
                while (true) {
                    try {
                        System.out.print("  Ingrese el número " + (i + 1) + ": ");
                        String linea = reader.readLine();
                        int numero = Integer.parseInt(linea.trim());
                        bw.write(String.valueOf(numero));
                        bw.newLine();
                        break;
                    } catch (NumberFormatException e) {
                        System.err.println("  Error: El valor ingresado no es un número entero válido. Intente de nuevo.");
                    } catch (IOException e) {
                        System.err.println("  Error de entrada/salida: " + e.getMessage());
                    }
                }
            }
            System.out.println("-> Archivo '" + Configuracion.ARCHIVO_DATOS + "' guardado correctamente.");

        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo '" + Configuracion.ARCHIVO_DATOS + "': " + e.getMessage());
        }
    }
}
