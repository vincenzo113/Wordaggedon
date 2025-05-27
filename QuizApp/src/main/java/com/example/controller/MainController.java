package com.example.controller;

import com.example.Exceptions.CampiNonCompilatiException;
import com.example.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainController {
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField emailField;
    @FXML
    private Label welcomeText;

    private LoginController loginController;
    private RegisterController registerController;
    private User user;






    /*Metodi privati*/
    private void initAuthControllers(){
        loginController = new LoginController();
        registerController = new RegisterController();
    }

    //Metodo per creare un utente dai textfields
    private User takeUser() throws CampiNonCompilatiException {
        if(usernameField.getText().trim().equals("") || passwordField.getText().trim().equals("")){
            throw new CampiNonCompilatiException("Perfavore , compila correttamente i seguenti campi");
        }
        String username = usernameField.getText();
        String password = passwordField.getText();
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            return; //Per prevenire il login
        }

        loginController.login(userToLog);
    }

    public void handleRegister(ActionEvent actionEvent) {
    }

    public void toggleMode(ActionEvent actionEvent) {
    }

    public void backToLogin(ActionEvent actionEvent) {
    }
}