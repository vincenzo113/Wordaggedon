package com.example.dao.SessionQuiz;

import com.example.difficultySettings.DifficultyEnum;
import com.example.models.SessioneQuiz;
import com.example.models.User;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import java.sql.*;

/**
 * Implementazione dell'interfaccia {@link SessionDAO} per l'interazione con un database PostgreSQL.
 * Questa classe gestisce le operazioni CRUD (Create, Read, Update, Delete) relative alle sessioni di quiz,
 * permettendo di recuperare le classifiche e inserire nuove sessioni.
 */
public class SessionDAOPostgres implements SessionDAO {



    /**
     * Recupera un elenco di sessioni di quiz ordinate per punteggio in ordine decrescente,
     * filtrate per la difficoltà specificata. Questo metodo è utile per visualizzare le
     * classifiche dei punteggi più alti per una data difficoltà.
     *
     * @param difficolta L'enumerazione {@link DifficultyEnum} che rappresenta il livello di difficoltà delle sessioni da recuperare.
     * @return Una {@link List} di oggetti {@link SessioneQuiz} che contengono i dettagli delle sessioni,
     * ordinate per punteggio dal più alto al più basso.
     * @throws SQLException se si verifica un errore durante l'accesso al database.
     */
    @Override
    public List<SessioneQuiz> selectSessionsWithTopScores(DifficultyEnum difficolta) throws SQLException {
        List<SessioneQuiz> sessions = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            String query = String.format("SELECT utente, punteggio FROM sessione WHERE difficolta = '%s' ORDER BY punteggio DESC ", difficolta.toString());
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                // Creazione dell'oggetto User
                String username = rs.getString("utente");
                User user = new User(username);

                int punteggio = rs.getInt("punteggio");

                // Creazione e aggiunta della SessioneQuiz alla lista
                sessions.add(new SessioneQuiz(user, difficolta, punteggio));
            }
        }
        return sessions;
    }

    /**
     * Inserisce una nuova sessione di quiz nel database.
     *
     * @param currentQuiz L'oggetto {@link SessioneQuiz} che rappresenta la sessione da salvare.
     * Deve contenere l'utente, la difficoltà e il punteggio.
     * @throws RuntimeException se si verifica un errore SQL durante l'operazione di inserimento.
     */
    @Override
    public void insertSessione(SessioneQuiz currentQuiz) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            String query = String.format(
                    "INSERT INTO sessione (utente, difficolta, punteggio) VALUES ('%s', '%s', %d)",
                    currentQuiz.getUser().getUsername(),
                    currentQuiz.getDifficolta().toString(),
                    currentQuiz.getScore()
            );
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Recupera la classifica personale di un utente per una specifica difficoltà del quiz.
     * Le sessioni sono ordinate per punteggio in ordine decrescente.
     *
     * @param user L'oggetto {@link User} per cui si desidera recuperare la classifica.
     * @param difficultyEnum L'enumerazione {@link DifficultyEnum} che rappresenta la difficoltà delle sessioni da cercare.
     * @return Una {@link List} di oggetti {@link SessioneQuiz} che rappresentano le sessioni completate dall'utente
     * per quella specifica difficoltà, ordinate per punteggio.
     * @throws SQLException se si verifica un errore durante l'accesso al database.
     */
    @Override
    public List<SessioneQuiz> selectPersonalScoreboard(User user, DifficultyEnum difficultyEnum) throws SQLException {
        List<SessioneQuiz> sessions = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            String query = String.format(
                    "SELECT utente, punteggio FROM sessione WHERE utente = '%s' AND difficolta = '%s' ORDER BY punteggio DESC",
                    user.getUsername(), difficultyEnum.toString()
            );
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int punteggio = rs.getInt("punteggio");

                // Creazione e aggiunta della SessioneQuiz alla lista
                sessions.add(new SessioneQuiz(user, difficultyEnum, punteggio));
            }
        }
        return sessions;
    }
}
