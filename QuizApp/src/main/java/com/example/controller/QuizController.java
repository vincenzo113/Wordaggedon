package com.example.controller;

import com.example.dao.QuizDAOPostgres;
import com.example.models.Documento;
import com.example.models.Domanda;
import com.example.models.Quiz;

import com.example.difficultySettings.DifficultyEnum;
import com.example.difficultySettings.DifficultySettings;
import com.example.timerService.TimerService;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QuizController {

   QuizDAOPostgres quizDAOPostgres = new QuizDAOPostgres();
    Map<String, Integer> conteggio ;

    public Quiz getQuiz(DifficultyEnum difficolta) {
        return null;
    }

    public static void startTimerPerTesto(List<Documento> documenti , int numeroTesto , Label timeLabel , ProgressBar timeProgressBar , DifficultyEnum difficolta , Label displayText , Label displayTitleText,
                                          Runnable showQuestionsAndAnswers) {
        int timeLimit = DifficultySettings.getTimeLimit(difficolta);
        int maxTesti = DifficultySettings.getNumeroTesti(difficolta);
        //Qui bisogna settare il testo e il titolo del testo ogni volta
        displayText.setText(documenti.get(numeroTesto).getContenuto());
        displayTitleText.setText(documenti.get(numeroTesto).getTitolo());

        TimerService timerService = new TimerService(timeLimit, () -> {
            Platform.runLater(() -> {
                if (numeroTesto + 1 < maxTesti) {
                    startTimerPerTesto(documenti, numeroTesto + 1, timeLabel, timeProgressBar, difficolta , displayText , displayTitleText,null);
                } else {
                    // Fine fase lettura , si passa alla nuova scena delle domande
                    Alert alert = new Alert(Alert.AlertType.INFORMATION , "Implementare che bisogna andare alla prossima scena , quella in cui si mostrano le domande");
                    alert.showAndWait();
                    showQuestionsAndAnswers.run();
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


}
