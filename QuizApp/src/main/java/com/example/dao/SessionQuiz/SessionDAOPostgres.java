package com.example.dao.SessionQuiz;

import com.example.difficultySettings.DifficultyEnum;
import com.example.models.SessioneQuiz;
import com.example.models.User;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import java.sql.*;

public class SessionDAOPostgres implements SessionDAO {
    @Override
    public List<SessioneQuiz> selectSessionsWithTopScores(DifficultyEnum difficolta) throws SQLException {
        List<SessioneQuiz> sessions = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            String query = String.format("SELECT utente, punteggio FROM sessione WHERE difficolta = '%s' ORDER BY punteggio DESC ", difficolta.toString());
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                //CREAZIONE USER
                String username = rs.getString("utente");
                User user = new User(username);


                int punteggio = rs.getInt("punteggio");

                /*
                //OTTENGO DIFFICOLTA'
                String difficolta = rs.getString("difficolta");
                DifficultyEnum difficulty = null;
                if ("EASY".equalsIgnoreCase(difficolta)) difficulty = DifficultyEnum.EASY;
                else if ("MEDIUM".equalsIgnoreCase(difficolta)) difficulty = DifficultyEnum.MEDIUM;
                else if ("HARD".equalsIgnoreCase(difficolta)) difficulty = DifficultyEnum.HARD;*/

                //CREAZIONE SESSIONE QUIZ
                sessions.add(new SessioneQuiz(user, difficolta, punteggio));

            }
        }
        return sessions;
    }

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

                /*
                //OTTENGO DIFFICOLTA'
                String difficolta = rs.getString("difficolta");
                DifficultyEnum difficulty = null;
                if ("EASY".equalsIgnoreCase(difficolta)) difficulty = DifficultyEnum.EASY;
                else if ("MEDIUM".equalsIgnoreCase(difficolta)) difficulty = DifficultyEnum.MEDIUM;
                else if ("HARD".equalsIgnoreCase(difficolta)) difficulty = DifficultyEnum.HARD;*/

                //CREAZIONE SESSIONE QUIZ
                sessions.add(new SessioneQuiz(user, difficultyEnum, punteggio));

            }
        }
        return sessions;
    }
}

