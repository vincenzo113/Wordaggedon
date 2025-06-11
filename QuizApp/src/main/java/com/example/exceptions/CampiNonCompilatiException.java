package com.example.exceptions;

/**
 * Eccezione personalizzata che viene lanciata quando uno o più campi obbligatori
 * non sono stati compilati dall'utente.
 */

public class CampiNonCompilatiException extends Exception {
    /**
     * Costruisce una nuova eccezione con il messaggio specificato.
     *
     * @param message Il messaggio dettagliato che descrive il motivo dell'eccezione.
     */
    public CampiNonCompilatiException(String message) {
        super(message);
    }
}
