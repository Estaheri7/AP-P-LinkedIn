package com.example.server.utils;

import com.example.server.Server;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;

public class AuthUtil {
    public static String getTokenFromHeader(HttpExchange exchange) throws IOException {
        String token = exchange.getRequestHeaders().getFirst("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            Server.sendResponse(exchange, 401, "Authorization token is missing or invalid");
            return null;
        }

        return token.substring(7); // Remove "Bearer " prefix
    }

    public static boolean isTokenValid(HttpExchange exchange, String token) throws IOException {
        if (!JwtUtil.validateToken(token)) {
            Server.sendResponse(exchange, 401, "Invalid token");
            return false;
        }
        return true;
    }

    public static boolean isUserAuthorized(HttpExchange exchange, String token, String email) throws IOException {
        String tokenEmail = JwtUtil.parseToken(token);
        if (!email.equals(tokenEmail)) {
            Server.sendResponse(exchange, 403, "You are not authorized to access this resource");
            return false;
        }
        return true;
    }

    public static boolean authorizeRequest(HttpExchange exchange, String email) throws IOException {
        String token = getTokenFromHeader(exchange);

        if (!isTokenValid(exchange, token)) {
            return false;
        }

        if (!isUserAuthorized(exchange, token, email)) {
            return false;
        }

        if (token == null) {
            return false;
        }

        return true;
    }
}