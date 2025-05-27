package com.example.controller;

import com.example.dao.UserDAOPostgresImp;
import com.example.models.User;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class RegisterController {
    public TextField usernameField;
    public PasswordField passwordField;
    public PasswordField confirmPasswordField;
    public Button registerButton;
    public UserDAOPostgresImp userDAOPostgres;

    public RegisterController(TextField usernameField, PasswordField passwordField, PasswordField confirmPasswordField, Button registerButton) {
        this.usernameField = usernameField;
        this.passwordField = passwordField;
        this.confirmPasswordField = confirmPasswordField;
        this.registerButton = registerButton;
    }

    public void registerUser() throws SQLException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();


        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            System.out.println("All fields are required.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            System.out.println("Passwords do not match.");
            return;
        }
        User user = new User(username,password,"user");
        userDAOPostgres = new UserDAOPostgresImp();
        boolean isRegistered = userDAOPostgres.isRegistered(user);

        if (isRegistered) {
            System.out.println("User registered successfully.");
        } else {
            System.out.println("Registration failed. Username may already exist.");
        }
    }
}
