package com.example.dao.Documento;

import com.example.difficultySettings.DifficultyEnum;
import com.example.models.Documento;
import com.example.models.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class DocumentoDAOPostgres implements DocumentoDAO<Documento>{


    //Metodo per ottenere il numero di documenti da mostrare all'utente in base alla difficolta scelta dall'utente stesso
    @Override
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

    @Override
    public void insertDocumento(Documento documento) {
        try(
                Connection c = DriverManager.getConnection(URL, USER, PASS);
                Statement s = c.createStatement();
        ) {
            String insertDocumento = String.format("INSERT INTO documento(titolo,contenuto) VALUES ('%s', '%s')", documento.getTitolo(), documento.getContenuto());
            s.executeUpdate(insertDocumento);

            insertParole(documento);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertParole(Documento documento) {
        documento.getMappaQuiz().entrySet().forEach(entry -> {
            try(
                    Connection c = DriverManager.getConnection(URL, USER, PASS);
                    Statement s = c.createStatement();
            ) {
                String insertParola = String.format("INSERT INTO parole(parola,id_documento) VALUES ('%s', '%s')", entry.getKey(), documento.getId());
                s.executeUpdate(insertParola);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}