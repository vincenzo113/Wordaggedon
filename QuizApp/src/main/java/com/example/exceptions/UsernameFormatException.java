package com.example.exceptions;

/**
 * Eccezione personalizzata che viene lanciata quando un nome utente (username)
 * non rispetta un formato atteso o predefinito.
 * Questa è una {@link RuntimeException}, il che significa che non è obbligatorio
 * dichiararla nelle clausole {@code throws} dei metodi. È utile per la validazione
 * dell'input utente per assicurarsi che gli username seguano regole specifiche (ad esempio,
 * lunghezza minima/massima, caratteri consentiti, ecc.).
 */
public class UsernameFormatException extends RuntimeException {

    /**
     * Costruisce una nuova eccezione {@code UsernameFormatException} con il messaggio di dettaglio specificato.
     *
     * @param message Il messaggio di dettaglio (che può essere recuperato dal metodo {@link Throwable#getMessage()}).
     * Questo messaggio dovrebbe spiegare il motivo per cui il formato dell'username non è valido.
     */
    public UsernameFormatException(String message) {
        super(message);
    }
}