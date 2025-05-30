package com.example.models;

public class ControlloreRisposte {

    // INVOCATO QUANDO IL GIOCO FINISCE PER CALCOLARE SCORE.

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
}
