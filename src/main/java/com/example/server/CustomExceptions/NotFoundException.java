package com.example.server.CustomExceptions;

public class NotFoundException extends Exception {
    public NotFoundException(String message) {
        super(message);
    }
}
