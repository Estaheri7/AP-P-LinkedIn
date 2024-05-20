package com.example.server.models;

public class Follow {
    private int id;
    private String follower;
    private String followed;

    public Follow(String follower, String followed) {
        this.follower = follower;
        this.followed = followed;
    }

    public Follow(int id, String follower, String followed) {
        this.id = id;
        this.follower = follower;
        this.followed = followed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public String getFollowed() {
        return followed;
    }

    public void setFollowed(String followed) {
        this.followed = followed;
    }
}
