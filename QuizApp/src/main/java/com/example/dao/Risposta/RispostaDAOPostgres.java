package com.example.dao.Risposta;

import com.example.dao.stopWordsDAO.StopWordsDAOPostgres;
import com.example.models.Documento;
import com.example.models.Risposta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Implementazione dell'interfaccia {@link RispostaDAO} per l'interazione con un database PostgreSQL.
 * Fornisce metodi per la selezione di risposte basate su diverse logiche di interrogazione del database,
 * come la frequenza delle parole nei documenti e la gestione delle stopwords.
 */
public class RispostaDAOPostgres implements RispostaDAO{


    /**
     * Seleziona la risposta corretta che indica la ripetizione di una specifica parola in un documento.
     * Restituisce il conteggio della parola se presente, altrimenti "0".
     *
     * @param documento Il {@link Documento} in cui cercare la parola.
     * @param parola La parola da cercare.
     * @return Una {@link Risposta} contenente il conteggio della parola e un flag che indica se è la risposta corretta.
     * @throws RuntimeException se si verifica un errore SQL durante l'esecuzione della query.
     */
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

    /**
     * Seleziona una parola casuale dal vocabolario che non è presente in nessun documento.
     * Questa è considerata la risposta corretta per una domanda del tipo "Qual è la parola che non si presenta in nessun documento?".
     *
     * @param documenti Una lista di {@link Documento} (anche se non direttamente usata nella query, può essere un parametro di contesto).
     * @return Una {@link Risposta} contenente la parola non presente e un flag che indica se è la risposta corretta.
     * Se nessuna parola viene trovata, restituisce una risposta di default.
     * @throws RuntimeException se si verifica un errore SQL durante l'esecuzione della query.
     */
    @Override
    public Risposta selectRispostaCorrettaNonPresente(List<Documento> documenti) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {
//Supponiamo vocabolario essere una tabella in cui ci mettiamo parole a caso , per poter trovare la risposta corretta alla domanda : Qual è la parola che non si presenta in nessun documento
            String query = "SELECT stringa FROM vocabolario " +
                    "WHERE LOWER(stringa) NOT IN (SELECT valore FROM parola) " +
                    "ORDER BY RANDOM() LIMIT 1";

            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()) {
                // System.out.println("CORRECT ANSWER");
                return new Risposta(rs.getString("stringa").toLowerCase(), true);
            } else return new Risposta("Nessuna delle altre risposte è corretta", true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("NULL");
        //return null;
    }

    /**
     * Seleziona la parola più frequente tra tutti i documenti forniti, escludendo le stopwords.
     *
     * @param documenti La lista di {@link Documento} su cui effettuare la ricerca.
     * @return Una {@link Risposta} contenente la parola più frequente e un flag che indica se è la risposta corretta.
     * Se nessuna parola valida viene trovata, restituisce una risposta di default.
     * @throws RuntimeException se si verifica un errore SQL durante l'esecuzione della query.
     */
    @Override
    public Risposta selectRispostaCorrettaPiuFrequenteInTutti(List<Documento> documenti) {
        // Caricamento delle stopwords
        StopWordsDAOPostgres stopWordsDAOPostgres = new StopWordsDAOPostgres();
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


    /**
     * Seleziona la parola più frequente all'interno di un singolo documento, escludendo le stopwords.
     *
     * @param documento Il {@link Documento} in cui cercare la parola più frequente.
     * @return Una {@link Risposta} contenente la parola più frequente e un flag che indica se è la risposta corretta.
     * Se nessuna parola valida viene trovata, restituisce una risposta di default.
     * @throws RuntimeException se si verifica un errore SQL durante l'esecuzione della query.
     */
    @Override
    public Risposta selectRispostaCorrettaPiuFrequenteDocumento(Documento documento) {
        StopWordsDAOPostgres stopWordsDAOPostgres = new StopWordsDAOPostgres();
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

    /**
     * Seleziona un elenco di parole "non più frequenti" per un dato documento, da usare come risposte errate.
     * Le parole selezionate sono diverse dalla parola corretta fornita, non sono stopwords e hanno un conteggio diverso (o minore) rispetto alla parola corretta.
     * Se non si trovano abbastanza parole nel documento, vengono aggiunte parole dal vocabolario.
     *
     * @param documento Il {@link Documento} di riferimento.
     * @param parolaCorretta La parola corretta, il cui conteggio viene usato come riferimento.
     * @return Una lista di {@link Risposta} false (risposte errate).
     * @throws RuntimeException se si verifica un errore durante l'operazione.
     */
    @Override
    public List<Risposta> selectParoleNonPiuFrequenti(Documento documento, String parolaCorretta) {
        StopWordsDAOPostgres stopWordsDAOPostgres = new StopWordsDAOPostgres();
        Set<String> stopWords = stopWordsDAOPostgres.getStopWords();
        String stopWordsList = stopWords.stream()
                .map(w -> "'" + w.toLowerCase().replace("'", "''") + "'")
                .reduce((a, b) -> a + ", " + b)
                .orElse("''");

        List<Risposta> risposteFake = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            // Ottieni conteggio della parolaCorretta
            String countQuery = "SELECT conteggio FROM parola WHERE documento = " + documento.getId() +
                    " AND LOWER(valore) = LOWER('" + parolaCorretta.replace("'", "''") + "') LIMIT 1";
            ResultSet rsCount = stmt.executeQuery(countQuery);
            int countCorretta = 0;
            if (rsCount.next()) {
                countCorretta = rsCount.getInt("conteggio");
            }

            // Seleziona parole diverse per conteggio
            String query = "SELECT valore FROM parola WHERE documento = " + documento.getId() +
                    " AND LOWER(valore) <> LOWER('" + parolaCorretta.replace("'", "''") + "') " +
                    "AND LOWER(valore) NOT IN (" + stopWordsList + ") " +
                    "AND conteggio <> " + countCorretta +
                    " ORDER BY RANDOM() LIMIT 3";

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                risposteFake.add(new Risposta(rs.getString("valore"), false));
            }

            // Se meno di 3, prendi dal vocabolario
            if (risposteFake.size() < 3) {
                int remaining = 3 - risposteFake.size();
                //SELEZIONA UNA PAROLA DAL VOCABOLARIO CHE NON È UGUALE A PAROLA CORRETTA , CHE NON È UNA STOPWORD,


                //MA QUESTA PAROLA POTREBBE ESSERE PRESENTE NEL DOCUMENTO (se ha un conteggio minore )
                String vocQuery =
                        "SELECT v.stringa " +
                                "FROM vocabolario v " +
                                "LEFT JOIN (" +
                                "   SELECT LOWER(valore) AS valore, COUNT(*) AS conteggio " +
                                "   FROM parola WHERE documento = " + documento.getId() +
                                "   GROUP BY LOWER(valore)" +
                                ") p ON LOWER(v.stringa) = p.valore " +
                                "WHERE LOWER(v.stringa) <> LOWER('" + parolaCorretta.replace("'", "''") + "') " +
                                "AND LOWER(v.stringa) NOT IN (" + stopWordsList + ") " +
                                "AND (p.conteggio IS NULL OR p.conteggio < " + countCorretta + ") " +
                                "ORDER BY RANDOM() LIMIT " + remaining;

                ResultSet rsVoc = stmt.executeQuery(vocQuery);
                while (rsVoc.next()) {
                    risposteFake.add(new Risposta(rsVoc.getString("stringa").toLowerCase(), false));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return risposteFake;
    }


    /**
     * Seleziona un elenco di parole "non più frequenti" tra tutti i documenti forniti, da usare come risposte errate.
     * Le parole selezionate sono diverse dalla parola corretta fornita, non sono stopwords e hanno un conteggio diverso (o minore) rispetto alla parola corretta.
     * Se non si trovano abbastanza parole nei documenti, vengono aggiunte parole dal vocabolario.
     *
     * @param documenti La lista di {@link Documento} di riferimento.
     * @param parolaCorretta La parola corretta, il cui conteggio aggregato viene usato come riferimento.
     * @return Una lista di {@link Risposta} false (risposte errate).
     * @throws RuntimeException se si verifica un errore durante l'operazione.
     */
    public List<Risposta> selectParoleNonPiuFrequentiInTutti(List<Documento> documenti, String parolaCorretta) {
        StopWordsDAOPostgres stopWordsDAOPostgres = new StopWordsDAOPostgres();
        Set<String> stopWords = stopWordsDAOPostgres.getStopWords();
        String stopWordsList = stopWords.stream()
                .map(w -> "'" + w.toLowerCase().replace("'", "''") + "'")
                .reduce((a, b) -> a + ", " + b)
                .orElse("''");

        List<Risposta> risposteFake = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            // Lista ID documenti
            StringBuilder idList = new StringBuilder();
            for (int i = 0; i < documenti.size(); i++) {
                if (i > 0) idList.append(", ");
                idList.append(documenti.get(i).getId());
            }

            // Conteggio della parola corretta
            String countQuery = "SELECT conteggio FROM parola WHERE LOWER(valore) = LOWER('" + parolaCorretta.replace("'", "''") + "') LIMIT 1";
            ResultSet rsCount = stmt.executeQuery(countQuery);
            int countCorretta = 0;
            if (rsCount.next()) {
                countCorretta = rsCount.getInt("conteggio");
            }

            // Query principale
            String query = "SELECT valore FROM parola WHERE documento IN (" + idList + ") " +
                    "AND LOWER(valore) <> LOWER('" + parolaCorretta.replace("'", "''") + "') " +
                    "AND LOWER(valore) NOT IN (" + stopWordsList + ") " +
                    "AND conteggio <> " + countCorretta +
                    " ORDER BY RANDOM() LIMIT 3";

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                risposteFake.add(new Risposta(rs.getString("valore").toLowerCase(), false));
            }

            // Se meno di 3, prendi da vocabolario
            if (risposteFake.size() < 3) {
                int remaining = 3 - risposteFake.size();
                //SELEZIONA UNA PAROLA DAL VOCABOLARIO CHE NON È UGUALE A PAROLA CORRETTA , CHE NON È UNA STOPWORD,
                // E CHE NON HA UN CONTEGGIO UGUALE A QUELLA CORRETTA

                //MA QUESTA PAROLA POTREBBE ESSERE PRESENTE IN UNO DEI DOCUMENTI
                String vocQuery =
                        "SELECT v.stringa " +
                                "FROM vocabolario v " +
                                "LEFT JOIN (" +
                                "   SELECT LOWER(valore) AS valore, COUNT(*) AS conteggio " +
                                "   FROM parola WHERE documento IN (" + idList + ") " +
                                "   GROUP BY LOWER(valore)" +
                                ") p ON LOWER(v.stringa) = p.valore " +
                                "WHERE LOWER(v.stringa) <> LOWER('" + parolaCorretta.replace("'", "''") + "') " +
                                "AND LOWER(v.stringa) NOT IN (" + stopWordsList + ") " +
                                "AND (p.conteggio IS NULL OR p.conteggio < " + countCorretta + ") " +
                                "ORDER BY RANDOM() LIMIT " + remaining;

                ResultSet rsVoc = stmt.executeQuery(vocQuery);
                while (rsVoc.next()) {
                    risposteFake.add(new Risposta(rsVoc.getString("stringa").toLowerCase(), false));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return risposteFake;
    }


    /**
     * Seleziona un elenco di parole presenti in almeno uno dei documenti forniti, escludendo le stopwords.
     * Utile per generare risposte multiple choice.
     *
     * @param docs La lista di {@link Documento} da cui estrarre le parole.
     * @return Una lista di {@link Risposta} false (risposte errate).
     * @throws RuntimeException se si verifica un errore durante l'operazione.
     */
    @Override
    public List<Risposta> selectParolePresentiInAlmenoUnDocumento(List<Documento> docs){
        // Caricamento delle stopwords
        StopWordsDAOPostgres stopWordsDAOPostgres = new StopWordsDAOPostgres();
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
                risposte.add(new Risposta(rs.getString("valore").toLowerCase(), false));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return risposte;
    }
}