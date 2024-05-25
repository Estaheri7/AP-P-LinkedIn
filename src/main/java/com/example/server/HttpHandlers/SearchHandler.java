package com.example.server.HttpHandlers;

import com.example.server.HttpControllers.SearchController;
import com.example.server.models.User;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class SearchHandler {
    private static final Gson gson = new Gson();

    public static void searchByName(HttpExchange exchange) throws IOException {
        HashMap<String, String> params = (HashMap<String, String>) exchange.getAttribute("queryParams");

        String name = params.get("name");

        String response;
        try {
            ArrayList<User> users = SearchController.getUsersByName(name);
            response = gson.toJson(users);
            exchange.sendResponseHeaders(200, response.length());
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
