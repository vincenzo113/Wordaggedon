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
import com.example.timerService.TimerService;
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

/**
 * Il {@code MainController} gestisce l'interazione utente e la logica di business principale
 * dell'applicazione quiz. Si occupa di:
 * <ul>
 * <li>Login e registrazione degli utenti.</li>
 * <li>Gestione delle impostazioni dell'utente (username, password).</li>
 * <li>Avvio e gestione delle sessioni di quiz, inclusa la generazione delle domande e il calcolo del punteggio.</li>
 * <li>Visualizzazione e interazione con l'interfaccia utente per il quiz e i risultati.</li>
 * <li>Gestione del caricamento di documenti e stopwords da parte degli amministratori.</li>
 * <li>Interazione con i DAO per l'accesso al database.</li>
 * <li>Gestione del salvataggio e caricamento delle sessioni di quiz.</li>
 * </ul>
 */
public class MainController {

<<<<<<< Updated upstream
    //Sezione di riepilogo
    public VBox riepilogoVBox;
    public Label domanda4Label;
    public Button restartGameButton;
    public Button goBackButton;
=======
    // Elementi FXML per Login/Registrazione
    @FXML public TextField loginUsernameField;
    @FXML public PasswordField loginPasswordField;
    @FXML public VBox loginVBox;
    @FXML public Button registerButton;
    @FXML public PasswordField confirmRegisterPasswordField;
    @FXML public PasswordField registerPasswordField;
    @FXML public TextField registerUsernameField;
    @FXML public VBox registerVBox;
    @FXML public Button switchModeButton;
>>>>>>> Stashed changes

    // Elementi FXML per la Selezione della Difficoltà
    @FXML public Label usernameWelcomeLabel;
    @FXML public VBox difficultyVBox;
    @FXML public RadioButton easyRadio;
    @FXML public RadioButton mediumRadio;
    @FXML public RadioButton hardRadio;
    @FXML public Button startGameButton;
    @FXML public ToggleButton facileButton;
    @FXML public ToggleButton medioButton;
    @FXML public ToggleButton difficileButton;
    @FXML public CheckBox punteggiPersonaliCheckBox;

    // Elementi FXML per la Visualizzazione del Testo del Quiz
    @FXML public Label displayTextLabel;
    @FXML public Label statusLabel; // Considera di rinominare per chiarezza se non è uno stato generico
    @FXML public Label titleLabel; // Considera di rinominare per chiarezza se non è un titolo generico
    @FXML public VBox testoVBox;
    @FXML public VBox domandaRispostaVBox;
    @FXML public Label titleQuiz;

    // Elementi FXML per Domande e Risposte
    @FXML public Label q1;
    @FXML public Label q2;
    @FXML public Label q3;
    @FXML public Label q4;
    @FXML public RadioButton q1opt1;
    @FXML public RadioButton q1opt2;
    @FXML public RadioButton q1opt3;
    @FXML public RadioButton q1opt4;
    @FXML public RadioButton q2opt1;
    @FXML public RadioButton q2opt2;
    @FXML public RadioButton q2opt3;
    @FXML public RadioButton q2opt4;
    @FXML public RadioButton q3opt1;
    @FXML public RadioButton q3opt2;
    @FXML public RadioButton q3opt3;
    @FXML public RadioButton q3opt4;
    @FXML public RadioButton q4opt1;
    @FXML public RadioButton q4opt2;
    @FXML public RadioButton q4opt3;
    @FXML public RadioButton q4opt4;
    @FXML public ToggleGroup gruppoDomanda1;
    @FXML public ToggleGroup gruppoDomanda2;
    @FXML public ToggleGroup gruppoDomanda3;
    @FXML public ToggleGroup gruppoDomanda4;
    @FXML public Button nextButton;

    // Elementi FXML per il Riepilogo del Quiz
    @FXML public VBox riepilogoVBox;
    @FXML public Label domanda4Label;
    @FXML public Label risposta4UtenteLabel;
    @FXML public Label risposta4CorrettaLabel;
    @FXML public Label domanda3Label;
    @FXML public Label risposta3UtenteLabel;
    @FXML public Label risposta3CorrettaLabel;
    @FXML public Label domanda2Label;
    @FXML public Label risposta2UtenteLabel;
    @FXML public Label risposta2CorrettaLabel;
    @FXML public Label domanda1Label;
    @FXML public Label risposta1UtenteLabel;
    @FXML public Label risposta1CorrettaLabel;

    // Elementi FXML per il Punteggio Finale
    @FXML public VBox finalScoreVBox;
    @FXML public Label scoreLabel;

    // Elementi FXML per la Classifica (Scoreboard)
    @FXML public TableView<SessioneQuiz> tableView;
    @FXML public TableColumn<SessioneQuiz, String> utenteColumn;
    @FXML public TableColumn<SessioneQuiz, Integer> scoreColumn;
    @FXML public VBox scoreboardVBox;
    @FXML public Label punteggioMedioLabel;
    @FXML public Label migliorPunteggioLabel;
    @FXML public Label numeroPartiteLabel;

    // Elementi FXML per le Impostazioni
    @FXML public VBox settingsVBox;
    @FXML public Button addTestoButton;
    @FXML public Button addStopwordsButton;
    @FXML public TextField usernameFieldSettings; // Rinominato da usernameField per distinguerlo
    @FXML public PasswordField passwordFieldSettings; // Rinominato da passwordField per distinguerlo
    @FXML public PasswordField passwordFieldNewSettings;
    @FXML public Button changePasswordButton;
    @FXML public Button saveUsernameSettings;
    @FXML public VBox adminSection;
    @FXML public VBox sezioneDocumenti; // Non utilizzato nell'FXML fornito, considera di rimuoverlo o utilizzarlo
    @FXML public VBox documentsList; // Non utilizzato nell'FXML fornito, considera di rimuoverlo o utilizzarlo

    // Elementi FXML per il Timer
    @FXML public Label timeLabel;
    @FXML public Label timeInfoLabel; // Non utilizzato nell'FXML fornito, considera di rimuoverlo o utilizzarlo
    @FXML public ProgressBar timeProgressBar;

    // Variabili di stato interne
    private RadioButton[] q1Options;
    private RadioButton[] q2Options;
    private RadioButton[] q3Options;
    private RadioButton[] q4Options;
    private ToggleGroup difficoltaToggleGroup; // Gruppo per i radio button di difficoltà (facile, medio, difficile)
    private SessioneQuiz currentQuiz;
    private User currentUser;
    private TimerService timerService; // Gestisce il timer del quiz
    private ObservableList<SessioneQuiz> sessioniQuizList; // Dati per la TableView della classifica

    // Istanze DAO
    private DocumentoDAOPostgres documentoDAOPostgres = new DocumentoDAOPostgres();
    private QuizController QuizController = new QuizController(); // Istanza di QuizController per la logica del quiz
    // Istanza di LoginController (commentato, ma potrebbe essere utile se non è tutto statico)
    // private LoginController loginController;

    /**
     * Chiamato per inizializzare il controller dopo che il suo elemento root è stato completamente processato.
     * Questo metodo imposta lo stato iniziale dei componenti dell'interfaccia utente,
     * inclusa la vista tabella, i gruppi di radio button per le risposte, i toggle button di difficoltà,
     * e lega le proprietà per l'abilitazione/disabilitazione dei pulsanti.
     */
    @FXML
    public void initialize() {
        initTableView();
        // Inizializza gli array di RadioButton per una gestione più semplice delle risposte del quiz
        q1Options = new RadioButton[]{q1opt1, q1opt2, q1opt3, q1opt4};
        q2Options = new RadioButton[]{q2opt1, q2opt2, q2opt3, q2opt4};
        q3Options = new RadioButton[]{q3opt1, q3opt2, q3opt3, q3opt4};
        q4Options = new RadioButton[]{q4opt1, q4opt2, q4opt3, q4opt4};

        setupToggleButtons();
        initBindings();
    }

    /**
     * Inizializza la TableView per la classifica, impostando le fabbriche di valori delle celle delle colonne
     * e l'ordinamento predefinito.
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
     * Imposta i binding delle proprietà per vari componenti dell'interfaccia utente,
     * come l'abilitazione/disabilitazione del pulsante "Next" in base al fatto che tutte le domande siano state risposte,
     * e l'abilitazione/disabilitazione dei pulsanti "Salva Username" e "Cambia Password" in base al contenuto dei campi di testo.
     */
    private void initBindings() {
        // Lega la proprietà 'disable' del 'nextButton' per garantire che tutti i gruppi di domande abbiano una selezione
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

        // Lega la proprietà 'disable' del pulsante 'saveUsernameSettings' al campo username vuoto
        saveUsernameSettings.disableProperty().bind(
                usernameFieldSettings.textProperty().isEmpty()
        );

        // Lega la proprietà 'disable' del pulsante 'changePasswordButton' ai campi password vuoti
        BooleanBinding disableButtonPassword = Bindings.createBooleanBinding(() ->
                        passwordFieldSettings.getText().trim().isEmpty() ||
                                passwordFieldNewSettings.getText().trim().isEmpty(),
                passwordFieldSettings.textProperty(),
                passwordFieldNewSettings.textProperty()
        );
        changePasswordButton.disableProperty().bind(disableButtonPassword);
    }

    /**
     * Pulisce tutti i campi di input nel form di registrazione.
     */
    private void clearRegisterFields() {
        registerUsernameField.clear();
        registerPasswordField.clear();
        confirmRegisterPasswordField.clear();
    }

    /**
     * Pulisce tutti i campi di input nel form di login.
     */
    private void clearLoginFields() {
        loginUsernameField.clear();
        loginPasswordField.clear();
    }

    /**
     * Pulisce la selezione di tutti i radio button nella sezione delle risposte del quiz.
     */
    private void clearQuizFields() {
        if (gruppoDomanda1.getSelectedToggle() != null) gruppoDomanda1.getSelectedToggle().setSelected(false);
        if (gruppoDomanda2.getSelectedToggle() != null) gruppoDomanda2.getSelectedToggle().setSelected(false);
        if (gruppoDomanda3.getSelectedToggle() != null) gruppoDomanda3.getSelectedToggle().setSelected(false);
        if (gruppoDomanda4.getSelectedToggle() != null) gruppoDomanda4.getSelectedToggle().setSelected(false);
    }

    /**
     * Mostra la sezione delle domande e risposte del quiz, nascondendo la visualizzazione del testo.
     * Popola anche le domande e le risposte dalla sessione {@code currentQuiz}.
     */
    private void showQuestionsAndAnswers() {
        testoVBox.setVisible(false);
        testoVBox.setManaged(false);
        domandaRispostaVBox.setVisible(true);
        domandaRispostaVBox.setManaged(true);
        currentQuiz.generaDomande(); // Genera domande specifiche basate sul contenuto del documento

        List<Domanda> domande = currentQuiz.getDomande();

        // Imposta il testo delle domande
        q1.setText(domande.get(0).getTesto());
        q2.setText(domande.get(1).getTesto());
        q3.setText(domande.get(2).getTesto());
        q4.setText(domande.get(3).getTesto());

        // Popola le opzioni di risposta per ogni domanda
        for (int i = 0; i < q1Options.length; i++) {
            q1Options[i].setText(domande.get(0).getRisposte().get(i).getTesto());
        }
        for (int i = 0; i < q2Options.length; i++) {
            q2Options[i].setText(domande.get(1).getRisposte().get(i).getTesto());
        }
        for (int i = 0; i < q3Options.length; i++) {
            q3Options[i].setText(domande.get(2).getRisposte().get(i).getTesto());
        }
        for (int i = 0; i < q4Options.length; i++) {
            q4Options[i].setText(domande.get(3).getRisposte().get(i).getTesto());
        }
    }

    /**
     * Restituisce la sessione di quiz corrente.
     *
     * @return L'oggetto {@link SessioneQuiz} corrente.
     */
    public SessioneQuiz getSessioneCorrente() {
        return currentQuiz;
    }

    /*Metodi privati*/

    /**
     * Configura i {@link ToggleButton} per la selezione della difficoltà,
     * li raggruppa in un {@link ToggleGroup} e imposta un listener
     * per aggiornare i dati visualizzati e gli stili dei pulsanti al cambio di selezione.
     */
    private void setupToggleButtons() {
        // Crea il ToggleGroup per i bottoni di difficoltà
        difficoltaToggleGroup = new ToggleGroup();

        facileButton.setToggleGroup(difficoltaToggleGroup);
        medioButton.setToggleGroup(difficoltaToggleGroup);
        difficileButton.setToggleGroup(difficoltaToggleGroup);

        // Imposta la difficoltà di default
        // Se currentQuiz è nullo o la difficoltà è EASY, seleziona "Facile"
        if (currentQuiz == null || currentQuiz.getDifficolta() == DifficultyEnum.EASY) {
            facileButton.setSelected(true);
        } else if (currentQuiz.getDifficolta() == DifficultyEnum.MEDIUM) {
            medioButton.setSelected(true);
        } else { // Difficoltà HARD
            difficileButton.setSelected(true);
        }


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

    /**
     * Aggiorna gli stili visivi dei {@link ToggleButton} di difficoltà.
     * Applica stili di base a tutti i pulsanti e uno stile evidenziato a quello selezionato.
     */
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

    /**
     * Aggiorna i dati visualizzati nella classifica e nelle statistiche dei punteggi
     * in base alla difficoltà selezionata e allo stato del checkbox "Punteggi Personali".
     */
    private void updateDisplayedData() {
        ToggleButton selected = (ToggleButton) difficoltaToggleGroup.getSelectedToggle();
        DifficultyEnum diff;
        if (selected == null || selected == facileButton) {
            diff = DifficultyEnum.EASY;
        } else if (selected == medioButton) {
            diff = DifficultyEnum.MEDIUM;
        } else { // selected == difficileButton
            diff = DifficultyEnum.HARD;
        }

        boolean soloPersonali = punteggiPersonaliCheckBox.isSelected();

        // Filtra i dati in base alla difficoltà e al checkbox
        List<SessioneQuiz> sessioni;
        if (!soloPersonali) {
            sessioni = QuizController.getScoreboard(diff);
        } else {
            sessioni = QuizController.getPersonalScoreboard(currentUser, diff);
        }

        // Aggiorna la tabella
        aggiornaTableView(sessioni);

        // Aggiorna le statistiche
        aggiornaStats(diff);
    }

    /**
     * Aggiorna la {@link TableView} con la lista di sessioni di quiz fornita.
     *
     * @param sessioni La {@link List} di {@link SessioneQuiz} da visualizzare nella tabella.
     */
    private void aggiornaTableView(List<SessioneQuiz> sessioni) {
        sessioniQuizList = FXCollections.observableList(sessioni);
        tableView.setItems(sessioniQuizList);
        tableView.refresh();
    }

    /**
     * Aggiorna le etichette che mostrano il punteggio medio, il miglior punteggio e il numero di partite
     * per l'utente corrente e la difficoltà specificata.
     *
     * @param difficultyEnum La {@link DifficultyEnum} per cui recuperare le statistiche.
     */
    private void aggiornaStats(DifficultyEnum difficultyEnum) {
        punteggioMedioLabel.setText(QuizController.getPunteggioMedio(currentUser, difficultyEnum));
        migliorPunteggioLabel.setText(QuizController.getMigliorPunteggio(currentUser, difficultyEnum));
        numeroPartiteLabel.setText(QuizController.getPartite(currentUser));
    }

    /**
     * Verifica i campi di login.
     *
     * @return Un oggetto {@link User} con username e password se i campi sono compilati.
     * @throws CampiNonCompilatiException Se uno dei campi di login è vuoto.
     */
    private User checkLogin() throws CampiNonCompilatiException {
        if (loginUsernameField.getText().trim().isEmpty() || loginPasswordField.getText().trim().isEmpty()) {
            throw new CampiNonCompilatiException("Username e password non possono essere vuoti.");
        }
        String username = loginUsernameField.getText().trim();
        String password = loginPasswordField.getText().trim();
        return new User(username, password, false);
    }

    /**
     * Verifica i campi di registrazione, inclusa la corrispondenza delle password e il formato dell'username.
     *
     * @param stage Lo {@link Stage} corrente dell'applicazione.
     * @return Un oggetto {@link User} se tutti i campi sono validi.
     * @throws CampiNonCompilatiException Se uno dei campi di registrazione è vuoto.
     * @throws PasswordDiverseException Se la password e la conferma password non corrispondono.
     * @throws UsernameFormatException Se il formato dell'username non è valido.
     */
    private User checkRegister(Stage stage) throws CampiNonCompilatiException, PasswordDiverseException, UsernameFormatException {
        if (registerUsernameField.getText().trim().isEmpty() || registerPasswordField.getText().trim().isEmpty() || confirmRegisterPasswordField.getText().trim().isEmpty()) {
            throw new CampiNonCompilatiException("Tutti i campi di registrazione devono essere compilati.");
        }
        if (!registerPasswordField.getText().equals(confirmRegisterPasswordField.getText())) {
            throw new PasswordDiverseException("Le password non corrispondono.");
        }
        if (!registerUsernameField.getText().matches("^[A-Za-z0-9_]+$")) {
            throw new UsernameFormatException("Il formato dell'username non è valido. Sono ammessi solo lettere, numeri e underscore.");
        }
        String username = registerUsernameField.getText().trim();
        String password = registerPasswordField.getText().trim();
        return new User(username, password, false);
    }

    /**
     * Restituisce la difficoltà selezionata dai radio button.
     *
     * @return L'enumerazione {@link DifficultyEnum} corrispondente alla selezione.
     */
    private DifficultyEnum getDifficoltaScelta() {
        if (easyRadio.isSelected()) return DifficultyEnum.EASY;
        else if (mediumRadio.isSelected()) return DifficultyEnum.MEDIUM;
        else return DifficultyEnum.HARD;
    }

    /***********/

    /**
     * Gestisce l'evento di login quando il pulsante di login viene cliccato.
     * Tenta di autenticare l'utente e, in caso di successo, mostra la schermata di selezione della difficoltà.
     *
     * @param actionEvent L'evento che ha scatenato l'azione.
     */
    @FXML
    public void handleLogin(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        User userToLog;
        try {
            userToLog = checkLogin();
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
            usernameFieldSettings.setText(userToLog.getUsername()); // Popola il campo username nelle impostazioni

        } else {
            AlertUtils.showAlert(AlertList.LOGIN_FAILURE, stage);
            return;
        }
    }

    /**
     * Gestisce l'evento di registrazione quando il pulsante di registrazione viene cliccato.
     * Tenta di registrare un nuovo utente e, in caso di successo, torna alla schermata di login.
     *
     * @param actionEvent L'evento che ha scatenato l'azione.
     * @throws SQLException Se si verifica un errore durante l'interazione con il database.
     */
    @FXML
    public void handleRegister(ActionEvent actionEvent) throws SQLException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        User userToRegister;
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
            clearRegisterFields(); // Pulisce i campi dopo una registrazione di successo
        } else {
            AlertUtils.showAlert(AlertList.REGISTER_FAILURE, stage);
            // Non pulire i campi qui per permettere all'utente di correggere
        }
    }

    /**
     * Gestisce il ritorno dalla schermata di login a quella di registrazione.
     *
     * @param actionEvent L'evento che ha scatenato l'azione.
     */
    @FXML
    public void backToRegister(ActionEvent actionEvent) {
        clearLoginFields();
        registerVBox.setVisible(true);
        registerVBox.setManaged(true);
        loginVBox.setVisible(false);
        loginVBox.setManaged(false);
    }

    /**
     * Gestisce il ritorno dalla schermata di registrazione a quella di login.
     *
     * @param actionEvent L'evento che ha scatenato l'azione.
     */
    @FXML
    public void backToLogin(ActionEvent actionEvent) {
        clearRegisterFields();
        registerVBox.setVisible(false);
        registerVBox.setManaged(false);
        loginVBox.setVisible(true);
        loginVBox.setManaged(true);
    }

    /**
     * Gestisce il logout dell'utente corrente.
     * Pulisce i campi di login, nasconde la schermata di selezione della difficoltà e mostra la schermata di login.
     * Resetta l'utente corrente a {@code null}.
     *
     * @param actionEvent L'evento che ha scatenato l'azione.
     */
    @FXML
    public void handleLogout(ActionEvent actionEvent) {
        clearLoginFields();
        difficultyVBox.setVisible(false);
        difficultyVBox.setManaged(false);
        loginVBox.setVisible(true);
        loginVBox.setManaged(true);
        currentUser = null;
        // In un'applicazione reale, potresti anche voler fermare qualsiasi timer attivo o resettare altre variabili di sessione.
    }

    /**
     * Controlla se esiste una sessione di quiz salvata per l'utente specificato.
     *
     * @param user L'{@link User} per cui controllare la sessione salvata.
     * @return {@code true} se esiste un file di salvataggio per l'utente, {@code false} altrimenti.
     */
    private boolean hasSessionSuspended(User user) {
        File file = new File("salvataggio_" + user.getUsername() + ".dat");
        return file.exists();
    }

    /**
     * Gestisce l'avvio del gioco (quiz).
     * Controlla se esiste una sessione salvata per l'utente e chiede se vuole riprenderla.
     * In caso contrario, o se l'utente non vuole riprendere, avvia una nuova sessione di quiz.
     *
     * @param actionEvent L'evento che ha scatenato l'azione.
     */
    @FXML
    public void handleStartGame(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        List<Documento> testiDaMostrare = new ArrayList<>();
        DifficultyEnum diff = getDifficoltaScelta();
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
        // Se l'utente ha una sessione in sospeso
        if (hasSessionSuspended(currentUser)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Vuoi riprendere la sessione salvata?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Sessione sospesa trovata");
            alert.setHeaderText(null);
            Optional<ButtonType> result = alert.showAndWait();
            // L'utente vuole procedere con la sessione recuperata
            if (result.isPresent() && result.get() == ButtonType.YES) {
<<<<<<< Updated upstream
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
=======
                System.out.println("Utente corrente: " + currentUser);
                SessioneQuiz sessioneRecuperata = GestoreSalvataggioSessione.loadSessione(currentUser.getUsername());
                // È fondamentale reinizializzare i transient DAO dopo la deserializzazione
                sessioneRecuperata.setDomandeDAOPostgres(new DomandeDAOPostgres());
                System.out.println("Sessione recuperata: " + sessioneRecuperata);
                currentQuiz = sessioneRecuperata;
                // Avvia il timer per il testo dalla posizione in cui era stata salvata la sessione
                QuizController.startTimerPerTesto(currentQuiz.getDocumenti(), 0, timeLabel, timeProgressBar, currentQuiz.getDifficolta(), displayTextLabel, titleQuiz, () -> showQuestionsAndAnswers());
                // Elimina la sessione salvata dopo averla ripresa
                GestoreSalvataggioSessione.eliminaSessione(currentUser.getUsername());
>>>>>>> Stashed changes

            } else {
                // Se non vuole continuare, eliminiamo la sessione vecchia e ne creiamo una nuova
                GestoreSalvataggioSessione.eliminaSessione(currentUser.getUsername());
                try {
                    testiDaMostrare = documentoDAOPostgres.getDocumentiPerDifficolta(diff);
                } catch (NotEnoughDocuments ex) {
                    System.out.println("[DEBUG] Non abbastanza documenti per difficoltà " + diff);
                    AlertUtils.showAlert(AlertList.NON_ABBASTANZA_DOCUMENTI, stage);
                    return;
                }
                SessioneQuiz sessioneQuiz = new SessioneQuiz(testiDaMostrare, diff, currentUser);
                sessioneQuiz.setDomandeDAOPostgres(new DomandeDAOPostgres());
                currentQuiz = sessioneQuiz; // Inizializza la sessione quiz con difficoltà scelta
                QuizController.startTimerPerTesto(testiDaMostrare, 0, timeLabel, timeProgressBar, diff, displayTextLabel, titleQuiz, () -> showQuestionsAndAnswers());
            }
            // Imposta le view per mostrare il testo del quiz
            difficultyVBox.setVisible(false);
            difficultyVBox.setManaged(false);
            testoVBox.setVisible(true);
            testoVBox.setManaged(true);
            return;
        }

        // Non aveva una sessione da recuperare, quindi procede normalmente
        try {
            testiDaMostrare = documentoDAOPostgres.getDocumentiPerDifficolta(diff);
        } catch (NotEnoughDocuments e) {
            System.out.println("[DEBUG] Non abbastanza documenti per difficoltà " + diff);
            AlertUtils.showAlert(AlertList.NON_ABBASTANZA_DOCUMENTI, stage);
            System.out.println("Eccezione " + e.getMessage() + " lanciata");
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

    /**
     * Gestisce il caricamento di un nuovo documento di testo nel sistema.
     * Permette all'amministratore di selezionare un file .txt, leggere il suo contenuto
     * e salvarlo nel database come un nuovo {@link Documento} con una difficoltà assegnata
     * in base alla lunghezza del testo.
     *
     * @param actionEvent L'evento che ha scatenato l'azione.
     */
    @FXML
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
                        .map(String::trim) // Per togliere gli spazi iniziali e finali
                        .filter(line -> !line.isEmpty()) // Elimina linee vuote
                        .map(line -> line.replaceAll("[\\s+]", " "))  // Elimina spazi in più
                        .reduce("", (acc, line) -> acc + " " + line); // Accumula le righe in un'unica stringa

                Documento documento = new Documento(selectedFile.getName().split("\\.")[0], content);
                String[] contentClean = content.split("[\\p{Punct}\\s]+"); // Suddivide il contenuto in parole
                if (contentClean.length < 10) {
                    AlertUtils.showAlert(AlertList.SHORT_TEXT, stage);
                    return;
                }
                // Assegna la difficoltà in base alla lunghezza del testo
                if (contentClean.length > 100) {
                    documento.setDifficolta(DifficultyEnum.HARD);
                } else if (contentClean.length > 50) {
                    documento.setDifficolta(DifficultyEnum.MEDIUM);
                } else {
                    documento.setDifficolta(DifficultyEnum.EASY);
                }

                documentoDAO.insertDocumento(documento);
            } catch (SQLException e) {
                System.out.println("Eccezione SQL lanciata durante l'inserimento del documento: " + e.getMessage());
                AlertUtils.showAlert(AlertList.TEXT_ALREADY_EXISTS, stage);
                return;
            } catch (IOException e) {
                System.err.println("Errore I/O durante la lettura del file: " + e.getMessage());
                AlertUtils.showAlert(AlertList.UPLOAD_FAILURE, stage);
                return;
            }
            AlertUtils.showAlert(AlertList.UPLOAD_SUCCESS, stage);
        }
    }

    /**
     * Gestisce la fine della fase di domande del quiz.
     * Imposta le risposte selezionate dall'utente nella {@link SessioneQuiz} corrente,
     * aggiorna la sessione come completa e mostra la sezione di riepilogo.
     *
     * @param actionEvent L'evento che ha scatenato l'azione.
     */
    @FXML
    public void finishGame(ActionEvent actionEvent) {
        // SETTA LE RISPOSTE SELEZIONATE DALL'UTENTE
        QuizController.setRisposteSelezionate(q1Options, q2Options, q3Options, q4Options, currentQuiz);
        domandaRispostaVBox.setVisible(false);
        domandaRispostaVBox.setManaged(false);
        // Popola le etichette di riepilogo con domande, risposte utente e risposte corrette
        RiepilogoController.setLabelPerRiepilogo(currentQuiz, domanda1Label, domanda2Label, domanda3Label, domanda4Label,
                risposta1UtenteLabel, risposta2UtenteLabel, risposta3UtenteLabel, risposta4UtenteLabel,
                risposta1CorrettaLabel, risposta2CorrettaLabel, risposta3CorrettaLabel, risposta4CorrettaLabel);
        // Imposta la sessione come completa, per evitare che venga salvata come incompleta
        currentQuiz.setIsCompleta(true);
        riepilogoVBox.setVisible(true);
        riepilogoVBox.setManaged(true);
    }

    /**
     * Gestisce la transizione alla schermata della classifica dopo aver completato il quiz.
     * Aggiorna il punteggio dell'utente e la classifica.
     *
     * @param actionEvent L'evento che ha scatenato l'azione.
     * @throws SQLException Se si verifica un errore durante l'interazione con il database per l'aggiornamento della classifica.
     */
    @FXML
    public void goToScoreboard(ActionEvent actionEvent) throws SQLException {
        QuizController.updateScoreboard(currentQuiz); // Salva il punteggio finale nel database
        aggiornaTableView(QuizController.getScoreboard(currentQuiz.getDifficolta())); // Aggiorna la tabella con la classifica generale
        finalScoreVBox.setManaged(false);
        finalScoreVBox.setVisible(false);
        scoreboardVBox.setManaged(true);
        scoreboardVBox.setVisible(true);
        updateDisplayedData(); // Aggiorna i dati visualizzati (classifica e statistiche)
        updateToggleButtonStyles(); // Aggiorna gli stili dei pulsanti di difficoltà
    }

    /**
     * Gestisce il riavvio del gioco dalla schermata della classifica.
     * Nasconde la classifica e mostra la schermata di selezione della difficoltà,
     * resettando la sessione di quiz corrente.
     *
     * @param actionEvent L'evento che ha scatenato l'azione.
     */
    @FXML
    public void restartGame(ActionEvent actionEvent) {
        scoreboardVBox.setVisible(false);
        scoreboardVBox.setManaged(false);
        difficultyVBox.setVisible(true);
        difficultyVBox.setManaged(true);
        currentQuiz = null; // Resetta la sessione corrente per un nuovo gioco
    }

    /**
     * Carica le stopwords da un file di testo (.txt) o CSV (.csv) e le inserisce nel database.
     * Questa funzione è tipicamente accessibile solo agli amministratori.
     *
     * @param actionEvent L'evento che ha scatenato l'azione.
     */
    @FXML
    public void loadStopwords(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        List<String> allStopWords = new ArrayList<>();
        StopWordsDAOPostgres stopWordsDAOPostgres = new StopWordsDAOPostgres();
        FileChooser fc = new FileChooser();
        fc.setTitle("Seleziona un file di stopwords");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("File di testo", "*.txt"),
                new FileChooser.ExtensionFilter("File CSV", "*.csv"),
                new FileChooser.ExtensionFilter("Tutti i file", "*.*")
        );
        File selectedFile = fc.showOpenDialog(stage);

        if (selectedFile != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Splitta la riga usando virgola, punto e virgola, tab o spazio come delimitatori
                    String[] campi = line.split("[,;\\t ]+");
                    for (String parola : campi) {
                        if (!parola.trim().isEmpty()) { // Aggiungi solo parole non vuote
                            allStopWords.add(parola.trim().toLowerCase()); // Converte in minuscolo e trimma
                        }
                    }
                }
                stopWordsDAOPostgres.inserisciStopWords(allStopWords);
                AlertUtils.showAlert(AlertList.UPLOAD_STOPWORDS_SUCCESS, stage);
            } catch (IOException e) {
                System.err.println("Errore I/O durante la lettura del file stopwords: " + e.getMessage());
                AlertUtils.showAlert(AlertList.UPLOAD_STOPWORDS_FAILURE, stage);
            }
        }
    }

    /**
     * Gestisce la transizione alla schermata del punteggio finale dopo il riepilogo del quiz.
     * Calcola il punteggio finale, lo visualizza e pulisce i campi del quiz.
     *
     * @param actionEvent L'evento che ha scatenato l'azione.
     */
    @FXML
    public void vaiAlPunteggio(ActionEvent actionEvent) {
        QuizController.setFinalScore(q1Options, q2Options, q3Options, q4Options, currentQuiz); // Calcola il punteggio
        riepilogoVBox.setManaged(false);
        riepilogoVBox.setVisible(false);
        finalScoreVBox.setVisible(true);
        finalScoreVBox.setManaged(true);
        clearQuizFields(); // Pulisce le selezioni dei radio button
        scoreLabel.setText("Il tuo punteggio finale è: " + currentQuiz.getScore()); // Visualizza il punteggio
    }

    /**
     * Gestisce la transizione alla schermata delle impostazioni.
     * Nasconde la schermata di selezione della difficoltà e mostra quella delle impostazioni.
     * Abilita o disabilita la sezione amministrativa in base ai privilegi dell'utente corrente.
     *
     * @param actionEvent L'evento che ha scatenato l'azione.
     */
    @FXML
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
     * Gestisce il ritorno dalla schermata delle impostazioni alla schermata di selezione della difficoltà.
     *
     * @param actionEvent L'evento che ha scatenato l'azione.
     */
    @FXML
    public void goBack(ActionEvent actionEvent) {
        settingsVBox.setManaged(false);
        settingsVBox.setVisible(false);
        difficultyVBox.setVisible(true);
        difficultyVBox.setManaged(true);
        StartGameController.aggiornaLabel(usernameWelcomeLabel, currentUser.getUsername()); // Aggiorna l'username di benvenuto
    }

    /**
     * Salva le modifiche all'username dell'utente.
     * Verifica il nuovo username per il formato e l'unicità prima di aggiornarlo nel database.
     *
     * @param actionEvent L'evento che ha scatenato l'azione.
     */
    @FXML
    public void saveUsername(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        String nuovoUsername = usernameFieldSettings.getText().trim();

        if (!nuovoUsername.matches("^[A-Za-z0-9_]+$")) {
            AlertUtils.showAlert(AlertList.USERNAME_FORMAT_NON_CORRETTO, stage);
            return;
        }

        UserDAOPostgres userDAOPostgres = new UserDAOPostgres();
        // Controlla se l'username è lo stesso, in tal caso non c'è nessuna modifica
        if (currentUser.getUsername().trim().equals(nuovoUsername)) {
            AlertUtils.showAlert(AlertList.MODIFICA_USERNAME_NON_AVVENUTA, stage);
            return;
        }
        try {
            userDAOPostgres.modificaUsername(currentUser, nuovoUsername);
            // Aggiorna l'username dell'utente corrente nell'applicazione
            currentUser.setUsername(nuovoUsername);

        } catch (UsernameGiaPreso ex) {
            AlertUtils.showAlert(AlertList.USERNAME_ALREADY_TAKEN, stage);
            return;
        }

        // Mostra messaggio di successo e aggiorna la label con il nuovo username
        AlertUtils.showAlert(AlertList.MODIFICA_USERNAME_SUCCESS, stage);
        usernameFieldSettings.setText(nuovoUsername); // Aggiorna il campo nelle impostazioni
        StartGameController.aggiornaLabel(usernameWelcomeLabel, nuovoUsername); // Aggiorna la label di benvenuto
    }

    /**
     * Salva le modifiche alla password dell'utente.
     * Verifica la password corrente e aggiorna la password nel database con la nuova password.
     * Esegue il logout dell'utente dopo un cambio password riuscito per motivi di sicurezza.
     *
     * @param actionEvent L'evento che ha scatenato l'azione.
     */
    @FXML
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
        // Successo
        AlertUtils.showAlert(AlertList.CAMBIO_PASSWORD_SUCCESS, stage);
        passwordFieldSettings.clear();
        passwordFieldNewSettings.clear();
        // Esegue il logout per richiedere il re-login con la nuova password
        handleLogout(actionEvent);
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





