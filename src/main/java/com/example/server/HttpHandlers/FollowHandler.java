package com.example.server.HttpHandlers;

import com.example.server.CustomExceptions.DuplicateDataException;
import com.example.server.CustomExceptions.NotFoundException;
import com.example.server.HttpControllers.FollowController;
import com.example.server.Server;
import com.example.server.models.Follow;
import com.example.server.utils.AuthUtil;
import com.example.server.utils.JwtUtil;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.example.server.Server.extractFromPath;

public class FollowHandler {
    private static final Gson gson = new Gson();

    public static void followHandler(HttpExchange exchange) throws IOException {
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
            FollowController.follow(viewerEmail, requestEmail);
            Server.sendResponse(exchange, 200, "Followed");
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

    public static void unfollowHandler(HttpExchange exchange) throws IOException {
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
            FollowController.unfollow(viewerEmail, requestEmail);
            Server.sendResponse(exchange, 200, "unFollowed");
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

    public static void getFollowersHandler(HttpExchange exchange) throws IOException {
        String email = extractFromPath(exchange.getRequestURI().getPath());

        try {
            ArrayList<Follow> followers = FollowController.getFollowers(email);
            Server.sendResponse(exchange, 200, gson.toJson(followers));
        } catch (NotFoundException e) {
            Server.sendResponse(exchange, 404, e.getMessage());
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void getFollowingsHandler(HttpExchange exchange) throws IOException {
        String email = extractFromPath(exchange.getRequestURI().getPath());

        try {
            ArrayList<Follow> followings = FollowController.getFollowing(email);
            Server.sendResponse(exchange, 200, gson.toJson(followings));
        } catch (NotFoundException e) {
            Server.sendResponse(exchange, 404, e.getMessage());
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void checkFollowHandler(HttpExchange exchange) throws IOException {
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
            if (FollowController.isFollowed(viewerEmail, requestEmail)) {
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
}
