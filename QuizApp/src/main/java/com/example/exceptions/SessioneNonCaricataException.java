package com.example.exceptions;

/**
 * Eccezione personalizzata che viene lanciata quando una sessione richiesta
 * non è stata caricata correttamente o è assente durante l'esecuzione.
 */
public class SessioneNonCaricataException extends RuntimeException {
    /**
     * Costruisce una nuova eccezione con il messaggio specificato.
     *
     * @param message Il messaggio che descrive l'errore.
     */
    public SessioneNonCaricataException(String message) {
        super(message);
    }
}
