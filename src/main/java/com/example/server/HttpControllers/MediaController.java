package com.example.server.HttpControllers;

import com.example.server.CustomExceptions.NotFoundException;
import com.example.server.database_conn.*;
import com.example.server.models.Chat;
import com.example.server.models.User;

import java.io.File;
import java.io.FilenameFilter;
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

    public static final String AVATARS = "avatars";
    public static final String BACKGROUNDS = "backgrounds";
    public static final String POST_MEDIA = "post_media";
    public static final String CHAT_MEDIA = "chat_media";

    public static File getAvatar(String email) throws NotFoundException {
        File avatarJPG = new File(AVATARS + "/" + email + ".jpg");
        File avatarPNG = new File(AVATARS + "/" + email + ".png");
        if (avatarJPG.exists()) {
            return avatarJPG;
        } else if (avatarPNG.exists()) {
            return avatarPNG;
        } else {
            throw new NotFoundException("Avatar not found for email: " + email);
        }
    }

    public static File getBackground(String email) throws NotFoundException {
        File backgroundJPG = new File(BACKGROUNDS + "/" + email + ".jpg");
        File backgroundPNG = new File(BACKGROUNDS + "/" + email + ".png");
        if (backgroundJPG.exists()) {
            return backgroundJPG;
        } else if (backgroundPNG.exists()) {
            return backgroundPNG;
        } else {
            throw new NotFoundException("Background not found for email: " + email);
        }
    }

    public static File getPostMedia(String postId) throws NotFoundException {
        File directory = new File(POST_MEDIA);

        File matchingFile = directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith(postId + ".");
            }
        })[0];

        if (matchingFile == null) {
            throw new NotFoundException("Post not found for postId: " + postId);
        }

        return matchingFile;
    }

    public static File getChatMedia(String unique) throws NotFoundException {
        File directory = new File(CHAT_MEDIA);

        File matchingFile = directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith(unique + ".");
            }
        })[0];

        if (matchingFile == null) {
            throw new NotFoundException("Media not found: " + unique);
        }

        return matchingFile;
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
