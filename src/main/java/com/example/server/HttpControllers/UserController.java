package com.example.server.HttpControllers;

import com.example.server.DataValidator.UserValidator;
import com.example.server.database_conn.UserDB;
import com.example.server.models.User;

import java.sql.SQLException;

public class UserController {
    public static void addUser(User user) throws SQLException  {
        if (!UserValidator.isValid(user)) {
            throw new IllegalArgumentException("Invalid data format");
        }

        UserDB userDB = new UserDB();
        userDB.insertData(user);
    }
}
