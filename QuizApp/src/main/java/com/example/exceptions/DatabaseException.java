package com.example.exceptions;

import java.sql.SQLException;

/**
 * Eccezione personalizzata che rappresenta errori relativi al database.
 * Può essere utilizzata per incapsulare eccezioni SQL e fornire messaggi più specifici.
 */
public class DatabaseException extends RuntimeException {
    /**
     * Costruisce una nuova eccezione con il messaggio specificato.
     *
     * @param message Il messaggio dettagliato dell'errore.
     */
    public DatabaseException(String message) {
        super(message);
    }

    /**
     * Costruisce una nuova eccezione incapsulando un'eccezione SQL esistente.
     *
     * @param message Il messaggio dettagliato dell'errore.
     * @param ex L'eccezione SQL originale da incapsulare.
     */
    public DatabaseException(String message, SQLException ex) {
        super(message, ex);
    }
}
