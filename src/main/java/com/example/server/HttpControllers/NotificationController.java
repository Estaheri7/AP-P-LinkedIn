package com.example.server.HttpControllers;

import com.example.server.database_conn.NotificationDB;
import com.example.server.models.Notification;

import java.sql.SQLException;
import java.util.ArrayList;

public class NotificationController extends BaseController {
    private final static NotificationDB notificationDB;

    static {
        try {
            notificationDB = new NotificationDB();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void addNotification(Notification notification) throws SQLException {
        notificationDB.insertData(notification);
    }

    public static ArrayList<Notification> getAllNotifications() throws SQLException {
        return notificationDB.getAllNotifications();
    }
}
