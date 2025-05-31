package com.example.dao.SessionQuiz;

import com.example.difficultySettings.DifficultyEnum;
import com.example.models.SessioneQuiz;
import com.example.models.User;

import java.util.List;
import java.util.ArrayList;

import java.sql.*;

public class SessionDAOPostgres implements SessionDAO {
    @Override
    public List<SessioneQuiz> selectSessionsWithTopScores() throws SQLException {
        List<SessioneQuiz> sessions = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            String query = "SeLECT username, punteggio FROM sessione ORDER BY punteggio DESC LIMIT 10";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                //CREAZIONE USER
                String username = rs.getString("username");
                User user = new User(username);


                int punteggio = rs.getInt("punteggio");

                //OTTENGO DIFFICOLTA'
                String difficolta = rs.getString("difficolta");
                DifficultyEnum difficulty = null;
                if (difficolta.equals("EASY")) difficulty = DifficultyEnum.EASY;
                else if (difficolta.equals("MEDIUM")) difficulty = DifficultyEnum.MEDIUM;
                else if (difficolta.equals("HARD")) difficulty = DifficultyEnum.HARD;

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

            String query = "INSERT INTO sessione (username, difficolta, punteggio) " +
                    "VALUES ('" + currentQuiz.getUser().getUsername() + "', '" +
                    currentQuiz.getDifficolta().name() + "', " + currentQuiz.getScore() + ")";
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

