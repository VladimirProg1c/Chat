package client;

import dao.entity.User;

import javax.swing.*;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerHandler implements Runnable {
    private Socket clientSocket;
    private Scanner inMessage;
    private PrintWriter outMessage;
    public boolean isUserReg;
    private ClientReg clientReg;
    private ClientStart clientStart;
    private ChatForm chatForm;

    public void setClientStart(ClientStart clientStart) {
        this.clientStart = clientStart;
    }

    public void setClientReg(ClientReg clientReg) {
        this.clientReg = clientReg;
    }

    public ServerHandler(Socket clientSocket, Scanner inMessage, PrintWriter outMessage) {
        this.clientSocket = clientSocket;
        this.inMessage = inMessage;
        this.outMessage = outMessage;
        isUserReg = false;
    }

    @Override
    public void run() {

        try {
            // бесконечный цикл
            while (true) {
                // если есть входящее сообщение
                if (inMessage.hasNext()) {
                    // считываем его
                    String inMes = inMessage.nextLine();

                    if (inMes.startsWith("#msg#")) {
                        String serMessage1 = inMes.substring(5);
                        String strChatName = serMessage1.substring(0, serMessage1.indexOf("#"));
                        String strTextMes = serMessage1.substring(serMessage1.indexOf("#") + 1);
                        // выводим сообщение
                        if(chatForm.getChat().getName().equals(strChatName)){

                        chatForm.jtaTextAreaMessage.append(strTextMes);
                        // добавляем строку перехода
                        chatForm.jtaTextAreaMessage.append("\n");
                        }
                    }
                    if (inMes.startsWith("#GME#")) {
                        //("chat_name")+"#"+("user_name")+"#"+("massage_text")+"#"+("created_at");

                        String strMessage = inMes.substring(5);
                        String strChatName = strMessage.substring(0, strMessage.indexOf("#"));

                        String strBuf = strMessage.substring( strMessage.indexOf("#")+1);
                        String strUserName = strBuf.substring(0, strBuf.indexOf("#"));

                        String strBuf1 = strBuf.substring( strBuf.indexOf("#")+1);
                        String strTextMes = strBuf1.substring(0, strBuf1.indexOf("#"));

                        String strCreated = strBuf1.substring( strBuf1.indexOf("#")+1);

                        // выводим сообщение
                        if(chatForm.getChat().getName().equals(strChatName)){

                            chatForm.jtaTextAreaMessage.append(strUserName + ": "+ strTextMes);
                            // добавляем строку перехода
                            chatForm.jtaTextAreaMessage.append("\n");
                        }
                    }
                    if (inMes.equalsIgnoreCase("#userReg+#")) {
                        isUserReg = true;
                        JOptionPane.showMessageDialog(clientReg, "Создан пользователь");
                        System.out.println("Создан пользователь");
                        clientReg.dispose();
                    }
                    if (inMes.equalsIgnoreCase("#userReg-#")) {
                        JOptionPane.showMessageDialog(clientReg, "Ой");
                        System.out.println("Ой");
                    }
                    if (inMes.startsWith("#userCheck+#")) {

                        Long idU = Long.valueOf(inMes.substring(12));
                        User user = new User(clientStart.getTextFieldUserName(), idU);

                        chatForm = new ChatForm(user, clientSocket, inMessage, outMessage, this);//clientSocket, inMessage, outMessage
                        clientStart.dispose();
                    }
                    if (inMes.equalsIgnoreCase("#userCheck-#")) {
                        JOptionPane.showMessageDialog(clientStart, "Не найден пользователь");
                        System.out.println("Ой");
                    }
                    if (inMes.startsWith("#GCH#")) {
                        String stGUS = inMes.substring(5);
                        chatForm.listChat.add(stGUS);
                        String[] arChat = chatForm.listChat.toArray(new String[0]);
                        chatForm.jlChat.setListData(arChat);
                    }
                    if (inMes.startsWith("#GUS#")) {
                        String stGUS = inMes.substring(5);
                        chatForm.listUser.add(stGUS);
                        String[] arUser = chatForm.listUser.toArray(new String[0]);
                        chatForm.jlUser.setListData(arUser);
                    }
                    if (inMes.startsWith("#ch+#")) {
                        String stChat = inMes.substring(5);
                        chatForm.listChat.add(stChat);
                        String[] arChat = chatForm.listChat.toArray(new String[0]);
                        chatForm.jlChat.setListData(arChat);
                    }
                    if (inMes.startsWith("#GUC#")) {
                        String stChat = inMes.substring(5);
                        chatForm.listUserInChat.add(stChat);
                        String[] arChat = chatForm.listUserInChat.toArray(new String[0]);
                        chatForm.jlUserInChat.setListData(arChat);
                    }
                } else {
                    System.out.println("test");
                }
            }
        } catch (Exception e) {
        }


    }
}
