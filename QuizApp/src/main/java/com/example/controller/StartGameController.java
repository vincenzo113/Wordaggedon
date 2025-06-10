package com.example.controller;

import javafx.scene.control.Label;

/**
 * Controller responsabile della gestione della schermata iniziale del gioco.
 * Gestisce l'interfaccia utente e l'aggiornamento delle informazioni visualizzate
 * all'avvio di una nuova partita.
 */
public class StartGameController {

    /**
     * Aggiorna il testo di una Label sostituendo il nome utente.
     * La funzione assume che il testo della Label sia formattato con campi separati da virgole,
     * dove il secondo campo rappresenta il nome utente.
     *
     * @param label L'elemento Label JavaFX da aggiornare
     * @param username Il nuovo nome utente da inserire nella Label
     */
    public static void aggiornaLabel(Label label, String username) {
        String[] fields = label.getText().split("[,]");
        fields[1] = username;
        label.setText(String.join(", ", fields));
    }
}