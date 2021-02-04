package client.Actions.ActionsChat;

import client.ChatForm;
import config.SocketConfig;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class AddChatAction implements ActionListener {

    ChatForm chatForm;
    private Socket clientSocket;
    private Scanner inMessage;
    private PrintWriter outMessage;

    public AddChatAction(ChatForm chatForm, PrintWriter outMessage, Scanner inMessage) {
        this.chatForm = chatForm;
        this.outMessage = outMessage;
        this.inMessage = inMessage;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String answer = JOptionPane.showInputDialog(chatForm,
                new String[]{"Введите имя чата"},
                "Создание чата",
                JOptionPane.WARNING_MESSAGE);
        if (!answer.isEmpty()) {

            Long userId = chatForm.getUser().getId();

            //String[] arChat = chatForm.listChat.toArray(new String[]{answer});
            //chatForm.jlChat.setListData(arChat);
            //jlChatSP = new JScrollPane(jlChat);

            //String SET_CHAT = "#ch+##userName#INSERT INTO db_chat ( chat_name, created_at) VALUES('" +answer+ "'," + "current_timestamp"+")";
            //String SET_CHAT_USER = "INSERT INTO db_chat ( chat_id, user_id) VALUES('" +answer+ "'," + "current_timestamp"+")";
            String SET_CHAT = "#ch+#" + answer + "#" + userId;
            //answer
            outMessage.println(SET_CHAT);
            outMessage.flush();
          /*  try {
                Thread.sleep(5000);

                while (true) {

                    if (inMessage.hasNext()) {

                        String clientMessage = inMessage.nextLine();

                        if (clientMessage.startsWith("#ch+#")) {
                            //String stGUS = clientMessage.substring(5);
                            chatForm.listChat.add(answer);
                        }
                        if (clientMessage.startsWith("#ch-#")) {

                            break;

                        }
                    } else {
                        break;
                    }
                }
            } catch (Exception e1) {
                System.out.println(e1.getStackTrace());
            }*/
            //chatForm.updateChat();
            //chatForm.repaint();

        }

    }
    //открыть диалог для ввода имени чата
    //JOptionPane.showMessageDialog(clientReg, "Создан пользователь");
    //создатьЧатНаСервере

}
