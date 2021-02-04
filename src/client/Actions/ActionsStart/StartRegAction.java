package client.Actions.ActionsStart;

import client.ClientReg;
import client.ServerHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class StartRegAction implements ActionListener {
    private Socket clientSocket;
    private Scanner inMessage;
    private PrintWriter outMessage;
    private ServerHandler serverHandler;

    public StartRegAction(Socket clientSocket, Scanner inMessage, PrintWriter outMessage, ServerHandler serverHandler) {
        this.clientSocket = clientSocket;
        this.inMessage = inMessage;
        this.outMessage = outMessage;
        this.serverHandler = serverHandler;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        ClientReg clientReg = new ClientReg(clientSocket, inMessage, outMessage,serverHandler);

    }
}
