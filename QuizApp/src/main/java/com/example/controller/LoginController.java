package com.example.controller;
import com.example.dao.User.UserDAO;
import com.example.dao.User.UserDAOPostgres;
import com.example.models.User;

/**
 * Controller responsabile della gestione del processo di autenticazione degli utenti.
 * Implementa il pattern MVC agendo come intermediario tra la vista (UI) e il modello (database).
 * Gestisce le richieste di login verificando le credenziali attraverso il DAO appropriato.
 */
public class LoginController {

    /**
     * Tenta l'autenticazione di un utente verificandone le credenziali.
     * Delega la verifica effettiva al layer di persistenza attraverso UserDAO.
     *
     * @param user L'oggetto User contenente le credenziali da verificare (username e password)
     * @return Un oggetto User popolato con tutti i dati dell'utente se l'autenticazione ha successo,
     *         null se le credenziali non sono valide
     */
    public static User hasLoginSuccess(User user) {
        UserDAO<User> userDAOPostgres = new UserDAOPostgres();
        return userDAOPostgres.login(user);
    }
}
