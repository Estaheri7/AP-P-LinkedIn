package com.example.server.HttpControllers;

import com.example.server.CustomExceptions.NotFoundException;
import com.example.server.database_conn.ChatDB;
import com.example.server.models.Chat;
import com.example.server.models.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class ChatController extends BaseController {
    private static final ChatDB chatDB;

    static {
        try {
            chatDB = new ChatDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendMessage(Chat chat) throws SQLException, NotFoundException {
        User sender = userDB.getUser(chat.getSender());
        User receiver = userDB.getUser(chat.getReceiver());
        if (sender == null || receiver == null) {
            throw new NotFoundException("User not found");
        }

        if (chat.getSender().equals(chat.getReceiver())) {
            throw new IllegalAccessError("Sender and Receiver are the same");
        }

        chatDB.insertData(chat);
    }

    public static ArrayList<Chat> displayChat(String sender, String receiver) throws SQLException, NotFoundException {
        User senderUser = userDB.getUser(sender);
        User receiverUser = userDB.getUser(receiver);
        if (senderUser == null || receiverUser == null) {
            throw new NotFoundException("User not found");
        }

        if (sender.equals(receiver)) {
            throw new IllegalAccessError("Sender and Receiver are the same");
        }

        return chatDB.getChats(sender, receiver);
    }

    public static ArrayList<Chat> getReceiverChat(String receiver) throws SQLException, NotFoundException {
        User receiverUser = userDB.getUser(receiver);
        if (receiverUser == null) {
            throw new NotFoundException("User not found");
        }

        return chatDB.getReceiverChats(receiver);
    }
}
