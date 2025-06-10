package com.example.controller;


import javafx.scene.control.Label;

/**
 * Il controller {@code StartGameController} fornisce utility per la gestione dell'interfaccia utente
 * nella fase di avvio del gioco, in particolare per l'aggiornamento dinamico delle etichette.
 */
public class StartGameController {

    /**
     * Aggiorna il testo di una {@link Label} per includere lo username fornito.
     * Si aspetta che il testo originale della label sia in un formato che contenga
     * un campo separato da virgole che verrà sostituito dallo username.
     * Ad esempio, se la label contiene "Benvenuto, [nome], inizia il quiz!",
     * questo metodo sostituirà "[nome]" con lo username.
     *
     * @param label La {@link Label} di JavaFX il cui testo deve essere aggiornato.
     * @param username Lo {@link String} dello username da inserire nella label.
     */
    public static void aggiornaLabel(Label label, String username){
        // Divide il testo corrente della label usando la virgola come delimitatore.
        String [] fields = label.getText().split("[,]");
        // Assumendo che lo username sia il secondo campo (indice 1), lo aggiorna.
        // Questa implementazione è rigida e presuppone un formato specifico della stringa.
        // Potrebbe essere migliorata per essere più robusta (es. usando placeholder).
        fields[1] = username ;
        // Ricostruisce il testo della label unendo i campi con ", ".
        label.setText(String.join(", ",fields));
    }

}