package com.example.controller;

import com.example.dao.User.UserDAO;
import com.example.dao.User.UserDAOPostgres;
import com.example.models.User;

/**
 * Controller responsabile della gestione del processo di registrazione di nuovi utenti.
 * Implementa il pattern MVC agendo come intermediario tra la vista (UI) e il modello (database).
 * Si occupa di processare le richieste di registrazione e delegare la persistenza dei dati al DAO appropriato.
 */
public class RegisterController {

    /**
     * Gestisce la registrazione di un nuovo utente nel sistema.
     * Verifica che i dati dell'utente siano validi e delega la registrazione effettiva al layer di persistenza.
     *
     * @param user L'oggetto User contenente i dati del nuovo utente da registrare 
     *             (username, password e altri dati personali richiesti)
     * @return true se la registrazione è avvenuta con successo, 
     *         false se l'utente non può essere registrato (es. username già esistente)
     */
    public static boolean hasRegisterSuccess(User user) {
        UserDAO<User> userDAOPostgres = new UserDAOPostgres();
        return userDAOPostgres.register(user);
    }
}