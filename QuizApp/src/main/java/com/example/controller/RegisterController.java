package com.example.controller;

import com.example.dao.User.UserDAO;
import com.example.dao.User.UserDAOPostgres;
import com.example.models.User;
import java.sql.SQLException;

/**
 * Il controller {@code RegisterController} gestisce la logica di business relativa al processo di registrazione di un nuovo utente.
 * Interagisce con il livello di accesso ai dati (DAO) per inserire le informazioni del nuovo utente nel database.
 */
public class RegisterController {

    /**
     * Tenta di registrare un nuovo utente nel sistema.
     * Questo metodo chiama il metodo di registrazione del {@link UserDAOPostgres} per persistere l'utente.
     *
     * @param user L'oggetto {@link User} contenente i dettagli (username, password, stato admin) del nuovo utente da registrare.
     * @return {@code true} se la registrazione ha successo, {@code false} altrimenti (es. username già esistente, errore SQL).
     */
    public static boolean hasRegisterSuccess(User user) {
        // Inizializza un'istanza di UserDAOPostgres per interagire con il database.
        UserDAO<User> userDAOPostgres = new UserDAOPostgres();
        boolean successRegistrazione = false;

        try {
            // Tenta di registrare l'utente chiamando il metodo register del DAO.
            successRegistrazione = userDAOPostgres.register(user);
        } catch (SQLException e) {
            // In caso di errore SQL, stampa lo stack trace.
            // In un'applicazione reale, si dovrebbe gestire l'eccezione in modo più robusto,
            // ad esempio loggandola o rilanciando un'eccezione custom più significativa per il livello superiore,
            // specialmente per distinguere tra un errore generico e un username già esistente.
            e.printStackTrace();
        }

        return successRegistrazione;
    }
}