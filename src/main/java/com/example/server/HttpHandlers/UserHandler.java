package com.example.server.HttpHandlers;

import com.example.server.CustomExceptions.AccountExistsException;
import com.example.server.CustomExceptions.NotFoundException;
import com.example.server.HttpControllers.UserController;
import com.example.server.Server;
import com.example.server.models.Contact;
import com.example.server.models.Education;
import com.example.server.models.Skill;
import com.example.server.models.User;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.example.server.Server.extractFromPath;

public class UserHandler {
    private static final Gson gson = new Gson();

    public static void postUserHandler(HttpExchange exchange) throws IOException {
        String requestBody = new String(exchange.getRequestBody().readAllBytes());

        User user = gson.fromJson(requestBody, User.class);

        try {
            UserController.addUser(user);
            Server.sendResponse(exchange, 200, "User added successfully");
        } catch (IllegalArgumentException | AccountExistsException e) {
            Server.sendResponse(exchange, 400, e.getMessage());
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void getUserHandler(HttpExchange exchange) throws IOException {
        String email = extractFromPath(exchange.getRequestURI().getPath());

        try {
            User user = UserController.getUser(email);
            Server.sendResponse(exchange, 200, gson.toJson(user));
        } catch (NotFoundException e) {
            Server.sendResponse(exchange, 404, e.getMessage());
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void getAllUserHandler(HttpExchange exchange) throws IOException {
        try {
            ArrayList<User> users = UserController.getAllUsers();
            Server.sendResponse(exchange, 200, gson.toJson(users));
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void getSkillHandler(HttpExchange exchange) throws IOException {
        String email = extractFromPath(exchange.getRequestURI().getPath());

        try {
            Skill skill = UserController.getSkill(email);
            Server.sendResponse(exchange, 200, gson.toJson(skill));
        } catch (NotFoundException e) {
            Server.sendResponse(exchange, 404, e.getMessage());
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void getEducationHandler(HttpExchange exchange) throws IOException {
        String email = extractFromPath(exchange.getRequestURI().getPath());

        try {
            Education education = UserController.getEducation(email);
            Server.sendResponse(exchange, 200, gson.toJson(education));
        } catch (NotFoundException e) {
            Server.sendResponse(exchange, 404, e.getMessage());
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void getAllEducationHandler(HttpExchange exchange) throws IOException {
        String email = extractFromPath(exchange.getRequestURI().getPath());

        try {
            ArrayList<Education> educations = UserController.getAllEducations(email);
            Server.sendResponse(exchange, 200, gson.toJson(educations));
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void getContactHandler(HttpExchange exchange) throws IOException {
        String email = extractFromPath(exchange.getRequestURI().getPath());

        try {
            Contact contact = UserController.getContact(email, email);
            Server.sendResponse(exchange, 200, gson.toJson(contact));
        } catch (NotFoundException e) {
            Server.sendResponse(exchange, 404, e.getMessage());
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }
}
