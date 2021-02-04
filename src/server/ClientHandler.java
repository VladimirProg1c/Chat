package server;

import dao.ConnectionBuilder;
import dao.entity.Chat;
import dao.entity.Message;
import dao.entity.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientHandler implements Runnable {

    private Server server;
    private PrintWriter outMessage;
    private Scanner inMessage;
    private User currentUser;

    public ClientHandler(Socket socket, Server server) {
        try {
            this.server = server;
            this.outMessage = new PrintWriter(socket.getOutputStream());
            this.inMessage = new Scanner(socket.getInputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {

            while (true) {

                if (inMessage.hasNext()) {

                    String clientMessage = inMessage.nextLine();

                    if (clientMessage.equalsIgnoreCase("##session##end##")) {
                        break;
                    }
                    if (clientMessage.startsWith("#reg#")) {

                        String answer = regActionServ(clientMessage);
                        outMessage.println(answer);
                        outMessage.flush();
                    }
                    if (clientMessage.startsWith("#che#")) {

                        String answer = checkActionServ(clientMessage);
                        outMessage.println(answer);
                        outMessage.flush();
                    }
                    if (clientMessage.startsWith("#GUS#")) {

                        ArrayList<String> answer = getUserServ(clientMessage);
                        for (int i = 0; i < answer.size(); i++) {
                            outMessage.println("#GUS#" +answer.get(i)+ System.lineSeparator());
                            outMessage.flush();
                        }
                    }
                    if (clientMessage.startsWith("#GCH#")) {

                        ArrayList<String> answer = getChatServ(clientMessage);
                        for (int i = 0; i < answer.size(); i++) {
                            outMessage.println("#GCH#" +answer.get(i)+ System.lineSeparator());
                            outMessage.flush();
                        }
                    }
                    if (clientMessage.startsWith("#GUC#")) {

                        ArrayList<String> answer = getUserServ(clientMessage);
                        for (int i = 0; i < answer.size(); i++) {
                            outMessage.println("#GUC#" +answer.get(i)+ System.lineSeparator());
                            outMessage.flush();
                        }
                    }
                    if (clientMessage.startsWith("#ch+#")) {
                        String answer = addChat(clientMessage);

                        outMessage.println(answer + System.lineSeparator());
                        outMessage.flush();
                    }
                    if (clientMessage.startsWith("#chu#")) {
                        String answer = addChatUser(clientMessage);
                    }
                    if (clientMessage.startsWith("#GME#")) {
                        ArrayList<String> answer = getMessage(clientMessage);
                        for (int i = 0; i < answer.size(); i++) {
                            outMessage.println("#GME#" +answer.get(i)+ System.lineSeparator());
                            outMessage.flush();
                        }
                    }

                    if (clientMessage.startsWith("#mes#")) {
                        Message currentMessage = addMessage(clientMessage);
                        server.sendMessageToAllClients(currentMessage);

                    }
                }
                Thread.sleep(100);
            }
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        finally {
            this.close();
        }
    }

    public void sendMsg(Message msg) {
        try {
            outMessage.println("#msg#"+ msg.getChat().getName() + "#" + msg.getAuthor().getUserName() + ": " +msg.getText() + System.lineSeparator());
            outMessage.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void close() {
        server.removeClient(this);
    }

    public String regActionServ(String clientMessage){

        try (Connection con = ConnectionBuilder.getConnection();
             Statement stmt = con.createStatement();
        ) {
            String SET_USER = clientMessage.substring(5);
            stmt.executeUpdate(SET_USER);

            System.out.println("Норм");
            String stReturn = "#userReg+#"+ System.lineSeparator();
            return stReturn;

        } catch (SQLException throwable) {
            throwable.printStackTrace();

            System.out.println("Ой");
            String stReturn = "#userReg-#"+ System.lineSeparator();
            return stReturn;
        }
    }

    public String checkActionServ(String clientMessage){
        String stReturn;
        long idU = 0;
        try (Connection con = ConnectionBuilder.getConnection();
             Statement stmt = con.createStatement();
             ) {

            String SELECT_USER = clientMessage.substring(5);
            ResultSet rs = stmt.executeQuery(SELECT_USER);
            while (rs.next()) {
                idU = rs.getLong("user_id");
            }
            if (idU!=0) {
                System.out.println("Норм");
                stReturn = "#userCheck+#" + idU + System.lineSeparator();
                currentUser = new User();
                currentUser.setId(idU);
            } else {
                stReturn = "#userCheck-#" + System.lineSeparator();
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();

            System.out.println("Ой");
            stReturn = "#userCheck-#"+ System.lineSeparator();
        }

        return stReturn;
    }

    public ArrayList<String> getUserServ(String clientMessage){

        String str = null;
        ArrayList<String> stReturn = new ArrayList<String>();

        try (Connection con = ConnectionBuilder.getConnection();
             Statement stmt = con.createStatement();
        ) {
            String GET_USER = clientMessage.substring(5);
            ResultSet rs = stmt.executeQuery(GET_USER);
            while (rs.next()) {
                str = rs.getString("user_name").toString();
                if (!str.isEmpty()) {
                    stReturn.add(str);
                }
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return stReturn;
    }

    public ArrayList<String> getChatServ(String clientMessage){

        String str = null;
        ArrayList<String> stReturn = new ArrayList<String>();

        try (Connection con = ConnectionBuilder.getConnection();
             Statement stmt = con.createStatement();
        ) {
            String GET_USER = clientMessage.substring(5);
            ResultSet rs = stmt.executeQuery(GET_USER);
            while (rs.next()) {
                str = rs.getString("chat_name").toString();
                if (!str.isEmpty()) {
                    stReturn.add(str);
                }
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return stReturn;
    }

    public String addChat(String clientMessage) {

        String answer;
        long str = 0;
        String clientMessage1 = clientMessage.substring(5);

        String messageChat = clientMessage1.substring(0, clientMessage1.indexOf("#"));
        String messageUser = clientMessage1.substring(clientMessage1.indexOf("#") + 1);
        String SET_CHAT = "INSERT INTO db_chat ( chat_name, created_at) VALUES('" + messageChat + "'," + "current_timestamp" + ")";
        String SELECT = "select * FROM db_chat WHERE  chat_name = '" + messageChat + "'";
        ;
        long idChat = 0;

        try (Connection con = ConnectionBuilder.getConnection();
             Statement stmt = con.createStatement();
        ) {
            stmt.executeUpdate(SET_CHAT);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        try (Connection con = ConnectionBuilder.getConnection();
             Statement stmt = con.createStatement();
        ) {
            ResultSet rs = stmt.executeQuery(SELECT);
            while (rs.next()) {
                str = rs.getLong("chat_id");
                if (str != 0) {
                    idChat = str;
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        try (Connection con = ConnectionBuilder.getConnection();
             Statement stmt = con.createStatement();
        ) {
            String SET_CHAT_USER = "INSERT INTO db_chat_user ( chat_id, user_id) VALUES('" + idChat + "','" + messageUser + "')";
            stmt.executeUpdate(SET_CHAT_USER);
            answer = "#ch+#"+messageChat;
        } catch (SQLException throwable) {
            answer = "#ch-#";
            throwable.printStackTrace();
        }

        return answer;
    }

    public String addChatUser(String clientMessage) {

        long str = 0;
        String clientMessage1 = clientMessage.substring(5);

        String messageChat = clientMessage1.substring(0, clientMessage1.indexOf("#"));
        String messageUser = clientMessage1.substring(clientMessage1.indexOf("#") + 1);

        String SELECT_CHAT = "select * FROM db_chat WHERE  chat_name = '" + messageChat + "'";
        String SELECT_USER = "select * FROM db_user WHERE  user_name = '" + messageUser + "'";
        ;
        long idChat = 0;
        long idUser= 0;

        try (Connection con = ConnectionBuilder.getConnection();
             Statement stmt = con.createStatement();
             //Statement stmt = con.prepareStatement();

        ) {
            ResultSet rs = stmt.executeQuery(SELECT_CHAT);
            while (rs.next()) {
                str = rs.getLong("chat_id");
                if (str != 0) {
                    idChat = str;
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        try (Connection con = ConnectionBuilder.getConnection();
             Statement stmt = con.createStatement();
        ) {
            ResultSet rs = stmt.executeQuery(SELECT_USER);
            while (rs.next()) {
                str = rs.getLong("user_id");
                if (str != 0) {
                    idUser = str;
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        try (Connection con = ConnectionBuilder.getConnection();
             Statement stmt = con.createStatement();
        ) {
            String SET_CHAT_USER = "INSERT INTO db_chat_user ( chat_id, user_id) VALUES('" + idChat + "','" + idUser + "')";
            stmt.executeUpdate(SET_CHAT_USER);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }


        return "";
    }

    public Message addMessage(String clientMessage) {
        //"#mes#"+ strMessage + "#" +chatName +"#"+userName+"#"+userId
        long lng = 0;
        String str = null;
        String clientMessage1 = clientMessage.substring(5);

        String messageText = clientMessage1.substring(0, clientMessage1.indexOf("#"));
        String messageBuf = clientMessage1.substring(clientMessage1.indexOf("#") + 1);
        String messageChat = messageBuf.substring(0, messageBuf.indexOf("#"));
        String messageBuf1 = messageBuf.substring(messageBuf.indexOf("#") + 1);



        String messageUserName = messageBuf1.substring(0, messageBuf1.indexOf("#"));//long
        String messageUserId = messageBuf1.substring(messageBuf1.indexOf("#") + 1);//long

        String SELECT_CHAT = "select * FROM db_chat WHERE  chat_name = '" + messageChat + "'";

        long idChat = 0;



        try (Connection con = ConnectionBuilder.getConnection();
             Statement stmt = con.createStatement();
        ) {
            ResultSet rs = stmt.executeQuery(SELECT_CHAT);
            while (rs.next()) {
                lng = rs.getLong("chat_id");
                str = rs.getString("chat_name");
                if (lng != 0) {
                    idChat = lng;
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        String SET_MESSAGE = "INSERT INTO db_massage ( chat_id, user_id, massage_text, created_at) VALUES('" + idChat + "'," + "'" + messageUserId + "'," + "'"  + messageText + "'," + "current_timestamp" + ")";

        try (Connection con = ConnectionBuilder.getConnection();
             Statement stmt = con.createStatement();
        ) {
            stmt.executeUpdate(SET_MESSAGE);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        User currentUser = new User();
        currentUser.setUserName(messageUserName);
        currentUser.setId(Long.valueOf(messageUserId));

        Chat chat = new Chat();
        chat.setId(idChat);
        chat.setName(str);

        Message message = new Message();
        message.setChat(chat);
        message.setAuthor(currentUser);
        message.setText(messageText);

        return message ;
    }

    public User getCurrentUser() {

        return currentUser;
    }
    public ArrayList<String> getMessage(String clientMessage){
        String str = null;
        ArrayList<String> stReturn = new ArrayList<String>();

        try (Connection con = ConnectionBuilder.getConnection();
             Statement stmt = con.createStatement();
        ) {
            String GET_MESSAGE = clientMessage.substring(5);
            ResultSet rs = stmt.executeQuery(GET_MESSAGE);
            while (rs.next()) {
                str = rs.getString("chat_name").toString()+"#"+rs.getString("user_name").toString()+"#"+rs.getString("massage_text")+"#"+rs.getDate("created_at").toString();
                if (!str.isEmpty()) {
                    stReturn.add(str);
                }
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return stReturn;

    }

}
