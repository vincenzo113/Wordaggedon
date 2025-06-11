package com.example.exceptions;

/**
 * Eccezione personalizzata che viene lanciata quando il formato dello username
 * inserito dall'utente non rispetta i requisiti richiesti.
 */
public class UsernameFormatException extends RuntimeException {
    /**
     * Costruisce una nuova eccezione con il messaggio specificato.
     *
     * @param message Il messaggio che descrive l'errore.
     */
    public UsernameFormatException(String message) {
        super(message);
    }
}
