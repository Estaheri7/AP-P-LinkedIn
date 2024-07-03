package com.example.server.models;

import java.sql.Timestamp;

public class Notification {
    private int id;
    private String email;
    private String title;
    private String message;
    private int postId;
    private Timestamp timestamp;

    public Notification(int id, String email, String title, String message, int postId, Timestamp timestamp) {
        this.id = id;
        this.email = email;
        this.title = title;
        this.message = message;
        this.postId = postId;
        this.timestamp = timestamp;
    }

    public Notification(String email, String title, String message, int postId) {
        this.email = email;
        this.title = title;
        this.message = message;
        this.postId = postId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

}
