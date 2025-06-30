package ar.edu.et32.gestorInventario;

import ar.edu.et32.consolaUtil.consolaUtil;
import ar.edu.et32.manejadorArchivos.manejadorArchivos;
import ar.edu.et32.producto.Producto;
import java.util.List;


public class gestorInventario {

    private static consolaUtil consola = new consolaUtil();
    private static manejadorArchivos manejadorArchivos = new manejadorArchivos();

    public static void main(String[] args) {
        while (true) {
            consola.mostrarMenu();
            String opcion = consola.leerTexto("Seleccione una opción: ");

            switch (opcion) {
                case "1":
                    agregarProducto();
                    break;
                case "2":
                    mostrarInventario();
                    break;
                case "3":
                    editarProducto();
                    break;
                case "4":
                    eliminarProducto();
                    break;
                case "5":
                    System.out.println(consolaUtil.ANSI_GREEN + "\n¡Hasta luego!" + consolaUtil.ANSI_RESET);
                    return; // Termina el programa
                default:
                    System.out.println(consolaUtil.ANSI_RED + "Opción no válida. Intente nuevamente." + consolaUtil.ANSI_RESET);
                    break;
            }
        }
    }


    private static void agregarProducto() {
        System.out.println(consolaUtil.ANSI_PURPLE + "\n--- Agregar Nuevo Producto ---" + consolaUtil.ANSI_RESET);

        String nombre = consola.leerTexto("Ingrese el nombre del producto: ");

        float precioCompra = leerNumeroFlotante("Ingrese el precio de compra: ");
        float precioVenta = leerNumeroFlotante("Ingrese el precio de venta: ");
        int stock = leerNumeroEntero("Ingrese el stock inicial: ");

        Producto nuevoProducto = new Producto(nombre, precioCompra, precioVenta, stock);
        manejadorArchivos.agregarProducto(nuevoProducto);

        System.out.println(consolaUtil.ANSI_GREEN + "\nProducto '" + nombre + "' agregado con éxito." + consolaUtil.ANSI_RESET);
    }


    private static void mostrarInventario() {
        System.out.println(consolaUtil.ANSI_PURPLE + "\n--- Inventario Actual ---" + consolaUtil.ANSI_RESET);
        List<Producto> productos = manejadorArchivos.leerTodosLosProductos();

        if (productos.isEmpty()) {
            System.out.println(consolaUtil.ANSI_YELLOW + "El inventario está vacío." + consolaUtil.ANSI_RESET);
            return;
        }

        System.out.printf(consolaUtil.ANSI_CYAN + "%-5s %-20s %-15s %-15s %-10s\n" + consolaUtil.ANSI_RESET,
                "ID", "Nombre", "P. Compra", "P. Venta", "Stock");
        System.out.println(consolaUtil.ANSI_CYAN + "------------------------------------------------------------------" + consolaUtil.ANSI_RESET);

        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
            System.out.printf("%-5d %-20s %-15.2f %-15.2f %-10d\n",
                    (i + 1), p.getNombre(), p.getPrecioCompra(), p.getPrecioVenta(), p.getStock());
        }
    }


    private static void editarProducto() {
        mostrarInventario();
        List<Producto> productos = manejadorArchivos.leerTodosLosProductos();
        if (productos.isEmpty()) return;

        int idEditar = leerNumeroEntero("\nIngrese el ID del producto que desea editar: ");
        int indice = idEditar - 1;

        if (indice >= 0 && indice < productos.size()) {
            Producto productoAEditar = productos.get(indice);
            System.out.println(consolaUtil.ANSI_PURPLE + "\nEditando producto: " + productoAEditar.getNombre() + consolaUtil.ANSI_RESET);

            String nuevoNombre = consola.leerTexto("Nuevo nombre (deje en blanco para no cambiar): ");
            float nuevoPrecioCompra = leerNumeroFlotante("Nuevo precio de compra (ingrese 0 para no cambiar): ");
            float nuevoPrecioVenta = leerNumeroFlotante("Nuevo precio de venta (ingrese 0 para no cambiar): ");
            int nuevoStock = leerNumeroEntero("Nuevo stock (ingrese -1 para no cambiar): ");

            if (!nuevoNombre.trim().isEmpty()) {
                productoAEditar.setNombre(nuevoNombre);
            }
            if (nuevoPrecioCompra > 0) {
                productoAEditar.setPrecioCompra(nuevoPrecioCompra);
            }
            if (nuevoPrecioVenta > 0) {
                productoAEditar.setPrecioVenta(nuevoPrecioVenta);
            }
            if (nuevoStock != -1) {
                productoAEditar.setStock(nuevoStock);
            }

            manejadorArchivos.reescribirArchivo(productos);
            System.out.println(consolaUtil.ANSI_GREEN + "\nProducto editado con éxito." + consolaUtil.ANSI_RESET);
        } else {
            System.out.println(consolaUtil.ANSI_RED + "ID no válido." + consolaUtil.ANSI_RESET);
        }
    }

    private static void eliminarProducto() {
        mostrarInventario();
        List<Producto> productos = manejadorArchivos.leerTodosLosProductos();
        if (productos.isEmpty()) return;

        int idEliminar = leerNumeroEntero("\nIngrese el ID del producto que desea eliminar: ");
        int indice = idEliminar - 1;

        if (indice >= 0 && indice < productos.size()) {
            productos.remove(indice);
            manejadorArchivos.reescribirArchivo(productos);
            System.out.println(consolaUtil.ANSI_GREEN + "\nProducto eliminado con éxito." + consolaUtil.ANSI_RESET);
        } else {
            System.out.println(consolaUtil.ANSI_RED + "ID no válido." + consolaUtil.ANSI_RESET);
        }
    }

    private static float leerNumeroFlotante(String mensaje) {
        while (true) {
            String input = consola.leerTexto(mensaje);
            consolaUtil.TipoNumero tipo = consola.validarNumero(input);
            if (tipo == consolaUtil.TipoNumero.FLOTANTE || tipo == consolaUtil.TipoNumero.ENTERO) {
                return Float.parseFloat(input.trim());
            } else {
                System.out.println(consolaUtil.ANSI_RED + "Error: Ingrese un número válido." + consolaUtil.ANSI_RESET);
            }
        }
    }

    private static int leerNumeroEntero(String mensaje) {
        while (true) {
            String input = consola.leerTexto(mensaje);
            if (consola.validarNumero(input) == consolaUtil.TipoNumero.ENTERO) {
                return Integer.parseInt(input.trim());
            } else {
                System.out.println(consolaUtil.ANSI_RED + "Error: Ingrese un número entero válido." + consolaUtil.ANSI_RESET);
            }
        }
    }
}
