package com.example.exceptions;

public class UsernameFormatException extends RuntimeException {
    public UsernameFormatException(String message) {
        super(message);
    }
}
