package com.example.server.HttpControllers;

import com.example.server.CustomExceptions.NotFoundException;
import com.example.server.database_conn.*;
import com.example.server.models.Chat;
import com.example.server.models.User;

import java.sql.SQLException;

public class MediaController extends BaseController {
    private static final PostDB postDB;
    private static final ChatDB chatDB;


    static {
        try {
            postDB = new PostDB();
            chatDB = new ChatDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateAvatar(String email, String fileUrl) throws SQLException, NotFoundException {
        if (userDB.getUser(email) == null) {
            throw new NotFoundException("User not found");
        }

        userDB.uploadAvatar(email, fileUrl);
    }

    public static void updateBackground(String email, String fileUrl) throws SQLException, NotFoundException {
        if (userDB.getUser(email) == null) {
            throw new NotFoundException("User not found");
        }

        userDB.uploadBackground(email, fileUrl);
    }

    public static void addMediaToPost(int postId, String fileUrl) throws SQLException, NotFoundException {
        if (postDB.getPost(postId) == null) {
            throw new NotFoundException("Post not found");
        }

        postDB.addMedia(postId, fileUrl);
    }

    public static void sendMediaInChat(String sender, String receiver, String fileUrl) throws SQLException, NotFoundException {
        User senderUser = userDB.getUser(sender);
        User receiverUser = userDB.getUser(receiver);
        if (senderUser == null || receiverUser == null) {
            throw new NotFoundException("User not found");
        }

        if (sender.equals(receiver)) {
            throw new IllegalAccessError("Sender and Receiver are the same");
        }

        Chat chat = new Chat(sender, receiver, fileUrl);
        chatDB.insertData(chat);
    }
}
