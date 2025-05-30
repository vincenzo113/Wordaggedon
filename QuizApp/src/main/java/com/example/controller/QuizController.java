package com.example.controller;

import com.example.dao.QuizDAOPostgres;
import com.example.models.Domanda;
import com.example.models.Quiz;
import com.example.models.Risposta;

import com.example.TimerService.TimerService;
import com.example.difficultySettings.DifficultyEnum;
import com.example.difficultySettings.DifficultySettings;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QuizController {

   QuizDAOPostgres quizDAOPostgres = new QuizDAOPostgres();
    Map<String, Integer> conteggio ;

    public Quiz getQuiz(DifficultyEnum difficolta) {
        Quiz quiz = new Quiz(null,null,difficolta);
        quiz.setTesti(quizDAOPostgres.selectDocumenti(difficolta)); // Esegui la query per ottenere i testi
        quiz.generaDomande();
        return quiz ;

    }

    public void setMappaturaQuiz(){
        String stringa = "" ;
        Stream<String> parole = Arrays.stream(stringa.split(" "));
        Map<String, Integer> conteggio = parole.collect(Collectors.groupingBy(String::toString,
                Collectors.summingInt(p->1))); //per ogni stringa somma 1 al conteggio

        Domanda [] domande = new Domanda[4]; // Supponiamo di avere quattro domande per il quiz facile
        Random random = new Random();

        List<String> chiavi = new ArrayList<>(conteggio.keySet());
        String parola = chiavi.get(random.nextInt(chiavi.size()));
        int rispostaCorretta = conteggio.get(parola);
    }

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
                    showNextDocument(); // Mostra il prossimo documento
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

    static private void showNextDocument(/*QUI GLI DEVI PASSARE TUTTO PER SETTARE DOCUMENTO*/){
        return ;
    }
}
