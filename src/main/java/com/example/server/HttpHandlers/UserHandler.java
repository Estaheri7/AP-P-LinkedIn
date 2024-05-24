package com.example.server.HttpHandlers;

import com.example.server.HttpControllers.UserController;
import com.example.server.models.Contact;
import com.example.server.models.Education;
import com.example.server.models.Skill;
import com.example.server.models.User;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.server.Server.extractEmailFromPath;

public class UserHandler {
    private static final Gson gson = new Gson();

    public static void postUserHandler(HttpExchange exchange) throws IOException {
        String requestBody = new String(exchange.getRequestBody().readAllBytes());

        User user = gson.fromJson(requestBody, User.class);

        String response;
        try {
            UserController.addUser(user);
            response = "User added successfully";
            exchange.sendResponseHeaders(200, response.getBytes().length);
        } catch (IllegalArgumentException e) {
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

    public static void getUserHandler(HttpExchange exchange) throws IOException {
        String email = extractEmailFromPath(exchange.getRequestURI().getPath());

        String response;
        try {
            User user = UserController.getUser(email);
            if (user == null) {
                response = "User not found";
                exchange.sendResponseHeaders(404, response.getBytes().length);
            } else {
                response = gson.toJson(user);
                exchange.sendResponseHeaders(200, response.getBytes().length);
            }
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

    public static void getAllUserHandler(HttpExchange exchange) throws IOException {
        String response;
        try {
            ArrayList<User> users = UserController.getAllUsers();
            response = gson.toJson(users);
            exchange.sendResponseHeaders(200, response.getBytes().length);
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

    public static void getSkillHandler(HttpExchange exchange) throws IOException {
        String email = extractEmailFromPath(exchange.getRequestURI().getPath());

        String response;
        try {
            Skill skill = UserController.getSkill(email);
            if (skill == null) {
                response = "User not found";
                exchange.sendResponseHeaders(404, response.getBytes().length);
            } else {
                response = gson.toJson(skill);
                exchange.sendResponseHeaders(200, response.getBytes().length);
            }
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

    public static void getEducationHandler(HttpExchange exchange) throws IOException {
        String email = extractEmailFromPath(exchange.getRequestURI().getPath());

        String response;
        try {
            Education education = UserController.getEducation(email);
            if (education == null) {
                response = "User not found";
                exchange.sendResponseHeaders(404, response.getBytes().length);
            } else {
                response = gson.toJson(education);
                exchange.sendResponseHeaders(200, response.getBytes().length);
            }
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

    public static void getAllEducationHandler(HttpExchange exchange) throws IOException {
        String email = extractEmailFromPath(exchange.getRequestURI().getPath());

        String response;
        try {
            ArrayList<Education> educations = UserController.getAllEducations(email);
            response = gson.toJson(educations);
            exchange.sendResponseHeaders(200, response.getBytes().length);
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

    public static void getContactHandler(HttpExchange exchange) throws IOException {
        String email = extractEmailFromPath(exchange.getRequestURI().getPath());

        String response;
        try {
            Contact contact = UserController.getContact(email);
            if (contact == null) {
                response = "User not found";
                exchange.sendResponseHeaders(404, response.getBytes().length);
            } else {
                response = gson.toJson(contact);
                exchange.sendResponseHeaders(200, response.getBytes().length);
            }
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
