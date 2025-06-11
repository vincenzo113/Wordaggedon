package com.example.exceptions;

/**
 * Eccezione personalizzata che viene lanciata quando non è stata effettuata alcuna modifica
 * rispetto allo stato precedente, rendendo quindi l'operazione non necessaria.
 */
public class NessunaModificaException extends RuntimeException {
    /**
     * Costruisce una nuova eccezione con il messaggio specificato.
     *
     * @param message Il messaggio dettagliato che descrive il motivo dell'eccezione.
     */
    public NessunaModificaException(String message) {
        super(message);
    }
}
