package com.example.exceptions;

/**
 * Eccezione personalizzata che viene lanciata quando un'operazione di modifica
 * viene tentata ma non produce alcun cambiamento effettivo, tipicamente perché
 * il nuovo valore fornito è identico a quello esistente.
 * Questa è una {@link RuntimeException}, il che significa che non è obbligatorio
 * dichiararla nelle clausole {@code throws} dei metodi.
 */
public class NessunaModificaException extends RuntimeException {

    /**
     * Costruisce una nuova eccezione {@code NessunaModificaException} con il messaggio di dettaglio specificato.
     *
     * @param message Il messaggio di dettaglio (che può essere recuperato dal metodo {@link Throwable#getMessage()}).
     * Questo messaggio dovrebbe spiegare perché non è stata effettuata alcuna modifica.
     */
    public NessunaModificaException(String message) {
        super(message);
    }
}