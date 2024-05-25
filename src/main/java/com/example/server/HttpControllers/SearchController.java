package com.example.server.HttpControllers;

import com.example.server.database_conn.UserDB;
import com.example.server.models.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class SearchController extends BaseController {

    public static ArrayList<User> getUsersByName(String name) throws SQLException {
           return userDB.getUserByName(name);
    }
}
