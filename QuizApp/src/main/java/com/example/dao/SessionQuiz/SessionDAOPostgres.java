package com.example.dao.SessionQuiz;

import com.example.difficultySettings.DifficultyEnum;
import com.example.exceptions.DatabaseException;
import com.example.models.SessioneQuiz;
import com.example.models.User;

import java.util.List;
import java.util.ArrayList;
import java.sql.*;

/**
 * Implementazione PostgreSQL del DAO per la gestione delle sessioni di quiz
 */
public class SessionDAOPostgres implements SessionDAO {
    
    private static final String SELECT_TOP_SCORES = 
            "SELECT utente, punteggio FROM sessione WHERE difficolta = ? ORDER BY punteggio DESC";
    private static final String INSERT_SESSION = 
            "INSERT INTO sessione (utente, difficolta, punteggio) VALUES (?, ?, ?)";
    private static final String SELECT_PERSONAL_SCORES = 
            "SELECT utente, punteggio FROM sessione WHERE utente = ? AND difficolta = ? ORDER BY punteggio DESC";

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SessioneQuiz> selectSessionsWithTopScores(DifficultyEnum difficolta) throws DatabaseException {
        if (difficolta == null) {
            throw new IllegalArgumentException("Il parametro difficoltà non può essere null");
        }

        List<SessioneQuiz> sessions = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(SELECT_TOP_SCORES)) {
            
            pstmt.setString(1, difficolta.toString());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String username = rs.getString("utente");
                    int punteggio = rs.getInt("punteggio");
                    
                    User user = new User(username);
                    sessions.add(new SessioneQuiz(user, difficolta, punteggio));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Errore durante il recupero dei punteggi migliori", e);
        }
        return sessions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertSessione(SessioneQuiz currentQuiz) throws DatabaseException {
        if (currentQuiz == null) {
            throw new IllegalArgumentException("La sessione quiz non può essere null");
        }
        if (currentQuiz.getUser() == null || currentQuiz.getDifficolta() == null) {
            throw new IllegalArgumentException("User e difficoltà non possono essere null");
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(INSERT_SESSION)) {
            
            pstmt.setString(1, currentQuiz.getUser().getUsername());
            pstmt.setString(2, currentQuiz.getDifficolta().toString());
            pstmt.setInt(3, currentQuiz.getScore());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Errore durante l'inserimento della sessione", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SessioneQuiz> selectPersonalScoreboard(User user, DifficultyEnum difficultyEnum) 
            throws DatabaseException {
        if (user == null || difficultyEnum == null) {
            throw new IllegalArgumentException("User e difficoltà non possono essere null");
        }

        List<SessioneQuiz> sessions = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(SELECT_PERSONAL_SCORES)) {
            
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, difficultyEnum.toString());
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int punteggio = rs.getInt("punteggio");
                    sessions.add(new SessioneQuiz(user, difficultyEnum, punteggio));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Errore durante il recupero della classifica personale", e);
        }
        return sessions;
    }
}