package com.example.server.HttpControllers;

import com.example.server.DataValidator.UserValidator;
import com.example.server.database_conn.UserDB;
import com.example.server.models.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserController {
    private static final UserDB userDB;

    static {
        try {
            userDB = new UserDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addUser(User user) throws SQLException  {
        if (!UserValidator.isValid(user)) {
            throw new IllegalArgumentException("Invalid data format");
        }

        userDB.insertData(user);
    }

    public static User getUser(String email) throws SQLException {
        return userDB.getUser(email);
    }

    public static ArrayList<User> getAllUsers() throws SQLException  {
        return userDB.getAllUsers();
    }
}
