package com.example.server.models;

import java.sql.Timestamp;

public class Like {
    private int id;
    private int postId;
    private String email;
    private String userName;
    private Timestamp likeTime;

    public Like(int postId, String email) {
        this.postId = postId;
        this.email = email;
    }

    public Like(int postId, String email, String userName) {
        this.postId = postId;
        this.email = email;
        this.userName = userName;
    }
    public Like(int id, int postId, String email, String userName, Timestamp likeTime) {
        this.id = id;
        this.postId = postId;
        this.email = email;
        this.userName = userName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Timestamp getLikeTime() {
        return likeTime;
    }

    public void setLikeTime(Timestamp likeTime) {
        this.likeTime = likeTime;
    }
}
