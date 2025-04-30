package ar.edu.et32.Archivos;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class archivos {
    File file;
    PrintStream ps;

    public archivos() {
        ps = new PrintStream(System.out);
        file = new File("archivo.txt");

        // Métodos comunes para trabajar con archivos (comentados)
        /*
        file.createNewFile();
        file.delete();
        file.deleteOnExit();
        file.exists();
        file.mkdir();
        file.getAbsoluteFile();
        file.getName();
        file.getPath();
        file.getParent();
        file.getTotalSpace();
        file.isFile();
        file.isDirectory();
        file.list();
        file.listFiles();
        file.length();
        file.renameTo(new File("nuevoNombre.txt"));
        */
    }

    public void crearFileConPrintStramEasy(File f) {
        FileOutputStream fos = null;
        PrintStream fs = null;
        try {
            fos = new FileOutputStream(f);
            fs = new PrintStream(fos);
            fs.println("Hola Mundo");
            fs.append("HOLAAA");
            fs.write("Hola mundo desde PrintStream".getBytes());  // Corrección aquí
            fs.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Logger.getLogger(archivos.class.getName()).log(Level.WARNING,null,e);
        } finally {
            if (fs != null) {
                fs.close();
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
