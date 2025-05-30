package com.example.dao;

import com.example.difficultySettings.DifficultyEnum;
import com.example.models.Domanda;
import com.example.models.Documento;

public class QuizDAOPostgres implements QuizDAO {
    @Override
    public Documento[] selectDocumenti(DifficultyEnum difficolta) {
        return null;
    }

    @Override
    public Domanda[] selectDomande() {
        return null;
    }
}
