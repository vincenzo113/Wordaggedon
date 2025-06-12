package com.example.exceptions;

/**
 * Eccezione lanciata quando si tenta di inserire un documento che è già presente nel database.
 * Questa eccezione viene utilizzata per gestire i casi in cui un documento duplicato
 * viene rilevato durante le operazioni di inserimento.
 * 
 * @author [Nome Autore]
 * @version 1.0
 * @since 1.0
 */
public class DocumentoDuplicatoException extends Throwable {
    
    /**
     * Costruisce una nuova DocumentoDuplicatoException con il messaggio di dettaglio specificato.
     * 
     * @param message il messaggio di dettaglio che descrive la causa dell'eccezione.
     *                Il messaggio di dettaglio viene salvato per essere recuperato
     *                successivamente tramite il metodo {@link Throwable#getMessage()}.
     */
    public DocumentoDuplicatoException(String message) {
        super(message);
    }
}