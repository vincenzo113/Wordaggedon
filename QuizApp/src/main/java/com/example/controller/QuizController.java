package com.example.controller;

import com.example.dao.SessionQuiz.SessionDAOPostgres;
import com.example.models.Documento;

import com.example.difficultySettings.DifficultyEnum;
import com.example.difficultySettings.DifficultySettings;
import com.example.models.Domanda;
import com.example.models.Risposta;
import com.example.models.SessioneQuiz;
import com.example.timerService.TimerService;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;

import java.sql.SQLException;
import java.util.*;

public class QuizController {

    Map<String, Integer> conteggio ;
    SessionDAOPostgres sessionDAOPostgres = new SessionDAOPostgres();

    public static void startTimerPerTesto(List<Documento> documenti , int numeroTesto , Label timeLabel , ProgressBar timeProgressBar , DifficultyEnum difficolta , Label displayText , Label displayTitleText,
                                          Runnable callback ) {
        int timeLimit = DifficultySettings.getTimeLimit(difficolta);
        int maxTesti = DifficultySettings.getNumeroTesti(difficolta);
        //Qui bisogna settare il testo e il titolo del testo ogni volta
        displayText.setText(documenti.get(numeroTesto).getContenuto());
        displayTitleText.setText(documenti.get(numeroTesto).getTitolo());

        TimerService timerService = new TimerService(timeLimit, () -> {
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

    public static void setFinalScore(SessioneQuiz sessioneQuiz){

        int score = 0;

        for (Domanda domanda : sessioneQuiz.getDomande()) {
            for (Risposta risposta : domanda.getRisposte()) {
                if (risposta.isSelected() && risposta.getCorretta()) {
                    score++;
                }
            }
        }
        sessioneQuiz.setScore(score);
    }


    public List<SessioneQuiz> setScoreboard(SessioneQuiz currentQuiz) throws SQLException {
        sessionDAOPostgres.insertSessione(currentQuiz);
        List<SessioneQuiz> sessioni = sessionDAOPostgres.selectSessionsWithTopScores();
        return sessioni;
    }

    public void setRisposteSelezionate(RadioButton[] q1Options, RadioButton[] q2Options, RadioButton[] q3Options, RadioButton[] q4Options, SessioneQuiz currentQuiz) {
        for(RadioButton r1 : q1Options) {
            if (r1.isSelected()) {
                currentQuiz.getDomande().get(0).getRisposte().forEach(risposta -> {
                    if (r1.isSelected()) {
                        risposta.setSelected(true);
                    }

                });
            }
            r1.setSelected(false);
        }
        for(RadioButton r2 : q2Options) {
            if (r2.isSelected()) {
                currentQuiz.getDomande().get(1).getRisposte().forEach(risposta -> {
                    if (r2.isSelected()) {
                        risposta.setSelected(true);
                    }
                });
            }
            r2.setSelected(false);
        }
        for(RadioButton r3 : q3Options) {
            if (r3.isSelected()) {
                currentQuiz.getDomande().get(2).getRisposte().forEach(risposta -> {
                    if (r3.isSelected()) {
                        risposta.setSelected(true);
                    }
                });
            }
            r3.setSelected(false);
        }
        for(RadioButton r4 : q4Options) {
            if (r4.isSelected()) {
                currentQuiz.getDomande().get(3).getRisposte().forEach(risposta -> {
                    if (r4.isSelected()) {
                        risposta.setSelected(true);
                    }
                });
            }
            r4.setSelected(false);
        }
    }
}
