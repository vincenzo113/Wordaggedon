package com.example.dao.Documento;

import com.example.dao.ConnectionConfig;
import com.example.difficultySettings.DifficultyEnum;
import com.example.models.Documento;

import java.util.List;

public interface DocumentoDAO<T> extends ConnectionConfig {

    public List<T> getDocumentiPerDifficolta(DifficultyEnum difficolta);
    public void insertDocumento(Documento documento);

}
