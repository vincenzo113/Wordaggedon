package com.example.dao.stopWordsDAO;

import com.example.exceptions.DatabaseException;
import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementazione PostgreSQL del DAO per la gestione delle stop words
 */
public class StopWordsDAOPostgres implements StopWordsDAO {
    
    private static final String INSERT_QUERY = "INSERT INTO stopwords(parola) VALUES (?)";
    private static final String SELECT_ALL_QUERY = "SELECT parola FROM stopwords";

    /**
     * {@inheritDoc}
     */
    @Override
    public void inserisciStopWords(List<String> stopWords) throws DatabaseException {
        if (stopWords == null || stopWords.isEmpty()) {
            throw new IllegalArgumentException("La lista di stop words non può essere null o vuota");
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(INSERT_QUERY)) {
            
            for (String stopWord : stopWords) {
                if (stopWord != null && !stopWord.trim().isEmpty()) {
                    pstmt.setString(1, stopWord.toLowerCase().trim());
                    pstmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Errore durante l'inserimento delle stop words", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> getStopWords() throws DatabaseException {
        Set<String> allStopWords = new HashSet<>();
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_QUERY);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                String stopWord = rs.getString("parola");
                if (stopWord != null && !stopWord.trim().isEmpty()) {
                    allStopWords.add(stopWord);
                }
            }
            
        } catch (SQLException e) {
            throw new DatabaseException("Errore durante il recupero delle stop words", e);
        }
        
        return allStopWords;
    }
}