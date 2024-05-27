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
import java.util.ArrayList;
import java.util.HashMap;

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
            if (!requestEmail.equals(viewerEmail)) {
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

    public static void updatePostHandler(HttpExchange exchange) throws IOException {
        HashMap<String, String> queryParams = (HashMap<String, String>) exchange.getAttribute("queryParams");

        int id = Integer.parseInt(queryParams.get("id"));
        String viewerEmail = JwtUtil.parseToken(AuthUtil.getTokenFromHeader(exchange));
        String requestEmail = extractEmailFromPath(exchange.getRequestURI().getPath());

        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        Post post = gson.fromJson(requestBody, Post.class);
        post.setId(id);
        post.setAuthor(requestEmail);

        try {
            Post postToUpdate = PostController.getPost(id);
            if (!requestEmail.equals(viewerEmail) || !postToUpdate.equals(post)) {
                Server.sendResponse(exchange, 403, "Forbidden request");
                return;
            }

            PostController.updatePost(post);
            Server.sendResponse(exchange, 200, "Post updated");
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void getPostHandler(HttpExchange exchange) throws IOException {
        String token = AuthUtil.getTokenFromHeader(exchange);

        if (token == null || !AuthUtil.isTokenValid(exchange, token)) {
            return;
        }

        String viewerEmail = JwtUtil.parseToken(AuthUtil.getTokenFromHeader(exchange));
        String requestEmail = extractEmailFromPath(exchange.getRequestURI().getPath());

        if (!AuthUtil.isUserAuthorized(exchange, token, viewerEmail)) {
            return;
        }

        try {
            ArrayList<Post> posts = PostController.getPosts(requestEmail);
            Server.sendResponse(exchange, 200, gson.toJson(posts));
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void deletePostHandler(HttpExchange exchange) throws IOException {
        HashMap<String, String> queryParams = (HashMap<String, String>) exchange.getAttribute("queryParams");

        int id = Integer.parseInt(queryParams.get("id"));
        String viewerEmail = JwtUtil.parseToken(AuthUtil.getTokenFromHeader(exchange));
        String requestEmail = extractEmailFromPath(exchange.getRequestURI().getPath());

        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        Post post = gson.fromJson(requestBody, Post.class);
        post.setId(id);
        post.setAuthor(requestEmail);

        try {
            Post postToDelete = PostController.getPost(id);
            if (!requestEmail.equals(viewerEmail) || !postToDelete.equals(post)) {
                Server.sendResponse(exchange, 403, "Forbidden request");
                return;
            }

            PostController.deletePost(id);
            Server.sendResponse(exchange, 200, "Post deleted successfully");
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }
}
