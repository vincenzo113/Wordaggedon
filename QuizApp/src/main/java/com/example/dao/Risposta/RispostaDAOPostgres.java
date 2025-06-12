package com.example.dao.Risposta;

import com.example.dao.stopWordsDAO.StopWordsDAOPostgres;
import com.example.exceptions.DatabaseException;
import com.example.models.Documento;
import com.example.models.Risposta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Collections;

/**
 * Implementazione PostgreSQL del DAO per la gestione delle parole nel database.
 * Questa classe fornisce metodi per:
 * - Selezionare la risposta corretta più frequente in un documento
 * - Selezionare parole non più frequenti (per generare risposte errate)
 * - Selezionare parole presenti in almeno un documento
 * - Gestire le operazioni CRUD relative alle parole nel database
 */

public class RispostaDAOPostgres implements RispostaDAO{
    /**
     * {@inheritDoc}
     */
    @Override
    public Risposta selectRispostaCorrettaRipetizioneParolaDocumento(Documento documento, String parola) 
            throws DatabaseException {
        String query = "SELECT conteggio FROM mappa WHERE documento = ? AND LOWER(valore) = LOWER(?)";
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, documento.getId());
            pstmt.setString(2, parola);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Risposta(String.valueOf(rs.getInt("conteggio")), true);
                }
                return new Risposta("0", true);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Errore nel recupero della ripetizione della parola nel documento", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Risposta selectRispostaCorrettaNonPresente(List<Documento> documenti)
            throws DatabaseException {
        String query = "SELECT stringa FROM vocabolario " +
                      "WHERE LOWER(stringa) NOT IN (SELECT LOWER(valore) FROM mappa) " +
                      "ORDER BY RANDOM() LIMIT 1";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Risposta(rs.getString("stringa").toLowerCase(), true);
                }
                return new Risposta("Nessuna delle altre risposte è corretta", true);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Errore nel recupero della parola non presente", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Risposta selectRispostaCorrettaPiuFrequenteInTutti(List<Documento> documenti) 
            throws DatabaseException {
        // Caricamento delle stopwords
        StopWordsDAOPostgres stopWordsDAOPostgres = new StopWordsDAOPostgres();
        Set<String> stopWords = stopWordsDAOPostgres.getStopWords();
        String stopWordsList = stopWords.stream()
            .map(w -> "'" + w.toLowerCase().replace("'", "''") + "'")
            .reduce((a, b) -> a + ", " + b)
            .orElse("''");

        String placeholders = String.join(",", Collections.nCopies(documenti.size(), "?"));
        String query = "SELECT valore " +
                      "FROM mappa " +
                      "WHERE documento IN (" + placeholders + ") " +
                      "AND LOWER(valore) NOT IN (" + stopWordsList + ") " +
                      "GROUP BY valore " +
                      "ORDER BY SUM(conteggio) DESC " +
                      "LIMIT 1";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            for (int i = 0; i < documenti.size(); i++) {
                pstmt.setInt(i + 1, documenti.get(i).getId());
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Risposta(rs.getString("valore"), true);
                }
                return new Risposta("Nessuna delle altre risposte è corretta", true);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Errore nel recupero della parola più frequente in tutti i documenti", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Risposta selectRispostaCorrettaPiuFrequenteDocumento(Documento documento) 
            throws DatabaseException {
        // Caricamento delle stopwords
        StopWordsDAOPostgres stopWordsDAOPostgres = new StopWordsDAOPostgres();
        Set<String> stopWords = stopWordsDAOPostgres.getStopWords();
        String stopWordsList = stopWords.stream()
            .map(w -> "'" + w.toLowerCase().replace("'", "''") + "'")
            .reduce((a, b) -> a + ", " + b)
            .orElse("''");

        String query = "SELECT valore FROM mappa p WHERE documento = ? " +
                      "AND p.conteggio = (" +
                          "SELECT MAX(p1.conteggio) FROM mappa p1 WHERE p1.documento = ? " +
                          "AND LOWER(p1.valore) NOT IN (" + stopWordsList + ")" +
                      ") AND LOWER(p.valore) NOT IN (" + stopWordsList + ") " +
                      "LIMIT 1";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, documento.getId());
            pstmt.setInt(2, documento.getId());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Risposta(rs.getString("valore"), true);
                }
                return new Risposta("Nessuna delle altre risposte è corretta", true);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Errore nel recupero della parola più frequente nel documento", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Risposta> selectParoleNonPiuFrequenti(Documento documento, String parolaCorretta) 
        throws DatabaseException {
    // Caricamento delle stopwords
    StopWordsDAOPostgres stopWordsDAOPostgres = new StopWordsDAOPostgres();
    Set<String> stopWords = stopWordsDAOPostgres.getStopWords();
    String stopWordsList = stopWords.stream()
        .map(w -> "'" + w.toLowerCase().replace("'", "''") + "'")
        .reduce((a, b) -> a + ", " + b)
        .orElse("''");

    List<Risposta> risposteFake = new ArrayList<>();
    try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
        // Ottieni conteggio della parolaCorretta
        String countQuery = "SELECT conteggio FROM mappa WHERE documento = ? " +
                          "AND LOWER(valore) = LOWER(?) LIMIT 1";
                          
        try (PreparedStatement pstmtCount = conn.prepareStatement(countQuery)) {
            pstmtCount.setInt(1, documento.getId());
            pstmtCount.setString(2, parolaCorretta);
            
            int countCorretta = 0;
            try (ResultSet rsCount = pstmtCount.executeQuery()) {
                if (rsCount.next()) {
                    countCorretta = rsCount.getInt("conteggio");
                }
            }

            // Seleziona parole diverse per conteggio
            String query = "SELECT valore FROM mappa WHERE documento = ? " +
                         "AND LOWER(valore) <> LOWER(?) " +
                         "AND LOWER(valore) NOT IN (" + stopWordsList + ") " +
                         "AND conteggio <> ? " +
                         "ORDER BY RANDOM() LIMIT 3";

            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, documento.getId());
                pstmt.setString(2, parolaCorretta);
                pstmt.setInt(3, countCorretta);

                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        risposteFake.add(new Risposta(rs.getString("valore"), false));
                    }
                }
            }

            // Se meno di 3, prendi dal vocabolario
            if (risposteFake.size() < 3) {
                int remaining = 3 - risposteFake.size();
                String vocQuery = "SELECT v.stringa " +
                                "FROM vocabolario v " +
                                "LEFT JOIN (" +
                                "   SELECT LOWER(valore) AS valore, COUNT(*) AS conteggio " +
                                "   FROM mappa WHERE documento = ? " +
                                "   GROUP BY LOWER(valore)" +
                                ") p ON LOWER(v.stringa) = p.valore " +
                                "WHERE LOWER(v.stringa) <> LOWER(?) " +
                                "AND LOWER(v.stringa) NOT IN (" + stopWordsList + ") " +
                                "AND (p.conteggio IS NULL OR p.conteggio < ?) " +
                                "ORDER BY RANDOM() LIMIT ?";

                try (PreparedStatement pstmtVoc = conn.prepareStatement(vocQuery)) {
                    pstmtVoc.setInt(1, documento.getId());
                    pstmtVoc.setString(2, parolaCorretta);
                    pstmtVoc.setInt(3, countCorretta);
                    pstmtVoc.setInt(4, remaining);

                    try (ResultSet rsVoc = pstmtVoc.executeQuery()) {
                        while (rsVoc.next()) {
                            risposteFake.add(new Risposta(rsVoc.getString("stringa").toLowerCase(), false));
                        }
                    }
                }
            }
        }

    } catch (SQLException e) {
        throw new DatabaseException("Errore nel recupero delle parole non più frequenti", e);
    }

    return risposteFake;
}



    /**
     * {@inheritDoc}
     */
    @Override
    public List<Risposta> selectParoleNonPiuFrequentiInTutti(List<Documento> documenti, String parolaCorretta) 
        throws DatabaseException {
    // Caricamento delle stopwords
    StopWordsDAOPostgres stopWordsDAOPostgres = new StopWordsDAOPostgres();
    Set<String> stopWords = stopWordsDAOPostgres.getStopWords();
    String stopWordsList = stopWords.stream()
        .map(w -> "'" + w.toLowerCase().replace("'", "''") + "'")
        .reduce((a, b) -> a + ", " + b)
        .orElse("''");

    String placeholders = String.join(",", Collections.nCopies(documenti.size(), "?"));
    List<Risposta> risposteFake = new ArrayList<>();

    try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
        // Conteggio della parola corretta
        String countQuery = "SELECT conteggio FROM mappa WHERE LOWER(valore) = LOWER(?) LIMIT 1";
        
        try (PreparedStatement pstmtCount = conn.prepareStatement(countQuery)) {
            pstmtCount.setString(1, parolaCorretta);
            
            int countCorretta = 0;
            try (ResultSet rsCount = pstmtCount.executeQuery()) {
                if (rsCount.next()) {
                    countCorretta = rsCount.getInt("conteggio");
                }
            }

            // Query principale
            String query = "SELECT valore FROM mappa WHERE documento IN (" + placeholders + ") " +
                         "AND LOWER(valore) <> LOWER(?) " +
                         "AND LOWER(valore) NOT IN (" + stopWordsList + ") " +
                         "AND conteggio < ? " +
                         "ORDER BY RANDOM() LIMIT 3";

            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                for (int i = 0; i < documenti.size(); i++) {
                    pstmt.setInt(i + 1, documenti.get(i).getId());
                }
                pstmt.setString(documenti.size() + 1, parolaCorretta);
                pstmt.setInt(documenti.size() + 2, countCorretta);

                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        risposteFake.add(new Risposta(rs.getString("valore").toLowerCase(), false));
                    }
                }
            }

            // Se meno di 3, prendi da vocabolario
            if (risposteFake.size() < 3) {
                int remaining = 3 - risposteFake.size();
                String vocQuery = "SELECT v.stringa " +
                                "FROM vocabolario v " +
                                "LEFT JOIN (" +
                                "   SELECT LOWER(valore) AS valore, COUNT(*) AS conteggio " +
                                "   FROM mappa WHERE documento IN (" + placeholders + ") " +
                                "   GROUP BY LOWER(valore)" +
                                ") p ON LOWER(v.stringa) = p.valore " +
                                "WHERE LOWER(v.stringa) <> LOWER(?) " +
                                "AND LOWER(v.stringa) NOT IN (" + stopWordsList + ") " +
                                "AND (p.conteggio IS NULL OR p.conteggio < ?) " +
                                "ORDER BY RANDOM() LIMIT ?";

                try (PreparedStatement pstmtVoc = conn.prepareStatement(vocQuery)) {
                    for (int i = 0; i < documenti.size(); i++) {
                        pstmtVoc.setInt(i + 1, documenti.get(i).getId());
                    }
                    pstmtVoc.setString(documenti.size() + 1, parolaCorretta);
                    pstmtVoc.setInt(documenti.size() + 2, countCorretta);
                    pstmtVoc.setInt(documenti.size() + 3, remaining);

                    try (ResultSet rsVoc = pstmtVoc.executeQuery()) {
                        while (rsVoc.next()) {
                            risposteFake.add(new Risposta(rsVoc.getString("stringa").toLowerCase(), false));
                        }
                    }
                }
            }
        }

    } catch (SQLException e) {
        throw new DatabaseException("Errore nel recupero delle parole non più frequenti in tutti i documenti", e);
    }

    return risposteFake;
}


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Risposta> selectParolePresentiInAlmenoUnDocumento(List<Documento> docs) 
        throws DatabaseException {
        // Caricamento delle stopwords
        StopWordsDAOPostgres stopWordsDAOPostgres = new StopWordsDAOPostgres();
        Set<String> stopWords = stopWordsDAOPostgres.getStopWords();
        String stopWordsList = stopWords.stream()
                .map(w -> "'" + w.toLowerCase().replace("'", "''") + "'")
                .reduce((a, b) -> a + ", " + b)
                .orElse("''");

        String placeholders = String.join(",", Collections.nCopies(docs.size(), "?"));
        List<Risposta> risposte = new ArrayList<>();

        String query = "SELECT valore FROM mappa WHERE documento IN (" + placeholders + ") " +
                "AND LOWER(valore) NOT IN (" + stopWordsList + ") " +
                "ORDER BY RANDOM() LIMIT 3";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            for (int i = 0; i < docs.size(); i++) {
                pstmt.setInt(i + 1, docs.get(i).getId());
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    risposte.add(new Risposta(rs.getString("valore").toLowerCase(), false));
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("Errore nel recupero delle parole presenti in almeno un documento", e);
        }

        return risposte;
    }
}