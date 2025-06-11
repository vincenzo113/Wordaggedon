package com.example.exceptions;

/**
 * Eccezione personalizzata che viene lanciata quando la password inserita
 * non è corretta durante un tentativo di autenticazione.
 */
public class PasswordNonCorrettaException extends RuntimeException {
    /**
     * Costruisce una nuova eccezione con il messaggio specificato.
     *
     * @param message Il messaggio che descrive l'errore.
     */
    public PasswordNonCorrettaException(String message) {
        super(message);
    }
}
