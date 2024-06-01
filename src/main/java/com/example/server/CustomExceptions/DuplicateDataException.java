package com.example.server.CustomExceptions;

public class DuplicateDataException extends Exception {
    public DuplicateDataException(String message) {
        super(message);
    }
}
