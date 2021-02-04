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
		// порт, который будет прослушивать наш сервер
    static final int PORT = 3443;
		// список клиентов, которые будут подключаться к серверу
    private ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();

    public Server() {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
            return;
        }

				// сокет клиента, это некий поток, который будет подключаться к серверу
				// по адресу и порту
        Socket clientSocket = null;
				// серверный сокет
        ServerSocket serverSocket = null;
        try {
						// создаём серверный сокет на определенном порту
            serverSocket = new ServerSocket(PORT);
            System.out.println("Сервер запущен!");
						// запускаем бесконечный цикл
            while (true) {
								// таким образом ждём подключений от сервера
                clientSocket = serverSocket.accept();
								// создаём обработчик клиента, который подключился к серверу
								// this - это наш сервер
                ClientHandler client = new ClientHandler(clientSocket, this);
                clients.add(client);
								// каждое подключение клиента обрабатываем в новом потоке
                new Thread(client).start();
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            try {
								// закрываем подключение
                clientSocket.close();
                System.out.println("Сервер остановлен");
                serverSocket.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
		
		// отправляем сообщение всем клиентам
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

		// удаляем клиента из коллекции при выходе из чата
    public void removeClient(ClientHandler client) {
        clients.remove(client);
    }

}
