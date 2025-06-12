package com.example.dao.Documento;

import com.example.difficultySettings.DifficultyEnum;
import com.example.exceptions.DatabaseException;
import com.example.exceptions.DocumentoDuplicatoException;
import com.example.exceptions.NotEnoughDocuments;
import com.example.models.Documento;
import com.example.models.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.example.difficultySettings.DifficultyEnum.*;
import static java.sql.DriverManager.getConnection;

/**
 * Implementazione dell'interfaccia DocumentoDAO per la gestione dei documenti
 * utilizzando un database PostgreSQL.
 */
public class DocumentoDAOPostgres implements DocumentoDAO<Documento>{


    /** {@inheritDoc} */
    @Override
    public List<Documento> getDocumentiPerDifficolta(DifficultyEnum difficolta) throws NotEnoughDocuments, DatabaseException {
        List<Documento> documenti = new ArrayList<>();
        int limiteNumeroDocumenti = 3 ;

        String sql = "SELECT * FROM documento WHERE difficolta = ? ORDER BY RANDOM() LIMIT ?" ;
         try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, difficolta.toString());
            stmt.setInt(2, limiteNumeroDocumenti);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Documento doc = new Documento(
                    rs.getInt("id"),
                    rs.getString("titolo"),
                    rs.getString("contenuto")
                );
                documenti.add(doc);

            }

             if(documenti.size() < limiteNumeroDocumenti) throw new NotEnoughDocuments("Non hai caricato abbastanza documenti per la difficoltà: "+difficolta);

        } catch (SQLException e) {
            throw new DatabaseException("Errore durante il recupero dei documenti dal database");
        }

        return documenti;

    }


    /** {@inheritDoc} */
    @Override
    public void insertDocumento(Documento documento) throws DatabaseException, DocumentoDuplicatoException {
    String controlQuery = "SELECT contenuto FROM documento WHERE contenuto = ?";
    String sql = "INSERT INTO documento(titolo, contenuto,difficolta , data_caricamento) VALUES (?, ? , ? , ?) RETURNING id";

    try (Connection c = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement stmtControl = c.prepareStatement(controlQuery);
         PreparedStatement stmt = c.prepareStatement(sql)) {

        stmtControl.setString(1, documento.getContenuto());
        ResultSet rsControl = stmtControl.executeQuery();
        if (rsControl.next()) {
            throw new DocumentoDuplicatoException("Documento già presente nel database.");
        }


        stmt.setString(1, documento.getTitolo());
        stmt.setString(2, documento.getContenuto());
        stmt.setString(3, documento.getDifficolta().toString());
        stmt.setDate(4,Date.valueOf(documento.getDataCaricamento()));
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            int generatedIdForDocument = rs.getInt("id");
            documento.setId(generatedIdForDocument);
        }

        insertMappaturaDocumento(documento);

    } catch (SQLException e) {
        throw new DatabaseException("Errore durante l'inserimento del documento nel database");
    }
}

    /** {@inheritDoc} */
    public void insertMappaturaDocumento(Documento documento) throws DatabaseException {
        Map<String,Integer> mappatura = documento.getMappaQuiz();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            for (Map.Entry<String, Integer> entry : mappatura.entrySet()) {
                String parola = entry.getKey().toLowerCase();
                int conteggio = entry.getValue();

                String query = String.format(
                        "INSERT INTO mappa (documento, valore, conteggio) VALUES ('%s', '%s', %d)",
                        documento.getId(), parola, conteggio);
                stmt.executeUpdate(query);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Errore durante l'inserimento della mappatura nel database");
        }
    }

    /** {@inheritDoc} */
    @Override
    public List<Documento> getAllDocuments() throws DatabaseException {
        List<Documento> allDocuments = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()){

            String query = "select * from documento;";
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                String difficolta = rs.getString("difficolta") ;
                DifficultyEnum diffUtilToInstance ;
                switch (difficolta){
                    case "EASY": diffUtilToInstance = EASY;
                    break;

                    case "MEDIUM": diffUtilToInstance=MEDIUM;
                    break;

                    case "HARD" : diffUtilToInstance=HARD;
                            break;

                    default: diffUtilToInstance = EASY;
                    break;

                }
                LocalDate dataCaricamento = rs.getDate("data_caricamento").toLocalDate();
                allDocuments.add(new Documento(rs.getInt("id"),rs.getString("titolo") ,  rs.getString("contenuto") , diffUtilToInstance, dataCaricamento));
            }

        }catch(SQLException ex){
            throw new DatabaseException("Errore durante il recupero di tutti i documenti dal database");
        }
        return allDocuments;
    }


    /** {@inheritDoc} */
    public void eliminaDocumento(int idDocumento) throws DatabaseException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()){

            String query = "delete from documento where id="+idDocumento;
            stmt.executeUpdate(query);

        } catch (SQLException e) {
            throw new DatabaseException("Errore durante l'eliminazione del documento dal database");
        }

    }
}