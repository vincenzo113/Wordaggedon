package com.example.exceptions;

public class NotEnoughDocuments extends Exception{
    public NotEnoughDocuments(String message) {
        super(message);
    }
}
