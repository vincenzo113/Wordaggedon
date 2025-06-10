package com.example.exceptions;

/**
 * Eccezione personalizzata che viene lanciata quando una password fornita
 * non corrisponde alla password attesa o corretta.
 * Questa è una {@link RuntimeException}, il che significa che non è obbligatorio
 * dichiararla nelle clausole {@code throws} dei metodi. È utile per segnalare
 * fallimenti di autenticazione o verifica della password.
 */
public class PasswordNonCorrettaException extends RuntimeException {

    /**
     * Costruisce una nuova eccezione {@code PasswordNonCorrettaException} con il messaggio di dettaglio specificato.
     *
     * @param message Il messaggio di dettaglio (che può essere recuperato dal metodo {@link Throwable#getMessage()}).
     * Questo messaggio dovrebbe spiegare che la password fornita non è corretta.
     */
    public PasswordNonCorrettaException(String message) {
        super(message);
    }
}
