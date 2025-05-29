package com.example.controller;

import com.example.TimerService.TimerService;
import com.example.difficultySettings.DifficultyEnum;
import com.example.difficultySettings.DifficultySettings;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;


public class QuizController {


    public static void setTitoloTesto(Label label , String titolo){
        label.setText(titolo);
    }




    public static void startTimerPerTesto(int numeroTesto , Label timeLabel , ProgressBar timeProgressBar , DifficultyEnum difficolta) {
        // Carica il titolo del testo numeroTesto (esempio)
        // QuizController.setTitoloTesto(titleQuiz, getTitoloPerTesto(numeroTesto));
        int timeLimit = DifficultySettings.getTimeLimit(difficolta);
        int numeroTesti = DifficultySettings.getNumeroTesti(difficolta);
        // Timer da 30 secondi con callback
        TimerService timerService = new TimerService(timeLimit, () -> {
            Platform.runLater(() -> {
                if (numeroTesto < numeroTesti) {
                    startTimerPerTesto(numeroTesto + 1 , timeLabel , timeProgressBar , difficolta);
                } else {
                    // Fine fase lettura , si passa alla nuova scena delle domande

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

    // Metodo di esempio per ottenere il titolo in base al numero testo
    private String getTitoloPerTesto(int numeroTesto) {
        // Qui prendi il titolo dal DB o da lista
        return "Testo " + numeroTesto;
    }
}
