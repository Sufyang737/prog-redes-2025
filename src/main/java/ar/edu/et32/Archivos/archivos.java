package ar.edu.et32.Archivos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class archivos {

    PrintStream ps;
    File file;

    /**
     * Constructor
     */
    // throw delega el trycatch a quien está llamando el método
    public archivos(String ruta) { // Corregido: nombre del constructor debe coincidir con la clase
        ps = new PrintStream(System.out);
        // "c:\\user\\Redes-04\\omg.txt"
        file = new File(ruta);// crea un nuevo archivo

        /*
         * file.createNewFile(); file.delete(); file.deleteOnExit(); //borra el archivo
         * una vez terminado file.exists();//para ver si el archivo está o no está
         * file.getAbsoluteFile();//te devuelve el archivo directamente file.getName();
         * file.getPath(); //devuelve la ruta de origen file.getParent();//devuelve el
         * directorio file.getTotalSpace();//devuelve la cantidad de bytes que pesa un
         * archivo file.isDirectory();//para saber si es una carpeta file.isHidden();
         * file.isFile();// file.list();//devuelve un listado de strings
         * file.listFiles();//devuelve file.mkdir();//para crear carpetas
         * file.renameTo(new File("waa.txt"));//cambia el nombre
         */
        //this.rutaFiles(file);// llama al método
        //this.crearFileConPrintStreamEasy(file);
    }

    /**
     * JavaDoc ESTE TEXTO NO TIENE NINGUNA ETIQUETA DE IDENTIFICACION. ESTO NO VA A
     * APARECER :( También se puede agregar referencias a class o métodos o
     * atributos con la instrucción: { @ por ejemplo: {@code <html></html>} o usar
     * {@link String}
     *
     * @return Este método devuelve el archivo.
     * @see FlujoDeDatos.File.
     * @since v1.0
     */
    public File getFiles() {
        return this.file;
    }

    // con esto se puede empezar a escribir en el archivo
    public void crearFileConPrintStreamEasy(File f) {
        FileOutputStream fos = null;
        PrintStream fs = null;
        try {
            fos = new FileOutputStream(f);
            fs = new PrintStream(fos);
            fs.print("Una linea");
            fs.println("Nueva linea");
            fs.write('d');// escribe
            fs.append(("HAIII"));// es como un print, pero escribe en la posición del cursor
            fs.flush();// limpia todo lo que hay en el canal
        } catch (FileNotFoundException e) {
            Logger.getLogger(archivos.class.getName()).log(Level.WARNING, null, e); // Corregido: nombre de clase
        } finally { // se ejecuta independientemente si hubo o no un error
            try {
                if (fs != null)
                    fs.close();
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            } // end try/catch
        } // end try/catch/finally
    }// end crearFileConPrintStreamEasy

    /**
     *
     * @param f archivo donde escribir
     */
    public void crearFileConPrinter(File f) {
        FileWriter fw = null; // usa buffered directo y además crea canales de comunicación
        PrintWriter pw = null; // es el escritor

        try {
            if (!f.exists()) {
                try {
                    f.createNewFile();
                } catch (IOException e) {
                    Logger.getLogger(archivos.class.getName()).log(Level.WARNING, null, e); // Corregido: nombre de clase
                }
            }

            fw = new FileWriter(f);
            pw = new PrintWriter(fw);

            pw.print("Una linea");
            pw.println("Nueva linea");
            pw.write('d');// escribe
            pw.append(("HAIII"));// es como un print, pero escribe en la posición del cursor
            pw.flush();// limpia todo lo que hay en el canal
        } catch (FileNotFoundException e) {
            Logger.getLogger(archivos.class.getName()).log(Level.WARNING, null, e); // Corregido: nombre de clase
        } catch (IOException e) {
            Logger.getLogger(archivos.class.getName()).log(Level.WARNING, null, e); // Corregido: nombre de clase
        } finally {
            try {
                if (pw != null)
                    pw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                Logger.getLogger(archivos.class.getName()).log(Level.SEVERE, null, ex); // Corregido: nombre de clase
            }
        }//finally
    }

    /**
     * @param f archivo donde escribir
     * @param texto texto a escribir
     */
    public void crearFileConBuffer(File f, String texto) {
        FileWriter fw = null;
        BufferedWriter bw = null;//escritor

        try {
            fw = new FileWriter(f, false); // true = append
            bw = new BufferedWriter(fw);

            //bw.append("ss");
            //bw.write('s');
            bw.append(texto);
            bw.newLine();
            bw.flush(); //opcional Buffered
        } catch (IOException e) {
            Logger.getLogger(archivos.class.getName()).log(Level.WARNING, null, e); // Corregido: nombre de clase
        } finally {
            try {
                if (bw != null) // Corregido: cerrar BufferedWriter primero
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException e) {
                Logger.getLogger(archivos.class.getName()).log(Level.WARNING, null, e); // Corregido: nombre de clase
            }
        }
    }

    /**
     * Descripción
     *
     * @param f un archivo al leer
     * @return Todo el texto leído.
     */
    public String leerFileConBuffer(File f) { // Corregido: nombre del método en camelCase
        FileReader fr = null;
        BufferedReader br = null;

        try {
            fr = new FileReader(f);
            br = new BufferedReader(fr);

            String line = "";
            StringBuilder texto = new StringBuilder(); // Corregido: usar StringBuilder
            while ((line = br.readLine()) != null) {
                texto.append(line).append("\n"); // Corregido: usar append correctamente
            }

            return texto.toString();
        } catch (FileNotFoundException e) {
            Logger.getLogger(archivos.class.getName()).log(Level.WARNING, null, e); // Corregido: nombre de clase
        } catch (IOException e) {
            Logger.getLogger(archivos.class.getName()).log(Level.WARNING, null, e); // Corregido: nombre de clase
        } finally {
            try {
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
            } catch (IOException e) {
                Logger.getLogger(archivos.class.getName()).log(Level.WARNING, null, e); // Corregido: nombre de clase
            }
        }

        return null;
    }

    public String leerFileCaracterCaracter(File f) {
        FileReader fr = null;

        try {
            fr = new FileReader(f);

            int caracter, EOF = -1;
            StringBuilder texto = new StringBuilder(); // Corregido: usar StringBuilder
            while ((caracter = fr.read()) != EOF) {

                if (caracter == '\n') {
                    texto.append("\n"); // Corregido: usar append correctamente
                } else {
                    texto.append((char) caracter); // Corregido: castear a char directamente
                }
            }
            return texto.toString();
        } catch (IOException e) {
            Logger.getLogger(archivos.class.getName()).log(Level.WARNING, null, e); // Corregido: nombre de clase
        } finally {
            try {
                if (fr != null)
                    fr.close();
            } catch (IOException e) {
                Logger.getLogger(archivos.class.getName()).log(Level.WARNING, null, e); // Corregido: nombre de clase
            }
        }
        return null;
    }
}