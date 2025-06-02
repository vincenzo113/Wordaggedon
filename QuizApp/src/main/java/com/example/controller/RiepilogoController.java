package com.example.controller;

import com.example.models.Domanda;
import com.example.models.Risposta;
import com.example.models.SessioneQuiz;
import javafx.scene.control.Label;

import java.util.List;


public class RiepilogoController {


    public static void setLabelPerRiepilogo(SessioneQuiz sessioneQuiz , Label domanda1 , Label domanda2 ,Label domanda3 , Label domanda4 , Label risposta1Utente , Label risposta2Utente,Label risposta3Utente,Label risposta4Utente , Label risposta1Corretta , Label risposta2Corretta , Label risposta3Corretta, Label risposta4Corretta){
        List<Domanda> domande = sessioneQuiz.getDomande();
        domanda1.setText(domande.get(0).getTesto());
        domanda2.setText(domande.get(1).getTesto());
        domanda3.setText(domande.get(2).getTesto());
        domanda4.setText(domande.get(3).getTesto());
        //Setto le risposte utente
        List<Risposta> risposteAllaDomanda1 = domande.get(0).getRisposte();
        for(Risposta risposta : risposteAllaDomanda1){
            if(risposta.isSelected()){
                risposta1Utente.setText("Hai risposto: "+risposta.getTesto());
                //Se è corretta la setti verde
                if(risposta.getCorretta()){

                    risposta1Utente.setStyle("-fx-text-fill: green; !important;");
                }
                else
                    risposta1Utente.setStyle("-fx-text-fill: red !important;");
                break;
            }

        }

        List<Risposta> risposteAllaDomanda2 = domande.get(1).getRisposte();
        for(Risposta risposta : risposteAllaDomanda2){
            if(risposta.isSelected()){
                risposta2Utente.setText("Hai risposto: "+risposta.getTesto());
                //Se è corretta la setti verde
                if(risposta.getCorretta()){

                    risposta2Utente.setStyle("-fx-text-fill: green !important;");

                }
                else
                    risposta2Utente.setStyle("-fx-text-fill: red !important;");

                break;
            }

        }

        List<Risposta> risposteAllaDomanda3 = domande.get(2).getRisposte();
        for(Risposta risposta : risposteAllaDomanda3){
            if(risposta.isSelected()){
                //Settiamo la label con quella selezionata
                risposta3Utente.setText("Hai risposto: "+risposta.getTesto());
                //Se è corretta la setti verde
                if(risposta.getCorretta()){

                    risposta3Utente.setStyle("-fx-text-fill: green !important;");
                }
                else
                    risposta3Utente.setStyle("-fx-text-fill: red !important;");
                break;
            }

        }

        List<Risposta> risposteAllaDomanda4 = domande.get(3).getRisposte();
        for(Risposta risposta : risposteAllaDomanda4){
            if(risposta.isSelected()){
                //Settiamo la label con quella selezionata
                risposta4Utente.setText("Hai risposto: "+risposta.getTesto());
                //Se è corretta la setti verde
                if(risposta.getCorretta()){

                    risposta4Utente.setStyle("-fx-text-fill: green !important;");
                }
                else
                    risposta4Utente.setStyle("-fx-text-fill: red !important;");


                break;
            }

        }


        //Settiamo ora le risposte corrette
        for(Risposta risposta : domande.get(0).getRisposte()){
            if(risposta.getCorretta()) {
                risposta1Corretta.setText("La risposta corretta era: "+risposta.getTesto());
                break;
            }
        }

        for(Risposta risposta : domande.get(1).getRisposte()){
            if(risposta.getCorretta()) {
                risposta2Corretta.setText("La risposta corretta era: "+risposta.getTesto());
                break;
            }
        }

        for(Risposta risposta : domande.get(2).getRisposte()){
            if(risposta.getCorretta()) {
                risposta3Corretta.setText("La risposta corretta era: "+risposta.getTesto());
                break;
            }
        }

        for(Risposta risposta : domande.get(3).getRisposte()){
            if(risposta.getCorretta()) {
                risposta4Corretta.setText("La risposta corretta era: "+risposta.getTesto());

                break;
            }
        }



    }

}
