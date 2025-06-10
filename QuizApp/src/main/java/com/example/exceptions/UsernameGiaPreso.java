package com.example.exceptions;

/**
 * Eccezione personalizzata che viene lanciata quando un tentativo di creare
 * o modificare un utente fallisce perché l'username desiderato è già in uso.
 * Questa è una {@link RuntimeException}, il che significa che non è obbligatorio
 * dichiararla nelle clausole {@code throws} dei metodi. È utile per segnalare
 * un conflitto di username durante la registrazione o la modifica del profilo.
 */
public class UsernameGiaPreso extends RuntimeException {

    /**
     * Costruisce una nuova eccezione {@code UsernameGiaPreso} con il messaggio di dettaglio specificato.
     *
     * @param message Il messaggio di dettaglio (che può essere recuperato dal metodo {@link Throwable#getMessage()}).
     * Questo messaggio dovrebbe spiegare che l'username è già occupato.
     */
    public UsernameGiaPreso(String message) {
        super(message);
    }
}