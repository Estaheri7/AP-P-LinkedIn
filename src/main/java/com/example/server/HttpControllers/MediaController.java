package com.example.server.HttpControllers;

import com.example.server.CustomExceptions.NotFoundException;
import com.example.server.database_conn.*;

import java.sql.SQLException;

public class MediaController extends BaseController {
    public static final PostDB postDB;

    static {
        try {
            postDB = new PostDB();
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
}
