package com.example.dao.stopWordsDAO;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementazione dell'interfaccia {@link StopWordsDAO} per la gestione delle stopwords in un database PostgreSQL.
 * Questa classe fornisce i metodi per inserire nuove stopwords e per recuperare tutte quelle presenti nel database.
 */
public class StopWordsDAOPostgres implements StopWordsDAO {




    /**
     * Inserisce un elenco di stopwords nel database. Ogni stopword viene convertita in minuscolo prima dell'inserimento.
     * Le eccezioni SQL vengono soppresse, il che potrebbe non essere l'approccio migliore per la gestione degli errori in un'applicazione di produzione.
     *
     * @param stopWords Una {@link List} di stringhe, dove ogni stringa rappresenta una stopword da inserire.
     */
    @Override
    public void inserisciStopWords(List<String> stopWords){
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            // Per ogni stopWord, eseguiamo una query di inserimento.
            // Si potrebbe considerare l'uso di un PreparedStatement con batching per migliorare le prestazioni
            // in caso di liste molto grandi di stopwords.
            String query = "";
            for(String stopWord : stopWords){
                query = String.format("INSERT INTO stopwords(parola) values('%s')",stopWord.toLowerCase());
                stmt.executeUpdate(query);
            }
        }catch(SQLException ex){
            // Gestione dell'eccezione: in un'applicazione reale, qui si dovrebbe loggare l'errore
            // o rilanciare una RuntimeException specifica o SQLException per una gestione a livello superiore.
            System.err.println("Errore durante l'inserimento delle stopwords: " + ex.getMessage());
        }
    }

    /**
     * Recupera tutte le stopwords presenti nel database.
     * Le stopwords vengono restituite come un {@link Set} per garantire l'unicità e prestazioni efficienti nella ricerca.
     * Le eccezioni SQL vengono soppresse, il che potrebbe non essere l'approccio migliore per la gestione degli errori in un'applicazione di produzione.
     *
     * @return Un {@link Set} di stringhe contenente tutte le stopwords recuperate dal database.
     * Restituisce un set vuoto se non ci sono stopwords o in caso di errore.
     */
    @Override
    public Set<String> getStopWords(){
        Set<String> allStopWords = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()){
            String query = "select parola from stopwords";
            ResultSet resultSet = stmt.executeQuery(query);
            while(resultSet.next()){
                allStopWords.add(resultSet.getString("parola"));
            }
        }catch (SQLException ex){
            // Gestione dell'eccezione: in un'applicazione reale, qui si dovrebbe loggare l'errore
            // o rilanciare una RuntimeException specifica o SQLException per una gestione a livello superiore.
            System.err.println("Errore durante il recupero delle stopwords: " + ex.getMessage());
        }
        return allStopWords;
    }
}
