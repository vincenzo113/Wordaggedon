package com.example.dao.Domande;

import com.example.models.Documento;
import com.example.models.Domanda;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementazione dell'interfaccia {@link DomandeDAO} per il database PostgreSQL.
 *
 * Questa classe gestisce le operazioni di accesso ai dati relativi alle domande
 * e alle parole casuali associate ai documenti.
 */
public class DomandeDAOPostgres implements DomandeDAO {

    /**
     * Recupera una lista di 4 domande casuali dal database.
     *
     * Effettua una query sulla tabella "domanda" per selezionare 4 record casuali
     * e costruisce una lista di oggetti {@link Domanda} con il testo della domanda.
     *
     * @return una lista di {@link Domanda} contenente 4 domande casuali.
     */
    @Override
    public List<Domanda> selectDomande() {
        List<Domanda> domande = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            String query = "SELECT * FROM domanda ORDER BY RANDOM() LIMIT 4";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Domanda domanda = new Domanda(
                        rs.getString("testoDomanda"),
                        null
                );
                domande.add(domanda);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return domande;
    }

    /**
     * Seleziona una parola casuale dal contenuto di un documento specifico,
     * escludendo le parole presenti nella tabella delle stopwords.
     *
     * La parola viene estratta dalla tabella "parola" filtrando per documento
     * e ignorando le stopwords.
     *
     * @param documento il documento da cui estrarre la parola casuale.
     * @return una parola casuale presente nel documento;
     *         una stringa vuota se non viene trovata alcuna parola.
     */
    @Override
    public String selectParolaCasuale(Documento documento) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {
            String query = String.format(
                    "SELECT valore FROM parola " +
                            "WHERE documento = '%s' " +
                            "AND valore NOT IN (SELECT parola FROM stopwords) " +
                            "LIMIT 1;",
                    documento.getId()
            );
            ResultSet resultSet = stmt.executeQuery(query);
            if (resultSet.next()) {
                return resultSet.getString("valore");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "";
    }
}
