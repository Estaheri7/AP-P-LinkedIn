package com.example.server.HttpHandlers;

import com.example.server.HttpControllers.PostController;
import com.example.server.Server;
import com.example.server.models.Post;
import com.example.server.utils.AuthUtil;
import com.example.server.utils.JwtUtil;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.sql.SQLException;

import static com.example.server.Server.extractEmailFromPath;

public class PostHandler {
    private static final Gson gson = new Gson();

    public static void addPostHandler(HttpExchange exchange) throws IOException {
        String viewerEmail = JwtUtil.parseToken(AuthUtil.getTokenFromHeader(exchange));
        String requestEmail = extractEmailFromPath(exchange.getRequestURI().getPath());

        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        Post post = gson.fromJson(requestBody, Post.class);
        post.setAuthor(requestEmail);

        try {
            if (!viewerEmail.equals(requestEmail)) {
                Server.sendResponse(exchange, 403, "Forbidden request");
                return;
            }
            PostController.addPost(post);
            Server.sendResponse(exchange, 200, "Post added");
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }
}
