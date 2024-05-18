package com.example.server.models;

import java.sql.Timestamp;

public class Like {
    private int id;
    private int postId;
    private String email;
    private Timestamp likeTime;

    public Like(int postId, String email) {
        this.postId = postId;
        this.email = email;
    }
    public Like(int id, int postId, String email, Timestamp likeTime) {
        this.id = id;
        this.postId = postId;
        this.email = email;
        this.likeTime = likeTime;
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

    public Timestamp getLikeTime() {
        return likeTime;
    }

    public void setLikeTime(Timestamp likeTime) {
        this.likeTime = likeTime;
    }
}
