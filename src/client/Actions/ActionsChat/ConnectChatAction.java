package client.Actions.ActionsChat;

import client.ChatForm;
import dao.entity.Chat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

public class ConnectChatAction implements ActionListener {
    private ChatForm chatForm;
    private PrintWriter outMessage;
    public ConnectChatAction(ChatForm chatForm, PrintWriter outMessage) {
        this.chatForm = chatForm;
        this.outMessage = outMessage;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String chatName = chatForm.getJlChat().getSelectedValue();
        Chat chat = new Chat();
        chat.setName(chatName);
        chatForm.lCurrentChat.setText(chatName);
        chatForm.setChat(chat);
        String GET_USERS = "select user_name FROM db_user where user_name IS NOT NULL and user_id in (" +
                "Select user_id FROM db_chat_user WHERE chat_id in (" +
                "Select chat_id FROM db_chat WHERE chat_name = '"+chatName+"') )";

        chatForm.listUserInChat.clear();

        this.outMessage.println("#GUC#" + GET_USERS + System.lineSeparator());
        this.outMessage.flush();
        String GET_MESSAGE = "SELECT chatTab.chat_name as chat_name," +
                "userTab.user_name as user_name," +
                "mesTab.massage_text as massage_text," +
                "mesTab.created_at as created_at " +
                "FROM (select * From db_massage WHERE chat_id in (" +
                "Select chat_id FROM db_chat WHERE chat_name = '"+chatName+"')" +
                "ORDER BY created_at DESC LIMIT 50) as mesTab " +
                "LEFT JOIN db_user as userTab " +
                "ON mesTab.user_id = userTab.user_id " +
                "LEFT JOIN db_chat as chatTab " +
                "ON mesTab.chat_id = chatTab.chat_id " +
                "ORDER BY created_at";
        this.outMessage.println("#GME#" + GET_MESSAGE + System.lineSeparator());
        this.outMessage.flush();
    }
}
