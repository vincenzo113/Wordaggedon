package com.example.dao.User;

import com.example.dao.ConnectionConfig;
import com.example.difficultySettings.DifficultyEnum;
import com.example.exceptions.DatabaseException;
import com.example.exceptions.NessunaModificaException;
import com.example.exceptions.PasswordNonCorrettaException;
import com.example.exceptions.UsernameGiaPreso;
import com.example.models.User;

/**
 * Interfaccia per la gestione delle operazioni CRUD relative agli utenti
 * @param <T> tipo generico che estende User
 */
public interface UserDAO<T extends User> extends ConnectionConfig {
    
    /**
     * Registra un nuovo utente nel sistema
     * @param user l'utente da registrare
     * @return true se la registrazione è avvenuta con successo, false se l'username è già presente
     * @throws DatabaseException in caso di errori di accesso al database
     */
    boolean register(T user) throws DatabaseException;

    /**
     * Calcola il punteggio medio dell'utente per una determinata difficoltà
     * @param user l'utente di cui calcolare il punteggio
     * @param difficultyEnum la difficoltà per cui calcolare il punteggio
     * @return il punteggio medio
     * @throws DatabaseException in caso di errori di accesso al database
     */
    float punteggioAvg(T user, DifficultyEnum difficultyEnum) throws DatabaseException;

    /**
     * Recupera il punteggio migliore dell'utente per una determinata difficoltà
     * @param user l'utente di cui recuperare il punteggio
     * @param difficultyEnum la difficoltà per cui recuperare il punteggio
     * @return il punteggio migliore
     * @throws DatabaseException in caso di errori di accesso al database
     */
    int punteggioBest(T user, DifficultyEnum difficultyEnum) throws DatabaseException;

    /**
     * Conta il numero totale di partite giocate dall'utente
     * @param user l'utente di cui contare le partite
     * @return il numero di partite giocate
     * @throws DatabaseException in caso di errori di accesso al database
     */
    int contPartite(T user) throws DatabaseException;

    /**
     * Effettua il login dell'utente
     * @param user l'utente che tenta il login
     * @return l'utente con i dati completi se il login ha successo, null altrimenti
     * @throws DatabaseException in caso di errori di accesso al database
     */
    T login(T user) throws DatabaseException;

    /**
     * Inserisce un nuovo utente nel database
     * @param user l'utente da inserire
     * @throws DatabaseException in caso di errori di accesso al database
     */
    void insert(T user) throws DatabaseException;

    /**
     * Modifica l'username di un utente
     * @param user l'utente di cui modificare l'username
     * @param nuovoUsername il nuovo username
     * @throws DatabaseException in caso di errori di accesso al database
     * @throws UsernameGiaPreso se il nuovo username è già utilizzato da un altro utente
     */
    void modificaUsername(T user, String nuovoUsername) throws DatabaseException, UsernameGiaPreso;

    /**
     * Modifica la password di un utente
     * @param userLogged l'utente di cui modificare la password
     * @param vecchiaPassInserita la password attuale
     * @param nuovaPassInserita la nuova password
     * @throws DatabaseException in caso di errori di accesso al database
     * @throws NessunaModificaException se la nuova password è uguale alla vecchia
     * @throws PasswordNonCorrettaException se la vecchia password non è corretta
     */
    void modificaPassword(T userLogged, String vecchiaPassInserita, String nuovaPassInserita) 
            throws DatabaseException, NessunaModificaException, PasswordNonCorrettaException;
}