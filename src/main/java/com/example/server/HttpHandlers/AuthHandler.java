package com.example.server.HttpHandlers;

import com.example.server.HttpControllers.AuthController;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class AuthHandler {
    private static final Gson gson = new Gson();

    public static void loginHandler(HttpExchange exchange) throws IOException {
        String requestBody = new String(exchange.getRequestBody().readAllBytes());

        HashMap<String, String> loginData = gson.fromJson(requestBody, HashMap.class);
        String email = loginData.get("email");
        String password = loginData.get("password");

        String response;
        try {
            String token = AuthController.loginUser(email, password);
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("token", token);
            response = gson.toJson(responseMap);
            exchange.sendResponseHeaders(200, response.getBytes().length);
        } catch (IllegalArgumentException e) {
            response = e.getMessage();
            exchange.sendResponseHeaders(400, response.getBytes().length);
        } catch (Exception e) {
            response = "Internal server error: " + e.getMessage();
            exchange.sendResponseHeaders(500, response.getBytes().length);
        }

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}

