package com.example.controller;

import com.example.dao.SessionQuiz.SessionDAOPostgres;
import com.example.dao.User.UserDAOPostgres;
import com.example.models.*;

import com.example.difficultySettings.DifficultyEnum;
import com.example.difficultySettings.DifficultySettings;
import com.example.timerService.TimerService;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;

import java.sql.SQLException;
import java.util.*;

/**
 * Il {@code QuizController} gestisce la logica principale del quiz, inclusi il timer,
 * la gestione delle domande e risposte, il calcolo del punteggio e l'interazione con
 * i DAO per salvare e recuperare i dati delle sessioni e degli utenti.
 */
public class QuizController {

    // Questo campo 'conteggio' non sembra essere utilizzato nel codice fornito,
    // potrebbe essere rimosso o la sua utilità chiarita.
    Map<String, Integer> conteggio;

    // Istanze statiche dei DAO per l'interazione con il database.
    // Sono statiche per essere facilmente accessibili dai metodi statici del controller.
    static SessionDAOPostgres sessionDAOPostgres = new SessionDAOPostgres();
    static UserDAOPostgres userDAOPostgres = new UserDAOPostgres();

    /**
     * Avvia un timer per la visualizzazione dei testi di un quiz. Questo metodo ricorsivo
     * mostra un testo alla volta per un periodo di tempo definito dalla difficoltà,
     * e passa al testo successivo o invoca un callback al termine della fase di lettura.
     *
     * @param documenti La lista di {@link Documento} da mostrare.
     * @param numeroTesto L'indice del testo corrente da visualizzare nella lista {@code documenti}.
     * @param timeLabel La {@link Label} di JavaFX che visualizza il tempo rimanente.
     * @param timeProgressBar La {@link ProgressBar} di JavaFX che visualizza il progresso del timer.
     * @param difficolta L'enumerazione {@link DifficultyEnum} che definisce il limite di tempo per testo.
     * @param displayText La {@link Label} di JavaFX dove viene visualizzato il contenuto del testo.
     * @param displayTitleText La {@link Label} di JavaFX dove viene visualizzato il titolo del testo.
     * @param callback Un {@link Runnable} da eseguire quando tutti i testi sono stati visualizzati (fine fase di lettura).
     */
    public static void startTimerPerTesto(List<Documento> documenti, int numeroTesto, Label timeLabel, ProgressBar timeProgressBar, DifficultyEnum difficolta, Label displayText, Label displayTitleText,
                                          Runnable callback) {
        int timeLimit = DifficultySettings.getTimeLimit(difficolta); // Recupera il limite di tempo per la difficoltà
        int maxTesti = DifficultySettings.getNumeroTesti(); // Recupera il numero massimo di testi

        // Qui bisogna settare il testo e il titolo del testo ogni volta
        System.out.println("Documenti: " + documenti); // Output di debugging

        // Imposta il contenuto e il titolo del testo corrente nelle etichette dell'UI.
        displayText.setText(documenti.get(numeroTesto).getContenuto());
        displayTitleText.setText(documenti.get(numeroTesto).getTitolo());

        // Crea un nuovo TimerService con un intervallo di 1 secondo per il conto alla rovescia.
        TimerService timerService = new TimerService(timeLimit, () -> { // Usa timeLimit come durata del timer
            Platform.runLater(() -> { // Esegue il codice sull'Application Thread di JavaFX
                if (numeroTesto + 1 < maxTesti) {
                    // Se ci sono ancora testi da mostrare, avvia il timer per il prossimo testo.
                    startTimerPerTesto(documenti, numeroTesto + 1, timeLabel, timeProgressBar, difficolta, displayText, displayTitleText, callback);
                } else {
                    // Fine fase lettura di tutti i testi, si passa alla nuova scena delle domande tramite il callback.
                    callback.run();
                }
            });
        });

        // Lega la proprietà di progresso della ProgressBar al progresso del TimerService.
        timeProgressBar.progressProperty().bind(timerService.progressProperty());

        // Aggiunge un listener al progresso del timer per aggiornare l'etichetta del tempo rimanente.
        timerService.progressProperty().addListener((obs, oldVal, newVal) -> {
            // Calcola il tempo rimanente in secondi.
            int remaining = timeLimit - (int) (newVal.doubleValue() * timeLimit);
            timeLabel.setText(remaining + "s");
        });

        // Avvia il servizio del timer.
        timerService.start();
    }

    /**
     * Calcola il punteggio finale della {@link SessioneQuiz} basandosi sulle risposte selezionate
     * dall'utente. Imposta anche le risposte selezionate all'interno dell'oggetto {@link SessioneQuiz}.
     *
     * @param q1Options Un array di {@link RadioButton} per le opzioni della domanda 1.
     * @param q2Options Un array di {@link RadioButton} per le opzioni della domanda 2.
     * @param q3Options Un array di {@link RadioButton} per le opzioni della domanda 3.
     * @param q4Options Un array di {@link RadioButton} per le opzioni della domanda 4.
     * @param sessioneQuiz L'oggetto {@link SessioneQuiz} corrente da aggiornare con il punteggio.
     */
    public static void setFinalScore(RadioButton[] q1Options, RadioButton[] q2Options, RadioButton[] q3Options, RadioButton[] q4Options, SessioneQuiz sessioneQuiz) {
        int score = 0;
        // Prima, imposta le risposte selezionate dall'utente nella sessione.
        setRisposteSelezionate(q1Options, q2Options, q3Options, q4Options, sessioneQuiz);

        // Poi, itera su tutte le domande della sessione e calcola il punteggio.
        for (Domanda domanda : sessioneQuiz.getDomande()) {
            for (Risposta risposta : domanda.getRisposte()) {
                if (risposta.isSelected() && risposta.getCorretta()) {
                    score++; // Incrementa il punteggio se la risposta selezionata è corretta.
                }
            }
        }
        sessioneQuiz.setScore(score); // Imposta il punteggio finale nella sessione.
    }

    /**
     * Aggiorna la classifica inserendo la sessione di quiz completata nel database.
     *
     * @param sessioneQuiz L'oggetto {@link SessioneQuiz} completato da salvare.
     */
    public static void updateScoreboard(SessioneQuiz sessioneQuiz) {
        sessionDAOPostgres.insertSessione(sessioneQuiz);
    }

    /**
     * Recupera la classifica delle migliori sessioni di quiz per una data difficoltà.
     *
     * @param difficolta L'enumerazione {@link DifficultyEnum} per cui recuperare la classifica.
     * @return Una {@link List} di {@link SessioneQuiz} che rappresenta la classifica.
     * @throws RuntimeException Se si verifica un errore SQL durante il recupero dei dati.
     */
    public static List<SessioneQuiz> getScoreboard(DifficultyEnum difficolta) {
        List<SessioneQuiz> sessioni = null;
        try {
            sessioni = sessionDAOPostgres.selectSessionsWithTopScores(difficolta);
        } catch (SQLException e) {
            System.err.println("Errore SQL nel recupero della classifica generale: " + e.getMessage());
            throw new RuntimeException("Errore nel recupero della classifica generale.", e);
        }
        return sessioni;
    }

    /**
     * Imposta la proprietà 'selected' delle risposte all'interno dell'oggetto {@link SessioneQuiz}
     * basandosi sui {@link RadioButton} selezionati dall'utente.
     *
     * @param q1Options Un array di {@link RadioButton} per le opzioni della domanda 1.
     * @param q2Options Un array di {@link RadioButton} per le opzioni della domanda 2.
     * @param q3Options Un array di {@link RadioButton} per le opzioni della domanda 3.
     * @param q4Options Un array di {@link RadioButton} per le opzioni della domanda 4.
     * @param currentQuiz L'oggetto {@link SessioneQuiz} corrente da aggiornare.
     */
    public static void setRisposteSelezionate(RadioButton[] q1Options, RadioButton[] q2Options, RadioButton[] q3Options, RadioButton[] q4Options, SessioneQuiz currentQuiz) {
        // Applica la selezione per ogni gruppo di domande.
        setRispostaSelezionataPerGruppo(q1Options, currentQuiz.getDomande().get(0));
        setRispostaSelezionataPerGruppo(q2Options, currentQuiz.getDomande().get(1));
        setRispostaSelezionataPerGruppo(q3Options, currentQuiz.getDomande().get(2));
        setRispostaSelezionataPerGruppo(q4Options, currentQuiz.getDomande().get(3));
    }

    /**
     * Metodo helper privato per impostare la risposta selezionata per una singola domanda.
     * Scorre i {@link RadioButton} di un gruppo e imposta la proprietà 'selected' sulla
     * corrispondente {@link Risposta} all'interno dell'oggetto {@link Domanda}.
     *
     * @param options Un array di {@link RadioButton} che rappresentano le opzioni di risposta per una domanda.
     * @param domanda L'oggetto {@link Domanda} da aggiornare con la risposta selezionata.
     */
    private static void setRispostaSelezionataPerGruppo(RadioButton[] options, Domanda domanda) {
        // Prima, resetta tutte le risposte della domanda a non selezionate.
        domanda.getRisposte().forEach(r -> r.setSelected(false));
        // Poi, trova il RadioButton selezionato e imposta la corrispondente Risposta come selezionata.
        for (int i = 0; i < options.length; i++) {
            if (options[i].isSelected()) {
                domanda.getRisposte().get(i).setSelected(true);
                break; // Una volta trovata la selezione, esce dal loop.
            }
        }
    }

    /**
     * Recupera la classifica personale delle sessioni di quiz per un dato utente e difficoltà.
     *
     * @param user L'{@link User} per cui recuperare la classifica personale.
     * @param difficolta L'enumerazione {@link DifficultyEnum} per cui filtrare le sessioni.
     * @return Una {@link List} di {@link SessioneQuiz} che rappresenta la classifica personale.
     * @throws RuntimeException Se si verifica un errore SQL durante il recupero dei dati.
     */
    public static List<SessioneQuiz> getPersonalScoreboard(User user, DifficultyEnum difficolta) {
        List<SessioneQuiz> sessioni = null;
        try {
            sessioni = sessionDAOPostgres.selectPersonalScoreboard(user, difficolta);
        } catch (SQLException e) {
            System.err.println("Errore SQL nel recupero della classifica personale: " + e.getMessage());
            throw new RuntimeException("Errore nel recupero della classifica personale.", e);
        }
        return sessioni;
    }

    /**
     * Recupera il punteggio medio dell'utente per una data difficoltà.
     *
     * @param user L'{@link User} per cui calcolare il punteggio medio.
     * @param difficolta L'enumerazione {@link DifficultyEnum} per cui filtrare le sessioni.
     * @return Una {@link String} formattata che rappresenta il punteggio medio (due decimali).
     */
    public static String getPunteggioMedio(User user, DifficultyEnum difficolta) {
        System.out.println("userdao" + userDAOPostgres); // Output di debugging
        String punteggioMedio;
        punteggioMedio = String.format("%.2f", userDAOPostgres.punteggioAvg(user, difficolta));
        return punteggioMedio;
    }

    /**
     * Recupera il miglior punteggio ottenuto dall'utente per una data difficoltà.
     *
     * @param user L'{@link User} per cui recuperare il miglior punteggio.
     * @param difficolta L'enumerazione {@link DifficultyEnum} per cui filtrare le sessioni.
     * @return Una {@link String} che rappresenta il miglior punteggio.
     */
    public static String getMigliorPunteggio(User user, DifficultyEnum difficolta) {
        String migliorPunteggio;
        migliorPunteggio = String.valueOf(userDAOPostgres.punteggioBest(user, difficolta));
        return migliorPunteggio;
    }

    /**
     * Recupera il numero totale di partite giocate dall'utente.
     *
     * @param user L'{@link User} per cui contare le partite.
     * @return Una {@link String} che rappresenta il numero totale di partite.
     */
    public static String getPartite(User user) {
        String partite;
        partite = String.valueOf(userDAOPostgres.contPartite(user));
        return partite;
    }
}
