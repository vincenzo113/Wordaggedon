package com.example.quizapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController {
    @FXML
    private Label welcomeText;

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