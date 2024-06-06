package com.example.server.models;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Hashtag {
    private int id;
    private int postId;
    private String hashtag;

    public Hashtag(int postId, String hashtag) {
        this.postId = postId;
        this.hashtag = hashtag;
    }

    public Hashtag(int id, int postId, String hashtag) {
        this.id = id;
        this.postId = postId;
        this.hashtag = hashtag;
    }

    public static ArrayList<String> extractHashtag(String content) {
        Pattern hashtagPattern = Pattern.compile("#(\\w+)");
        ArrayList<String> hashtags = new ArrayList<>();
        Matcher matcher = hashtagPattern.matcher(content);

        while (matcher.find()) {
            hashtags.add(matcher.group(1));
        }
        return hashtags;
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

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }
}
