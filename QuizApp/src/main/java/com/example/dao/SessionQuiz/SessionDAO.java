package com.example.dao.SessionQuiz;

import com.example.dao.ConnectionConfig;
import com.example.difficultySettings.DifficultyEnum;
import com.example.models.SessioneQuiz;
import com.example.models.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Interfaccia DAO (Data Access Object) per la gestione delle sessioni di quiz.
 * Estende {@link ConnectionConfig} per garantire l'accesso alle configurazioni di connessione al database.
 */
public interface SessionDAO extends ConnectionConfig {

    /**
     * Seleziona un elenco di sessioni di quiz con i punteggi migliori per una determinata difficoltà.
     *
     * @param difficolta L'enumerazione {@link DifficultyEnum} che rappresenta la difficoltà del quiz.
     * @return Una lista di oggetti {@link SessioneQuiz} contenenti le sessioni con i punteggi più alti.
     * @throws SQLException se si verifica un errore di accesso al database.
     */
    List<SessioneQuiz> selectSessionsWithTopScores(DifficultyEnum difficolta) throws SQLException;

    /**
     * Inserisce una nuova sessione di quiz nel database.
     *
     * @param currentQuiz L'oggetto {@link SessioneQuiz} da inserire.
     * @throws RuntimeException se si verifica un errore SQL durante l'inserimento della sessione.
     */
    void insertSessione(SessioneQuiz currentQuiz);

    /**
     * Seleziona la classifica personale di un utente per una specifica difficoltà.
     *
     * @param user L'oggetto {@link User} per cui si desidera recuperare la classifica personale.
     * @param difficultyEnum L'enumerazione {@link DifficultyEnum} che rappresenta la difficoltà del quiz.
     * @return Una lista di oggetti {@link SessioneQuiz} che rappresentano le sessioni dell'utente per quella difficoltà.
     * @throws SQLException se si verifica un errore di accesso al database.
     */
    List<SessioneQuiz> selectPersonalScoreboard(User user, DifficultyEnum difficultyEnum) throws SQLException;
}
