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
    public List<SessioneQuiz> selectSessionsWithTopScores() throws SQLException {
        List<SessioneQuiz> sessions = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            String query = "SELECT username, punteggio, difficolta FROM sessione ORDER BY punteggio DESC ";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                //CREAZIONE USER
                String username = rs.getString("username");
                User user = new User(username);


                int punteggio = rs.getInt("punteggio");

                //OTTENGO DIFFICOLTA'
                String difficolta = rs.getString("difficolta");
                DifficultyEnum difficulty = null;
                if ("EASY".equalsIgnoreCase(difficolta)) difficulty = DifficultyEnum.EASY;
                else if ("MEDIUM".equalsIgnoreCase(difficolta)) difficulty = DifficultyEnum.MEDIUM;
                else if ("HARD".equalsIgnoreCase(difficolta)) difficulty = DifficultyEnum.HARD;

                //CREAZIONE SESSIONE QUIZ
                sessions.add(new SessioneQuiz(user, difficulty, punteggio));

            }
        }
        return sessions;
    }

    @Override
    public void insertSessione(SessioneQuiz currentQuiz) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            String query = String.format(
                    "INSERT INTO sessione (username, difficolta, punteggio) VALUES ('%s', '%s', %d)",
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
    public List<SessioneQuiz> selectPersonalScoreboard(User user) throws SQLException {
        List<SessioneQuiz> sessions = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            String query = String.format(
                    "SELECT username, punteggio, difficolta FROM sessione WHERE username = '%s' ORDER BY punteggio DESC",
                    user.getUsername()
            );
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int punteggio = rs.getInt("punteggio");

                //OTTENGO DIFFICOLTA'
                String difficolta = rs.getString("difficolta");
                DifficultyEnum difficulty = null;
                if ("EASY".equalsIgnoreCase(difficolta)) difficulty = DifficultyEnum.EASY;
                else if ("MEDIUM".equalsIgnoreCase(difficolta)) difficulty = DifficultyEnum.MEDIUM;
                else if ("HARD".equalsIgnoreCase(difficolta)) difficulty = DifficultyEnum.HARD;

                //CREAZIONE SESSIONE QUIZ
                sessions.add(new SessioneQuiz(user, difficulty, punteggio));

            }
        }
        return sessions;
    }
}

