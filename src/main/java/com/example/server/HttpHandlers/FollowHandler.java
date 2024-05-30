package com.example.server.HttpHandlers;

import com.example.server.HttpControllers.FollowController;
import com.example.server.Server;
import com.example.server.utils.AuthUtil;
import com.example.server.utils.JwtUtil;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.sql.SQLException;

import static com.example.server.Server.extractEmailFromPath;

public class FollowHandler {
    private static final Gson gson = new Gson();

    public static void followHandler(HttpExchange exchange) throws IOException {
        String token = AuthUtil.getTokenFromHeader(exchange);
        if (token == null || !AuthUtil.isTokenValid(exchange, token)) {
            return;
        }

        String viewerEmail = JwtUtil.parseToken(token);
        String requestEmail = extractEmailFromPath(exchange.getRequestURI().getPath());

        if (!AuthUtil.isUserAuthorized(exchange, token, viewerEmail)) {
            return;
        }

        try {
            if (requestEmail == null || requestEmail.isEmpty()) {
                Server.sendResponse(exchange, 404, "User not found");
                return;
            }

            if (requestEmail.equals(viewerEmail)) {
                Server.sendResponse(exchange, 403, "You cannot follow yourself");
                return;
            }

            if (FollowController.isFollowed(viewerEmail, requestEmail)) {
                Server.sendResponse(exchange, 403, "You already followed this user");
                return;
            }

            FollowController.follow(viewerEmail, requestEmail);
            Server.sendResponse(exchange, 200, "Followed");
        } catch (IllegalArgumentException e) {
            Server.sendResponse(exchange, 400, e.getMessage());
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public class UnFollowHandler {

        private static final Gson gson = new Gson();

        public static void followHandler(HttpExchange exchange) throws IOException {
            String token = AuthUtil.getTokenFromHeader(exchange);
            if (token == null || !AuthUtil.isTokenValid(exchange, token)) {
                return;
            }

            String viewerEmail = JwtUtil.parseToken(token);
            String requestEmail = extractEmailFromPath(exchange.getRequestURI().getPath());

            if (!AuthUtil.isUserAuthorized(exchange, token, viewerEmail)) {
                return;
            }

            try {
                if (requestEmail == null || requestEmail.isEmpty()) {
                    Server.sendResponse(exchange, 404, "User not found");
                    return;
                }

                if (requestEmail.equals(viewerEmail)) {
                    Server.sendResponse(exchange, 403, "You cannot unfollow yourself");
                    return;
                }
                if (!FollowController.isFollowed(viewerEmail, requestEmail)) {
                    Server.sendResponse(exchange, 403, "You already unfollowed this user");
                    return;
                }
                FollowController.follow(viewerEmail, requestEmail);
                Server.sendResponse(exchange, 200, "unFollowed");
            } catch (IllegalArgumentException e) {
                Server.sendResponse(exchange, 400, e.getMessage());
            } catch (SQLException e) {
                Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
            } catch (Exception e) {
                Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
            }

        }
    }

}
