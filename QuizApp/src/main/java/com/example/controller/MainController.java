package com.example.controller;

import com.example.TimerService.TimerService;
import com.example.alerts.Alert;
import com.example.alerts.AlertList;
import com.example.exceptions.CampiNonCompilatiException;
import com.example.exceptions.PasswordDiverseException;
import com.example.models.Domanda;
import com.example.models.Quiz;
import com.example.models.Risposta;
import com.example.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
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


    public Label timeLabel;
    public Label timeInfoLabel;
    public ProgressBar timeProgressBar;
    public Label displayTextLabel;
    public Label statusLabel;
    public Label titleLabel;

    public Label q1 ;
    public Label q2 ;
    public Label q3 ;
    public Label q4 ;

    public RadioButton q1opt1;
    public RadioButton q1opt2;
    public RadioButton q1opt3;
    public RadioButton q1opt4;

    public RadioButton q2opt1;
    public RadioButton q2opt2;
    public RadioButton q2opt3;
    public RadioButton q2opt4;

    public RadioButton q3opt1;
    public RadioButton q3opt2;
    public RadioButton q3opt3;
    public RadioButton q3opt4;

    public RadioButton q4opt1;
    public RadioButton q4opt2;
    public RadioButton q4opt3;
    public RadioButton q4opt4;

    public RadioButton[] q1Options = {
            q1opt1, q1opt2, q1opt3, q1opt4
    };
    public RadioButton[] q2Options = {
            q2opt1, q2opt2, q2opt3, q2opt4
    };
    public RadioButton[] q3Options = {
            q3opt1, q3opt2, q3opt3, q3opt4
    };
    public RadioButton[] q4Options = {
            q4opt1, q4opt2, q4opt3, q4opt4
    };

    public VBox registerVBox;
    public Button switchModeButton;
    public VBox loginVBox;
    public VBox testoVBox;
    public VBox domandaRispostaVBox;


    public Label titleQuiz;

    public Label usernameWelcomeLabel;
    public VBox difficultyVBox;

    @FXML
    private Label welcomeText;

    private LoginController loginController;
    private QuizController QuizController;

    private int currentQuizId ;

    private TimerService timerService;

    Quiz currentQuiz;

    private void getQuiz(){
         currentQuiz = QuizController.getQuiz("facile");
    }
    private void showQuizText(ActionEvent actionEvent) {
        // Mostra il testo del quiz

        loginVBox.setVisible(false);
        loginVBox.setManaged(false);
        registerVBox.setVisible(false);
        registerVBox.setManaged(false);
        testoVBox.setVisible(true);
        testoVBox.setManaged(true);
        domandaRispostaVBox.setVisible(false);
        domandaRispostaVBox.setManaged(false);

        titleQuiz.setText(currentQuiz.getTitolo());

        displayTextLabel.setText("...");


        //FAI PARTIRE TIMER!

        timerService = new TimerService(
                60,

                () -> {
                    showQuestionsAndAnswers(actionEvent);
                    return;
                }
        );
        timeProgressBar.progressProperty().bind(timerService.progressProperty());
        timerService.start();



    }

    private void showQuestionsAndAnswers(ActionEvent actionEvent) {
        // Mostra le domande e le risposte del quiz
        loginVBox.setVisible(false);
        loginVBox.setManaged(false);
        registerVBox.setVisible(false);
        registerVBox.setManaged(false);
        testoVBox.setVisible(false);
        testoVBox.setManaged(false);
        domandaRispostaVBox.setVisible(true);
        domandaRispostaVBox.setManaged(true);

        Domanda [] domande = currentQuiz.getDomande();

        q1.setText(domande[0].getTesto());
        q2.setText(domande[1].getTesto());
        q3.setText(domande[2].getTesto());
        q4.setText(domande[3].getTesto());

        int i = 0;
        for(RadioButton q1opt : q1Options) {
            q1opt.setText(domande[0].getRisposte()[i].getTesto());
        }
        i=0;
        for(RadioButton q2opt : q2Options) {
            q2opt.setText(domande[1].getRisposte()[i].getTesto());
        }
        i=0;
        for(RadioButton q3opt : q3Options) {
            q3opt.setText(domande[2].getRisposte()[i].getTesto());
        }
        i=0;
        for(RadioButton q4opt : q4Options) {
            q4opt.setText(domande[3].getRisposte()[i].getTesto());
        }


    }


    /*Metodi privati*/
    private void initAuthControllers(){
        loginController = new LoginController();
        QuizController = new QuizController();
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

        RegisterController.registerUser(userToRegister);

        registerVBox.setVisible(false);
        registerVBox.setManaged(false);
        loginVBox.setVisible(true);
        loginVBox.setManaged(true);

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
    }
}