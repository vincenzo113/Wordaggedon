package com.example.controller;

import com.example.alerts.Alert;
import com.example.alerts.AlertList;
import com.example.exceptions.CampiNonCompilatiException;
import com.example.models.User;
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






    /*Metodi privati*/
    private void initAuthControllers(){
        loginController = new LoginController();
    }

    //Metodo per creare un utente dai textfields
    private User checkLogin() throws CampiNonCompilatiException {
        if (loginUsernameField.getText().trim().isEmpty() || loginPasswordField.getText().trim().isEmpty()) {
            throw new CampiNonCompilatiException("");
        }

        String username = loginUsernameField.getText();
        String password = loginPasswordField.getText();
        return new User(username,password,false);
    }

    private User checkRegister() throws CampiNonCompilatiException {
        if (registerUsernameField.getText().trim().isEmpty() || registerPasswordField.getText().trim().isEmpty() || registerPasswordField.getText().trim().isEmpty()) {
            throw new CampiNonCompilatiException("");
        }

        if(!registerPasswordField.getText().equals(confirmRegisterPasswordField.getText())){
            throw new CampiNonCompilatiException(""); //sistemare con nuova eccezione
        }

        String username = registerUsernameField.getText();
        String password = registerPasswordField.getText();
        return new User(username,password,false);
    }


    /***********/


    @FXML
    public void initialize(){
        initAuthControllers();
    }

    public void handleLogin() {
        User userToLog = null;
        try {
             userToLog = checkLogin();
        } catch (CampiNonCompilatiException e) {
            Alert.showAlert(AlertList.FIELDS_EMPTY);
            return; //Per prevenire il login
        }

        boolean cond = loginController.login(userToLog);

        if(cond) { //VAI ALLA SCHERMATA
            //
        }
    }

    public void handleRegister(ActionEvent actionEvent) throws SQLException {
        User userToRegister = null;
        try {
            userToRegister = checkRegister();
        } catch (CampiNonCompilatiException e) {
            Alert.showAlert(AlertList.FIELDS_EMPTY);
            return;
        }

        RegisterController.registerUser(userToRegister);

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

    private void goToRegister() {

    }
}