package com.example.dao.SessionQuiz;

import com.example.dao.ConnectionConfig;
import com.example.difficultySettings.DifficultyEnum;
import com.example.exceptions.DatabaseException;
import com.example.models.SessioneQuiz;
import com.example.models.User;

import java.util.List;

/**
 * Interfaccia per la gestione delle sessioni di quiz nel database.
 * Fornisce metodi per l'inserimento e il recupero delle sessioni di gioco.
 */
public interface SessionDAO extends ConnectionConfig {

    /**
     * Recupera la classifica delle migliori sessioni per una determinata difficoltà.
     *
     * @param difficolta il livello di difficoltà per cui recuperare i punteggi
     * @return lista delle sessioni ordinate per punteggio decrescente
     * @throws DatabaseException in caso di errori di accesso al database
     * @throws IllegalArgumentException se il parametro difficolta è null
     */
    List<SessioneQuiz> selectSessionsWithTopScores(DifficultyEnum difficolta) throws DatabaseException;

    /**
     * Inserisce una nuova sessione di quiz nel database.
     *
     * @param currentQuiz la sessione di quiz da salvare
     * @throws DatabaseException in caso di errori di accesso al database
     * @throws IllegalArgumentException se currentQuiz è null o contiene dati non validi
     */
    void insertSessione(SessioneQuiz currentQuiz) throws DatabaseException;

    /**
     * Recupera lo storico delle sessioni di un utente per una specifica difficoltà.
     *
     * @param user l'utente di cui recuperare le sessioni
     * @param difficultyEnum il livello di difficoltà delle sessioni da recuperare
     * @return lista delle sessioni dell'utente per la difficoltà specificata
     * @throws DatabaseException in caso di errori di accesso al database
     * @throws IllegalArgumentException se user o difficultyEnum sono null
     */
    List<SessioneQuiz> selectPersonalScoreboard(User user, DifficultyEnum difficultyEnum) throws DatabaseException;
}