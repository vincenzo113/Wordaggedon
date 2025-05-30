package com.example.controller;

import com.example.alerts.Alert;
import com.example.alerts.AlertList;
import com.example.dao.Documento.DocumentoDAOPostgres;
import com.example.difficultySettings.DifficultyEnum;
import com.example.exceptions.CampiNonCompilatiException;
import com.example.exceptions.PasswordDiverseException;
import com.example.models.Documento;
import com.example.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.List;

public class MainController {

    //DAOs
    private DocumentoDAOPostgres documentoDAOPostgres = new DocumentoDAOPostgres();

    //LOGIN
    public TextField loginUsernameField;
    public PasswordField loginPasswordField;
    public RadioButton easyRadio;
    public RadioButton mediumRadio;
    public RadioButton hardRadio;
    public VBox loginVBox;
    //***********
    //REGISTRAZIONE
    public Button registerButton;
    public PasswordField confirmRegisterPasswordField;
    public PasswordField registerPasswordField;
    public TextField registerUsernameField;
    public VBox registerVBox;
    //**********
    public User user;

    //SCELTA DELLA DIFFICOLTA'
    public Label usernameWelcomeLabel;
    public VBox difficultyVBox;
    public Button addTestoButton;
    //*******


    //Schermata in cui si mostrano il / i testo/i a seconda della difficoltà
    public VBox testoVBox;
    public Label titleQuiz;
    public Label timeLabel;
    public ProgressBar timeProgressBar;
    public Label displayTextLabel;


    //Metodo per creare un utente dai textfields
    private User checkLogin() throws CampiNonCompilatiException {
        if (loginUsernameField.getText().trim().isEmpty() || loginPasswordField.getText().trim().isEmpty()) {
            throw new CampiNonCompilatiException("");
        }

        String username = loginUsernameField.getText();
        String password = loginPasswordField.getText();
        return new User(username,password,false);
    }

    private User checkRegister() throws CampiNonCompilatiException  , PasswordDiverseException {
        if (registerUsernameField.getText().trim().isEmpty() || registerPasswordField.getText().trim().isEmpty() || registerPasswordField.getText().trim().isEmpty()) {
            throw new CampiNonCompilatiException("");
        }

        if(!registerPasswordField.getText().equals(confirmRegisterPasswordField.getText())){
            throw new PasswordDiverseException("");
        }

        String username = registerUsernameField.getText();
        String password = registerPasswordField.getText();
        return new User(username,password,false);
    }

    private DifficultyEnum  getDifficoltaScelta(){
        if(easyRadio.isSelected()) return DifficultyEnum.EASY;
        else if(mediumRadio.isSelected()) return DifficultyEnum.MEDIUM;
        else return  DifficultyEnum.HARD;
    }
    /***********/


    @FXML
    public void initialize(){

    }

    public void handleLogin() {

        User userToLog = null;
        try {
            userToLog = checkLogin();
        } catch (CampiNonCompilatiException e) {
            Alert.showAlert(AlertList.FIELDS_EMPTY);
            return;
        }

        user = LoginController.hasLoginSuccess(userToLog);

        if(user != null) {
            System.out.println("Login avvenuto con successo");
            loginVBox.setVisible(false);
            difficultyVBox.setVisible(true);
            difficultyVBox.setManaged(true);
            if(user.isAdmin()) {
                addTestoButton.setVisible(true);
                addTestoButton.setManaged(true);
            }
            StartGameController.aggiornaLabel(usernameWelcomeLabel , userToLog.getUsername());
        } else {
            Alert.showAlert(AlertList.LOGIN_FAILURE);
            return;
        }

    }

    public void handleRegister(ActionEvent actionEvent) throws SQLException {
        try {
            user = checkRegister();
        } catch (CampiNonCompilatiException e) {
            Alert.showAlert(AlertList.FIELDS_EMPTY);
            return;
        }
        catch (PasswordDiverseException ex){
            Alert.showAlert(AlertList.PASSWORD_MISMATCH);
            return;
        }

        if(RegisterController.hasRegisterSuccess(user)) {
            //Alert.showAlert(AlertList.REGISTER_SUCCESS);
            System.out.println("Registrazione avvenuta con successo");
            registerVBox.setVisible(false);
            registerVBox.setManaged(false);
            difficultyVBox.setVisible(true);
            difficultyVBox.setManaged(true);
        }
        else {
            Alert.showAlert(AlertList.REGISTER_FAILURE);
            return;
        }

    }

    public void backToRegister(ActionEvent actionEvent) {
        registerVBox.setVisible(true);
        registerVBox.setManaged(true);
        loginVBox.setVisible(false);
        loginVBox.setManaged(false);
    }

    public void backToLogin(ActionEvent actionEvent) {
        registerVBox.setVisible(false);
        registerVBox.setManaged(false);
        loginVBox.setVisible(true);
        loginVBox.setManaged(true);
    }


    public void handleStartGame(ActionEvent actionEvent) {
        difficultyVBox.setVisible(false);
        testoVBox.setVisible(true);
        testoVBox.setManaged(true);
        DifficultyEnum diff = getDifficoltaScelta();
        List<Documento> testiDaMostrare = documentoDAOPostgres.getDocumentiPerDifficolta(diff);
        QuizController.startTimerPerTesto(testiDaMostrare ,0, timeLabel , timeProgressBar , diff, displayTextLabel , titleQuiz);
    }


}