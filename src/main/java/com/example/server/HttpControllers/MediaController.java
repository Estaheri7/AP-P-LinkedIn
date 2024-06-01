package com.example.server.HttpControllers;

import com.example.server.CustomExceptions.NotFoundException;

import java.sql.SQLException;

public class MediaController extends BaseController {
    public static void updateAvatar(String email, String fileUrl) throws SQLException, NotFoundException {
        if (userDB.getUser(email) == null) {
            throw new NotFoundException("User not found");
        }
        userDB.uploadAvatar(email, fileUrl);
    }
}
