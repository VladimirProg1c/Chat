package client.Actions.ActionsChat;

import client.ChatForm;
import config.SocketConfig;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SendMessage implements ActionListener {
    ChatForm chatForm;
    PrintWriter outMessage;
    Scanner inMessage;

    public SendMessage(ChatForm chatForm, Scanner inMessage, PrintWriter outMessage) {
        this.chatForm = chatForm;
        this.outMessage = outMessage;
        this.inMessage = inMessage;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String strMessage = chatForm.getJtfMessage().getText();

        String userName = chatForm.getUser().getUserName();//id
        String userId = chatForm.getUser().getId().toString();//id
        String chatName = chatForm.getJlChat().getSelectedValue();
        String SET_MES = "#mes#" + strMessage + "#" + chatName + "#" + userName + "#" + userId;
        //answer
        System.out.println("SendMessage");
        outMessage.println(SET_MES);
        outMessage.flush();


    }
}
