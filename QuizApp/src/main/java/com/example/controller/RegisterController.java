package com.example.controller;

import com.example.dao.UserDAOPostgres;
import com.example.dao.UserDAOPostgresImp;
import com.example.models.User;
import com.example.utils.AlertPrompt;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class RegisterController {
    UserDAOPostgres userDAOPostgres ;

    public void registerUser(String username , String password, String confirmPassword) throws SQLException {

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            AlertPrompt.compilaCorrettamenteICampi();
            return;
        }

        if (!password.equals(confirmPassword)) {

            return;
        }
        User user = new User(username,password,"user");
        userDAOPostgres = new UserDAOPostgresImp();
        boolean isRegistered = userDAOPostgres.checkRegistered(user);

        if (!isRegistered) {
            System.out.println("User registered successfully.");
        } else {
            System.out.println("Registration failed. Username may already exist.");
        }
    }
}
