package com.example.server.HttpHandlers;

import com.example.server.HttpControllers.ConnectionController;
import com.example.server.Server;
import com.example.server.models.Connection;
import com.example.server.utils.AuthUtil;
import com.example.server.utils.JwtUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.server.Server.extractFromPath;

public class ConnectionHandler {

    private static final Gson gson = new Gson();

    public static void sendConnectionHandler(HttpExchange exchange) throws IOException {
        String token = AuthUtil.getTokenFromHeader(exchange);
        if (token == null || !AuthUtil.isTokenValid(exchange, token)) {
            return;
        }

        String viewerEmail = JwtUtil.parseToken(token);
        String requestEmail = extractFromPath(exchange.getRequestURI().getPath());

        if (!AuthUtil.isUserAuthorized(exchange, token, viewerEmail)) {
            return;
        }

        String requestContent = new String(exchange.getRequestBody().readAllBytes());
        String notes = "";
        if (requestContent != null && !requestContent.isEmpty()) {
            HashMap<String, String> requestBody = gson.fromJson(requestContent, HashMap.class);
            notes = requestBody.get("notes");
        }

        try {
            if (requestEmail == null || requestEmail.isEmpty()) {
                Server.sendResponse(exchange, 404, "User not found");
                return;
            }

            if (requestEmail.equals(viewerEmail)) {
                Server.sendResponse(exchange, 403, "You cannot connect yourself");
                return;
            }

            ConnectionController.sendConnection(viewerEmail, requestEmail, notes);
            Server.sendResponse(exchange, 200, "Connected");
        } catch (IllegalArgumentException e) {
            Server.sendResponse(exchange, 400, e.getMessage());
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void getConnectionsHandler(HttpExchange exchange) throws IOException {
        String email = extractFromPath(exchange.getRequestURI().getPath());

        try {
            ArrayList<Connection> connections = ConnectionController.getConnections(email);
            Server.sendResponse(exchange, 200, gson.toJson(connections));
        } catch (IllegalArgumentException e) {
            Server.sendResponse(exchange, 404, e.getMessage());
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void getSenderNotificationHandler(HttpExchange exchange) throws IOException {
        String email = extractFromPath(exchange.getRequestURI().getPath());

        try {
            ArrayList<Connection> connections = ConnectionController.getSenderNotification(email);
            Server.sendResponse(exchange, 200, gson.toJson(connections));
        } catch (IllegalArgumentException e) {
            Server.sendResponse(exchange, 404, e.getMessage());
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void getRecieverNotificationHandler(HttpExchange exchange) throws IOException {
        String email = extractFromPath(exchange.getRequestURI().getPath());

        try {
            ArrayList<Connection> connections = ConnectionController.getReceiverNotification(email);
            Server.sendResponse(exchange, 200, gson.toJson(connections));
        } catch (IllegalArgumentException e) {
            Server.sendResponse(exchange, 404, e.getMessage());
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void acceptConnectionHandler(HttpExchange exchange) throws IOException {
        HashMap<String, String> queryParams = (HashMap<String, String>) exchange.getAttribute("queryParams");
        String senderEmail = queryParams.get("sender");
        String recieverEmail = extractFromPath(exchange.getRequestURI().getPath());

        if (!AuthUtil.authorizeRequest(exchange, recieverEmail)) {
            return;
        }

        try {
            if (senderEmail == null || senderEmail.isEmpty()) {
                Server.sendResponse(exchange, 404, "not found");
            }

            ConnectionController.acceptConnection(senderEmail, recieverEmail);
        } catch (IllegalArgumentException e) {
            Server.sendResponse(exchange, 404, e.getMessage());
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void declineConnectionHandler(HttpExchange exchange) throws IOException {
        String token = AuthUtil.getTokenFromHeader(exchange);
        if (token == null || !AuthUtil.isTokenValid(exchange, token)) {
            Server.sendResponse(exchange, 401, "Invalid or missing token");
            return;
        }

        String senderEmail = JwtUtil.parseToken(token);
        JsonObject requestBody;
        try (InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8)) {
            requestBody = JsonParser.parseReader(isr).getAsJsonObject();
        } catch (Exception e) {
            Server.sendResponse(exchange, 400, "Invalid request body");
            return;
        }

        String receiverEmail = requestBody.get("receiver").getAsString();

        if (!AuthUtil.isUserAuthorized(exchange, token, senderEmail)) {
            Server.sendResponse(exchange, 403, "User not authorized");
            return;
        }

        try {
            if (receiverEmail == null || receiverEmail.isEmpty()) {
                Server.sendResponse(exchange, 404, "Receiver not found");
                return;
            }

            if (receiverEmail.equals(senderEmail)) {
                Server.sendResponse(exchange, 403, "You cannot decline yourself");
                return;
            }

            ConnectionController.declineConnection(senderEmail, receiverEmail);
            Server.sendResponse(exchange, 200, "Connection declined successfully");
        } catch (IllegalArgumentException e) {
            Server.sendResponse(exchange, 404, e.getMessage());
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }


}
