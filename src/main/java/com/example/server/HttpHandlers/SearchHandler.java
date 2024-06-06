package com.example.server.HttpHandlers;

import com.example.server.HttpControllers.SearchController;
import com.example.server.Server;
import com.example.server.models.Post;
import com.example.server.models.User;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class SearchHandler {
    private static final Gson gson = new Gson();
    private static HashMap<String, String> params;

    public static void searchHandler(HttpExchange exchange) throws IOException {
        params = (HashMap<String, String>) exchange.getAttribute("queryParams");

        if (params.containsKey("name")) {
            searchByName(exchange);
        } else if (params.containsKey("hashtag")) {
            searchByHashtag(exchange);
        }
    }

    public static void searchByName(HttpExchange exchange) throws IOException {
        String name = params.get("name");

        try {
            ArrayList<User> users = SearchController.getUsersByName(name);
            Server.sendResponse(exchange, 200, gson.toJson(users));
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void searchByHashtag(HttpExchange exchange) throws IOException {
        String hashtag = params.get("hashtag");

        try {
            ArrayList<Post> posts = SearchController.getPostsByHashtag(hashtag);
            Server.sendResponse(exchange, 200, gson.toJson(posts));
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }
}
