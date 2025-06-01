package com.example.controller;

import com.example.alerts.Alert;
import com.example.alerts.AlertList;
import com.example.dao.Documento.DocumentoDAO;
import com.example.dao.Documento.DocumentoDAOPostgres;
import com.example.difficultySettings.DifficultyEnum;
import com.example.exceptions.CampiNonCompilatiException;
import com.example.exceptions.PasswordDiverseException;
import com.example.models.*;
import com.example.timerService.TimerService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.sql.SQLException;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MainController {


    public Button addTestoButton;
    public CheckBox punteggiPersonaliCheckBox;


    //DAOs
    private DocumentoDAOPostgres documentoDAOPostgres = new DocumentoDAOPostgres();

    //LOGIN
    public TextField loginUsernameField;
    public PasswordField loginPasswordField;
    public VBox loginVBox;
    private LoginController loginController;
    //***********
    //REGISTRAZIONE
    public Button registerButton;
    public PasswordField confirmRegisterPasswordField;
    public PasswordField registerPasswordField;
    public TextField registerUsernameField;
    public VBox registerVBox;
    public Button switchModeButton;

    //TIMER
    public Label timeLabel;
    public Label timeInfoLabel;
    public ProgressBar timeProgressBar;


    public Label displayTextLabel;
    public Label statusLabel;
    public Label titleLabel;

    //DOMANDE
    public Label q1 ;
    public Label q2 ;
    public Label q3 ;
    public Label q4 ;
    //OPZIONI RISPOSTE
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

    //ARRAY DI RISPOSTE
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

    //SCELTA DELLA DIFFICOLTA'
    public Label usernameWelcomeLabel;
    public VBox difficultyVBox;
    public RadioButton easyRadio;
    public RadioButton mediumRadio;
    public RadioButton hardRadio;
    public Button startGameButton;
    private Label welcomeText;
    //*******


    //QUIZ
    public VBox testoVBox;
    public VBox domandaRispostaVBox;
    public Label titleQuiz;
    private QuizController QuizController;
    private SessioneQuiz currentQuiz;
    private User currentUser;

    //SCORE
    public VBox finalScoreVBox;
    public Label scoreLabel;

    //SCOREBOARD
    public TableView<SessioneQuiz> tableView;
    public TableColumn<SessioneQuiz,String> utenteColumn;
    public TableColumn<SessioneQuiz,Integer> scoreColumn;
    public TableColumn<SessioneQuiz,String> difficoltaColumn;
    public VBox scoreboardVBox;
    ObservableList<SessioneQuiz> sessioniQuizList;

    private TimerService timerService;


    private void clearRegisterFields(){
        registerUsernameField.clear();
        registerPasswordField.clear();
        confirmRegisterPasswordField.clear();
    }


    private void  clearLoginFields(){
        loginUsernameField.clear();
        loginPasswordField.clear();
    }

    private void showQuestionsAndAnswers() {

        testoVBox.setVisible(false);
        testoVBox.setManaged(false);
        domandaRispostaVBox.setVisible(true);
        domandaRispostaVBox.setManaged(true);

        currentQuiz.generaDomande();
        List<Domanda> domande = currentQuiz.getDomande();

        q1.setText(domande.get(0).getTesto());
        q2.setText(domande.get(1).getTesto());
        q3.setText(domande.get(2).getTesto());
        q4.setText(domande.get(3).getTesto());

        int i = 0;
        for(RadioButton q1opt : q1Options) {
            q1opt.setText(domande.get(0).getRisposte().get(i).getTesto());
        }
        i=0;
        for(RadioButton q2opt : q2Options) {
            q2opt.setText(domande.get(1).getRisposte().get(i).getTesto());
        }
        i=0;
        for(RadioButton q3opt : q3Options) {
            q3opt.setText(domande.get(2).getRisposte().get(i).getTesto());
        }
        i=0;
        for(RadioButton q4opt : q4Options) {
            q4opt.setText(domande.get(3).getRisposte().get(i).getTesto());
        }


    }


    /*Metodi privati*/

    private void aggiornaTableView(List<SessioneQuiz> sessioni){
        sessioniQuizList = FXCollections.observableList(sessioni);
        tableView.setItems(sessioniQuizList);
        tableView.refresh();
    }

    //Metodo per creare un utente dai textfields
    private User checkLogin() throws CampiNonCompilatiException {
        if (loginUsernameField.getText().trim().isEmpty() || loginPasswordField.getText().trim().isEmpty()) {
            throw new CampiNonCompilatiException("");
        }

        String username = loginUsernameField.getText();
        String password = loginPasswordField.getText();
        return new User(username.trim(),password.trim(),false);
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
        return new User(username.trim(),password.trim(),false);
    }

    private DifficultyEnum getDifficoltaScelta(){
        if(easyRadio.isSelected()) return DifficultyEnum.EASY;
        else if(mediumRadio.isSelected()) return DifficultyEnum.MEDIUM;
        else return  DifficultyEnum.HARD;
    }


    private void initTableView(){
        sessioniQuizList = FXCollections.observableArrayList();
        tableView.setItems(sessioniQuizList);
        utenteColumn.setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getUser().getUsername()) //cellData.getValue() è una SessioneQuiz
                //devi ritornare per forza un valore observable, quindi uso SimpleStringProperty per il nome utente
        );
        scoreColumn.setCellValueFactory(cellData ->
                        new SimpleIntegerProperty(cellData.getValue().getScore()).asObject()
                //perché SimpleIntegerProperty da solo non è un ObservableValue<Integer> — è un ObservableValue<Number>.
                // Ma la colonna vuole esattamente ObservableValue<Integer>. asObject() converte il tipo Number in Integer,
                // che è ciò che la colonna si aspetta.
        );
        difficoltaColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDifficolta().toString())
        );

        scoreColumn.setSortType(TableColumn.SortType.DESCENDING);
        tableView.getSortOrder().setAll(scoreColumn);
        tableView.sort();

        punteggiPersonaliCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            List<SessioneQuiz> sessioni = null;
            if (newVal) {sessioni = QuizController.getPersonalScoreboard(currentQuiz.getUser());}
            else {sessioni = QuizController.getScoreboard();}
            aggiornaTableView(sessioni);
        });


    }
    /***********/




    @FXML
    public void initialize(){
     initTableView();
    }

    public void handleLogin() {

        User userToLog = null;
        try {
            userToLog = checkLogin();
        } catch (CampiNonCompilatiException e) {
            Alert.showAlert(AlertList.FIELDS_EMPTY);
            return;
        }

        userToLog = LoginController.hasLoginSuccess(userToLog);

        if(userToLog != null) {
            System.out.println("Login avvenuto con successo");
            currentUser = userToLog; // Imposta l'utente corrente
            loginVBox.setVisible(false);
            difficultyVBox.setVisible(true);
            difficultyVBox.setManaged(true);
            if(userToLog.isAdmin()) {
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
        clearLoginFields();
        registerVBox.setVisible(true);
        registerVBox.setManaged(true);
        loginVBox.setVisible(false);
        loginVBox.setManaged(false);
    }

    public void backToLogin(ActionEvent actionEvent) {
        clearRegisterFields();
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
        currentQuiz = new SessioneQuiz(testiDaMostrare, diff, currentUser); // Inizializza la sessione quiz con difficoltà scelta
        QuizController.startTimerPerTesto(testiDaMostrare ,0, timeLabel , timeProgressBar , diff, displayTextLabel , titleQuiz ,()->showQuestionsAndAnswers());
    }


    public void addTesto(ActionEvent actionEvent) {
        DocumentoDAO<Documento> documentoDAO = new DocumentoDAOPostgres();
        FileChooser fc = new FileChooser();
        fc.setTitle("Seleziona un file di testo");
        fc.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("File di testo", "*.txt"),
            new FileChooser.ExtensionFilter("Tutti i file", "*.*")
        );
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null && selectedFile.getName().endsWith(".txt")) {
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                String contentClean = reader.lines()
                        .map(String::trim)
                        .filter(line -> !line.isEmpty())
                        .reduce("", (acc, line) -> acc + line + " ")
                        .trim()
                        .replaceAll("\\s+", " ");
                Documento documento = new Documento(selectedFile.getName().split("\\.")[0], contentClean);
                documentoDAO.insertDocumento(documento);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void finishGame(ActionEvent actionEvent) {
        //SETTA LE RISPOSTE SELEZIONATE
        //QuizController.setRisposteSelezionate(q1Options,q2Options,q3Options,q4Options,currentQuiz);
        QuizController.setFinalScore(currentQuiz);
        finalScoreVBox.setVisible(true);
        finalScoreVBox.setManaged(true);
        domandaRispostaVBox.setVisible(false);
        domandaRispostaVBox.setManaged(false);
        scoreLabel.setText("Il tuo punteggio finale è: " + currentQuiz.getScore());



    }

    public void goToScoreboard(ActionEvent actionEvent) throws SQLException {
        if (currentQuiz == null) {
            System.err.println("Error: currentQuiz is null!");
            // Handle the error appropriately - maybe show an alert or return early
            return;
        }
        QuizController.updateScoreboard(currentQuiz);
        aggiornaTableView(QuizController.getScoreboard());
        finalScoreVBox.setManaged(false);
        finalScoreVBox.setVisible(false);
        scoreboardVBox.setManaged(true);
        scoreboardVBox.setVisible(true);

        punteggiPersonaliCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            List<SessioneQuiz> sessioni = null;
            if (newVal) {
                if(currentQuiz==null) System.out.println("NULL!!!!!!");
                sessioni = QuizController.getPersonalScoreboard(currentQuiz.getUser());}
            else {sessioni = QuizController.getScoreboard();}
            aggiornaTableView(sessioni);
        });

    }

}





