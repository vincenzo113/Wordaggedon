package com.example.controller;

import com.example.dao.User.UserDAO;
import com.example.dao.User.UserDAOPostgres;
import com.example.models.User;

import java.sql.SQLException;

/**
 * Il controller {@code LoginController} gestisce la logica di business relativa al processo di autenticazione dell'utente.
 * Interagisce con il livello di accesso ai dati (DAO) per verificare le credenziali dell'utente.
 */
public class LoginController {

    /**
     * Verifica le credenziali di login di un utente.
     * Questo metodo tenta di autenticare l'utente fornito tramite il {@link UserDAOPostgres}.
     *
     * @param user L'oggetto {@link User} contenente le credenziali (username e password) da verificare.
     * @return L'oggetto {@link User} completo con lo stato di amministratore se il login ha successo;
     * {@code null} se il login fallisce (credenziali non valide) o in caso di errore SQL.
     */
    public static User hasLoginSuccess(User user) {
        // Inizializza un'istanza di UserDAOPostgres per interagire con il database.
        UserDAO<User> userDAOPostgres = new UserDAOPostgres();
        try {
            // Tenta di effettuare il login chiamando il metodo login del DAO.
            return userDAOPostgres.login(user);
        } catch (SQLException e) {
            // In caso di errore SQL, stampa lo stack trace.
            // In un'applicazione reale, si dovrebbe gestire l'eccezione in modo più robusto,
            // ad esempio loggandola o rilanciando un'eccezione custom più significativa per il livello superiore.
            e.printStackTrace();
        }
        // Ritorna null se si verifica un'eccezione SQL o se il login fallisce.
        return null;
    }
}
