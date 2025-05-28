package ar.edu.et32;
import ar.edu.et32.Archivos.archivos;

public class Main { // Corregido: nombre de clase con mayúscula inicial

    public static void main(String[] args) {
        archivos arch = new archivos("consortil.txt"); // Corregido: sintaxis de instanciación

        arch.crearFileConBuffer(arch.getFiles(), "hola mundo");
    }
}