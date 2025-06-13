package com.example.exceptions;

/**
 * Eccezione lanciata quando una password non soddisfa i criteri di formato richiesti.
 */
public class PasswordFormatException extends RuntimeException {
    
    /**
     * Costruisce una nuova PasswordFormatException con il messaggio di dettaglio specificato.
     * 
     * @param message il messaggio di dettaglio che spiega perché il formato della password non è valido
     */
    public PasswordFormatException(String message) {
        super(message);
    }
}