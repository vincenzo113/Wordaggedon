package com.example.alerts;

public enum AlertList {

    LOGIN_SUCCESS("Login effettuato con successo!"),
    LOGIN_FAILURE("Username o password errati , riprova."),
    REGISTER_SUCCESS("Registrazione completata con successo!"),
    REGISTER_FAILURE("Registrazione fallita, riprova."),
    FIELDS_EMPTY("Perfavore, compila tutti i campi richiesti."),
    DATABASE_ERROR("Errore di connessione al database, riprova più tardi."),
    USER_NOT_FOUND("Utente non trovato."),
    PASSWORD_MISMATCH("Le password non corrispondono.");

    private final String message;

    AlertList(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
