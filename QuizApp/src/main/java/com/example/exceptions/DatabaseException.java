package com.example.exceptions;

import java.sql.SQLException;

public class DatabaseException extends RuntimeException {
    public DatabaseException(String message) {
        super(message);
    }


    public DatabaseException(String message, SQLException ex) {
        super(message, ex);
    }
}
