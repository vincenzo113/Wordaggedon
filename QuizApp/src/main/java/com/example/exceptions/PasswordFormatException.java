package com.example.exceptions;

public class PasswordFormatException extends RuntimeException {
    public PasswordFormatException(String message) {
        super(message);
    }
}
