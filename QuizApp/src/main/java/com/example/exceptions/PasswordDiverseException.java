package com.example.exceptions;

/**
 * Eccezione personalizzata che viene lanciata quando le password inserite
 * non coincidono durante un'operazione di conferma o registrazione.
 */
public class PasswordDiverseException extends Exception{
    /**
     * Costruisce una nuova eccezione con il messaggio specificato.
     *
     * @param message Il messaggio che descrive l'errore.
     */
    public PasswordDiverseException(String message) {
        super(message);
    }
}
