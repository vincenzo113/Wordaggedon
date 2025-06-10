package com.example.exceptions;

/**
 * Eccezione personalizzata che viene lanciata quando uno o più campi obbligatori
 * non sono stati compilati dall'utente.
 * Questa eccezione è utile per segnalare un errore di input utente,
 * indicando che alcuni dati essenziali sono mancanti.
 */
public class CampiNonCompilatiException extends Exception {

    /**
     * Costruisce una nuova eccezione {@code CampiNonCompilatiException} con il messaggio di dettaglio specificato.
     *
     * @param message Il messaggio di dettaglio (che può essere recuperato dal metodo {@link Throwable#getMessage()}).
     * Questo messaggio dovrebbe spiegare quali campi non sono stati compilati.
     */
    public CampiNonCompilatiException(String message) {
        super(message);
    }
}
