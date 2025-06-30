package ar.edu.et32.Chat;
import Utils.enumType;

import java.io.*;
import java.net.UnknownHostException;

public class Cliente extends connection {
    public Cliente(enumType type) throws UnknownHostException, IOException{
        super(type);
    }
    public  void clientOn() throws IOException {
        InputStreamReader isrClient = new InputStreamReader(sockC.getInputStream());
        dosClient = new DataOutputStream(sockC.getOutputStream());
        BufferedReader br = new BufferedReader(isrClient);

        dosClient.writeUTF("Hola me llamo Consorti");
        dosClient.flush();
        while((msg = br.readLine()) != null) {
            ps.printf(Utils.Colors.ANSI_YELLOW + "\tMensaje: %s\n" + Utils.Colors.ANSI_RESET, msg);
            dosClient.writeUTF("ok");
            dosClient.flush();
        }
    }
}
