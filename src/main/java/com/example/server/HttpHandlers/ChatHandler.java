package com.example.server.HttpHandlers;

import com.example.server.CustomExceptions.DuplicateDataException;
import com.example.server.CustomExceptions.NotFoundException;
import com.example.server.HttpControllers.ChatController;
import com.example.server.Server;
import com.example.server.models.Chat;
import com.example.server.utils.AuthUtil;
import com.example.server.utils.JwtUtil;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.example.server.Server.extractFromPath;

public class ChatHandler {
    private static final Gson gson = new Gson();

    public static void sendMessageHandler(HttpExchange exchange) throws IOException {
        String token = AuthUtil.getTokenFromHeader(exchange);
        if (token == null || !AuthUtil.isTokenValid(exchange, token)) {
            return;
        }

        String sender = JwtUtil.parseToken(token);
        String receiver = extractFromPath(exchange.getRequestURI().getPath());
        if (!AuthUtil.isUserAuthorized(exchange, token, sender)) {
            return;
        }

        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        Chat chat = gson.fromJson(requestBody, Chat.class);
        chat.setSender(sender);
        chat.setReceiver(receiver);

        try {
            ChatController.sendMessage(chat);
            Server.sendResponse(exchange, 200, "Message sent successfully");
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

    public static void displayChatHandler(HttpExchange exchange) throws IOException {
        String token = AuthUtil.getTokenFromHeader(exchange);
        if (token == null || !AuthUtil.isTokenValid(exchange, token)) {
            return;
        }

        String sender = JwtUtil.parseToken(token);
        String receiver = extractFromPath(exchange.getRequestURI().getPath());
        if (!AuthUtil.isUserAuthorized(exchange, token, sender)) {
            return;
        }

        try {
            ArrayList<Chat> chats = ChatController.displayChat(sender, receiver);
            Server.sendResponse(exchange, 200, gson.toJson(chats));
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
}
