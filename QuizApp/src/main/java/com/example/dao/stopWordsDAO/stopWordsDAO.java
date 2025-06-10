package com.example.dao.stopWordsDAO;

import com.example.dao.ConnectionConfig;
import com.example.exceptions.DatabaseException;

import java.util.List;
import java.util.Set;

/**
 * Interfaccia per la gestione delle stop words nel database.
 * Le stop words sono parole comuni che vengono filtrate durante l'elaborazione del testo.
 */
public interface StopWordsDAO extends ConnectionConfig {

    /**
     * Inserisce una lista di stop words nel database.
     * Se una stop word è già presente, viene ignorata.
     *
     * @param stopWords lista di stop words da inserire
     * @throws DatabaseException se si verifica un errore durante l'accesso al database
     */
    void inserisciStopWords(List<String> stopWords) throws DatabaseException;

    /**
     * Recupera tutte le stop words presenti nel database.
     *
     * @return un Set contenente tutte le stop words
     * @throws DatabaseException se si verifica un errore durante l'accesso al database
     */
    Set<String> getStopWords() throws DatabaseException;
}