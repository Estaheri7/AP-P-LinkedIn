package com.example.server.HttpHandlers;

import com.example.server.HttpControllers.NotificationController;
import com.example.server.Server;
import com.example.server.models.Notification;
import com.example.server.utils.AuthUtil;
import com.example.server.utils.JwtUtil;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.example.server.Server.extractFromPath;

public class NotificationHandler {
    private static final Gson gson = new Gson();

    public static void addNotificationHandler(HttpExchange exchange) throws IOException {
        String email = extractFromPath(exchange.getRequestURI().getPath());
        if (!AuthUtil.authorizeRequest(exchange, email)) {
            return;
        }

        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        Notification notification = gson.fromJson(requestBody, Notification.class);
        notification.setEmail(email);

        try {
            NotificationController.addNotification(notification);
            Server.sendResponse(exchange, 200, "Notification added successfully");
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void getAllNotificationsHandler(HttpExchange exchange) throws IOException {
        String email = extractFromPath(exchange.getRequestURI().getPath());
        if (!AuthUtil.authorizeRequest(exchange, email)) {
            return;
        }

        try {
            ArrayList<Notification> notifications = NotificationController.getAllNotifications(email);
            Server.sendResponse(exchange, 200, gson.toJson(notifications));
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }
}
