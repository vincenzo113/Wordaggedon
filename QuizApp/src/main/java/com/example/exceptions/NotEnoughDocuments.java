package com.example.exceptions;

/**
 * Eccezione personalizzata che viene lanciata quando non ci sono abbastanza documenti
 * disponibili per completare un'operazione che ne richiede un numero minimo.
 * Ad esempio, potrebbe essere usata in un sistema di quiz se non ci sono abbastanza
 * documenti per generare le domande.
 */
public class NotEnoughDocuments extends Exception{

    /**
     * Costruisce una nuova eccezione {@code NotEnoughDocuments} con il messaggio di dettaglio specificato.
     *
     * @param message Il messaggio di dettaglio (che può essere recuperato dal metodo {@link Throwable#getMessage()}).
     * Questo messaggio dovrebbe spiegare perché non ci sono abbastanza documenti.
     */
    public NotEnoughDocuments(String message) {
        super(message);
    }
}
