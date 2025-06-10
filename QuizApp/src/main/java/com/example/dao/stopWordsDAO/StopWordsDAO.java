package com.example.dao.stopWordsDAO;

import com.example.dao.ConnectionConfig;

import java.util.List;
import java.util.Set;

/**
 * Interfaccia DAO (Data Access Object) per la gestione delle stopwords.
 * Estende {@link ConnectionConfig} per garantire l'accesso alle configurazioni di connessione al database.
 * Implementazioni di questa interfaccia gestiranno l'inserimento e il recupero delle stopwords da una sorgente dati.
 */
public interface StopWordsDAO extends ConnectionConfig {

    /**
     * Inserisce un elenco di stopwords nella sorgente dati.
     * Le implementazioni di questo metodo dovrebbero gestire l'aggiunta di nuove stopwords
     * e, se necessario, prevenire duplicati o gestire aggiornamenti.
     *
     * @param stopWords Una {@link List} di stringhe, dove ogni stringa rappresenta una stopword da inserire.
     */
    void inserisciStopWords(List<String> stopWords);

    /**
     * Recupera tutte le stopwords dalla sorgente dati.
     * Questo metodo dovrebbe restituire un insieme di stringhe per garantire l'unicità delle stopwords
     * e permettere operazioni efficienti di ricerca.
     *
     * @return Un {@link Set} di stringhe contenente tutte le stopwords presenti nella sorgente dati.
     * Ritorna un set vuoto se non ci sono stopwords.
     */
    Set<String> getStopWords();
}
