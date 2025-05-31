package com.example.dao.SessionQuiz;

import com.example.dao.ConnectionConfig;
import com.example.models.SessioneQuiz;

import java.sql.SQLException;
import java.util.List;

public interface SessionDAO extends ConnectionConfig {

    List<SessioneQuiz> selectSessionsWithTopScores() throws SQLException;

    void insertSessione(SessioneQuiz currentQuiz);
}
