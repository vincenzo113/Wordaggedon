package com.example.dao.Risposta;

import com.example.dao.stopWordsDAO.stopWordsDAOPostgres;
import com.example.models.Documento;
import com.example.models.Risposta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RispostaDAOPostgres implements RispostaDAO{
    @Override
    public Risposta selectRispostaCorrettaRipetizioneParolaDocumento(Documento documento, String parola) {
        String query = "" ;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

             query = "SELECT conteggio FROM parola WHERE documento = " + documento.getId() +
                     " AND LOWER(valore) = LOWER('" + parola + "')";
            //System.out.println("Query: " + query);

            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()) {
               // System.out.println("Ripetizione trovata");
                return new Risposta(rs.getInt("conteggio") + "", true);
            } else return new Risposta("0", true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
      //  System.out.println("Ripetizione non trovata");
        //return null;
    }

    @Override
    public Risposta selectRispostaCorrettaNonPresente(List<Documento> documenti) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {
//Supponiamo vocabolario essere una tabella in cui ci mettiamo parole a caso , per poter trovare la risposta corretta alla domanda : Qual è la parola che non si presenta in nessun documento
            String query = "SELECT stringa FROM vocabolario " +
                           "WHERE stringa NOT IN (SELECT valore FROM parola) " +
                           "ORDER BY RANDOM() LIMIT 1";

            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()) {
               // System.out.println("CORRECT ANSWER");
                return new Risposta(rs.getString("stringa"), true);
            } else return new Risposta("Nessuna delle altre risposte è corretta", true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("NULL");
        //return null;
    }

    @Override
    public Risposta selectRispostaCorrettaPiuFrequenteInTutti(List<Documento> documenti) {
        // Caricamento delle stopwords
        stopWordsDAOPostgres stopWordsDAOPostgres = new stopWordsDAOPostgres();
        Set<String> stopWords = stopWordsDAOPostgres.getStopWords();
        String stopWordsList = stopWords.stream()
            .map(w -> "'" + w.toLowerCase().replace("'", "''") + "'")
            .reduce((a, b) -> a + ", " + b)
            .orElse("''");
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            // Costruzione dell'elenco ID documento per l'IN (...)
            StringBuilder ids = new StringBuilder();
            for (int i = 0; i < documenti.size(); i++) {
                if (i > 0) ids.append(", ");
                ids.append(documenti.get(i).getId());
            }

            String query = String.format(
                    "SELECT valore " +
                            "FROM parola " +
                            "WHERE documento IN (%s) AND LOWER(valore) NOT IN (%s) " +
                            "GROUP BY valore " +
                            "ORDER BY SUM(conteggio) DESC " +
                            "LIMIT 1", ids.toString(), stopWordsList);

            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()) {
                return new Risposta(rs.getString("valore"), true);
            } else return new Risposta("Nessuna delle altre risposte è corretta", true);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //return null;
    }



    @Override
    public Risposta selectRispostaCorrettaPiuFrequenteDocumento(Documento documento) {
        stopWordsDAOPostgres stopWordsDAOPostgres = new stopWordsDAOPostgres();
        Set<String> stopWords = stopWordsDAOPostgres.getStopWords();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement())
        {
            String stopWordsList = stopWords.stream()
                .map(w -> "'" + w.toLowerCase().replace("'", "''") + "'")
                .reduce((a, b) -> a + ", " + b)
                .orElse("''");

            String query = String.format(
                "SELECT valore FROM parola p WHERE documento=%s " +
                "AND p.conteggio = (" +
                    "SELECT MAX(p1.conteggio) FROM parola p1 WHERE p1.documento = %s " +
                    "AND LOWER(p1.valore) NOT IN (%s)" +
                ") AND LOWER(p.valore) NOT IN (%s) LIMIT 1",
                documento.getId(), documento.getId(), stopWordsList, stopWordsList
            );

            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()) {
                return new Risposta(rs.getString("valore"), true);
            } else return new Risposta("Nessuna delle altre risposte è corretta", true);

        } catch (SQLException sqle) {
            //System.out.println("Non ho trovato la piu frequente nel documento");
            throw new RuntimeException(sqle);
        }
       // System.out.println("Non ho trovato la piu frequente nel documento");

        //return null;
    }


    @Override
    public List<Risposta> selectParoleNonPiuFrequenti(Documento documento , String parola){
        stopWordsDAOPostgres stopWordsDAOPostgres = new stopWordsDAOPostgres();
        Set<String> stopWords = stopWordsDAOPostgres.getStopWords();
        String stopWordsList = stopWords.stream()
            .map(w -> "'" + w.toLowerCase().replace("'", "''") + "'")
            .reduce((a, b) -> a + ", " + b)
            .orElse("''");

        List<Risposta> risposteFake = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()){
            String query = "SELECT valore FROM parola WHERE documento = " + documento.getId() +
                    " AND LOWER(valore) <> LOWER('" + parola + "') " +
                    "AND LOWER(valore) NOT IN (" + stopWordsList + ") " +
                    "ORDER BY RANDOM() LIMIT 3";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                risposteFake.add(new Risposta(rs.getString("valore"), false));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return risposteFake;
    }



    public List<Risposta> selectParoleNonPiuFrequentiInTutti(List<Documento> documenti, String parolaCorretta){
        // Caricamento delle stopwords
        stopWordsDAOPostgres stopWordsDAOPostgres = new stopWordsDAOPostgres();
        Set<String> stopWords = stopWordsDAOPostgres.getStopWords();
        String stopWordsList = stopWords.stream()
            .map(w -> "'" + w.toLowerCase().replace("'", "''") + "'")
            .reduce((a, b) -> a + ", " + b)
            .orElse("''");
        List<Risposta> risposteFake = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            StringBuilder idList = new StringBuilder();
            for (int i = 0; i < documenti.size(); i++) {
                if (i > 0) idList.append(", ");
                idList.append(documenti.get(i).getId());
            }

            String query = "SELECT valore FROM parola WHERE documento IN (" + idList + ") " +
                           "AND LOWER(valore) <> LOWER('" + parolaCorretta + "') " +
                           "AND LOWER(valore) NOT IN (" + stopWordsList + ") ORDER BY RANDOM() LIMIT 3";

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                risposteFake.add(new Risposta(rs.getString("valore"), false));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return risposteFake;
    }



    @Override
    public List<Risposta> selectParolePresentiInAlmenoUnDocumento(List<Documento> docs){
        // Caricamento delle stopwords
        stopWordsDAOPostgres stopWordsDAOPostgres = new stopWordsDAOPostgres();
        Set<String> stopWords = stopWordsDAOPostgres.getStopWords();
        String stopWordsList = stopWords.stream()
            .map(w -> "'" + w.toLowerCase().replace("'", "''") + "'")
            .reduce((a, b) -> a + ", " + b)
            .orElse("''");

        List<Risposta> risposte = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {
            StringBuilder idList = new StringBuilder();
            for (int i = 0; i < docs.size(); i++) {
                if (i > 0) idList.append(", ");
                idList.append(docs.get(i).getId());
            }
            String query = "SELECT valore FROM parola WHERE documento IN (" + idList + ") " +
                           "AND LOWER(valore) NOT IN (" + stopWordsList + ") " +
                           "ORDER BY RANDOM() LIMIT 3";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                risposte.add(new Risposta(rs.getString("valore"), false));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return risposte;
    }


}
