package com.example.dao.SessionQuiz;

import com.example.dao.ConnectionConfig;
import com.example.difficultySettings.DifficultyEnum;
import com.example.models.SessioneQuiz;
import com.example.models.User;

import java.sql.SQLException;
import java.util.List;

public interface SessionDAO extends ConnectionConfig {

    List<SessioneQuiz> selectSessionsWithTopScores(DifficultyEnum difficolta) throws SQLException;

    void insertSessione(SessioneQuiz currentQuiz);

    List<SessioneQuiz> selectPersonalScoreboard(User user, DifficultyEnum difficultyEnum) throws SQLException;
}
