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
    private PrintWriter outMessage;
    private static String SET_USER ;


    public RegAction(ClientReg clientReg, PrintWriter outMessage){

        this.clientReg = clientReg;
        this.outMessage = outMessage;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        clientReg.serverHandler.setClientReg(clientReg);

        SET_USER = "INSERT INTO db_user ( user_name, user_pass) VALUES('" +clientReg.getTextFieldUserName()+ "', '" +clientReg.gettextFieldUserPass()+"')";

        outMessage.println("#reg#" + SET_USER);
        outMessage.flush();
    }
}
