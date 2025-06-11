package com.example.exceptions;

/**
 * Eccezione personalizzata che viene lanciata quando lo username inserito
 * è già stato preso da un altro utente nel sistema.
 */
public class UsernameGiaPreso extends RuntimeException {
    /**
     * Costruisce una nuova eccezione con il messaggio specificato.
     *
     * @param message Il messaggio che descrive l'errore.
     */
    public UsernameGiaPreso(String message) {
        super(message);
    }
}
