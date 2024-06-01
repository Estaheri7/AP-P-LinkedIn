package com.example.server.DataValidator;

public class PostValidator {
    public static boolean titleValidator(String title) {
        return title != null && !title.isEmpty();
    }
}
