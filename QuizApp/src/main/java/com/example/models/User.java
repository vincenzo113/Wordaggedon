package com.example.models;

import java.io.Serializable;

/**
 * Rappresenta un utente con username, password e ruolo di amministratore.
 * Implementa Serializable per permettere la serializzazione dell'oggetto.
 */
public class User implements Serializable {

    private String username;
    private String password;
    private boolean admin;

    /**
     * Costruisce un utente con username, password e ruolo amministratore.
     *
     * @param username Il nome utente.
     * @param password La password dell'utente.
     * @param admin Indica se l'utente ha privilegi amministrativi.
     */
    public User(String username, String password, boolean admin) {
        this.username = username;
        this.password = password;
        this.admin = admin;
    }
    /**
     * Costruisce un utente con solo username.
     *
     * @param username Il nome utente.
     */
    public User(String username) {
            this.username=username;
    }

    /**
     * Restituisce lo username dell'utente.
     *
     * @return Lo username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Imposta lo username dell'utente.
     *
     * @param username Lo username da impostare.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Restituisce la password dell'utente.
     *
     * @return La password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Imposta la password dell'utente.
     *
     * @param password La password da impostare.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Indica se l'utente ha privilegi amministrativi.
     *
     * @return true se è amministratore, false altrimenti.
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * Imposta il ruolo amministratore dell'utente.
     *
     * @param admin true per amministratore, false altrimenti.
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }


    /**
     * Restituisce una rappresentazione in stringa dell'utente.
     *
     * @return Una stringa che rappresenta l'utente.
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
