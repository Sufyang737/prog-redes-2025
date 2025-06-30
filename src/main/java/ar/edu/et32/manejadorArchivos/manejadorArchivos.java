package ar.edu.et32.manejadorArchivos;

import ar.edu.et32.producto.Producto;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase para manejar la lectura y escritura del archivo de inventario.
 * El nombre de la clase, "ManejadorArchivos", debe coincidir con el nombre del archivo.
 */
public class manejadorArchivos {
    private static final String NOMBRE_ARCHIVO = "Inventario.dat";

    public void agregarProducto(Producto producto) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO, true))) {
            writer.write(producto.toCSVString());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    public List<Producto> leerTodosLosProductos() {
        List<Producto> productos = new ArrayList<>();
        File archivo = new File(NOMBRE_ARCHIVO);
        if (!archivo.exists()) {
            return productos;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos.length == 4) {
                    productos.add(new Producto(datos[0], Float.parseFloat(datos[1]), Float.parseFloat(datos[2]), Integer.parseInt(datos[3])));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
        return productos;
    }

    public void reescribirArchivo(List<Producto> productos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO, false))) {
            for (Producto producto : productos) {
                writer.write(producto.toCSVString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al reescribir el archivo: " + e.getMessage());
        }
    }
}
