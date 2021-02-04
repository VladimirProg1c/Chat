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
    private PrintWriter outMessage;

    public AddChatAction(ChatForm chatForm, PrintWriter outMessage) {
        this.chatForm = chatForm;
        this.outMessage = outMessage;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String answer = JOptionPane.showInputDialog(chatForm,
                new String[]{"Введите имя чата"},
                "Создание чата",
                JOptionPane.WARNING_MESSAGE);
        if (!answer.isEmpty()) {

            Long userId = chatForm.getUser().getId();

            String SET_CHAT = "#ch+#" + answer + "#" + userId;

            outMessage.println(SET_CHAT);
            outMessage.flush();
        }
    }
}
