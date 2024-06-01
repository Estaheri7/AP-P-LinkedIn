package com.example.server.HttpHandlers;

import com.example.server.CustomExceptions.DuplicateDataException;
import com.example.server.CustomExceptions.NotFoundException;
import com.example.server.HttpControllers.PostController;
import com.example.server.HttpControllers.UserController;
import com.example.server.Server;
import com.example.server.models.Comment;
import com.example.server.models.Like;
import com.example.server.models.Post;
import com.example.server.models.User;
import com.example.server.utils.AuthUtil;
import com.example.server.utils.JwtUtil;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.server.Server.extractFromPath;

public class PostHandler {
    private static final Gson gson = new Gson();

    public static void showAllPosts(HttpExchange exchange) throws IOException {
        try {
            ArrayList<Post> posts = PostController.getAllPosts();
            Server.sendResponse(exchange, 200, gson.toJson(posts));
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void addPostHandler(HttpExchange exchange) throws IOException {
        String requestEmail = extractFromPath(exchange.getRequestURI().getPath());

        if (!AuthUtil.authorizeRequest(exchange, requestEmail)) {
            return;
        }

        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        Post post = gson.fromJson(requestBody, Post.class);
        if (post == null) {
            Server.sendResponse(exchange, 404, "Invalid request");
            return;
        }
        post.setAuthor(requestEmail);

        try {
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
        String requestEmail = extractFromPath(exchange.getRequestURI().getPath());

        if (!AuthUtil.authorizeRequest(exchange, requestEmail)) {
            return;
        }

        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        Post post = gson.fromJson(requestBody, Post.class);
        if (post == null) {
            Server.sendResponse(exchange, 404, "Invalid request");
            return;
        }
        post.setId(id);
        post.setAuthor(requestEmail);

        try {
            Post postToUpdate = PostController.getPost(id);
            if (!post.equals(postToUpdate)) {
                Server.sendResponse(exchange, 403, "Permission denied");
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
        String requestEmail = extractFromPath(exchange.getRequestURI().getPath());

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
        String requestEmail = extractFromPath(exchange.getRequestURI().getPath());

        if (!AuthUtil.authorizeRequest(exchange, requestEmail)) {
            return;
        }

        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        Post post = gson.fromJson(requestBody, Post.class);
        if (post == null) {
            Server.sendResponse(exchange, 404, "Invalid request");
            return;
        }
        post.setId(id);
        post.setAuthor(requestEmail);

        try {
            PostController.deletePost(id, post);
            Server.sendResponse(exchange, 200, "Post deleted successfully");
        } catch (NotFoundException e) {
            Server.sendResponse(exchange, 404, e.getMessage());
        } catch (IllegalAccessError e) {
            Server.sendResponse(exchange, 403, e.getMessage());
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void likePostHandler(HttpExchange exchange) throws IOException {
        String viewerEmail = JwtUtil.parseToken(AuthUtil.getTokenFromHeader(exchange));
        if (!AuthUtil.authorizeRequest(exchange, viewerEmail)) {
            return;
        }

        int postId = Integer.parseInt(extractFromPath(exchange.getRequestURI().getPath()));

        try {
            User user = UserController.getUser(viewerEmail);
            Like like = new Like(postId, viewerEmail, user.getFirstName() + " " + user.getLastName());

            PostController.likePost(like);
            Server.sendResponse(exchange, 200, "Liked post successfully");
        } catch (NotFoundException e) {
            Server.sendResponse(exchange, 404, e.getMessage());
        } catch (DuplicateDataException e) {
            Server.sendResponse(exchange, 403, e.getMessage());
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void dislikePostHandler(HttpExchange exchange) throws IOException {
        String viewerEmail = JwtUtil.parseToken(AuthUtil.getTokenFromHeader(exchange));
        if (!AuthUtil.authorizeRequest(exchange, viewerEmail)) {
            return;
        }

        int postId = Integer.parseInt(extractFromPath(exchange.getRequestURI().getPath()));

        try {
            Like like = new Like(postId, viewerEmail);

            PostController.dislikePost(like);
            Server.sendResponse(exchange, 200, "Disliked post successfully");
        } catch (NotFoundException e) {
            Server.sendResponse(exchange, 403, e.getMessage());
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void getLikesHandler(HttpExchange exchange) throws IOException {
        int postId = Integer.parseInt(extractFromPath(exchange.getRequestURI().getPath()));

        try {
            ArrayList<Like> likes = PostController.getAllLikes(postId);
            Server.sendResponse(exchange, 200, gson.toJson(likes));
        } catch (NotFoundException e) {
            Server.sendResponse(exchange, 404, e.getMessage());
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void addCommentHandler(HttpExchange exchange) throws IOException {
        String viewerEmail = JwtUtil.parseToken(AuthUtil.getTokenFromHeader(exchange));
        if (!AuthUtil.authorizeRequest(exchange, viewerEmail)) {
            return;
        }

        int postId = Integer.parseInt(extractFromPath(exchange.getRequestURI().getPath()));
        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        Comment comment = gson.fromJson(requestBody, Comment.class);
        if (comment == null) {
            Server.sendResponse(exchange, 404, "Invalid request");
            return;
        }
        comment.setPostId(postId);
        comment.setEmail(viewerEmail);

        try {
            User user = UserController.getUser(viewerEmail);

            comment.setUserName(user.getFirstName() + " " + user.getLastName());
            PostController.addCommentToPost(comment);
            Server.sendResponse(exchange, 200, "Comment added successfully");
        } catch (NotFoundException e) {
            Server.sendResponse(exchange, 404, e.getMessage());
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void removeCommentHandler(HttpExchange exchange) throws IOException {
        String viewerEmail = JwtUtil.parseToken(AuthUtil.getTokenFromHeader(exchange));
        if (!AuthUtil.authorizeRequest(exchange, viewerEmail)) {
            return;
        }

        int commentId = Integer.parseInt(extractFromPath(exchange.getRequestURI().getPath()));

        try {
            PostController.deleteCommentFromPost(commentId, viewerEmail);
            Server.sendResponse(exchange, 200, "Comment deleted successfully");
        } catch (NotFoundException e) {
            Server.sendResponse(exchange, 404, e.getMessage());
        } catch (IllegalAccessError e) {
            Server.sendResponse(exchange, 403, e.getMessage());
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void updateCommentHandler(HttpExchange exchange) throws IOException {
        String viewerEmail = JwtUtil.parseToken(AuthUtil.getTokenFromHeader(exchange));
        if (!AuthUtil.authorizeRequest(exchange, viewerEmail)) {
            return;
        }

        int commentId = Integer.parseInt(extractFromPath(exchange.getRequestURI().getPath()));

        String requestBody = new String(exchange.getRequestBody().readAllBytes());
        Comment updateComment = gson.fromJson(requestBody, Comment.class);
        if (updateComment == null) {
            Server.sendResponse(exchange, 404, "Invalid request");
            return;
        }
        updateComment.setEmail(viewerEmail);

        try {
            PostController.updateComment(commentId , updateComment);
            Server.sendResponse(exchange, 200, "Comment updated successfully");
        } catch (NotFoundException e) {
            Server.sendResponse(exchange, 404, e.getMessage());
        } catch (IllegalAccessError e) {
            Server.sendResponse(exchange, 403, e.getMessage());
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    public static void getCommentsHandler(HttpExchange exchange) throws IOException {
        int postId = Integer.parseInt(extractFromPath(exchange.getRequestURI().getPath()));

        try {
            ArrayList<Comment> comments = PostController.getAllComments(postId);
            Server.sendResponse(exchange, 200, gson.toJson(comments));
        } catch (NotFoundException e) {
            Server.sendResponse(exchange, 404, e.getMessage());
        } catch (SQLException e) {
            Server.sendResponse(exchange, 500, "Database error: " + e.getMessage());
        } catch (Exception e) {
            Server.sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }
}
