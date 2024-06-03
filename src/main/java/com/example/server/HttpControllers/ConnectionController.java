package com.example.server.HttpControllers;

import com.example.server.CustomExceptions.DuplicateDataException;
import com.example.server.CustomExceptions.NotFoundException;
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

    public static ArrayList<Connection> getConnections(String email) throws SQLException, NotFoundException {
        if (userDB.getUser(email) == null) {
            throw new NotFoundException("User not found");
        }

        return connectionDB.getConnections(email);
    }

    public static ArrayList<Connection> getSenderNotification(String email) throws SQLException, NotFoundException {
        if (userDB.getUser(email) == null) {
            throw new NotFoundException("User not found");
        }

        return connectionDB.getSenderNotification(email);
    }

    public static ArrayList<Connection> getReceiverNotification(String email) throws SQLException, NotFoundException {
        if (userDB.getUser(email) == null) {
            throw new NotFoundException("User not found");
        }

        return connectionDB.getReceiverNotification(email);
    }

    public static void sendConnection(String sender, String receiver, String notes) throws SQLException, NotFoundException, DuplicateDataException {
        User senderUser = userDB.getUser(sender);
        User receiverUser = userDB.getUser(receiver);
        if (senderUser == null || receiverUser == null) {
            throw new NotFoundException("User not found");
        }

        if (sender.equals(receiver)) {
            throw new IllegalAccessError("Sender and Receiver are the same");
        }

        if (connectionDB.connectionExists(receiver, sender)) {
            throw new DuplicateDataException("This connection is still pending...");
        }

        Connection connection = new Connection(sender, receiver, notes);
        if (isConnected(connection)) {
            throw new DuplicateDataException("You sent connection request to this user before");
        }
        connectionDB.insertData(connection);
    }

    public static void acceptConnection(String sender, String receiver) throws SQLException, NotFoundException {
        User senderUser = userDB.getUser(sender);
        User receiverUser = userDB.getUser(receiver);
        if (senderUser == null || receiverUser == null) {
            throw new NotFoundException("User not found");
        }

        Connection connection = connectionDB.getConnection(sender, receiver);
        if (connection == null) {
            throw new NotFoundException("Connection not found");
        }

        connectionDB.acceptConnection(sender, receiver);
        connectionDB.insertData(new Connection(receiver, sender));
        connectionDB.acceptConnection(receiver, sender);
        if (!followDB.isFollowed(sender, receiver)) {
            followDB.insertData(new Follow(sender, receiver));
            userDB.increaseFollowers(receiver);
            userDB.increaseFollowings(sender);
        }
        if (!followDB.isFollowed(receiver, sender)) {
            followDB.insertData(new Follow(receiver, sender));
            userDB.increaseFollowers(sender);
            userDB.increaseFollowings(receiver);
        }
        userDB.increaseConnections(sender);
        userDB.increaseConnections(receiver);
    }

    public static void declineConnection(String sender, String receiver) throws SQLException, NotFoundException {
        User senderUser = userDB.getUser(sender);
        User receiverUser = userDB.getUser(receiver);
        if (senderUser == null || receiverUser == null) {
            throw new NotFoundException("User not found");
        }

        Connection connection = connectionDB.getConnection(sender, receiver);
        if (connection == null) {
            throw new NotFoundException("Connection not found");
        }

        if (connection.isCommited()) {
            throw new IllegalAccessError("Commited connection can't be declined");
        }

        connectionDB.deleteData(connection.getId());
    }

    public static boolean isConnected(Connection connection) throws SQLException {
        return connectionDB.connectionExists(connection.getSender(), connection.getReceiver());
    }
}
