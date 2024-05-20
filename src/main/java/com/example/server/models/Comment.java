package com.example.server.models;

public class Comment {
    private int id;
    private int postId;
    private String email;
    private String comment;

    public Comment(int postId, String email, String comment) {
        this.postId = postId;
        this.email = email;
        this.comment = comment;
    }

    public Comment(int id, int postId, String email, String comment) {
        this.id = id;
        this.postId = postId;
        this.email = email;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
