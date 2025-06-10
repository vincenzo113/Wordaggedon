package com.example.controller;

import com.example.alerts.AlertUtils;
import com.example.alerts.AlertList;
import com.example.dao.Documento.DocumentoDAO;
import com.example.dao.Documento.DocumentoDAOPostgres;
import com.example.dao.Domande.DomandeDAOPostgres;
import com.example.dao.User.UserDAOPostgres;
import com.example.dao.stopWordsDAO.StopWordsDAOPostgres;
import com.example.difficultySettings.DifficultyEnum;
import com.example.exceptions.*;
import com.example.models.*;
import com.example.utils.GestoreSalvataggioSessione;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;

import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MainController {
    public ToggleGroup gruppoDomanda1;
    public ToggleGroup gruppoDomanda2;
    public ToggleGroup gruppoDomanda3;
    public ToggleGroup gruppoDomanda4;
    public PasswordField passwordFieldSettings;
    public PasswordField passwordFieldNewSettings;
    public Button changePasswordButton;
    public VBox sezioneDocumenti;
    public VBox documentsList;
    private StringProperty initialUsernameProperty = new SimpleStringProperty();
    private Set<Documento> documentiAggiunti = new HashSet<>();
    public ToggleButton facileButton;
    public ToggleButton medioButton;
    public ToggleButton difficileButton;

    //Sezione di riepilogo
    public VBox riepilogoVBox;
    public Label domanda4Label;
    public Button restartGameButton;
    public Button goBackButton;

    public Label risposta4UtenteLabel;
    public Label risposta4CorrettaLabel;
    public Label domanda3Label;
    public Label risposta3UtenteLabel;
    public Label risposta3CorrettaLabel;
    public Label domanda2Label;
    public Label risposta2UtenteLabel;
    public Label risposta2CorrettaLabel;
    public Label domanda1Label;
    public Label risposta1UtenteLabel;
    public Label risposta1CorrettaLabel;
    public Button nextButton;
    public PasswordField currentPasswordField;
    public TextField usernameFieldSettings;
    public Button saveUsernameSettings;


    /// /////////////////////////////
    private ToggleGroup difficoltaToggleGroup;

    public Label punteggioMedioLabel;
    public Label migliorPunteggioLabel;
    public Label numeroPartiteLabel;

    public CheckBox punteggiPersonaliCheckBox;


    //DAOs
    private DocumentoDAOPostgres documentoDAOPostgres = new DocumentoDAOPostgres();

    //LOGIN
    public TextField loginUsernameField;
    public PasswordField loginPasswordField;
    public VBox loginVBox;
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
    public ProgressBar timeProgressBar;


    public Label displayTextLabel;

    public Label titleLabel;

    //DOMANDE
    public Label q1;
    public Label q2;
    public Label q3;
    public Label q4;
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

    //4 opzioni per domanda
    private RadioButton[] q1Options;
    private RadioButton[] q2Options;
    private RadioButton[] q3Options;
    private RadioButton[] q4Options;

    //SCELTA DELLA DIFFICOLTA'
    public Label usernameWelcomeLabel;
    public VBox difficultyVBox;
    public RadioButton easyRadio;
    public RadioButton mediumRadio;
    public RadioButton hardRadio;
    public Button startGameButton;
    //*******


    //QUIZ
    public VBox testoVBox;
    public VBox domandaRispostaVBox;
    public Label titleQuiz;
    private SessioneQuiz currentQuiz;
    private User currentUser;

    //SCORE
    public VBox finalScoreVBox;
    public Label scoreLabel;

    //SCOREBOARD
    public TableView<SessioneQuiz> tableView;
    public TableColumn<SessioneQuiz, String> utenteColumn;
    public TableColumn<SessioneQuiz, Integer> scoreColumn;
    public VBox scoreboardVBox;
    ObservableList<SessioneQuiz> sessioniQuizList;

    //Settings
    public VBox settingsVBox;
    public Button addTestoButton;
    public Button addStopwordsButton;
    public VBox adminSection;




    private void clearRegisterFields() {
        registerUsernameField.clear();
        registerPasswordField.clear();
        confirmRegisterPasswordField.clear();
    }

    private void clearLoginFields() {
        loginUsernameField.clear();
        loginPasswordField.clear();
    }

    private void clearQuizFields() {
        q1opt1.setSelected(false);
        q1opt2.setSelected(false);
        q1opt3.setSelected(false);
        q1opt4.setSelected(false);

        q2opt1.setSelected(false);
        q2opt2.setSelected(false);
        q2opt3.setSelected(false);
        q2opt4.setSelected(false);

        q3opt1.setSelected(false);
        q3opt2.setSelected(false);
        q3opt3.setSelected(false);
        q3opt4.setSelected(false);

        q4opt1.setSelected(false);
        q4opt2.setSelected(false);
        q4opt3.setSelected(false);
        q4opt4.setSelected(false);
    }

    private void showQuestionsAndAnswers() {
        testoVBox.setVisible(false);
        testoVBox.setManaged(false);
        domandaRispostaVBox.setVisible(true);
        domandaRispostaVBox.setManaged(true);
        currentQuiz.generaDomande();

        List<Domanda> domande = currentQuiz.getDomande();

        //Setta il testo per le domande
        List<Label> domandeLabels =Arrays.asList(q1,q2,q3,q4);
        for(int i = 0 ; i < domandeLabels.size() ; i++){
            domandeLabels.get(i).setText(domande.get(i).getTesto());
        }

        //Struttura che mantiene , per ogni domanda
        RadioButton[][] allOptions = { q1Options, q2Options, q3Options, q4Options };

        //Per ogni domanda , settiamo l'array di opzioni associati
        for (int i = 0; i < allOptions.length; i++) {
            List<Risposta> risposte = domande.get(i).getRisposte();
            RadioButton[] opzioni = allOptions[i];
            //Settiamo le risposte a tutte le opzioni della i-esima domanda
            for (int j = 0; j < opzioni.length; j++) {
                opzioni[j].setText(risposte.get(j).getTesto());
            }
        }


    }


    public SessioneQuiz getSessioneCorrente() {
        return currentQuiz;
    }


    /*Metodi privati*/

    private void setupToggleButtons() {
        // Crea il ToggleGroup per i bottoni di difficoltà
        difficoltaToggleGroup = new ToggleGroup();

        facileButton.setToggleGroup(difficoltaToggleGroup);
        medioButton.setToggleGroup(difficoltaToggleGroup);
        difficileButton.setToggleGroup(difficoltaToggleGroup);

        // Imposta difficolta di default
        //da sistemare sembra dia sempre null
        if (currentQuiz == null || currentQuiz.getDifficolta() == DifficultyEnum.EASY)
            facileButton.setSelected(true);
        else if (currentQuiz.getDifficolta() == DifficultyEnum.MEDIUM)
            medioButton.setSelected(true);
        else
            difficileButton.setSelected(true);

        // Aggiungi listener per il cambio di selezione
        difficoltaToggleGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                updateDisplayedData();
                updateToggleButtonStyles();
            } else {
                // Se nessun bottone è selezionato, riseleziona "FACILE"
                facileButton.setSelected(true);
            }
        });

        // Listener per il checkbox punteggi personali
        punteggiPersonaliCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            updateDisplayedData();
        });
    }

    private void updateToggleButtonStyles() {
        // Reset stili e applica colori di base
        facileButton.setStyle("-fx-background-color: #8BC34A; -fx-text-fill: white;");
        medioButton.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white;");
        difficileButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;");

        // Applica stile al bottone selezionato
        ToggleButton selected = (ToggleButton) difficoltaToggleGroup.getSelectedToggle();
        if (selected != null) {
            selected.setStyle(selected.getStyle() + "; -fx-font-weight: bold; -fx-border-color: #333333; -fx-border-width: 2px;");
        }
    }

    private void updateDisplayedData() {
        ToggleButton selected = (ToggleButton) difficoltaToggleGroup.getSelectedToggle();
        DifficultyEnum diff;
        if (selected == null)
            diff = DifficultyEnum.EASY;
        else if (selected == facileButton)
            diff = DifficultyEnum.EASY;
        else if (selected == medioButton)
            diff = DifficultyEnum.MEDIUM;
        else diff = DifficultyEnum.HARD;

        boolean soloPersonali = punteggiPersonaliCheckBox.isSelected();

        // Filtra i dati in base alla difficoltà e al checkbox
        List<SessioneQuiz> sessioni;
        if (!soloPersonali)
            sessioni = QuizController.getScoreboard(diff);
        else sessioni = QuizController.getPersonalScoreboard(currentUser, diff);

        // Aggiorna la tabella
        aggiornaTableView(sessioni);

        // Aggiorna le statistiche
        aggiornaStats(diff);
    }

    private void aggiornaTableView(List<SessioneQuiz> sessioni) {
        sessioniQuizList = FXCollections.observableList(sessioni);
        tableView.setItems(sessioniQuizList);
        tableView.refresh();
    }

    private void aggiornaStats(DifficultyEnum difficultyEnum) {
        punteggioMedioLabel.setText(QuizController.getPunteggioMedio(currentUser, difficultyEnum));
        migliorPunteggioLabel.setText(QuizController.getMigliorPunteggio(currentUser, difficultyEnum));
        numeroPartiteLabel.setText(QuizController.getPartite(currentUser));
    }

    //Metodo per creare un utente dai textfields
    private User getLoggedUser() throws CampiNonCompilatiException {

        if (loginUsernameField.getText().trim().isEmpty() || loginPasswordField.getText().trim().isEmpty()) {
            throw new CampiNonCompilatiException("");
        }

        String username = loginUsernameField.getText();
        String password = loginPasswordField.getText();
        return new User(username.trim(), password.trim(), false);
    }

    private User checkRegister(Stage stage) throws CampiNonCompilatiException, PasswordDiverseException {
        if (registerUsernameField.getText().trim().isEmpty() || registerPasswordField.getText().trim().isEmpty() || registerPasswordField.getText().trim().isEmpty()) {
            throw new CampiNonCompilatiException("");
        }

        if (!registerPasswordField.getText().equals(confirmRegisterPasswordField.getText())) {
            throw new PasswordDiverseException("");
        }

        if (!registerUsernameField.getText().matches("^[A-Za-z0-9_]+$")) {
            throw new UsernameFormatException("Formato dell'username non valido");
        }

        String username = registerUsernameField.getText();
        String password = registerPasswordField.getText();
        return new User(username.trim(), password.trim(), false);
    }

    private DifficultyEnum getDifficoltaScelta() {
        if (easyRadio.isSelected()) return DifficultyEnum.EASY;
        else if (mediumRadio.isSelected()) return DifficultyEnum.MEDIUM;
        else return DifficultyEnum.HARD;
    }

    private void initTableView() {
        sessioniQuizList = FXCollections.observableArrayList();
        tableView.setItems(sessioniQuizList);
        utenteColumn.setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getUser().getUsername()) //cellData.getValue() è una SessioneQuiz
                //devi ritornare per forza un valore observable, quindi uso SimpleStringProperty per il nome utente
        );
        scoreColumn.setCellValueFactory(cellData ->
                        new SimpleIntegerProperty(cellData.getValue().getScore()).asObject()
                //perche SimpleIntegerProperty da solo non è un ObservableValue<Integer> — è un ObservableValue<Number>.
                // Ma la colonna vuole esattamente ObservableValue<Integer>. asObject() converte il tipo Number in Integer,
                // che è ciò che la colonna si aspetta.
        );

        scoreColumn.setSortType(TableColumn.SortType.DESCENDING);
        tableView.getSortOrder().setAll(scoreColumn);
        tableView.sort();


    }

    /***********/


    @FXML
    public void initialize() {
        initTableView();
        q1Options = new RadioButton[]{
                q1opt1, q1opt2, q1opt3, q1opt4
        };
        q2Options = new RadioButton[]{
                q2opt1, q2opt2, q2opt3, q2opt4
        };
        q3Options = new RadioButton[]{
                q3opt1, q3opt2, q3opt3, q3opt4
        };
        q4Options = new RadioButton[]{
                q4opt1, q4opt2, q4opt3, q4opt4
        };

        setupToggleButtons();
        initBindings();


    }


    private void initBindings() {
        BooleanBinding tutteRisposteDate = Bindings.createBooleanBinding(
                () -> gruppoDomanda1.getSelectedToggle() != null &&
                        gruppoDomanda2.getSelectedToggle() != null &&
                        gruppoDomanda3.getSelectedToggle() != null &&
                        gruppoDomanda4.getSelectedToggle() != null,
                gruppoDomanda1.selectedToggleProperty(),
                gruppoDomanda2.selectedToggleProperty(),
                gruppoDomanda3.selectedToggleProperty(),
                gruppoDomanda4.selectedToggleProperty()
        );

        nextButton.disableProperty().bind(tutteRisposteDate.not());
        saveUsernameSettings.disableProperty().bind(
                usernameFieldSettings.textProperty().isEmpty()
        );
        BooleanBinding disableButtonPassword = Bindings.createBooleanBinding(() ->
                        passwordFieldSettings.getText().trim().isEmpty() ||
                                passwordFieldNewSettings.getText().trim().isEmpty(),
                passwordFieldSettings.textProperty(),
                passwordFieldNewSettings.textProperty()
        );

        changePasswordButton.disableProperty().bind(disableButtonPassword);


    }

    public void handleLogin(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        User userToLog = null;
        try {
            userToLog = getLoggedUser();
        } catch (CampiNonCompilatiException e) {
            AlertUtils.showAlert(AlertList.FIELDS_EMPTY, stage);
            return;
        }

        userToLog = LoginController.hasLoginSuccess(userToLog);

        if (userToLog != null) {
            currentUser = userToLog; // Imposta l'utente corrente
            loginVBox.setVisible(false);
            loginVBox.setManaged(false);

            difficultyVBox.setVisible(true);
            difficultyVBox.setManaged(true);
            StartGameController.aggiornaLabel(usernameWelcomeLabel, userToLog.getUsername());
            usernameFieldSettings.setText(userToLog.getUsername());

        } else {
            AlertUtils.showAlert(AlertList.LOGIN_FAILURE, stage);
            return;
        }

    }

    public void handleRegister(ActionEvent actionEvent) throws SQLException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        User userToRegister = null;
        try {
            userToRegister = checkRegister(stage);
        } catch (CampiNonCompilatiException e) {
            AlertUtils.showAlert(AlertList.FIELDS_EMPTY, stage);
            return;
        } catch (PasswordDiverseException ex) {
            AlertUtils.showAlert(AlertList.PASSWORD_MISMATCH, stage);
            return;
        } catch (UsernameFormatException ufx) {
            AlertUtils.showAlert(AlertList.USERNAME_FORMAT_NON_CORRETTO, stage);
            return;
        }

        if (RegisterController.hasRegisterSuccess(userToRegister)) {
            registerVBox.setVisible(false);
            registerVBox.setManaged(false);
            loginVBox.setVisible(true);
            loginVBox.setManaged(true);
        } else {
            AlertUtils.showAlert(AlertList.REGISTER_FAILURE, stage);
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

    public void handleLogout(ActionEvent actionEvent) {
        clearLoginFields();
        difficultyVBox.setVisible(false);
        difficultyVBox.setManaged(false);
        loginVBox.setVisible(true);
        loginVBox.setManaged(true);
        currentUser = null;
    }

    private boolean hasSessionSuspended(User user) {
        File file = new File("salvataggio_" + user.getUsername() + ".dat");
        return file.exists();
    }

    public void handleStartGame(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        List<Documento> testiDaMostrare;
        DifficultyEnum diff = getDifficoltaScelta();
        // Se l'utente ha una sessione in sospeso
        if (hasSessionSuspended(currentUser)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Vuoi riprendere la sessione salvata?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Sessione sospesa trovata");
            alert.setHeaderText(null);
            Optional<ButtonType> result = alert.showAndWait();
            //L'utente vuole procedere con la sessione recuperata
            if (result.isPresent() && result.get() == ButtonType.YES) {
                System.out.println("Current user: " + currentUser);
                try {
                    SessioneQuiz sessioneRecuperata = GestoreSalvataggioSessione.loadSessione(currentUser.getUsername());
                    sessioneRecuperata.setDomandeDAOPostgres(new DomandeDAOPostgres());
                    System.out.println("Sessione recuperata: " + sessioneRecuperata);
                    currentQuiz = sessioneRecuperata;
                    QuizController.startTimerPerTesto(currentQuiz.getDocumenti(), 0, timeLabel, timeProgressBar, currentQuiz.getDifficolta(), displayTextLabel, titleQuiz, () -> showQuestionsAndAnswers());
                    //Elimino la sessione salvata dopo averla ripresa
                    GestoreSalvataggioSessione.eliminaSessione(currentUser.getUsername());
                } catch (SessioneNonCaricataException ex) {
                    AlertUtils.showAlert(AlertList.SESSIONE_NON_CARICATA, stage);
                    return;
                }

            } else {
                //Se non vuole continuare , eliminiamo la sessione vecchia e ne creiamo una nuova
                GestoreSalvataggioSessione.eliminaSessione(currentUser.getUsername());
                try {
                     testiDaMostrare = documentoDAOPostgres.getDocumentiPerDifficolta(diff);
                }catch (NotEnoughDocuments ex){
                    System.out.println("[DEBUG] Non abbastanza documenti per difficoltà " + diff);
                    AlertUtils.showAlert(AlertList.NON_ABBASTANZA_DOCUMENTI,stage);
                    return;
                }
                SessioneQuiz sessioneQuiz = new SessioneQuiz(testiDaMostrare, diff, currentUser);
                sessioneQuiz.setDomandeDAOPostgres(new DomandeDAOPostgres());
                currentQuiz = sessioneQuiz; // Inizializza la sessione quiz con difficoltà scelta
                QuizController.startTimerPerTesto(testiDaMostrare, 0, timeLabel, timeProgressBar, diff, displayTextLabel, titleQuiz, () -> showQuestionsAndAnswers());

            }
            //Setta le view
            difficultyVBox.setVisible(false);
            difficultyVBox.setManaged(false);
            testoVBox.setVisible(true);
            testoVBox.setManaged(true);
            return;


        }
        //Non aveva una sessione da recuperare , quindi va normalmente

        try {
            testiDaMostrare = documentoDAOPostgres.getDocumentiPerDifficolta(diff);
        } catch (NotEnoughDocuments e) {
            System.out.println("[DEBUG] Non abbastanza documenti per difficoltà " + diff);
            AlertUtils.showAlert(AlertList.NON_ABBASTANZA_DOCUMENTI , stage);
            System.out.println("Eccezione "+e.getMessage()+" lanciata");
            return;
        }
        SessioneQuiz sessioneQuiz = new SessioneQuiz(testiDaMostrare, diff, currentUser);
        sessioneQuiz.setDomandeDAOPostgres(new DomandeDAOPostgres());
        currentQuiz = sessioneQuiz;
        System.out.println("Eccezione non lanciata");
        QuizController.startTimerPerTesto(testiDaMostrare, 0, timeLabel, timeProgressBar, diff, displayTextLabel, titleQuiz, () -> showQuestionsAndAnswers());
        difficultyVBox.setVisible(false);
        difficultyVBox.setManaged(false);
        testoVBox.setVisible(true);
        testoVBox.setManaged(true);
    }


    public void addTesto(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        DocumentoDAO<Documento> documentoDAO = new DocumentoDAOPostgres();
        FileChooser fc = new FileChooser();
        fc.setTitle("Seleziona un file di testo");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("File di testo", "*.txt"),
                new FileChooser.ExtensionFilter("Tutti i file", "*.*")
        );
        File selectedFile = fc.showOpenDialog(stage);
        if (selectedFile != null && selectedFile.getName().endsWith(".txt")) {
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                String content = reader.lines()
                        .map(String::trim) //per togliere gli spazi iniziali e finali
                        .filter(line -> !line.isEmpty()) //elimina linee vuote
                        .map(line -> line.replaceAll("[\\s+]", " "))  //elimina spazi in più
                        .reduce("", (acc, line) -> acc + " " + line);
                // "" valore di partenza , acc contenuto accumulato in precedenza, line è la riga corrente
                // accumulo le righe in un'unica stringa

                Documento documento = new Documento(selectedFile.getName().split("\\.")[0], content);
                String[] contentClean = content.split("[\\p{Punct}\\s]+");
                if (contentClean.length < 10) {
                    AlertUtils.showAlert(AlertList.SHORT_TEXT, stage);
                    return;
                }
                if (contentClean.length > 100) documento.setDifficolta(DifficultyEnum.HARD);
                else if (contentClean.length > 50) documento.setDifficolta(DifficultyEnum.MEDIUM);
                else documento.setDifficolta(DifficultyEnum.EASY);

                documentoDAO.insertDocumento(documento);
            } catch (DatabaseException e) {
                System.out.println("Eccezione lanciata");
                AlertUtils.showAlert(AlertList.TEXT_ALREADY_EXISTS, stage);
                return;
            } catch (IOException e) {
                AlertUtils.showAlert(AlertList.UPLOAD_FAILURE, stage);
                return;
            }

            AlertUtils.showAlert(AlertList.UPLOAD_SUCCESS, stage);
        }

    }

    public void finishGame(ActionEvent actionEvent) {
        //SETTA LE RISPOSTE SELEZIONATE
        QuizController.setRisposteSelezionate(q1Options, q2Options, q3Options, q4Options, currentQuiz);
        domandaRispostaVBox.setVisible(false);
        domandaRispostaVBox.setManaged(false);
        RiepilogoController.setLabelPerRiepilogo(currentQuiz, domanda1Label, domanda2Label, domanda3Label, domanda4Label, risposta1UtenteLabel, risposta2UtenteLabel, risposta3UtenteLabel, risposta4UtenteLabel, risposta1CorrettaLabel, risposta2CorrettaLabel, risposta3CorrettaLabel, risposta4CorrettaLabel);
        //Imposta la sessione come completa , per evitare che salvi la sessione come incompleta
        currentQuiz.setIsCompleta(true);
        riepilogoVBox.setVisible(true);
        riepilogoVBox.setManaged(true);


    }

    public void goToScoreboard(ActionEvent actionEvent) throws SQLException {

        QuizController.updateScoreboard(currentQuiz);
        aggiornaTableView(QuizController.getScoreboard(currentQuiz.getDifficolta()));
        finalScoreVBox.setManaged(false);
        finalScoreVBox.setVisible(false);
        scoreboardVBox.setManaged(true);
        scoreboardVBox.setVisible(true);
        updateDisplayedData();
        updateToggleButtonStyles();
    }

    public void restartGame(ActionEvent actionEvent) {
        scoreboardVBox.setVisible(false);
        scoreboardVBox.setManaged(false);
        difficultyVBox.setVisible(true);
        difficultyVBox.setManaged(true);
        currentQuiz = null;
    }


    @FXML
    public void loadStopwords(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        List<String> allStopWords = new ArrayList<>();
        StopWordsDAOPostgres stopWordsDAOPostgres = new StopWordsDAOPostgres();
        FileChooser fc = new FileChooser();
        fc.setTitle("Seleziona un file csv");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("File di testo", "*.txt"),
                new FileChooser.ExtensionFilter("File csv", "*.csv"),
                new FileChooser.ExtensionFilter("Tutti i file", "*.*")
        );
        File selectedFile = fc.showOpenDialog(stage);
        if (selectedFile != null && selectedFile.getName().endsWith(".csv")) {
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                String line = "";
                while ((line = reader.readLine()) != null) {
                    String[] campi = line.split("[,;\\t ]+");
                    for (String parola : campi) allStopWords.add(parola);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (selectedFile != null && selectedFile.getName().endsWith(".txt")) {
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                String line = "";
                while ((line = reader.readLine()) != null) {
                    String[] campi = line.split("[,;\\t ]+");
                    for (String parola : campi) allStopWords.add(parola);
                }
            } catch (IOException e) {
                e.printStackTrace();
                AlertUtils.showAlert(AlertList.UPLOAD_STOPWORDS_FAILURE, stage);
                return;
            }
            stopWordsDAOPostgres.inserisciStopWords(allStopWords);
            AlertUtils.showAlert(AlertList.UPLOAD_STOPWORDS_SUCCESS, stage);
        }


    }

    public void vaiAlPunteggio(ActionEvent actionEvent) {
        QuizController.setFinalScore(q1Options, q2Options, q3Options, q4Options, currentQuiz);
        riepilogoVBox.setManaged(false);
        riepilogoVBox.setVisible(false);
        finalScoreVBox.setVisible(true);
        finalScoreVBox.setManaged(true);
        clearQuizFields();
        scoreLabel.setText("Il tuo punteggio finale è: " + currentQuiz.getScore());
    }

    public void goSettings(ActionEvent actionEvent) {
        difficultyVBox.setVisible(false);
        difficultyVBox.setManaged(false);
        settingsVBox.setManaged(true);
        settingsVBox.setVisible(true);
        if (currentUser.isAdmin()) {
            adminSection.setVisible(true);
            adminSection.setManaged(true);
        } else {
            adminSection.setVisible(false);
            adminSection.setManaged(false);
        }
    }

    public void goBack(ActionEvent actionEvent) {
        settingsVBox.setManaged(false);
        settingsVBox.setVisible(false);
        difficultyVBox.setVisible(true);
        difficultyVBox.setManaged(true);
        StartGameController.aggiornaLabel(usernameWelcomeLabel, currentUser.getUsername());
    }

    public void saveUsername(ActionEvent actionEvent) {

        saveUsernameSettings.disableProperty().bind(
                usernameFieldSettings.textProperty().isEmpty()
        );
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        String nuovoUsername = usernameFieldSettings.getText().trim();
        if (!nuovoUsername.matches("^[A-Za-z0-9_]+$")) {
            AlertUtils.showAlert(AlertList.USERNAME_FORMAT_NON_CORRETTO, stage);
            return;
        }
        UserDAOPostgres userDAOPostgres = new UserDAOPostgres();
        if (currentUser.getUsername().trim().equals(nuovoUsername.trim())) {
            AlertUtils.showAlert(AlertList.MODIFICA_USERNAME_NON_AVVENUTA, stage);
            return;
        }
        try {

            userDAOPostgres.modificaUsername(currentUser, nuovoUsername);


        } catch (UsernameGiaPreso ex) {
            AlertUtils.showAlert(AlertList.USERNAME_ALREADY_TAKEN, stage);
            return;
        }

        //Mostra messaggio di successo ed aggiorna la label con il nuovo username
        AlertUtils.showAlert(AlertList.MODIFICA_USERNAME_SUCCESS, stage);
        usernameFieldSettings.setText(nuovoUsername);
        StartGameController.aggiornaLabel(usernameWelcomeLabel, nuovoUsername);

    }

    public void savePassword(ActionEvent actionEvent) {
        UserDAOPostgres userDAOPostgres = new UserDAOPostgres();
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        try {

            userDAOPostgres.modificaPassword(currentUser, passwordFieldSettings.getText(), passwordFieldNewSettings.getText());

        } catch (PasswordNonCorrettaException ex) {
            AlertUtils.showAlert(AlertList.PASSWORD_NON_CORRETTA, stage);
            return;
        } catch (NessunaModificaException ex) {
            AlertUtils.showAlert(AlertList.NESSUNA_MODIFICA_DI_PASSWORD, stage);
            return;
        }
        //Success

        AlertUtils.showAlert(AlertList.CAMBIO_PASSWORD_SUCCESS, stage);
        passwordFieldSettings.clear();
        passwordFieldNewSettings.clear();
        logout();

    }

    //logout dopo cambio password
    private void logout() {
        clearLoginFields();
        settingsVBox.setVisible(false);
        settingsVBox.setManaged(false);
        loginVBox.setVisible(true);
        loginVBox.setManaged(true);
        currentUser = null;
    }


    public void changeDati(ActionEvent actionEvent) {
    }


    @FXML
    public void goToDocuments(ActionEvent actionEvent) {
        settingsVBox.setManaged(false);
        settingsVBox.setVisible(false);
        sezioneDocumenti.setVisible(true);
        sezioneDocumenti.setManaged(true);
        List<Documento> allDocuments = documentoDAOPostgres.getAllDocuments();
        for(Documento documento : allDocuments) addDocumentToUI(documento);
    }


    private void addDocumentToUI(Documento documento) {
        //Se gia mappato return
        if(documentiAggiunti.contains(documento)) return;
        HBox documentRow = new HBox(15);
        documentRow.setAlignment(Pos.CENTER_LEFT);
        documentRow.setPadding(new Insets(10)); // padding interno
        documentRow.setStyle("-fx-background-color: #f9f9f9; -fx-background-radius: 5;");

        // Cambio colore sfondo al passaggio mouse
        documentRow.setOnMouseEntered(e -> documentRow.setStyle("-fx-background-color: #e0e0e0; -fx-background-radius: 5;"));
        documentRow.setOnMouseExited(e -> documentRow.setStyle("-fx-background-color: #f9f9f9; -fx-background-radius: 5;"));

        Label nameLabel = new Label(documento.getTitolo());
        nameLabel.getStyleClass().add("document-label");
        nameLabel.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));

        Button deleteButton = new Button("Elimina");
        deleteButton.getStyleClass().add("delete-button");
        deleteButton.setPrefSize(80, 30);
        deleteButton.setStyle(
                "-fx-background-color: #ff4c4c; " +  // rosso acceso
                        "-fx-text-fill: white; " +
                        "-fx-background-radius: 5;"
        );

        deleteButton.setOnAction(event -> {
            documentsList.getChildren().remove(documentRow);
          documentoDAOPostgres.eliminaDocumento(documento.getId());
          documentiAggiunti.remove(documento);
        });

        // Aggiungo uno spazio flessibile tra label e bottone
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        documentRow.getChildren().addAll(nameLabel, spacer, deleteButton);
        documentsList.getChildren().add(documentRow);
        documentiAggiunti.add(documento);
    }

    public void goBackToSettingsFromDocuments(ActionEvent actionEvent) {
        sezioneDocumenti.setManaged(false);
        sezioneDocumenti.setVisible(false);
        settingsVBox.setVisible(true);
        settingsVBox.setManaged(true);
    }

    public void goLeaderboard(ActionEvent actionEvent) {
        difficultyVBox.setVisible(false);
        difficultyVBox.setManaged(false);
        scoreboardVBox.setVisible(true);
        scoreboardVBox.setManaged(true);
        updateDisplayedData();
        updateToggleButtonStyles();
        restartGameButton.setVisible(false);
        restartGameButton.setManaged(false);
        goBackButton.setVisible(true);
        goBackButton.setManaged(true);
    }

    public void goBack2(ActionEvent actionEvent) {
        restartGameButton.setVisible(true);
        restartGameButton.setManaged(true);
        goBackButton.setVisible(false);
        goBackButton.setManaged(false);
        scoreboardVBox.setVisible(false);
        scoreboardVBox.setManaged(false);
        difficultyVBox.setVisible(true);
        difficultyVBox.setManaged(true);
    }
}





