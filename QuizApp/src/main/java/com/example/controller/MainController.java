package com.example.controller;

import com.example.TimerService.TimerService;
import com.example.alerts.Alert;
import com.example.alerts.AlertList;
import com.example.exceptions.CampiNonCompilatiException;
import com.example.exceptions.PasswordDiverseException;
import com.example.models.Quiz;
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

    public RadioButton[] q1Options;
    public RadioButton[] q2Options;
    public RadioButton[] q3Options;
    public RadioButton[] q4Options;
    @FXML
    private Label welcomeText;

    private LoginController loginController;
    private QuizController QuizController;

    private int currentQuizId ;

    private TimerService timerService;



    private void getQuiz(){

        Quiz currentQuiz = QuizController.getQuiz("facile");


    }
    private void showQuizText(ActionEvent actionEvent) {
        // Mostra il testo del quiz
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/quizapp/testo-view.fxml"));
            Parent root = fxmlLoader.load();


            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        getQuiz();

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
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/quizapp/domanda-risposta-view.fxml"));
            Parent root = fxmlLoader.load();


            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
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
            System.out.println("LOGIN");
        } else {}

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