package com.example.server.CustomExceptions;

public class AccountExistsException extends Exception {
    public AccountExistsException(String message) {
        super(message);
    }
}
