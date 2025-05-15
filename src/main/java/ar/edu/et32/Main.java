package ar.edu.et32;
import ar.edu.et32.solucionGuiaEjercicios.SolucionGuiaEjercicios;
import ar.edu.et32.utils.InputUtils;

public class Main {
    public static void main(String[] args) {
        SolucionGuiaEjercicios solucion = new SolucionGuiaEjercicios();
        int opcion;

        do {
            solucion.mostrarMenu();
            opcion = solucion.leerOpcionMenu();

            switch (opcion) {
                case 1: solucion.resolverEjercicio1(); break;
                case 2: solucion.resolverEjercicio2(); break;
                case 3: solucion.resolverEjercicio3(); break;
                case 4: solucion.resolverEjercicio4(); break;
                case 5: solucion.resolverEjercicio5(); break;
                case 6: solucion.resolverEjercicio6(); break;
                case 7: solucion.resolverEjercicio7(); break;
                case 8: solucion.resolverEjercicio8(); break;
                case 9: solucion.resolverEjercicio9(); break;
                case 10: solucion.resolverEjercicio10(); break;
                case 11: solucion.resolverEjercicio11(); break;
                case 12: solucion.resolverEjercicio12(); break;
                case 13: solucion.resolverEjercicio13(); break;
                case 14: solucion.resolverEjercicio14(); break;
                case 15: solucion.resolverEjercicio15(); break;
                case 16:
                    System.out.println("Saliendo del programa...");
                    InputUtils.cerrarBufferedReader();
                    break;

            }

            if (opcion >= 1 && opcion <= 15) {
                solucion.presioneEnterParaContinuar();
            }

        } while (opcion != 16);
    }
}