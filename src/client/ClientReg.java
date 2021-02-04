package client;

import client.Actions.ActionsReg.RegAction;
import client.Actions.ActionsStart.StartRegAction;
import dao.entity.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class ClientReg  extends JFrame {

    JTextField  textFieldUserName;
    JTextField  textFieldUserPass;
    JTextField  textFieldUserPassDubl;
    User user;
    private Socket clientSocket;
    private Scanner inMessage;
    private PrintWriter outMessage;
    public ServerHandler serverHandler;

    public Socket getClientSocket() {
        return clientSocket;
    }

    public Scanner getInMessage() {
        return inMessage;
    }

    public PrintWriter getOutMessage() {
        return outMessage;
    }

    public ClientReg(Socket clientSocket, Scanner inMessage, PrintWriter outMessage, ServerHandler serverHandler){

    this.clientSocket = clientSocket;
    this.inMessage = inMessage;
    this.outMessage = outMessage;
    this.serverHandler = serverHandler;

    JLabel labelUserName = new JLabel("Логин: ");
    JLabel labelUserPass = new JLabel("Пароль: ");
    JLabel labelUserPassDubl = new JLabel("Подтверждение пароля: ");
    textFieldUserName       = new JTextField();
    textFieldUserPass       = new JTextField();
    textFieldUserPassDubl       = new JTextField();
    JButton btnReg = new JButton("Зарегистрироваться");

    user = new User();

    RegAction regAction = new RegAction(this, clientSocket, inMessage, outMessage);
    btnReg.addActionListener(regAction);

    textFieldUserName.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            user.setUserName(textFieldUserName.getText());
        }
    });

    textFieldUserPass.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            user.setUserPass(textFieldUserPass.getText());
        }
    });

    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setAutoCreateGaps(true);
    layout.setAutoCreateContainerGaps(true);

    layout.setHorizontalGroup(layout.createParallelGroup()
            .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup()
                            .addComponent(labelUserName)
                            .addComponent(textFieldUserName)
                    )
                    .addGroup(layout.createParallelGroup()
                            .addComponent(labelUserPass)
                            .addComponent(textFieldUserPass)
                            .addComponent(labelUserPassDubl)
                            .addComponent(textFieldUserPassDubl)
                    )
            )
            .addGroup(layout.createParallelGroup()
                    .addComponent(btnReg)
            )
    );

    layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup()
                    .addComponent(labelUserName)
                    .addComponent(labelUserPass)
            )
            .addGroup(layout.createParallelGroup()
                    .addComponent(textFieldUserName)
                    .addComponent(textFieldUserPass)
            )
            .addGroup(layout.createParallelGroup()
                    .addComponent(labelUserPassDubl)
            )
            .addGroup(layout.createParallelGroup()
                    .addComponent(textFieldUserPassDubl)
            )
            .addGroup(layout.createParallelGroup()
                    .addComponent(btnReg)
            )
    );

    pack();
    setVisible(true);

}

    public String getTextFieldUserName() {

        return textFieldUserName.getText();

    }
    public String gettextFieldUserPass() {

        return textFieldUserPass.getText();

    }



}
