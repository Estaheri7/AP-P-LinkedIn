package com.example.server.HttpHandlers;

import com.example.server.HttpControllers.AuthController;
import com.example.server.Server;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AuthHandler {
    private static final Gson gson = new Gson();

    public static void loginHandler(HttpExchange exchange) throws IOException {
        String requestBody = new String(exchange.getRequestBody().readAllBytes());

        HashMap<String, String> loginData = gson.fromJson(requestBody, HashMap.class);
        String email = loginData.get("email");
        String password = loginData.get("password");

        try {
            String token = AuthController.loginUser(email, password);
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("token", token);
            Server.sendResponse(exchange, 200, gson.toJson(responseMap));
        } catch (IllegalArgumentException e) {
            Server.sendResponse(exchange, 400, gson.toJson(e.getMessage()));
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, gson.toJson(e.getMessage()));
        }
    }
}

