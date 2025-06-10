package com.example.exceptions;

public class PasswordNonCorrettaException extends RuntimeException {
    public PasswordNonCorrettaException(String message) {
        super(message);
    }
}
