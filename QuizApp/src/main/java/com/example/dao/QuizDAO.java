package com.example.dao;

import com.example.models.Testo;

public interface QuizDAO {

    public static final String URL = "jdbc:postgresql://localhost:6060/quiz";

    Testo[] selectTesti(String difficolta);


}
