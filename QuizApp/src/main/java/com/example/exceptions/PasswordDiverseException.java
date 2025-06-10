package com.example.exceptions;

/**
 * Eccezione personalizzata che viene lanciata quando due password fornite
 * (ad esempio, una password e la sua conferma) non corrispondono.
 * Questa eccezione è utile per la validazione dell'input utente in contesti
 * come la registrazione o il cambio password.
 */
public class PasswordDiverseException extends Exception{

    /**
     * Costruisce una nuova eccezione {@code PasswordDiverseException} con il messaggio di dettaglio specificato.
     *
     * @param message Il messaggio di dettaglio (che può essere recuperato dal metodo {@link Throwable#getMessage()}).
     * Questo messaggio dovrebbe spiegare che le password fornite sono diverse.
     */
    public PasswordDiverseException(String message) {
        super(message);
    }
}