package com.example.models;

import java.io.Serializable;

/**
 * Rappresenta un utente all'interno del sistema.
 * Include lo username, la password e un flag che indica se l'utente ha privilegi di amministratore.
 * Implementa {@link Serializable} per consentire la persistenza o la trasmissione dell'oggetto.
 */
public class User implements Serializable {
    /**
     * Il serialVersionUID per la serializzazione.
     */
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private boolean admin;

    /**
     * Costruisce una nuova istanza di {@code User} con username, password e stato di amministratore specificati.
     *
     * @param username Lo username dell'utente.
     * @param password La password dell'utente.
     * @param admin Un valore booleano che indica se l'utente è un amministratore (true) o un utente normale (false).
     */
    public User(String username, String password, boolean admin) {
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    /**
     * Costruisce una nuova istanza di {@code User} con il solo username specificato.
     * Questo costruttore può essere utile quando si ha bisogno di un oggetto utente
     * solo per identificazione, ad esempio per query che non richiedono la password o lo stato di amministratore.
     *
     * @param username Lo username dell'utente.
     */
    public User(String username) {
        this.username = username;
    }

    /**
     * Restituisce lo username dell'utente.
     * @return Lo username dell'utente.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Imposta lo username dell'utente.
     * @param username Il nuovo username da assegnare all'utente.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Restituisce la password dell'utente.
     * @return La password dell'utente.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Imposta la password dell'utente.
     * @param password La nuova password da assegnare all'utente.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Verifica se l'utente ha privilegi di amministratore.
     * @return {@code true} se l'utente è un amministratore; {@code false} altrimenti.
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * Imposta lo stato di amministratore dell'utente.
     * @param admin Il valore booleano per impostare lo stato di amministratore.
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    /**
     * Restituisce una rappresentazione in stringa dell'oggetto {@code User}.
     * Questa stringa include lo username, la password e lo stato di amministratore.
     *
     * @return Una stringa formattata con i dettagli dell'utente.
     */
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", admin=" + admin +
                '}';
    }
}

