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
import java.time.LocalDate;
import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Controller principale per l'applicazione di quiz a lettura veloce.
 * Gestisce tutta la logica dell'interfaccia grafica e il coordinamento tra i vari componenti.
 */
public class MainController {

    // Componenti UI
    public ToggleGroup gruppoDomanda1, gruppoDomanda2, gruppoDomanda3, gruppoDomanda4;
    public PasswordField passwordFieldSettings, passwordFieldNewSettings;
    public Button changePasswordButton;
    public VBox sezioneDocumenti, documentsList;
    public Label titoloDocumentoLabel;
    public Label difficoltaDocumentoLabel;
    public TextArea contenutoDocumentoTextArea;
    public VBox documentoSection;
    public Button indietroButton;
    public VBox noDocumentsPlaceholder;
    private StringProperty initialUsernameProperty = new SimpleStringProperty();
    private Set<Documento> documentiAggiunti = new HashSet<>();
    private Set<Documento> documentiDaAggiungereAllaUI = new HashSet<>();
    public ToggleButton facileButton, medioButton, difficileButton;

    // Sezione riepilogo
   public  ToggleGroup difficoltaToggleGroup = new ToggleGroup();
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

    // Statistiche
    public Label punteggioMedioLabel;
    public Label migliorPunteggioLabel;
    public Label numeroPartiteLabel;
    public CheckBox punteggiPersonaliCheckBox;

    // DAOs
    private DocumentoDAOPostgres documentoDAOPostgres = new DocumentoDAOPostgres();

    // Login
    public TextField loginUsernameField;
    public PasswordField loginPasswordField;
    public VBox loginVBox;

    // Registrazione
    public Button registerButton;
    public PasswordField confirmRegisterPasswordField;
    public PasswordField registerPasswordField;
    public TextField registerUsernameField;
    public VBox registerVBox;
    public Button switchModeButton;

    // Timer
    public Label timeLabel;
    public ProgressBar timeProgressBar;
    public Label displayTextLabel;
    public Label titleLabel;

    // Domande
    public Label q1, q2, q3, q4;

    // Opzioni risposte
    @FXML
    public RadioButton q1opt1, q1opt2, q1opt3, q1opt4;
    @FXML
    public RadioButton q2opt1, q2opt2, q2opt3, q2opt4;
    @FXML
    public RadioButton q3opt1, q3opt2, q3opt3, q3opt4;
    @FXML
    public RadioButton q4opt1, q4opt2, q4opt3, q4opt4;

    private RadioButton[] q1Options ;
    private RadioButton[] q2Options ;
    private RadioButton[] q3Options;
    private RadioButton[] q4Options;


    // Scelta difficoltà
    public Label usernameWelcomeLabel;
    public VBox difficultyVBox;
    public RadioButton easyRadio;
    public RadioButton mediumRadio;
    public RadioButton hardRadio;
    public Button startGameButton;

    // Quiz
    public VBox testoVBox;
    public VBox domandaRispostaVBox;
    public Label titleQuiz;
    private SessioneQuiz currentQuiz;
    private User currentUser;

    // Punteggio finale
    public VBox finalScoreVBox;
    public Label scoreLabel;

    // Classifica
    public TableView<SessioneQuiz> tableView;
    public TableColumn<SessioneQuiz, String> utenteColumn;
    public TableColumn<SessioneQuiz, Integer> scoreColumn;
    public VBox scoreboardVBox;
    ObservableList<SessioneQuiz> sessioniQuizList;

    // Impostazioni
    public VBox settingsVBox;
    public Button addTestoButton;
    public Button addStopwordsButton;
    public VBox adminSection;

    /**
     * Pulisce i campi del form di registrazione
     */
    private void clearRegisterFields() {
        registerUsernameField.clear();
        registerPasswordField.clear();
        confirmRegisterPasswordField.clear();
    }

    /**
     * Pulisce i campi del form di login
     */
    private void clearLoginFields() {
        loginUsernameField.clear();
        loginPasswordField.clear();
    }

    @FXML
    public void initialize(){
         q1Options = new RadioButton[]{q1opt1,q1opt2 , q1opt3,q1opt4};
         q2Options = new RadioButton[]{q2opt1,q2opt2 , q2opt3,q2opt4};
          q3Options = new RadioButton[]{q3opt1,q3opt2,q3opt3,q3opt4};
          q4Options = new RadioButton[]{q4opt1,q4opt2,q4opt3,q4opt4};
          setupToggleButtons();
          initTableView();
          initBindings();
    }

    /**
     * Pulisce le selezioni delle risposte del quiz
     */
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

    /**
     * Mostra le domande e le risposte del quiz
     */
    private void showQuestionsAndAnswers() {
        testoVBox.setVisible(false);
        testoVBox.setManaged(false);
        domandaRispostaVBox.setVisible(true);
        domandaRispostaVBox.setManaged(true);
        currentQuiz.generaDomande();

        List<Domanda> domande = currentQuiz.getDomande();
        List<Label> domandeLabels = Arrays.asList(q1,q2,q3,q4);
        for(int i = 0 ; i < domandeLabels.size() ; i++){
            domandeLabels.get(i).setText(domande.get(i).getTesto());
        }

        RadioButton[][] allOptions = { q1Options, q2Options, q3Options, q4Options };
        for (int i = 0; i < allOptions.length; i++) {
            List<Risposta> risposte = domande.get(i).getRisposte();
            RadioButton[] opzioni = allOptions[i];
            for (int j = 0; j < opzioni.length; j++) {
                opzioni[j].setText(risposte.get(j).getTesto());
            }
        }

    }

    /**
     * Restituisce la sessione quiz corrente
     * @return la sessione quiz corrente
     */
    public SessioneQuiz getSessioneCorrente() {
        return currentQuiz;
    }

    /**
     * Configura i pulsanti di difficoltà
     */
    private void setupToggleButtons() {


        facileButton.setToggleGroup(difficoltaToggleGroup);
        medioButton.setToggleGroup(difficoltaToggleGroup);
        difficileButton.setToggleGroup(difficoltaToggleGroup);

        if (currentQuiz == null || currentQuiz.getDifficolta() == DifficultyEnum.EASY)
            facileButton.setSelected(true);
        else if (currentQuiz.getDifficolta() == DifficultyEnum.MEDIUM)
            medioButton.setSelected(true);
        else
            difficileButton.setSelected(true);

        difficoltaToggleGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                updateDisplayedData();
                updateToggleButtonStyles();
            } else {
                facileButton.setSelected(true);
            }
        });

        punteggiPersonaliCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            updateDisplayedData();
        });
    }

    /**
     * Aggiorna gli stili dei pulsanti di difficoltà
     */
    private void updateToggleButtonStyles() {
        facileButton.setStyle("-fx-background-color: #8BC34A; -fx-text-fill: white;");
        medioButton.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white;");
        difficileButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;");

        ToggleButton selected = (ToggleButton) difficoltaToggleGroup.getSelectedToggle();
        if (selected != null) {
            selected.setStyle(selected.getStyle() + "; -fx-font-weight: bold; -fx-border-color: #333333; -fx-border-width: 2px;");
        }
    }

    /**
     * Aggiorna i dati visualizzati in base alla difficoltà selezionata
     */
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

        List<SessioneQuiz> sessioni;
        if (!soloPersonali)
            sessioni = QuizController.getScoreboard(diff);
        else sessioni = QuizController.getPersonalScoreboard(currentUser, diff);

        aggiornaTableView(sessioni);
        aggiornaStats(diff);
    }

    /**
     * Aggiorna la tabella delle classifiche
     * @param sessioni lista delle sessioni da visualizzare
     */
    private void aggiornaTableView(List<SessioneQuiz> sessioni) {
        sessioniQuizList = FXCollections.observableList(sessioni);
        tableView.setItems(sessioniQuizList);
        tableView.refresh();
    }

    /**
     * Aggiorna le statistiche visualizzate
     * @param difficultyEnum livello di difficoltà
     */
    private void aggiornaStats(DifficultyEnum difficultyEnum) {
        punteggioMedioLabel.setText(QuizController.getPunteggioMedio(currentUser, difficultyEnum));
        migliorPunteggioLabel.setText(QuizController.getMigliorPunteggio(currentUser, difficultyEnum));
        numeroPartiteLabel.setText(QuizController.getPartite(currentUser));
    }

    /**
     * Crea un oggetto User dai campi di login
     * @return l'utente creato
     * @throws CampiNonCompilatiException se i campi sono vuoti
     */
    private User getLoggedUser() throws CampiNonCompilatiException {
        if (loginUsernameField.getText().trim().isEmpty() || loginPasswordField.getText().trim().isEmpty()) {
            throw new CampiNonCompilatiException("");
        }

        String username = loginUsernameField.getText();
        String password = loginPasswordField.getText();
        return new User(username.trim(), password.trim(), false);
    }

    /**
     * Verifica i dati di registrazione
     * @param stage lo stage corrente
     * @return l'utente creato
     * @throws CampiNonCompilatiException se i campi sono vuoti
     * @throws PasswordDiverseException se le password non coincidono
     */
    private User checkRegister(Stage stage) throws CampiNonCompilatiException, PasswordDiverseException {
        if (registerUsernameField.getText().trim().isEmpty() || registerPasswordField.getText().trim().isEmpty() || registerPasswordField.getText().trim().isEmpty()) {
            throw new CampiNonCompilatiException("");
        }

        if (!registerUsernameField.getText().matches("^[A-Za-z0-9_]+$")) {
            throw new UsernameFormatException("Formato dell'username non valido");
        }

        String password = registerPasswordField.getText();
        if (password.length() < 6 || !password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*") || !password.matches(".*[0-9].*")) {
            throw new PasswordFormatException("La password deve contenere almeno 6 caratteri con almeno una maiuscola e un numero");
        }


        if (!password.equals(confirmRegisterPasswordField.getText())) {
            throw new PasswordDiverseException("");
        }

        String username = registerUsernameField.getText();
        return new User(username.trim(), password.trim(), false);
    }

    /**
     * Restituisce la difficoltà selezionata
     * @return la difficoltà scelta
     */
    private DifficultyEnum getDifficoltaScelta() {
        if (easyRadio.isSelected()) return DifficultyEnum.EASY;
        else if (mediumRadio.isSelected()) return DifficultyEnum.MEDIUM;
        else return DifficultyEnum.HARD;
    }

    /**
     * Inizializza la tabella delle classifiche
     */
    private void initTableView() {
        sessioniQuizList = FXCollections.observableArrayList();
        tableView.setItems(sessioniQuizList);
        utenteColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getUser().getUsername())
        );
        scoreColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getScore()).asObject()
        );

        scoreColumn.setSortType(TableColumn.SortType.DESCENDING);
        tableView.getSortOrder().setAll(scoreColumn);
        tableView.sort();
    }

    /**
     * Inizializza i binding delle proprietà
     */
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

    /**
     * Gestisce il login dell'utente
     * @param actionEvent l'evento che ha scatenato l'azione
     */
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
            currentUser = userToLog;
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

    /**
     * Gestisce la registrazione di un nuovo utente
     * @param actionEvent l'evento che ha scatenato l'azione
     * @throws SQLException se si verifica un errore di database
     */
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
        } catch (PasswordFormatException pfx) {
            AlertUtils.showAlert(AlertList.PASSWORD_FORMAT_NON_CORRETTO, stage);
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

    /**
     * Torna alla schermata di registrazione
     * @param actionEvent l'evento che ha scatenato l'azione
     */
    public void backToRegister(ActionEvent actionEvent) {
        clearLoginFields();
        registerVBox.setVisible(true);
        registerVBox.setManaged(true);
        loginVBox.setVisible(false);
        loginVBox.setManaged(false);
    }

    /**
     * Torna alla schermata di login
     * @param actionEvent l'evento che ha scatenato l'azione
     */
    public void backToLogin(ActionEvent actionEvent) {
        clearRegisterFields();
        registerVBox.setVisible(false);
        registerVBox.setManaged(false);
        loginVBox.setVisible(true);
        loginVBox.setManaged(true);
    }

    /**
     * Effettua il logout dall'applicazione
     * @param actionEvent l'evento che ha scatenato l'azione
     */
    public void handleLogout(ActionEvent actionEvent) {
        clearLoginFields();
        difficultyVBox.setVisible(false);
        difficultyVBox.setManaged(false);
        loginVBox.setVisible(true);
        loginVBox.setManaged(true);
        currentUser = null;
    }

    /**
     * Verifica se esiste una sessione sospesa per l'utente
     * @param user l'utente da verificare
     * @return true se esiste una sessione sospesa, false altrimenti
     */
    private boolean hasSessionSuspended(User user) {
        File file = new File("salvataggio_" + user.getUsername() + ".dat");
        return file.exists();
    }

    /**
     * Avvia una nuova sessione di gioco
     * @param actionEvent l'evento che ha scatenato l'azione
     */
    public void handleStartGame(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        List<Documento> testiDaMostrare;
        DifficultyEnum diff = getDifficoltaScelta();

        if (hasSessionSuspended(currentUser)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Vuoi riprendere la sessione salvata?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Sessione sospesa trovata");
            alert.setHeaderText(null);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.YES) {
                try {
                    SessioneQuiz sessioneRecuperata = GestoreSalvataggioSessione.loadSessione(currentUser.getUsername());
                    sessioneRecuperata.setDomandeDAOPostgres(new DomandeDAOPostgres());
                    currentQuiz = sessioneRecuperata;
                    QuizController.startTimerPerTesto(currentQuiz.getDocumenti(), 0, timeLabel, timeProgressBar, currentQuiz.getDifficolta(), displayTextLabel, titleQuiz, () -> showQuestionsAndAnswers());
                    GestoreSalvataggioSessione.eliminaSessione(currentUser.getUsername());
                } catch (SessioneNonCaricataException ex) {
                    AlertUtils.showAlert(AlertList.SESSIONE_NON_CARICATA, stage);
                    return;
                }

            } else {
                GestoreSalvataggioSessione.eliminaSessione(currentUser.getUsername());
                try {
                    testiDaMostrare = documentoDAOPostgres.getDocumentiPerDifficolta(diff);
                }catch (NotEnoughDocuments ex){
                    AlertUtils.showAlert(AlertList.NON_ABBASTANZA_DOCUMENTI,stage);
                    return;
                }
                SessioneQuiz sessioneQuiz = new SessioneQuiz(testiDaMostrare, diff, currentUser);
                sessioneQuiz.setDomandeDAOPostgres(new DomandeDAOPostgres());
                currentQuiz = sessioneQuiz;
                QuizController.startTimerPerTesto(testiDaMostrare, 0, timeLabel, timeProgressBar, diff, displayTextLabel, titleQuiz, () -> showQuestionsAndAnswers());
            }

            difficultyVBox.setVisible(false);
            difficultyVBox.setManaged(false);
            testoVBox.setVisible(true);
            testoVBox.setManaged(true);
            return;
        }

        try {
            testiDaMostrare = documentoDAOPostgres.getDocumentiPerDifficolta(diff);
        } catch (NotEnoughDocuments e) {
            AlertUtils.showAlert(AlertList.NON_ABBASTANZA_DOCUMENTI , stage);
            return;
        }
        SessioneQuiz sessioneQuiz = new SessioneQuiz(testiDaMostrare, diff, currentUser);
        sessioneQuiz.setDomandeDAOPostgres(new DomandeDAOPostgres());
        currentQuiz = sessioneQuiz;
        QuizController.startTimerPerTesto(testiDaMostrare, 0, timeLabel, timeProgressBar, diff, displayTextLabel, titleQuiz, () -> showQuestionsAndAnswers());
        difficultyVBox.setVisible(false);
        difficultyVBox.setManaged(false);
        testoVBox.setVisible(true);
        testoVBox.setManaged(true);
    }

    /**
     * Aggiunge un nuovo documento al sistema
     * @param actionEvent l'evento che ha scatenato l'azione
     */
    public void addTesto(ActionEvent actionEvent) {
        Documento documento = null;
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
                        .map(String::trim)
                        .filter(line -> !line.isEmpty())
                        .map(line -> line.replaceAll("[\\s+]", " "))
                        .reduce("", (acc, line) -> acc + " " + line);

                 documento = new Documento(selectedFile.getName().split("\\.")[0], content);
                String[] contentClean = content.split("[\\p{Punct}\\s]+");
                if (contentClean.length < 10) {
                    AlertUtils.showAlert(AlertList.SHORT_TEXT, stage);
                    return;
                }
                if (contentClean.length > 100) documento.setDifficolta(DifficultyEnum.HARD);
                else if (contentClean.length > 50) documento.setDifficolta(DifficultyEnum.MEDIUM);
                else documento.setDifficolta(DifficultyEnum.EASY);

                //Setto la data di caricamento del documento :
                documento.setDataCaricamento(LocalDate.now());

                documentoDAO.insertDocumento(documento);
            } catch (DatabaseException e) {
                AlertUtils.showAlert(AlertList.TEXT_ALREADY_EXISTS, stage);
                return;
            } catch (IOException e) {
                AlertUtils.showAlert(AlertList.UPLOAD_FAILURE, stage);
                return;
            }

            //Refresho la UI nella sezione dei documenti
            addDocumentToUI(documento);
            documentiAggiunti.add(documento);
            if(noDocumentsPlaceholder.isVisible()) {
                noDocumentsPlaceholder.setVisible(false);
                noDocumentsPlaceholder.setManaged(false);
            }
            AlertUtils.showAlert(AlertList.UPLOAD_SUCCESS, stage);
        }
    }

    /**
     * Termina il gioco e mostra il riepilogo
     * @param actionEvent l'evento che ha scatenato l'azione
     */
    public void finishGame(ActionEvent actionEvent) {
        QuizController.setRisposteSelezionate(q1Options, q2Options, q3Options, q4Options, currentQuiz);
        domandaRispostaVBox.setVisible(false);
        domandaRispostaVBox.setManaged(false);
        RiepilogoController.setLabelPerRiepilogo(currentQuiz, domanda1Label, domanda2Label, domanda3Label, domanda4Label, risposta1UtenteLabel, risposta2UtenteLabel, risposta3UtenteLabel, risposta4UtenteLabel, risposta1CorrettaLabel, risposta2CorrettaLabel, risposta3CorrettaLabel, risposta4CorrettaLabel);
        currentQuiz.setIsCompleta(true);
        riepilogoVBox.setVisible(true);
        riepilogoVBox.setManaged(true);
    }

    /**
     * Mostra la classifica dei punteggi
     * @param actionEvent l'evento che ha scatenato l'azione
     * @throws SQLException se si verifica un errore di database
     */
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

    /**
     * Riavvia il gioco
     * @param actionEvent l'evento che ha scatenato l'azione
     */
    public void restartGame(ActionEvent actionEvent) {
        scoreboardVBox.setVisible(false);
        scoreboardVBox.setManaged(false);
        difficultyVBox.setVisible(true);
        difficultyVBox.setManaged(true);
        currentQuiz = null;
    }

    /**
     * Carica le stopwords nel sistema
     * @param actionEvent l'evento che ha scatenato l'azione
     */
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

    /**
     * Mostra il punteggio finale
     * @param actionEvent l'evento che ha scatenato l'azione
     */
    public void vaiAlPunteggio(ActionEvent actionEvent) {
        QuizController.setFinalScore(q1Options, q2Options, q3Options, q4Options, currentQuiz);
        riepilogoVBox.setManaged(false);
        riepilogoVBox.setVisible(false);
        finalScoreVBox.setVisible(true);
        finalScoreVBox.setManaged(true);
        clearQuizFields();
        scoreLabel.setText("Il tuo punteggio finale è: " + currentQuiz.getScore());
    }

    /**
     * Mostra le impostazioni
     * @param actionEvent l'evento che ha scatenato l'azione
     */
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

    /**
     * Torna alla schermata principale dalle impostazioni
     * @param actionEvent l'evento che ha scatenato l'azione
     */
    public void goBack(ActionEvent actionEvent) {
        settingsVBox.setManaged(false);
        settingsVBox.setVisible(false);
        difficultyVBox.setVisible(true);
        difficultyVBox.setManaged(true);
        StartGameController.aggiornaLabel(usernameWelcomeLabel, currentUser.getUsername());
    }

    /**
     * Salva il nuovo username
     * @param actionEvent l'evento che ha scatenato l'azione
     */
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

        AlertUtils.showAlert(AlertList.MODIFICA_USERNAME_SUCCESS, stage);
        usernameFieldSettings.setText(nuovoUsername);
        StartGameController.aggiornaLabel(usernameWelcomeLabel, nuovoUsername);
    }

    /**
     * Salva la nuova password
     * @param actionEvent l'evento che ha scatenato l'azione
     */
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

        AlertUtils.showAlert(AlertList.CAMBIO_PASSWORD_SUCCESS, stage);
        passwordFieldSettings.clear();
        passwordFieldNewSettings.clear();
        logout();
    }

    /**
     * Effettua il logout
     */
    private void logout() {
        clearLoginFields();
        settingsVBox.setVisible(false);
        settingsVBox.setManaged(false);
        loginVBox.setVisible(true);
        loginVBox.setManaged(true);
        currentUser = null;
    }



    /**
     * Mostra la sezione documenti
     * @param actionEvent l'evento che ha scatenato l'azione
     */
    @FXML
    public void goToDocuments(ActionEvent actionEvent) {
        settingsVBox.setManaged(false);
        settingsVBox.setVisible(false);
        sezioneDocumenti.setVisible(true);
        sezioneDocumenti.setManaged(true);
        List<Documento> allDocuments = documentoDAOPostgres.getAllDocuments();
        //Aggiungo ai "documenti da aggiungere alla UI" , solo quelli che non sono già da aggiungere
        allDocuments.stream().filter(documento -> !documentiDaAggiungereAllaUI.contains(documento)).forEach(docu->documentiDaAggiungereAllaUI.add(docu));
        //Se i documenti già aggiunti alla UI sono vuoti , allora mostra il placeholder
        if(documentiAggiunti.isEmpty() && documentiDaAggiungereAllaUI.isEmpty()) {
            noDocumentsPlaceholder.setVisible(true);
            noDocumentsPlaceholder.setManaged(true);
            return;
        }
        for(Documento documento : documentiDaAggiungereAllaUI) addDocumentToUI(documento);
        //Pulisco tutti i documenti da aggiungere alla UI , altrimenti non potremo mai avere la condizione isEmpty()
        documentiDaAggiungereAllaUI.clear(); //I documenti da aggiungere alla UI sono sempre quelli presi nel momeento in cui si preme il bottone , non si deve mantenere alcuno stato
    }



    private void goToDetailOfDocument(Documento documento ){
        sezioneDocumenti.setVisible(false);
        sezioneDocumenti.setManaged(false);
        titoloDocumentoLabel.setText(documento.getTitolo());
        difficoltaDocumentoLabel.setText(documento.getDifficolta()+"");
        contenutoDocumentoTextArea.setText(documento.getContenuto());
        documentoSection.setVisible(true);
        documentoSection.setManaged(true);
    }


    /**
     * Aggiunge un documento all'interfaccia
     * @param documento il documento da aggiungere
     */
    private void addDocumentToUI(Documento documento) {
        if(documentiAggiunti.contains(documento)) return;

        // Container principale del documento
        HBox documentRow = new HBox(15);
        documentRow.setAlignment(Pos.CENTER_LEFT);
        documentRow.setPadding(new Insets(16, 20, 16, 20));

        // Stile base del documento
        String baseStyle = "-fx-background-color: white; " +
                "-fx-background-radius: 8; " +
                "-fx-border-color: #E5E7EB; " +
                "-fx-border-width: 1; " +
                "-fx-border-radius: 8; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 4, 0, 0, 2); " +
                "-fx-cursor: hand;";

        String hoverStyle = "-fx-background-color: #F9FAFB; " +
                "-fx-background-radius: 8; " +
                "-fx-border-color: #D1D5DB; " +
                "-fx-border-width: 1; " +
                "-fx-border-radius: 8; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 4); " +
                "-fx-cursor: hand;";

        documentRow.setStyle(baseStyle);

        // Eventi mouse
        documentRow.setOnMouseClicked(e -> {
            goToDetailOfDocument(documento);
        });

        documentRow.setOnMouseEntered(e -> documentRow.setStyle(hoverStyle));
        documentRow.setOnMouseExited(e -> documentRow.setStyle(baseStyle));

        // Contenitore per icona e info documento
        HBox documentInfo = new HBox(12);
        documentInfo.setAlignment(Pos.CENTER_LEFT);

        // Icona del documento
        Label iconLabel = new Label("📄");
        iconLabel.setStyle("-fx-font-size: 20; -fx-text-fill: #6B7280;");

        // Contenitore per nome e dettagli
        VBox textContainer = new VBox(2);

        // Nome del documento
        Label nameLabel = new Label(documento.getTitolo());
        nameLabel.setStyle("-fx-font-family: 'Segoe UI Semibold', sans-serif; " +
                "-fx-font-size: 14; " +
                "-fx-font-weight: 600; " +
                "-fx-text-fill: #111827;");

        // Dettagli aggiuntivi (opzionale - puoi aggiungere data, dimensione, etc.)
        Label detailsLabel = new Label("Documento txt • Caricato il " +documento.getDataCaricamento());
        detailsLabel.setStyle("-fx-font-family: 'Segoe UI', sans-serif; " +
                "-fx-font-size: 12; " +
                "-fx-text-fill: #6B7280;");

        textContainer.getChildren().addAll(nameLabel, detailsLabel);
        documentInfo.getChildren().addAll(iconLabel, textContainer);

        // Spacer per spingere il pulsante a destra
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Pulsante elimina migliorato
        Button deleteButton = new Button("Elimina");
        deleteButton.setPrefSize(80, 32);
        deleteButton.setStyle("-fx-background-color: #EF4444; " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 6; " +
                "-fx-border-color: transparent; " +
                "-fx-font-family: 'Segoe UI Semibold', sans-serif; " +
                "-fx-font-size: 12; " +
                "-fx-font-weight: 600; " +
                "-fx-cursor: hand; " +
                "-fx-effect: dropshadow(gaussian, rgba(239,68,68,0.25), 4, 0, 0, 2);");

        // Effetti hover per il pulsante elimina
        deleteButton.setOnMouseEntered(e ->
                deleteButton.setStyle("-fx-background-color: #DC2626; " +
                        "-fx-text-fill: white; " +
                        "-fx-background-radius: 6; " +
                        "-fx-border-color: transparent; " +
                        "-fx-font-family: 'Segoe UI Semibold', sans-serif; " +
                        "-fx-font-size: 12; " +
                        "-fx-font-weight: 600; " +
                        "-fx-cursor: hand; " +
                        "-fx-effect: dropshadow(gaussian, rgba(239,68,68,0.4), 6, 0, 0, 3);")
        );

        deleteButton.setOnMouseExited(e ->
                deleteButton.setStyle("-fx-background-color: #EF4444; " +
                        "-fx-text-fill: white; " +
                        "-fx-background-radius: 6; " +
                        "-fx-border-color: transparent; " +
                        "-fx-font-family: 'Segoe UI Semibold', sans-serif; " +
                        "-fx-font-size: 12; " +
                        "-fx-font-weight: 600; " +
                        "-fx-cursor: hand; " +
                        "-fx-effect: dropshadow(gaussian, rgba(239,68,68,0.25), 4, 0, 0, 2);")
        );

        deleteButton.setOnAction(event -> {
            documentsList.getChildren().remove(documentRow);
            documentoDAOPostgres.eliminaDocumento(documento.getId());
            documentiAggiunti.remove(documento);
            checkIfEmpty(documentiAggiunti); // Dopo aver rimosso un documento , controlliamo sempre se mettere il placeholder
        });

        // Assemblaggio finale
        documentRow.getChildren().addAll(documentInfo, spacer, deleteButton);
        documentsList.getChildren().add(documentRow);
        documentiAggiunti.add(documento);
    }

    private void checkIfEmpty(Set<Documento> documentsUI){
        if(documentsUI.isEmpty()){
            //Imposta il fallback
            noDocumentsPlaceholder.setVisible(true);
        }
    }

    /**
     * Torna alle impostazioni dalla sezione documenti
     * @param actionEvent l'evento che ha scatenato l'azione
     */
    public void goBackToSettingsFromDocuments(ActionEvent actionEvent) {
        sezioneDocumenti.setManaged(false);
        sezioneDocumenti.setVisible(false);
        settingsVBox.setVisible(true);
        settingsVBox.setManaged(true);
    }

    /**
     * Mostra la classifica
     * @param actionEvent l'evento che ha scatenato l'azione
     */
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

    /**
     * Torna alla schermata principale dalla classifica
     * @param actionEvent l'evento che ha scatenato l'azione
     */
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

    public void vaiAiDocumenti(ActionEvent actionEvent) {
        documentoSection.setVisible(false);
        documentoSection.setManaged(false);
        sezioneDocumenti.setVisible(true);
        sezioneDocumenti.setManaged(true);
    }

    public void returnToSettingsFromDocumentiSection(ActionEvent actionEvent) {
        sezioneDocumenti.setVisible(false);
        sezioneDocumenti.setManaged(false);
        settingsVBox.setVisible(true);
        settingsVBox.setManaged(true);
    }
}





