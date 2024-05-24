package com.example.server.HttpHandlers;

import com.example.server.HttpControllers.ProfileController;
import com.example.server.models.User;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

import static com.example.server.Server.extractEmailFromPath;

public class ProfileHandler {
    private static final Gson gson = new Gson();

    public static void userUpdateHandler(HttpExchange exchange) throws IOException {
        String email = extractEmailFromPath(exchange.getRequestURI().getPath());

        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        User user = gson.fromJson(requestBody, User.class);
        user.setEmail(email);

        String response;
        try {
            ProfileController.updateUser(user);
            response = "Profile updated successfully";
            exchange.sendResponseHeaders(200, response.getBytes().length);
        }  catch (IllegalArgumentException e) {
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