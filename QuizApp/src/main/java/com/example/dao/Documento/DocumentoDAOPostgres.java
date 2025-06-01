package com.example.dao.Documento;

import com.example.difficultySettings.DifficultyEnum;
import com.example.models.Documento;
import com.example.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
    //Appena inserisce il documento , inseriamo anche la sua mappatura associata
    public void insertDocumento(Documento  documento) {
        try(
                Connection c = DriverManager.getConnection(URL, USER, PASS);
                Statement s = c.createStatement();
        ) {
            //Query per inserire un documento , e ritornare l'id generato per quel documento
            String insertDocumento = String.format("INSERT INTO documento(titolo,contenuto) VALUES ('%s', '%s') RETURNING ID", documento.getTitolo(), documento.getContenuto());
            ResultSet resultSet = s.executeQuery(insertDocumento);
            if(resultSet.next()){
                //Settiamo l'id generato sul documento corrente , altrimenti avremmo sempre il valore di default "0"
                int generatedIdForDocument = resultSet.getInt("id");
                documento.setId(generatedIdForDocument);
            }
            //Ora possiamo inserire anche l'analisi per quel documento
            insertMappaturaDocumento(documento);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertMappaturaDocumento(Documento documento) {
        Map<String,Integer> mappatura = documento.getMappaQuiz();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            for (Map.Entry<String, Integer> entry : mappatura.entrySet()) {
                String parola = entry.getKey();
                int conteggio = entry.getValue();

                String query = String.format(
                        "INSERT INTO parola (documento, valore, conteggio) VALUES ('%s', '%s', %d)",
                        documento.getId(), parola, conteggio);
                stmt.executeUpdate(query);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}