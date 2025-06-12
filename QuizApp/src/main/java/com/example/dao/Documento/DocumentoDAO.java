package com.example.dao.Documento;

import com.example.dao.ConnectionConfig;
import com.example.difficultySettings.DifficultyEnum;
import com.example.exceptions.DatabaseException;
import com.example.exceptions.DocumentoDuplicatoException;
import com.example.exceptions.NotEnoughDocuments;
import com.example.models.Documento;

import java.util.List;

/**
 * Interfaccia per la gestione dei documenti nel database.
 * Fornisce le operazioni CRUD di base per la manipolazione dei documenti
 * e funzionalità specifiche per la gestione basata sulla difficoltà.
 *
 * @param <T> il tipo di documento gestito dall'implementazione
 * @author [Il tuo nome]
 * @version 1.0
 * @since 1.0
 */
public interface DocumentoDAO<T> extends ConnectionConfig {

    /**
     * Recupera una lista di documenti filtrati per livello di difficoltà.
     * Il metodo seleziona i documenti dal database che corrispondono
     * al livello di difficoltà specificato.
     *
     * @param difficolta il livello di difficoltà desiderato per i documenti
     * @return List<T> una lista di documenti del tipo specificato
     * @throws NotEnoughDocuments se non ci sono sufficienti documenti per il livello richiesto
     * @throws DatabaseException in caso di errori di connessione o query al database
     * @see DifficultyEnum
     */
    List<T> getDocumentiPerDifficolta(DifficultyEnum difficolta) 
        throws NotEnoughDocuments, DatabaseException;

    /**
     * Inserisce un nuovo documento nel database.
     * Il metodo valida i dati del documento prima dell'inserimento
     * e gestisce la generazione di eventuali chiavi primarie.
     *
     * @param documento l'oggetto Documento da inserire nel database
     * @throws DatabaseException in caso di errori durante l'inserimento o la connessione
     * @throws IllegalArgumentException se il documento è null o contiene dati non validi
     */
    void insertDocumento(Documento documento) throws DatabaseException, DocumentoDuplicatoException;

    /**
     * Recupera tutti i documenti presenti nel database.
     * Il metodo esegue una query per ottenere l'elenco completo
     * dei documenti senza applicare alcun filtro.
     *
     * @return List<Documento> lista contenente tutti i documenti nel database
     * @throws DatabaseException in caso di errori durante il recupero dei dati
     */
    List<Documento> getAllDocuments() throws DatabaseException;
}