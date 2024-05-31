package com.example.server.HttpControllers;

import com.example.server.database_conn.ConnectionDB;
import com.example.server.database_conn.FollowDB;
import com.example.server.models.Connection;
import com.example.server.models.Follow;
import com.example.server.models.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class ConnectionController extends BaseController {

    private final static ConnectionDB connectionDB;
    private final static FollowDB followDB;

    static {
        try {
            connectionDB = new ConnectionDB();
            followDB = new FollowDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Connection> getConnections(String email) throws SQLException {
        User user = userDB.getUser(email);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return connectionDB.getConnections(email);
    }

    public static ArrayList<Connection> getSenderNotification(String email) throws SQLException {
        User user = userDB.getUser(email);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
            return connectionDB.getSenderNotification(email);
    }

    public static ArrayList<Connection> getReceiverNotification(String email) throws SQLException {
        User user = userDB.getUser(email);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return connectionDB.getReceiverNotification(email);
    }

    public static void sendConnection(String sender, String receiver) throws SQLException {
        User senderUser = userDB.getUser(sender);
        User receiverUser = userDB.getUser(receiver);
        if (senderUser == null || receiverUser == null) {
            throw new IllegalArgumentException("User not found");
        }

        Connection connection = new Connection(sender, receiver);
        connectionDB.insertData(connection);
    }


    public static void acceptConnection(String sender, String receiver) throws SQLException {
        User senderUser = userDB.getUser(sender);
        User receiverUser = userDB.getUser(receiver);
        if (senderUser == null || receiverUser == null) {
            throw new IllegalArgumentException("User not found");
        }

        Connection connection = connectionDB.getConnection(sender, receiver);
        if (connection == null) {
            throw new IllegalArgumentException("Connection not found");
        }

        connectionDB.acceptConnection(sender, receiver);
        connectionDB.insertData(new Connection(receiver, sender));
        if (!followDB.isFollowed(sender, receiver)) {
            followDB.insertData(new Follow(sender, receiver));
            userDB.increaseFollowers(receiver);
        }
        if (!followDB.isFollowed(receiver, sender)) {
            followDB.insertData(new Follow(receiver, sender));
            userDB.increaseFollowers(sender);
        }
    }



}
