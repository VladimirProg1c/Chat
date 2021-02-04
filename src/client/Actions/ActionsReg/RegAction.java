package client.Actions.ActionsReg;

import client.ClientReg;
import dao.ConnectionBuilder;
import dao.entity.User;
import server.Server;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class RegAction implements ActionListener {

    public ClientReg clientReg;
    public String textFieldUserName;
    public String textFieldUserPass;

    private Socket clientSocket;
    private Scanner inMessage;
    private PrintWriter outMessage;
    private static String SET_USER ;


    public RegAction(ClientReg clientReg, Socket clientSocket, Scanner inMessage, PrintWriter outMessage){

        this.clientReg = clientReg;
        this.clientSocket = clientSocket;
        this.inMessage = inMessage;
        this.outMessage = outMessage;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        clientReg.serverHandler.setClientReg(clientReg);

        SET_USER = "INSERT INTO db_user ( user_name, user_pass) VALUES('" +clientReg.getTextFieldUserName()+ "', '" +clientReg.gettextFieldUserPass()+"')";

        outMessage.println("#reg#" + SET_USER);
        outMessage.flush();

/*                try {
                    Thread.sleep(5000);



                    while (true) {
                        System.out.println("ааа");

                            if (inMessage.hasNext()) {
                            System.out.println("hasNext1");
                            String clientMessage = inMessage.nextLine();
                            System.out.println("nextLine");

                            if (clientMessage.equalsIgnoreCase("#userReg+#")) {
                                JOptionPane.showMessageDialog(clientReg, "Создан пользователь");
                                System.out.println("Создан пользователь");
                                clientReg.dispose();
                                break;
                            }
                            if (clientMessage.equalsIgnoreCase("#userReg-#")) {
                                JOptionPane.showMessageDialog(clientReg, "Ой");
                                System.out.println("Ой");
                                break;
                            }
                            Thread.sleep(1000);

                        }

                    }
                } catch (Exception e1) {
                    System.out.println(e1.getStackTrace());
                }
                finally {
                    System.out.println("finally");
                }*/
    }
}
