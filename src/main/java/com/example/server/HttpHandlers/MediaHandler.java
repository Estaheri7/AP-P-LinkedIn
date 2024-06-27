package com.example.server.HttpHandlers;

import com.example.server.CustomExceptions.MediaException;
import com.example.server.CustomExceptions.NotFoundException;
import com.example.server.HttpControllers.MediaController;
import com.example.server.Server;
import com.example.server.utils.AuthUtil;
import com.example.server.utils.JwtUtil;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;

import static com.example.server.Server.extractFromPath;

public class MediaHandler {

    public static void getAvatarHandler(HttpExchange exchange) throws IOException {
        String email = extractFromPath(exchange.getRequestURI().getPath());

        String token = AuthUtil.getTokenFromHeader(exchange);
        if (token == null || !AuthUtil.isTokenValid(exchange, token)) {
            return;
        }

        try {
            File avatar = MediaController.getAvatar(email);
            sendFileResponse(exchange, avatar);
        } catch (NotFoundException e) {
            Server.sendResponse(exchange, 404, e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void getBackgroundHandler(HttpExchange exchange) throws IOException {
        String email = extractFromPath(exchange.getRequestURI().getPath());

        String token = AuthUtil.getTokenFromHeader(exchange);
        if (token == null || !AuthUtil.isTokenValid(exchange, token)) {
            return;
        }

        try {
            File background = MediaController.getBackground(email);
            sendFileResponse(exchange, background);
        } catch (NotFoundException e) {
            Server.sendResponse(exchange, 404, e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void updateAvatarHandler(HttpExchange exchange) throws IOException {
        String requestEmail = extractFromPath(exchange.getRequestURI().getPath());

        if (!AuthUtil.authorizeRequest(exchange, requestEmail)) {
            return;
        }

        try {
            MediaController.updateAvatar(requestEmail, createFile(exchange, requestEmail, MediaController.AVATARS));
            Server.sendResponse(exchange, 200, "Avatar changed successfully");
        } catch (NotFoundException e) {
            Server.sendResponse(exchange, 404, e.getMessage());
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void updateBackgroundHandler(HttpExchange exchange) throws IOException {
        String requestEmail = extractFromPath(exchange.getRequestURI().getPath());

        if (!AuthUtil.authorizeRequest(exchange, requestEmail)) {
            return;
        }

        try {
            MediaController.updateBackground(requestEmail, createFile(exchange, requestEmail, MediaController.BACKGROUNDS));
            Server.sendResponse(exchange, 200, "Background changed successfully");
        } catch (NotFoundException e) {
            Server.sendResponse(exchange, 404, e.getMessage());
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void addMediaToPostHandler(HttpExchange exchange) throws IOException {
        HashMap<String, String> queryParams = (HashMap<String, String>) exchange.getAttribute("queryParams");

        int postId = Integer.parseInt(queryParams.get("id"));
        String requestEmail = extractFromPath(exchange.getRequestURI().getPath());

        if (!AuthUtil.authorizeRequest(exchange, requestEmail)) {
            return;
        }

        try {
            MediaController.addMediaToPost(postId, createFile(exchange, Integer.toString(postId), MediaController.POST_MEDIA));
            Server.sendResponse(exchange, 200, "Media added successfully");
        } catch (NotFoundException e) {
            Server.sendResponse(exchange, 404, e.getMessage());
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void sendMediaChatHandler(HttpExchange exchange) throws IOException {
        String token = AuthUtil.getTokenFromHeader(exchange);
        if (token == null || !AuthUtil.isTokenValid(exchange, token)) {
            return;
        }

        String sender = JwtUtil.parseToken(token);
        String receiver = extractFromPath(exchange.getRequestURI().getPath());
        if (!AuthUtil.isUserAuthorized(exchange, token, sender)) {
            return;
        }

        try {
            String unique = sender + "_to_" + receiver;
            MediaController.sendMediaInChat(sender, receiver, createFile(exchange, unique, MediaController.CHAT_MEDIA));
            Server.sendResponse(exchange, 200, "File sent successfully");
        } catch (NotFoundException e) {
            Server.sendResponse(exchange, 404, e.getMessage());
        } catch (IllegalArgumentException e) {
            Server.sendResponse(exchange, 400, e.getMessage());
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    private static String createFile(HttpExchange exchange, String unique, String directory) throws IOException {
        try {
            String contentType = exchange.getRequestHeaders().getFirst("Content-Type");
            if (contentType == null || !contentType.contains("multipart/form-data")) {
                throw new MediaException("Invalid Content-Type");
            }

            String boundary = extractBoundary(contentType);
            if (boundary == null) {
                throw new MediaException("Missing boundary");
            }

            InputStream requestBody = exchange.getRequestBody();
            byte[] bodyBytes = requestBody.readAllBytes();
            byte[] boundaryBytes = ("--" + boundary).getBytes();
            int pos = indexOf(bodyBytes, boundaryBytes, 0);

            while (pos >= 0) {
                int start = pos + boundaryBytes.length + 2;
                pos = indexOf(bodyBytes, boundaryBytes, start);
                if (pos < 0) {
                    break;
                }

                int end = pos - 2;
                byte[] part = Arrays.copyOfRange(bodyBytes, start, end);
                int headerEnd = indexOf(part, "\r\n\r\n".getBytes(), 0);
                if (headerEnd < 0) {
                    break;
                }

                byte[] headers = Arrays.copyOfRange(part, 0, headerEnd);
                byte[] data = Arrays.copyOfRange(part, headerEnd + 4, part.length);
                String headersString = new String(headers);

                if (headersString.contains("Content-Disposition: form-data;")) {
                    String[] fileParts = extractFilename(headersString).split("\\.");
                    String filename =  unique + "." + fileParts[fileParts.length - 1];
                    File dir = new File(directory);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    File file = new File(dir, filename);
                    try (FileOutputStream fos = new FileOutputStream(file)) {
                        fos.write(data);
                        return dir + "/" + filename;
                    } catch (Exception e) {
                        Server.sendResponse(exchange, 500, e.getMessage());
                    }
                }
            }

        } catch (Exception e) {
            Server.sendResponse(exchange, 500, e.getMessage());
        }
        return null;
    }

    private static String extractBoundary(String contentType) {
        String[] params = contentType.split(";");
        for (String param : params) {
            param = param.trim();
            if (param.startsWith("boundary=")) {
                return param.substring("boundary=".length());
            }
        }
        return null;
    }

    private static String extractFilename(String headers) {
        String[] lines = headers.split("\r\n");
        for (String line : lines) {
            if (line.startsWith("Content-Disposition:")) {
                String[] parts = line.split(";");
                for (String part : parts) {
                    part = part.trim();
                    if (part.startsWith("filename=\"")) {
                        return part.substring("filename=\"".length(), part.length() - 1);
                    }
                }
            }
        }
        return null;
    }

    private static int indexOf(byte[] array, byte[] target, int start) {
        outer:
        for (int i = start; i < array.length - target.length + 1; i++) {
            for (int j = 0; j < target.length; j++) {
                if (array[i + j] != target[j]) {
                    continue outer;
                }
            }
            return i;
        }
        return -1;
    }

    private static void sendFileResponse(HttpExchange exchange, File file) throws IOException {
        exchange.sendResponseHeaders(200, file.length());
        try (FileInputStream fis = new FileInputStream(file);
             OutputStream os = exchange.getResponseBody()) {
            byte[] buffer = new byte[1024];
            int count;
            while ((count = fis.read(buffer)) >= 0) {
                os.write(buffer, 0, count);
            }
        }
    }
}
