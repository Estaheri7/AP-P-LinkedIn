package com.example.server.DataValidator;

import com.example.server.models.Post;

public class PostValidator {
    public static boolean titleValidator(String title) {
        return title != null && !title.isEmpty();
    }

    public static boolean isValid(Post post) {
        return titleValidator(post.getTitle());
    }
}
