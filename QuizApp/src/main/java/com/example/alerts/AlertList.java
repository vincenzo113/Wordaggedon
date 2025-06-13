package com.example.alerts;

/**
 * Enum che rappresenta una lista di messaggi di avviso utilizzati all'interno dell'applicazione.
 * Ogni costante enum fornisce un messaggio testuale specifico che può essere mostrato all'utente
 * in seguito a varie azioni o errori durante l'utilizzo dell'applicazione.
 */
public enum AlertList {

    LOGIN_FAILURE("Username o password errati , riprova."),
    REGISTER_FAILURE("Registrazione fallita, riprova."),
    FIELDS_EMPTY("Perfavore, compila tutti i campi richiesti."),
    DATABASE_ERROR("Errore di connessione al database, riprova più tardi."),
    USER_NOT_FOUND("Utente non trovato."),
    PASSWORD_MISMATCH("Le password non corrispondono."),
    UPLOAD_SUCCESS("Caricamento del documento completato con successo!"),
    UPLOAD_FAILURE("Caricamento del documento fallito, riprova."),
    UPLOAD_STOPWORDS_SUCCESS("Caricamento delle stopwords completato con successo!"),
    UPLOAD_STOPWORDS_FAILURE("Caricamento delle stopwords fallito, riprova."),
    SHORT_TEXT("Il testo inserito è troppo corto, deve contenere almeno 10 parola"),
    TEXT_ALREADY_EXISTS("Testo inserito già esistente, riprova con un testo diverso."),
    USERNAME_ALREADY_TAKEN("Username già registrato , riprova con un altro"),
    MODIFICA_USERNAME_SUCCESS("Hai modificato correttamente l'username"),
    MODIFICA_USERNAME_NON_AVVENUTA("Non hai modificato nulla del tuo username , riprova"),
    USERNAME_FORMAT_NON_CORRETTO("Formato dell'username non corretto"),
    PASSWORD_NON_CORRETTA("Errore , la vecchia password inserita non è corretta"),
    CAMBIO_PASSWORD_SUCCESS("Cambio password avvenuto con successo"),
    NESSUNA_MODIFICA_DI_PASSWORD("Devi cambiare la password per poter rendere effettivo l'update"),
    NON_ABBASTANZA_DOCUMENTI("Non hai abbastanza documenti per la difficoltà scelta , ricaricane altri e riprova"),
    SESSIONE_NON_CARICATA("La sessione precedente non è stata salvata, ricomincia il gioco"),
    PASSWORD_FORMAT_NON_CORRETTO("La password deve contenere almeno 6 caratteri con almeno una maiuscola e un numero!");
    private final String message;

    AlertList(String message) {
        this.message = message;
    }

    /**
     * Restituisce il messaggio associato all'enum.
     *
     * @return Il messaggio di avviso da visualizzare.
     */
    public String getMessage() {
        return message;
    }
}
