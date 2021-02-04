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

public class AddUserToChat implements ActionListener {

    ChatForm chatForm;
    PrintWriter outMessage;
    Scanner inMessage;

    public AddUserToChat(ChatForm chatForm, PrintWriter outMessage, Scanner inMessage) {
        this.chatForm = chatForm;
        this.outMessage = outMessage;
        this.inMessage = inMessage;
    }

    @Override
    public void actionPerformed(ActionEvent e) {



        String userName = chatForm.getJlUser().getSelectedValue();
        String chatName = chatForm.getJlChat().getSelectedValue();

        String SET_CHAT = "#chu#" + chatName + "#" + userName;
        //answer
        outMessage.println(SET_CHAT);
        outMessage.flush();




        //Long userId = chatForm.getUser().getId();

/*        String answer = JOptionPane.showInputDialog(chatForm,
                new String[]{"Введите имя пользователя"},
                "Выбор пользователя",
                JOptionPane.WARNING_MESSAGE);
        if (!answer.isEmpty()) {
            String userName = answer;
            String chatName = chatForm.getJlChat().getSelectedValue();
            String SET_CHAT = "#chu#" + chatName + "#" + userName;
            outMessage.println(SET_CHAT);
            outMessage.flush();
        }*/

    }
}
