package config;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketConfig {

    public static final String SERVER_HOST = "localhost";
    public static final int SERVER_PORT = 3443;
    private Socket clientSocket;
    private Scanner inMessage;
    private PrintWriter outMessage;

    public SocketConfig(){
        try {
            clientSocket = new Socket(SERVER_HOST, SERVER_PORT);
            inMessage = new Scanner(clientSocket.getInputStream());
            outMessage = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Scanner getInMessage() {
        return inMessage;
    }

    public PrintWriter getOutMessage() {
        return outMessage;
    }

    public Socket getSocket() {
        return clientSocket;
    }
}
