package com.example.dao.User;


import com.example.difficultySettings.DifficultyEnum;
import com.example.models.User;
import com.example.dao.ConnectionConfig;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Interfaccia DAO (Data Access Object) per la gestione degli utenti.
 * Estende {@link ConnectionConfig} per fornire l'accesso alle configurazioni di connessione al database.
 * Questa interfaccia definisce le operazioni di base e specifiche per la gestione degli utenti nel sistema.
 *
 * @param <T> Il tipo di oggetto utente gestito da questa DAO, tipicamente {@link User}.
 */
public interface UserDAO<T> extends ConnectionConfig {

    /**
     * Seleziona un utente dal database in base al suo username.
     *
     * @param username Lo username dell'utente da cercare.
     * @return Un {@link Optional} contenente l'oggetto {@code T} (utente) se trovato, altrimenti un {@link Optional#empty()}.
     */
    Optional<T> select(String username);

    /**
     * Seleziona tutti gli utenti presenti nel database.
     *
     * @return Una {@link List} di oggetti {@code T} (utenti) presenti nel database.
     * @throws SQLException se si verifica un errore di accesso al database.
     */
    List<T> selectAll() throws SQLException;

    /**
     * Inserisce un nuovo utente nel database.
     *
     * @param t L'oggetto {@code T} (utente) da inserire.
     */
    void insert(T t);

    /**
     * Elimina un utente dal database.
     *
     * @param t L'oggetto {@code T} (utente) da eliminare.
     */
    void delete(T t);

    /**
     * Esegue l'operazione di login per un utente.
     * Questo metodo dovrebbe verificare le credenziali dell'utente (ad esempio, username e password).
     *
     * @param user L'oggetto {@link User} contenente le credenziali per il login.
     * @return L'oggetto {@link User} se il login ha successo, altrimenti {@code null} o lancia un'eccezione a seconda dell'implementazione.
     * @throws SQLException se si verifica un errore di accesso al database.
     */
    User login(User user) throws SQLException;

    /**
     * Registra un nuovo utente nel sistema.
     *
     * @param user L'oggetto {@link User} da registrare.
     * @return {@code true} se la registrazione ha successo, {@code false} altrimenti (es. username già esistente).
     * @throws SQLException se si verifica un errore di accesso al database.
     */
    boolean register(User user) throws SQLException;

    /**
     * Calcola il punteggio medio di un utente per una specifica difficoltà.
     *
     * @param user L'oggetto {@link User} per cui calcolare il punteggio medio.
     * @param difficultyEnum L'enumerazione {@link DifficultyEnum} che rappresenta la difficoltà delle sessioni.
     * @return Il punteggio medio dell'utente per la difficoltà specificata.
     */
    float punteggioAvg(User user, DifficultyEnum difficultyEnum);

    /**
     * Recupera il miglior punteggio ottenuto da un utente per una specifica difficoltà.
     *
     * @param user L'oggetto {@link User} per cui recuperare il miglior punteggio.
     * @param difficultyEnum L'enumerazione {@link DifficultyEnum} che rappresenta la difficoltà delle sessioni.
     * @return Il punteggio più alto ottenuto dall'utente per la difficoltà specificata.
     */
    int punteggioBest(User user, DifficultyEnum difficultyEnum);

    /**
     * Conta il numero totale di partite giocate da un utente.
     *
     * @param user L'oggetto {@link User} per cui contare le partite.
     * @return Il numero totale di partite giocate dall'utente.
     */
    int contPartite(User user);

    /**
     * Modifica lo username di un utente.
     *
     * @param userLoggato L'oggetto {@link User} dell'utente attualmente loggato (con lo username da modificare).
     * @param nuovoUsername La nuova stringa dello username da assegnare all'utente.
     */
    void modificaUsername(User userLoggato , String nuovoUsername);
}