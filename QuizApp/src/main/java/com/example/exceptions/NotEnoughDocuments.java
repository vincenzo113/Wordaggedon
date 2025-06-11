package com.example.exceptions;

/**
 * Eccezione personalizzata che viene lanciata quando non ci sono documenti sufficienti
 * per completare un'operazione richiesta dall'applicazione.
 */
public class NotEnoughDocuments extends Exception{
    /**
     * Costruisce una nuova eccezione con il messaggio specificato.
     *
     * @param message Il messaggio che descrive l'errore.
     */
    public NotEnoughDocuments(String message) {
        super(message);
    }
}
