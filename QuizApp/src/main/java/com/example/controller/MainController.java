package com.example.controller;

import com.example.Exceptions.CampiNonCompilatiException;
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

public class MainController {
    @FXML
    public TextField loginUsernameField;
    @FXML
    public PasswordField loginPasswordField;
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
        if(loginUsernameField.getText().trim().equals("") || loginPasswordField.getText().trim().equals("")){
            throw new CampiNonCompilatiException("Perfavore , compila correttamente i seguenti campi");
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