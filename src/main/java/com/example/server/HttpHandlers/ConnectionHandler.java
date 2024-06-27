package com.example.server.HttpHandlers;

import com.example.server.CustomExceptions.DuplicateDataException;
import com.example.server.CustomExceptions.NotFoundException;
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
            ConnectionController.sendConnection(viewerEmail, requestEmail, notes);
            Server.sendResponse(exchange, 200, "Connection sent successfully");
        } catch (NotFoundException e) {
            Server.sendResponse(exchange, 404, e.getMessage());
        } catch (IllegalAccessError | DuplicateDataException e) {
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
        } catch (NotFoundException e) {
            Server.sendResponse(exchange, 404, e.getMessage());
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void getSenderNotificationHandler(HttpExchange exchange) throws IOException {
        String email = extractFromPath(exchange.getRequestURI().getPath());
        if (!AuthUtil.authorizeRequest(exchange, email)) {
            return;
        }

        try {
            ArrayList<Connection> connections = ConnectionController.getSenderNotification(email);
            Server.sendResponse(exchange, 200, gson.toJson(connections));
        } catch (NotFoundException e) {
            Server.sendResponse(exchange, 404, e.getMessage());
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void getReceiverNotificationHandler(HttpExchange exchange) throws IOException {
        String email = extractFromPath(exchange.getRequestURI().getPath());
        if (!AuthUtil.authorizeRequest(exchange, email)) {
            return;
        }

        try {
            ArrayList<Connection> connections = ConnectionController.getReceiverNotification(email);
            Server.sendResponse(exchange, 200, gson.toJson(connections));
        } catch (NotFoundException e) {
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
        String receiverEmail = extractFromPath(exchange.getRequestURI().getPath());

        if (!AuthUtil.authorizeRequest(exchange, receiverEmail)) {
            return;
        }

        try {
            ConnectionController.acceptConnection(senderEmail, receiverEmail);
            Server.sendResponse(exchange, 200, "Accepted");
        } catch (NotFoundException e) {
            Server.sendResponse(exchange, 404, e.getMessage());
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void declineConnectionHandler(HttpExchange exchange) throws IOException {
        HashMap<String, String> queryParams = (HashMap<String, String>) exchange.getAttribute("queryParams");
        String senderEmail = queryParams.get("sender");
        String receiverEmail = extractFromPath(exchange.getRequestURI().getPath());

        if (!AuthUtil.authorizeRequest(exchange, receiverEmail)) {
            return;
        }

        try {
            ConnectionController.declineConnection(senderEmail, receiverEmail);
            Server.sendResponse(exchange, 200, "Declined");
        } catch (NotFoundException e) {
            Server.sendResponse(exchange, 404, e.getMessage());
        } catch (IllegalAccessError e) {
            Server.sendResponse(exchange, 400, e.getMessage());
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void getPendingHandlers(HttpExchange exchange) throws IOException {
        HashMap<String, String> queryParams = (HashMap<String, String>) exchange.getAttribute("queryParams");
        String profileEmail = queryParams.get("profile");
        String senderEmail = extractFromPath(exchange.getRequestURI().getPath());

        if (!AuthUtil.authorizeRequest(exchange, senderEmail)) {
            return;
        }

        try {
            boolean doesExist = ConnectionController.getConnectionByPending(senderEmail, profileEmail);
            if (doesExist) {
                Server.sendResponse(exchange, 200, "Exists");
            } else {
                Server.sendResponse(exchange, 404, "Not Found");
            }
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void isAcceptedHandler(HttpExchange exchange) throws IOException {
        String token = AuthUtil.getTokenFromHeader(exchange);
        if (token == null || !AuthUtil.isTokenValid(exchange, token)) {
            return;
        }

        String viewerEmail = JwtUtil.parseToken(token);
        String requestEmail = extractFromPath(exchange.getRequestURI().getPath());

        if (!AuthUtil.isUserAuthorized(exchange, token, viewerEmail)) {
            return;
        }

        try {
            if (ConnectionController.isAccepted(viewerEmail, requestEmail)) {
                Server.sendResponse(exchange, 200, "Accepted");
            } else {
                Server.sendResponse(exchange, 404, "Not Accepted");
            }
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }
}
