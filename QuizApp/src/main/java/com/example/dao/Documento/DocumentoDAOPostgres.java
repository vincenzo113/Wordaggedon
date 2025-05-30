package com.example.dao.Documento;

import com.example.difficultySettings.DifficultyEnum;
import com.example.models.Documento;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class DocumentoDAOPostgres implements DocumentoDAO<DocumentoDAOPostgres>{
    private final String USER = "postgres";
    private final String PASS = "tw2024";

    //Metodo per ottenere il numero di documenti da mostrare all'utente in base alla difficolta scelta dall'utente stesso
    public List<Documento> getDocumentiPerDifficolta(DifficultyEnum difficolta) {
        List<Documento> documenti = new ArrayList<>();
        int limiteNumeroDocumenti;
        switch (difficolta) {
            case EASY:
                limiteNumeroDocumenti = 1;
                break;
            case MEDIUM:
                limiteNumeroDocumenti = 3;
                break;
            case HARD:
                limiteNumeroDocumenti = 5;
                break;
            default:
                limiteNumeroDocumenti = 1;
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            String query = "SELECT * FROM documento ORDER BY RANDOM() LIMIT " + limiteNumeroDocumenti;
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Documento doc = new Documento(
                    rs.getInt("id"),
                    rs.getString("titolo"),
                    rs.getString("contenuto")
                );
                documenti.add(doc);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return documenti;

    }
}
