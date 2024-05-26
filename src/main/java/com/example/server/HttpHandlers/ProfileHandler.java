package com.example.server.HttpHandlers;

import com.example.server.HttpControllers.ProfileController;
import com.example.server.HttpControllers.UserController;
import com.example.server.Server;
import com.example.server.models.*;
import com.example.server.utils.AuthUtil;
import com.example.server.utils.JwtUtil;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.sql.SQLException;

import static com.example.server.Server.extractEmailFromPath;

public class ProfileHandler {
    private static final Gson gson = new Gson();

    public static void getProfileHandler(HttpExchange exchange) throws IOException {
        String email = extractEmailFromPath(exchange.getRequestURI().getPath());
        String viewerEmail = JwtUtil.parseToken(AuthUtil.getTokenFromHeader(exchange));

        try {
            UserProfile userProfile = ProfileController.getProfile(email, viewerEmail);
            if (userProfile != null) {
                Server.sendResponse(exchange, 200, gson.toJson(userProfile));
            } else {
                Server.sendResponse(exchange, 404, "Not found");
            }
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void userUpdateHandler(HttpExchange exchange) throws IOException {
        String requestEmail = extractEmailFromPath(exchange.getRequestURI().getPath());

        if (!AuthUtil.authorizeRequest(exchange, requestEmail)) {
            return;
        }

        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        User user = gson.fromJson(requestBody, User.class);
        user.setEmail(requestEmail);

        try {
            ProfileController.updateUser(user);
            Server.sendResponse(exchange, 200, "Profile updated successfully");
        } catch (IllegalArgumentException e) {
            Server.sendResponse(exchange, 400, e.getMessage());
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void skillUpdateHandler(HttpExchange exchange) throws IOException {
        String requestEmail = extractEmailFromPath(exchange.getRequestURI().getPath());

        if (!AuthUtil.authorizeRequest(exchange, requestEmail)) {
            return;
        }

        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        Skill skill = gson.fromJson(requestBody, Skill.class);
        skill.setEmail(requestEmail);

        try {
            ProfileController.updateSkill(skill);
            Server.sendResponse(exchange, 200, "Skill updated successfully");
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void addEducationHandler(HttpExchange exchange) throws IOException {
        String requestEmail = extractEmailFromPath(exchange.getRequestURI().getPath());

        if (!AuthUtil.authorizeRequest(exchange, requestEmail)) {
            return;
        }

        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        Education education = gson.fromJson(requestBody, Education.class);
        education.setEmail(requestEmail);

        try {
            ProfileController.addEducation(education);
            Server.sendResponse(exchange, 200, "Education added successfully");
        } catch (IllegalArgumentException e) {
            Server.sendResponse(exchange, 400, e.getMessage());
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void educationUpdateHandler(HttpExchange exchange) throws IOException {
        String requestEmail = extractEmailFromPath(exchange.getRequestURI().getPath());

        if (!AuthUtil.authorizeRequest(exchange, requestEmail)) {
            return;
        }

        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        Education education = gson.fromJson(requestBody, Education.class);

        try {
            Education currentEducation = UserController.getEducation(requestEmail);
            education.setId(currentEducation.getId());
            ProfileController.updateEducation(education);
            Server.sendResponse(exchange, 200, "Education updated successfully");
        } catch (IllegalArgumentException e) {
            Server.sendResponse(exchange, 400, e.getMessage());
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void contactUpdateHandler(HttpExchange exchange) throws IOException {
        String requestEmail = extractEmailFromPath(exchange.getRequestURI().getPath());

        if (!AuthUtil.authorizeRequest(exchange, requestEmail)) {
            return;
        }

        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        Contact contact = gson.fromJson(requestBody, Contact.class);
        contact.setEmail(requestEmail);

        try {
            ProfileController.updateContact(contact);
            Server.sendResponse(exchange, 200, "Contact updated successfully");
        } catch (IllegalArgumentException e) {
            Server.sendResponse(exchange, 400, e.getMessage());
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }
}
