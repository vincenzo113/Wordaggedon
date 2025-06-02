package com.example.controller;

import com.example.dao.SessionQuiz.SessionDAOPostgres;
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

public class QuizController {

    Map<String, Integer> conteggio ;
    static SessionDAOPostgres sessionDAOPostgres = new SessionDAOPostgres();

    public static void startTimerPerTesto(List<Documento> documenti , int numeroTesto , Label timeLabel , ProgressBar timeProgressBar , DifficultyEnum difficolta , Label displayText , Label displayTitleText,
                                          Runnable callback ) {
        int timeLimit = DifficultySettings.getTimeLimit(difficolta);
        int maxTesti = DifficultySettings.getNumeroTesti(difficolta);
        //Qui bisogna settare il testo e il titolo del testo ogni volta
        displayText.setText(documenti.get(numeroTesto).getContenuto());
        displayTitleText.setText(documenti.get(numeroTesto).getTitolo());

        TimerService timerService = new TimerService(1, () -> {
            Platform.runLater(() -> {
                if (numeroTesto + 1 < maxTesti) {
                    startTimerPerTesto(documenti, numeroTesto + 1, timeLabel, timeProgressBar, difficolta , displayText , displayTitleText,callback);
                } else {
                    // Fine fase lettura , si passa alla nuova scena delle domande
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

    public static void setFinalScore(RadioButton[] q1Options, RadioButton[] q2Options, RadioButton[] q3Options, RadioButton[] q4Options ,SessioneQuiz sessioneQuiz){

        int score = 0;
        setRisposteSelezionate(q1Options, q2Options, q3Options, q4Options, sessioneQuiz);
        for (Domanda domanda : sessioneQuiz.getDomande()) {
            for (Risposta risposta : domanda.getRisposte()) {
                if (risposta.isSelected() && risposta.getCorretta()) {
                    score++;
                }
            }
        }
        sessioneQuiz.setScore(score);
    }

    public static void updateScoreboard(SessioneQuiz sessioneQuiz){
        sessionDAOPostgres.insertSessione(sessioneQuiz);
        return;
    }
    public static List<SessioneQuiz> getScoreboard() {
        List<SessioneQuiz> sessioni = null;
        try {
            sessioni = sessionDAOPostgres.selectSessionsWithTopScores();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sessioni;
    }

    public static void setRisposteSelezionate(RadioButton[] q1Options, RadioButton[] q2Options, RadioButton[] q3Options, RadioButton[] q4Options, SessioneQuiz currentQuiz) {
        setRispostaSelezionataPerGruppo(q1Options, currentQuiz.getDomande().get(0));
        setRispostaSelezionataPerGruppo(q2Options, currentQuiz.getDomande().get(1));
        setRispostaSelezionataPerGruppo(q3Options, currentQuiz.getDomande().get(2));
        setRispostaSelezionataPerGruppo(q4Options, currentQuiz.getDomande().get(3));
    }

    private static void setRispostaSelezionataPerGruppo(RadioButton[] options, Domanda domanda) {
        for (int i = 0; i < options.length; i++) {
            if (options[i].isSelected()) {
                // Imposta selected = true solo sulla risposta corrispondente a questo RadioButton
                domanda.getRisposte().forEach(r -> r.setSelected(false)); // prima resetta tutte a false
                domanda.getRisposte().get(i).setSelected(true);
                break;
            }
        }
    }

    public static List<SessioneQuiz> getPersonalScoreboard(User user) {
        List<SessioneQuiz> sessioni = null;
        try {
            sessioni = sessionDAOPostgres.selectPersonalScoreboard(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sessioni;
    }
}
