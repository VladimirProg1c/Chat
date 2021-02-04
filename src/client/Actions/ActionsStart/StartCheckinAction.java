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
    private PrintWriter outMessage;
    private static String SELECT_USER;

    public StartCheckinAction(ClientStart clientStart, PrintWriter outMessage) {

        this.clientStart = clientStart;
        this.outMessage = outMessage;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        SELECT_USER = "select * FROM db_user WHERE  user_name = '" + clientStart.getTextFieldUserName() +"' and user_pass = '"+ clientStart.gettextFieldUserPass()+"'";

        outMessage.println("#che#" + SELECT_USER);
        outMessage.flush();
    }
}
