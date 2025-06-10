package com.example.controller;

import com.example.models.Domanda;
import com.example.models.Risposta;
import com.example.models.SessioneQuiz;
import javafx.scene.control.Label;

import java.util.Arrays;
import java.util.List;

/**
 * Controller per la visualizzazione del riepilogo di una sessione di quiz.
 * Imposta i testi e gli stili delle etichette (Label) che mostrano:
 * - le domande
 * - le risposte date dall'utente (con colore verde per le corrette e rosso per le errate)
 * - le risposte corrette.
 */
public class RiepilogoController {

    /**
     * Imposta le etichette del riepilogo per una sessione di quiz. Popola i testi
     * delle domande, delle risposte fornite dall'utente e delle risposte corrette.
     *
     * @param sessioneQuiz Oggetto {@link SessioneQuiz} contenente le domande e le risposte della sessione.
     * @param domanda1 Etichetta ({@link Label}) per la prima domanda.
     * @param domanda2 Etichetta ({@link Label}) per la seconda domanda.
     * @param domanda3 Etichetta ({@link Label}) per la terza domanda.
     * @param domanda4 Etichetta ({@link Label}) per la quarta domanda.
     * @param risposta1Utente Etichetta ({@link Label}) per la risposta dell'utente alla prima domanda.
     * @param risposta2Utente Etichetta ({@link Label}) per la risposta dell'utente alla seconda domanda.
     * @param risposta3Utente Etichetta ({@link Label}) per la risposta dell'utente alla terza domanda.
     * @param risposta4Utente Etichetta ({@link Label}) per la risposta dell'utente alla quarta domanda.
     * @param risposta1Corretta Etichetta ({@link Label}) per la risposta corretta alla prima domanda.
     * @param risposta2Corretta Etichetta ({@link Label}) per la risposta corretta alla seconda domanda.
     * @param risposta3Corretta Etichetta ({@link Label}) per la risposta corretta alla terza domanda.
     * @param risposta4Corretta Etichetta ({@link Label}) per la risposta corretta alla quarta domanda.
     */
    public static void setLabelPerRiepilogo(SessioneQuiz sessioneQuiz, Label domanda1, Label domanda2, Label domanda3, Label domanda4, Label risposta1Utente, Label risposta2Utente, Label risposta3Utente, Label risposta4Utente, Label risposta1Corretta, Label risposta2Corretta, Label risposta3Corretta, Label risposta4Corretta) {
        /*Tutte le label da settare*/
        List<Label> labelsUtente = Arrays.asList(risposta1Utente, risposta2Utente, risposta3Utente, risposta4Utente);
        List<Label> labelsCorrette = Arrays.asList(risposta1Corretta, risposta2Corretta, risposta3Corretta, risposta4Corretta);
        List<Label> domandeLabel = Arrays.asList(domanda1, domanda2, domanda3, domanda4);
        /*****/

        //Ottieni le domande della sessione
        List<Domanda> domande = sessioneQuiz.getDomande();

        //Setto le label delle domande
        for (int i = 0; i < domande.size(); i++) {
            setLabelPerDomanda(domande.get(i), domandeLabel.get(i));
        }

        //Setto la label per la risposta che ha messo l'utente
        for (int i = 0; i < domande.size(); i++) {
            setRispostaUtente(domande.get(i).getRisposte(), labelsUtente.get(i));
        }

        //Setto le label per le risposte corrette
        for (int i = 0; i < domande.size(); i++) {
            setRispostaCorretta(domande.get(i).getRisposte(), labelsCorrette.get(i));
        }
    }

    /**
     * Imposta il testo e lo stile dell'etichetta che mostra la risposta data dall'utente.
     * Il colore del testo sarà verde se la risposta è corretta, rosso altrimenti.
     *
     * @param opzioni Lista di oggetti {@link Risposta} tra cui trovare la risposta selezionata dall'utente.
     * @param rispostaUtenteLabel Etichetta ({@link Label}) su cui impostare il testo e lo stile.
     */
    private static void setRispostaUtente(List<Risposta> opzioni, Label rispostaUtenteLabel) {
        for (Risposta risposta : opzioni) {
            if (risposta.isSelected()) {
                rispostaUtenteLabel.setText("Hai risposto: " + risposta.getTesto());
                //Se è corretta la setti verde
                if (risposta.isCorretta()) {
                    rispostaUtenteLabel.setStyle("-fx-text-fill: green; ");
                } else {
                    rispostaUtenteLabel.setStyle("-fx-text-fill: red ");
                }
                //Dopo aver settato tutto , fai il break , senza controllare inutilmente anche le altre risposte
                break;
            }
        }
    }

    /**
     * Imposta il testo dell'etichetta che mostra la risposta corretta per una data domanda.
     *
     * @param opzioni Lista di oggetti {@link Risposta} tra cui trovare la risposta corretta.
     * @param rispostaCorrettaLabel Etichetta ({@link Label}) su cui impostare il testo.
     */
    private static void setRispostaCorretta(List<Risposta> opzioni, Label rispostaCorrettaLabel) {
        for (Risposta risposta : opzioni) {
            if (risposta.isCorretta()) {
                rispostaCorrettaLabel.setText("La risposta corretta era: " + risposta.getTesto());
                break;
            }
        }
    }

    /**
     * Imposta il testo dell'etichetta con il testo della domanda.
     *
     * @param domanda Oggetto {@link Domanda} da cui ottenere il testo.
     * @param labelDomanda Etichetta ({@link Label}) su cui impostare il testo della domanda.
     */
    private static void setLabelPerDomanda(Domanda domanda, Label labelDomanda) {
        labelDomanda.setText(domanda.getTesto());
    }
}