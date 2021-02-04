package client.Actions.ActionsStart;


import client.ChatForm;
import client.ClientReg;
import client.ClientStart;
import config.SocketConfig;
import dao.entity.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class StartCheckinAction implements ActionListener {

    public ClientStart clientStart;

    private Socket clientSocket;
    private Scanner inMessage;
    private PrintWriter outMessage;

    private static String SELECT_USER;


    public StartCheckinAction(ClientStart clientStart, Socket clientSocket, Scanner inMessage, PrintWriter outMessage) {

        this.clientStart = clientStart;
        this.clientSocket = clientSocket;
        this.inMessage = inMessage;
        this.outMessage = outMessage;

    }

    @Override
    public void actionPerformed(ActionEvent e) {


        SELECT_USER = "select * FROM db_user WHERE  user_name = '" + clientStart.getTextFieldUserName() +"' and user_pass = '"+ clientStart.gettextFieldUserPass()+"'";

        outMessage.println("#che#" + SELECT_USER);
        outMessage.flush();
/*        try {
            Thread.sleep(1000);

            while (true) {

                if (inMessage.hasNext()) {

                    String clientMessage = inMessage.nextLine();
                    if (clientMessage.startsWith("#userCheck+#")) {

                        Long idU = Long.valueOf(clientMessage.substring(12));
                        User user = new User(clientStart.getTextFieldUserName(), idU);

                        ChatForm chatForm = new ChatForm(user, clientSocket, inMessage, outMessage);//clientSocket, inMessage, outMessage
                        clientStart.dispose();

                        break;
                    }
                    if (clientMessage.equalsIgnoreCase("#userCheck-#")) {
                        JOptionPane.showMessageDialog(clientStart, "Не найден пользователь");
                        System.out.println("Ой");
                        break;
                    }
                    Thread.sleep(1000);

                }
            }
        } catch (Exception e1) {
            System.out.println(e1.getStackTrace());
        } finally {
            System.out.println("finally");
        }*/
    }
}
