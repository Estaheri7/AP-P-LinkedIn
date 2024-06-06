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

        chatDB.insertData(chat);
    }
}
