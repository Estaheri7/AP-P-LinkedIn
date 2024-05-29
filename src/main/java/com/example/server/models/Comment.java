package com.example.server.models;

import java.sql.Timestamp;

public class Comment {
    private int id;
    private int postId;
    private String email;
    private String userName;
    private String comment;
    private Timestamp commentDate;

    public Comment(int postId, String email, String comment) {
        this.postId = postId;
        this.email = email;
        this.comment = comment;
    }

    public Comment(int postId, String email, String userName, String comment) {
        this.postId = postId;
        this.email = email;
        this.userName = userName;
        this.comment = comment;
    }

    public Comment(int id, int postId, String email, String comment, String userName, Timestamp commentDate ) {
        this.id = id;
        this.postId = postId;
        this.email = email;
        this.userName = userName;
        this.comment = comment;
        this.commentDate = commentDate;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Timestamp commentDate) {
        this.commentDate = commentDate;
    }
}
