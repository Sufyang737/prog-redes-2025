package ar.edu.et32.Chat;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class mainCliente {
    public static void main(String[] args) {
        try {
            Cliente cli = new Cliente(Utils.enumType.CLIENT);
            cli.clientOn();
        } catch (IOException ex) {
            Logger.getLogger(mainCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
