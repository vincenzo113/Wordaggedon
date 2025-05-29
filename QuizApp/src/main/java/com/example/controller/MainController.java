package com.example.controller;

import com.example.TimerService.TimerService;
import com.example.alerts.Alert;
import com.example.alerts.AlertList;
import com.example.exceptions.CampiNonCompilatiException;
import com.example.exceptions.PasswordDiverseException;
import com.example.models.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

public class MainController {
    //LOGIN
    public TextField loginUsernameField;
    public PasswordField loginPasswordField;
    private LoginController loginController;
    public VBox loginVBox;
    //***********
    //REGISTRAZIONE
    public Button registerButton;
    public PasswordField confirmRegisterPasswordField;
    public PasswordField registerPasswordField;
    public TextField registerUsernameField;
    public VBox registerVBox;
    //**********

    //SCELTA DELLA DIFFICOLTA'
    public Label usernameWelcomeLabel;
    public VBox difficultyVBox;
    //*******


    //Schermata in cui si mostrano il / i testo/i a seconda della difficoltà
    public VBox testoVBox;
    public Label titleQuiz;
    public Label timeLabel;
    public ProgressBar timeProgressBar;
    public Label displayTextLabel;
    /*******










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
            return;
        }

        boolean loginResult = loginController.hasLoginSuccess(userToLog);

        if(loginResult) {
            loginVBox.setVisible(false);
            difficultyVBox.setVisible(true);
            difficultyVBox.setManaged(true);
            StartGameController.aggiornaLabel(usernameWelcomeLabel , userToLog.getUsername());
        } else {
            Alert.showAlert(AlertList.LOGIN_FAILURE);
            return;
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
        catch (PasswordDiverseException ex){
            Alert.showAlert(AlertList.PASSWORD_MISMATCH);
            return;
        }

        if(RegisterController.hasRegisterSuccess(userToRegister)) {
            Alert.showAlert(AlertList.REGISTER_SUCCESS);
            registerVBox.setVisible(false);
            registerVBox.setManaged(false);
            loginVBox.setVisible(true);
            loginVBox.setManaged(true);
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
        startTimerPerTesto(1);
    }

    private void startTimerPerTesto(int numeroTesto) {
        // Carica il titolo del testo numeroTesto (esempio)
       // QuizController.setTitoloTesto(titleQuiz, getTitoloPerTesto(numeroTesto));

        // Timer da 30 secondi con callback
        TimerService timerService = new TimerService(15, () -> {
            Platform.runLater(() -> {
                timeLabel.setText("Tempo scaduto!");
                // Quando il timer finisce, avvia il prossimo testo se ce n'è un altro
                if (numeroTesto < 2) {
                    startTimerPerTesto(numeroTesto + 1);
                } else {
                    // Fine quiz, nessun altro testo
                    timeLabel.setText("Quiz terminato!");
                }
            });
        });

        timeProgressBar.progressProperty().bind(timerService.progressProperty());

        timerService.progressProperty().addListener((obs, oldVal, newVal) -> {
            int remaining = 30 - (int) (newVal.doubleValue() * 30);
            timeLabel.setText(remaining + "s");
        });

        timerService.start();
    }

    // Metodo di esempio per ottenere il titolo in base al numero testo
    private String getTitoloPerTesto(int numeroTesto) {
        // Qui prendi il titolo dal DB o da lista
        return "Testo " + numeroTesto;
    }
}