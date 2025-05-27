package com.example.controller;

import com.example.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController {
    @FXML
    private Label welcomeText;

    private LoginController loginController;
    private RegisterController registerController;
    private User user;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void handleLogin(ActionEvent actionEvent) {

    }

    public void handleRegister(ActionEvent actionEvent) {
    }

    public void toggleMode(ActionEvent actionEvent) {
    }

    public void backToLogin(ActionEvent actionEvent) {
    }
}