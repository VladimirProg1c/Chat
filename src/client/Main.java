package client;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        //ClientWindow clientWindow = new ClientWindow();

        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run() {

                //ClientFormOne clientFormOne = new ClientFormOne();

                ClientStart clientStart = new ClientStart();

                //ClientReg clientReg = new ClientReg();
            }
        });

    }
}
