package com.example.server.HttpHandlers;

import com.example.server.HttpControllers.UserController;
import com.example.server.models.User;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

public class UserHandler {
    public static void userPostHandler(HttpExchange exchange) throws IOException {
        String requestBody = new String(exchange.getRequestBody().readAllBytes());

        Gson gson = new Gson();
        User user = gson.fromJson(requestBody, User.class);

        String response;
        try {
            UserController.addUser(user);
            response = "User added successfully";
            exchange.sendResponseHeaders(200, response.getBytes().length);
        } catch (IllegalArgumentException e) {
            response = e.getMessage();
            exchange.sendResponseHeaders(400, response.getBytes().length);
        } catch (SQLException e) {
            response = "Database error: " + e.getMessage();
            exchange.sendResponseHeaders(500, response.getBytes().length);
        } catch (Exception e) {
            response = "Internal server error: " + e.getMessage();
            exchange.sendResponseHeaders(500, response.getBytes().length);
        }

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
