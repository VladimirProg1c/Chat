package client;

import client.Actions.ActionsChat.AddChatAction;
import client.Actions.ActionsChat.AddUserToChat;
import client.Actions.ActionsChat.ConnectChatAction;
import client.Actions.ActionsChat.SendMessage;
import dao.entity.Chat;
import dao.entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import static javax.swing.GroupLayout.Alignment.BASELINE;

public class ChatForm extends JFrame {

    private static String GET_USERS = "select user_name FROM db_user where user_name IS NOT NULL";

    private User user;
    private Chat chat;
    private PrintWriter outMessage;
    private Scanner inMessage;

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public Chat getChat() {
        return chat;
    }

    private Socket clientSocket;
    private ServerHandler serverHandler;

    public ArrayList<String> listChat = new ArrayList<String>();
    public ArrayList<String> listUserInChat = new ArrayList<String>();
    ArrayList<String> listUser = new ArrayList<String>();
    public JTextArea jtaTextAreaMessage;

    public JList<String> jlChat;
    public JList<String> jlUser;
    public JList<String> jlUserInChat;
    private JTextField jtfMessage;
    public JLabel lCurrentChat;


    public ChatForm(User user, Socket clientSocket, Scanner inMessage, PrintWriter outMessage, ServerHandler serverHandler) { //Socket clientSocket, Scanner inMessage, PrintWriter outMessage

        this.user = user;
        this.clientSocket = clientSocket;
        this.inMessage = inMessage;
        this.outMessage = outMessage;
        this.serverHandler = serverHandler;

        setBounds(600, 300, 600, 500);

        JLabel lChat = new JLabel("Название чата:");
        JLabel lChatUser = new JLabel("Пользователи чата:");
        JLabel lUser = new JLabel("Все пользователи:");
        lCurrentChat = new JLabel("                                                ");

        JButton btnPlusChat = new JButton("+ Chat");
        JButton btnSend = new JButton("Send");
        JButton btnPlusUser = new JButton("+ User");
        JButton btnSelectChat = new JButton("Подключиться к чату");
        btnPlusUser.addActionListener(new AddUserToChat(this, outMessage, inMessage));
        AddChatAction addChatAction = new AddChatAction(this, outMessage);
        btnPlusChat.addActionListener(addChatAction);

        btnSelectChat.addActionListener(new ConnectChatAction(this,outMessage));

        JTextArea jtaMessage = new JTextArea();
        jtaMessage.setLineWrap(true);  //авто перенос невлезших строк

        jlChat = new JList<String>();
        jlUser = new JList<String>();

        jlUserInChat = new JList<String>();

        setTitle("User:" + user.getUserName() + "; id:" + user.getId());

        updateUser();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        updateChat();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JScrollPane jlChatSP = new JScrollPane(jlChat);
        JScrollPane jlUserSP = new JScrollPane(jlUser);

        JScrollPane jlUserChatSP = new JScrollPane(jlUserInChat);
        jlUserChatSP.setMaximumSize(new Dimension(120, Short.MAX_VALUE));
        jlUserSP.setMaximumSize(new Dimension(120, Short.MAX_VALUE));

        jtaTextAreaMessage = new JTextArea();
        jtaTextAreaMessage.setEditable(false);
        jtaTextAreaMessage.setLineWrap(true);
        JScrollPane jspText = new JScrollPane(jtaTextAreaMessage);

        jtfMessage = new JTextField("Введите ваше сообщение: ");
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jlChatSP)
                        .addComponent(btnPlusChat)
                        .addComponent(lChat)
                )
                .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(lCurrentChat)
                                .addComponent(btnSelectChat)
                        )
                        .addComponent(jspText)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jtfMessage)
                                .addComponent(btnSend)
                        )
                ).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(jlUserChatSP)
                        .addComponent(btnPlusUser)
                        .addComponent(lChatUser)
                ).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(jlUserSP)
                        .addComponent(lUser)
                )
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(lChat)
                        .addComponent(lCurrentChat)
                        .addComponent(btnSelectChat)
                        .addComponent(lChatUser)
                        .addComponent(lUser))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(jlChatSP)
                        .addComponent(jspText)
                        .addComponent(jlUserChatSP)
                        .addComponent(jlUserSP))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(btnPlusChat)
                        .addComponent(jtfMessage)
                        .addComponent(btnSend)
                        .addComponent(btnPlusUser)
                )
        );

        btnSend.addActionListener(new SendMessage(this, inMessage, outMessage));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    // отправляем служебное сообщение, которое является признаком того, что клиент вышел из чата
                    outMessage.println("##session##end##");
                    outMessage.flush();
                    outMessage.close();
                    inMessage.close();
                    clientSocket.close();
                } catch (IOException exc) {

                }
            }
        });
        setVisible(true);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public JList<String> getJlChat() {
        return jlChat;
    }

    public JList<String> getJlUser() {
        return jlUser;
    }

    public JTextField getJtfMessage() {
        return jtfMessage;
    }

    public void updateChat() {
        String GET_CHATS = "select chat_name FROM db_chat where chat_name IS NOT NULL and chat_id in (SELECT chat_id FROM db_chat_user WHERE user_id = '"
                + user.getId() + "') ";
        outMessage.println("#GCH#" + GET_CHATS + System.lineSeparator());
        outMessage.flush();
    }

    public void updateUser() {
        this.outMessage.println("#GUS#" + GET_USERS + System.lineSeparator());
        this.outMessage.flush();
    }
}
