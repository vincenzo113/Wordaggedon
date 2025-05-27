package com.example.controller;

import com.example.exceptions.CampiNonCompilatiException;
import com.example.models.User;
import com.example.utils.AlertPrompt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class MainController {
    @FXML
    public TextField loginUsernameField;
    @FXML
    public PasswordField loginPasswordField;
    @FXML
    public Button registerButton;
    public PasswordField confirmRegisterPasswordField;
    public PasswordField registerPasswordField;
    public TextField registerUsernameField;
    @FXML
    private Label welcomeText;

    private LoginController loginController;
    private RegisterController registerController;






    /*Metodi privati*/
    private void initAuthControllers(){
        loginController = new LoginController();
        registerController = new RegisterController();
    }

    //Metodo per creare un utente dai textfields
    private User takeUser() throws CampiNonCompilatiException {
        if (loginUsernameField.getText().trim().isEmpty() || loginPasswordField.getText().trim().isEmpty()) {
            throw new CampiNonCompilatiException("");
        }


        String username = loginUsernameField.getText();
        String password = loginPasswordField.getText();
        return new User(username,password,null);
    }


    /***********/


    @FXML
    public void initialize(){
        initAuthControllers();
    }

    public void handleLogin() {
        User userToLog = null;
        try {
             userToLog = takeUser();
        } catch (CampiNonCompilatiException e) {
            AlertPrompt.compilaCorrettamenteICampi();
            return; //Per prevenire il login
        }

        loginController.login(userToLog);
    }

    public void handleRegister(ActionEvent actionEvent) throws SQLException {
        registerController.registerUser(registerUsernameField.getText().trim(),
                registerPasswordField.getText().trim(), confirmRegisterPasswordField.getText().trim());
    }

    public void backToRegister(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/quizapp/registrazione-view.fxml"));
            Parent root = fxmlLoader.load();


            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void backToLogin(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/quizapp/login-view.fxml"));
            Parent root = fxmlLoader.load();


            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}