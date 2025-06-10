package com.example.dao.Documento;

import com.example.dao.ConnectionConfig;
import com.example.difficultySettings.DifficultyEnum;
import com.example.exceptions.NotEnoughDocuments;
import com.example.models.Documento;

import java.sql.SQLException;
import java.util.List;

/**
 * Interfaccia per la gestione dei documenti in un database, con metodi
 * per l'inserimento, il recupero e la selezione basata sulla difficoltà.
 *
 * @param <T> il tipo di oggetto Documento gestito
 */
public interface DocumentoDAO<T> extends ConnectionConfig {

    /**
     * Restituisce una lista di documenti in base alla difficoltà specificata.
     *
     * @param difficolta la difficoltà selezionata dall'utente
     * @return lista di documenti corrispondenti alla difficoltà
     * @throws NotEnoughDocuments se non ci sono abbastanza documenti per la difficoltà richiesta
     */
    public List<T> getDocumentiPerDifficolta(DifficultyEnum difficolta) throws NotEnoughDocuments;

    /**
     * Inserisce un nuovo documento nel database.
     *
     * @param documento il documento da inserire
     * @throws SQLException se si verifica un errore durante l'inserimento
     */
    public void insertDocumento(Documento documento) throws SQLException;

    /**
     * Recupera tutti i documenti presenti nel database.
     *
     * @return lista di tutti i documenti
     */
    public List<Documento> getAllDocuments();

}
