package client;

import client.Actions.ActionsStart.StartCheckinAction;
import client.Actions.ActionsStart.StartRegAction;
import config.SocketConfig;
import dao.entity.User;
import server.ClientHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static javax.swing.GroupLayout.Alignment.*;

public class ClientStart extends JFrame {

    private JTextField jtfUserName;
    JTextField  textFieldUserName;
    JTextField  textFieldUserPass;
    private Socket clientSocket;
    private Scanner inMessage;
    private PrintWriter outMessage;
    User user;
    public ServerHandler serverHandler;


    public ClientStart(){

        SocketConfig socketConfig = new SocketConfig();

        clientSocket = socketConfig.getSocket();
        inMessage = socketConfig.getInMessage();
        outMessage = socketConfig.getOutMessage();

        serverHandler = new ServerHandler(clientSocket,inMessage,outMessage);
        new Thread(serverHandler).start();
        serverHandler.setClientStart(this);

        setTitle("Регистрация/Вход");

        JPanel bottomPanel = new JPanel(new BorderLayout());

        JLabel labelUserName = new JLabel("Логин: ");
        JLabel labelUserPass = new JLabel("Пароль: ");
        textFieldUserName       = new JTextField(10);
        textFieldUserPass       = new JTextField(10);
        JButton btnCheckin = new JButton("Войти");
        JButton btnReg = new JButton("Зарегистрироваться");

        StartRegAction startRegAction = new StartRegAction(clientSocket,inMessage,outMessage,serverHandler);
        btnReg.addActionListener(startRegAction);

        StartCheckinAction startCheckinAction = new StartCheckinAction(this,outMessage);
        btnCheckin.addActionListener(startCheckinAction);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(labelUserName)
                                        .addComponent(textFieldUserName)
                                )
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(labelUserPass)
                                        .addComponent(textFieldUserPass)
                                )
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(btnCheckin)
                                .addComponent(btnReg)
                        )
        );
        layout.linkSize(SwingConstants.HORIZONTAL, btnCheckin, btnReg);

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(BASELINE)
                    .addComponent(labelUserName)
                    .addComponent(labelUserPass))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(textFieldUserName)
                        .addComponent(textFieldUserPass))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(btnCheckin))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(btnReg))
        );

        pack();
        setVisible(true);

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
    }

    public String getTextFieldUserName() {

        return textFieldUserName.getText();

    }
    public String gettextFieldUserPass() {

        return textFieldUserPass.getText();

    }


}
