package server;

import dao.ConnectionBuilder;
import dao.entity.Message;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Server {

    static final int PORT = 3443;
    private ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();

    public Server() {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
            return;
        }

        Socket clientSocket = null;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Сервер запущен!");
            while (true) {
                clientSocket = serverSocket.accept();
                ClientHandler client = new ClientHandler(clientSocket, this);
                clients.add(client);
                new Thread(client).start();
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                clientSocket.close();
                System.out.println("Сервер остановлен");
                serverSocket.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void sendMessageToAllClients(Message msg) {

        String SELECT_CHAT_User = "select * FROM db_chat_user WHERE  chat_id = '" + msg.getChat().getId() + "'";

        long idUser = 0;
        ArrayList<Long> arLong = new ArrayList<>();

        try (Connection con = ConnectionBuilder.getConnection();
             Statement stmt = con.createStatement();
        ) {
            ResultSet rs = stmt.executeQuery(SELECT_CHAT_User);
            while (rs.next()) {
                idUser = rs.getLong("user_id");
                if (idUser != 0) {
                    arLong.add(idUser);
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        for (ClientHandler o : clients) {
            if (arLong.contains(o.getCurrentUser().getId()))
            {
                o.sendMsg(msg);
            }
        }
    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
    }

}
