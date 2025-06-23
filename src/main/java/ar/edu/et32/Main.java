package ar.edu.et32;

import ar.edu.et32.configuration.Configuracion;
import ar.edu.et32.manejadorDeDatos.ManejadorDeDatos;
import ar.edu.et32.procesadorDeDatos.ProcesadorDeDatos;

public class Main {

    public static void main(String[] args) {
        System.out.println("--- Inicio del Trabajo Práctico N°1: Flujo de Datos (Versión Modular) ---");

        ManejadorDeDatos manejador = new ManejadorDeDatos();
        ProcesadorDeDatos procesador = new ProcesadorDeDatos();

        System.out.println("\n[PUNTO 1] Iniciando la carga de datos...");
        int[] vectorNumeros = manejador.ingresarDatosParaVector();
        manejador.ingresarDatosParaArchivo();

        System.out.println("\n[PUNTO 1] Carga de datos finalizada con éxito.");
        System.out.println("\n[PUNTO 2] Procesando datos del vector...");
        procesador.procesarDatosPunto2(vectorNumeros);

        // Esta línea ahora funcionará porque importamos Configuracion
        System.out.println("[PUNTO 2] Procesamiento finalizado. Revisa '" + Configuracion.ARCHIVO_RESULTADOS + "' y '" + Configuracion.ARCHIVO_ERRORES + "'.");

        System.out.println("\n[PUNTO 3] Procesando datos del vector y del archivo...");
        procesador.procesarDatosPunto3(vectorNumeros);
        System.out.println("[PUNTO 3] Procesamiento finalizado. Los archivos han sido actualizados.");

        System.out.println("\n--- Programa finalizado ;)) ---");
    }
}
