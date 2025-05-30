package com.example.dao.Documento;

import com.example.dao.URL;
import com.example.difficultySettings.DifficultyEnum;
import com.example.models.Documento;

import java.util.List;

public interface DocumentoDAO<T> extends URL {

    public List<T> getDocumentiPerDifficolta(DifficultyEnum difficolta);
    public void insertDocumento(Documento documento);

}
