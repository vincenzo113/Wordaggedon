package com.example.controller;

import com.example.dao.SessionQuiz.SessionDAOPostgres;
import com.example.dao.User.UserDAOPostgres;
import com.example.exceptions.DatabaseException;
import com.example.models.*;
import com.example.difficultySettings.DifficultyEnum;
import com.example.difficultySettings.DifficultySettings;
import com.example.timerService.TimerService;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import java.util.*;

/**
 * Controller principale per la gestione del quiz e delle sue funzionalità.
 * Gestisce il flusso del gioco, inclusi timer, punteggi, e interazioni con il database.
 * Coordina la visualizzazione dei testi, la raccolta delle risposte e il calcolo dei punteggi.
 */
public class QuizController {

    /** Mappa per il conteggio interno */
    Map<String, Integer> conteggio;
    
    /** DAO per l'accesso ai dati delle sessioni di quiz */
    static SessionDAOPostgres sessionDAOPostgres = new SessionDAOPostgres();
    
    /** DAO per l'accesso ai dati degli utenti */
    static UserDAOPostgres userDAOPostgres = new UserDAOPostgres();

    /**
     * Gestisce il timer per la visualizzazione sequenziale dei testi del quiz.
     * Controlla la progressione attraverso i testi e aggiorna l'interfaccia utente.
     *
     * @param documenti Lista dei documenti da mostrare
     * @param numeroTesto Indice del testo corrente (0-based)
     * @param timeLabel Label per il countdown
     * @param timeProgressBar Barra di avanzamento del timer
     * @param difficolta Livello di difficoltà che determina i limiti di tempo
     * @param displayText Label per il contenuto del testo
     * @param displayTitleText Label per il titolo del testo
     * @param callback Azione da eseguire al completamento di tutti i testi
     */
    public static void startTimerPerTesto(List<Documento> documenti, int numeroTesto, Label timeLabel, 
            ProgressBar timeProgressBar, DifficultyEnum difficolta, Label displayText, 
            Label displayTitleText, Runnable callback) {
        int timeLimit = DifficultySettings.getTimeLimit(difficolta);
        int maxTesti  = 3 ;
        
        displayText.setText(documenti.get(numeroTesto).getContenuto());
        displayTitleText.setText(documenti.get(numeroTesto).getTitolo());

        TimerService timerService = new TimerService(timeLimit, () -> {
            Platform.runLater(() -> {
                if (numeroTesto + 1 < maxTesti) {
                    startTimerPerTesto(documenti, numeroTesto + 1, timeLabel, timeProgressBar, 
                            difficolta, displayText, displayTitleText, callback);
                } else {
                    callback.run();
                }
            });
        });

        timeProgressBar.progressProperty().bind(timerService.progressProperty());

        timerService.progressProperty().addListener((obs, oldVal, newVal) -> {
            int remaining = timeLimit - (int) (newVal.doubleValue() * timeLimit);
            timeLabel.setText(remaining + "s");
        });

        timerService.start();
    }

    /**
     * Calcola e registra il punteggio finale della sessione di quiz.
     * Verifica le risposte selezionate e aggiorna il punteggio della sessione.
     *
     * @param q1Options Risposte della prima domanda
     * @param q2Options Risposte della seconda domanda
     * @param q3Options Risposte della terza domanda
     * @param q4Options Risposte della quarta domanda
     * @param sessioneQuiz Sessione da aggiornare con il punteggio
     */
    public static void setFinalScore(RadioButton[] q1Options, RadioButton[] q2Options, 
            RadioButton[] q3Options, RadioButton[] q4Options, SessioneQuiz sessioneQuiz) {
        int score = 0;
        setRisposteSelezionate(q1Options, q2Options, q3Options, q4Options, sessioneQuiz);
        for (Domanda domanda : sessioneQuiz.getDomande()) {
            for (Risposta risposta : domanda.getRisposte()) {
                if (risposta.isSelected() && risposta.isCorretta()) {
                    score++;
                }
            }
        }
        sessioneQuiz.setScore(score);
    }

    /**
     * Aggiorna la classifica con una nuova sessione di quiz completata.
     *
     * @param sessioneQuiz Sessione da salvare nel database
     */
    public static void updateScoreboard(SessioneQuiz sessioneQuiz) {
        sessionDAOPostgres.insertSessione(sessioneQuiz);
    }

    /**
     * Recupera la classifica generale per una specifica difficoltà.
     *
     * @param difficolta Livello di difficoltà della classifica
     * @return Lista delle sessioni con i punteggi migliori
     * @throws RuntimeException se si verificano errori di accesso al database
     */
    public static List<SessioneQuiz> getScoreboard(DifficultyEnum difficolta) {
        try {
            return sessionDAOPostgres.selectSessionsWithTopScores(difficolta);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Registra le risposte selezionate dall'utente per tutte le domande.
     *
     * @param q1Options array di RadioButton per la prima domanda
     * @param q2Options array di RadioButton per la seconda domanda
     * @param q3Options array di RadioButton per la terza domanda
     * @param q4Options array di RadioButton per la quarta domanda
     * @param currentQuiz oggetto SessioneQuiz contenente le domande attuali
     */
    public static void setRisposteSelezionate(RadioButton[] q1Options, RadioButton[] q2Options, 
            RadioButton[] q3Options, RadioButton[] q4Options, SessioneQuiz currentQuiz) {
        setRispostaSelezionataPerGruppo(q1Options, currentQuiz.getDomande().get(0));
        setRispostaSelezionataPerGruppo(q2Options, currentQuiz.getDomande().get(1));
        setRispostaSelezionataPerGruppo(q3Options, currentQuiz.getDomande().get(2));
        setRispostaSelezionataPerGruppo(q4Options, currentQuiz.getDomande().get(3));
    }

    /**
     * Registra la risposta selezionata per una singola domanda.
     */
    private static void setRispostaSelezionataPerGruppo(RadioButton[] options, Domanda domanda) {
        for (int i = 0; i < options.length; i++) {
            if (options[i].isSelected()) {
                domanda.getRisposte().forEach(r -> r.setSelected(false));
                domanda.getRisposte().get(i).setSelected(true);
                break;
            }
        }
    }

    /**
     * Recupera la classifica personale di un utente per una specifica difficoltà.
     *
     * @param user Utente di cui recuperare la classifica
     * @param difficolta Livello di difficoltà
     * @return Lista delle sessioni dell'utente ordinate per punteggio
     * @throws RuntimeException se si verificano errori di accesso al database
     */
    public static List<SessioneQuiz> getPersonalScoreboard(User user, DifficultyEnum difficolta) {
        try {
            return sessionDAOPostgres.selectPersonalScoreboard(user, difficolta);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Calcola e restituisce il punteggio medio di un utente per una difficoltà.
     *
     * @param user Utente di cui calcolare la media
     * @param difficolta Livello di difficoltà
     * @return Punteggio medio formattato con due decimali
     */
    public static String getPunteggioMedio(User user, DifficultyEnum difficolta) {
        return String.format("%.2f", userDAOPostgres.punteggioAvg(user, difficolta));
    }

    /**
     * Recupera il miglior punteggio di un utente per una difficoltà.
     *
     * @param user Utente di cui recuperare il record
     * @param difficolta Livello di difficoltà
     * @return Miglior punteggio come stringa
     */
    public static String getMigliorPunteggio(User user, DifficultyEnum difficolta) {
        return String.valueOf(userDAOPostgres.punteggioBest(user, difficolta));
    }

    /**
     * Recupera il numero totale di partite giocate da un utente.
     *
     * @param user Utente di cui contare le partite
     * @return Numero totale di partite come stringa
     */
    public static String getPartite(User user) {
        return String.valueOf(userDAOPostgres.contPartite(user));
    }
}