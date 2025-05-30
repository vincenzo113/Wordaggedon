package com.example.dao;

import com.example.difficultySettings.DifficultyEnum;
import com.example.models.Documento;
import com.example.models.Domanda;

public interface QuizDAO {

    public static final String URL = "jdbc:postgresql://localhost:6060/quiz";

    Documento[] selectDocumenti(DifficultyEnum difficolta);

    Domanda[] selectDomande();


}
