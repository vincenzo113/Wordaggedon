package com.example.dao.Documento;

import com.example.difficultySettings.DifficultyEnum;
import com.example.exceptions.NotEnoughDocuments;
import com.example.models.Documento;
import com.example.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.sql.DriverManager.getConnection;

/**
 * Implementazione dell'interfaccia DocumentoDAO per la gestione dei documenti
 * utilizzando un database PostgreSQL.
 */
public class DocumentoDAOPostgres implements DocumentoDAO<Documento>{


    /**
     * Restituisce una lista di documenti in base alla difficoltà specificata.
     *
     * @param difficolta la difficoltà selezionata dall'utente
     * @return lista di documenti corrispondenti alla difficoltà
     * @throws NotEnoughDocuments se non ci sono abbastanza documenti per la difficoltà richiesta
     */
    @Override
    public List<Documento> getDocumentiPerDifficolta(DifficultyEnum difficolta) throws NotEnoughDocuments {
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
            e.printStackTrace();
        }

        return documenti;

    }


    /**
     * Inserisce un nuovo documento nel database. Se il documento è già presente,
     * viene generata un'eccezione. Inoltre, inserisce anche la mappatura delle parole.
     *
     * @param documento il documento da inserire
     * @throws SQLException se si verifica un errore durante l'inserimento
     */
    @Override
    //Appena inserisce il documento , inseriamo anche la sua mappatura associata
    public void insertDocumento(Documento  documento)  throws SQLException {

        String controlQuery = "SELECT contenuto FROM documento WHERE contenuto = ?";
        String sql = "INSERT INTO documento(titolo, contenuto,difficolta) VALUES (?, ? , ?) RETURNING id";
        try(
                Connection c = DriverManager.getConnection(URL, USER, PASS);
                PreparedStatement stmtControl = c.prepareStatement(controlQuery);
                PreparedStatement stmt = c.prepareStatement(sql);
        ) {
            //Query per inserire un documento , e ritornare l'id generato per quel documento

            stmtControl.setString(1, documento.getContenuto());
            ResultSet rsControl = stmtControl.executeQuery();
            if(rsControl.next()) {
                System.out.println("Documento già presente nel database.");
                throw new SQLException("Documento già presente nel database.");
            }


            stmt.setString(1, documento.getTitolo());
            stmt.setString(2, documento.getContenuto());
            stmt.setString(3, documento.getDifficolta().toString());
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                //Settiamo l'id generato sul documento corrente , altrimenti avremmo sempre il valore di default "0"
                int generatedIdForDocument = rs.getInt("id");
                documento.setId(generatedIdForDocument);
            }
            //Ora possiamo inserire anche l'analisi per quel documento
            insertMappaturaDocumento(documento);
        }
    }

    /**
     * Inserisce nel database la mappatura delle parole associate a un documento.
     *
     * @param documento il documento contenente la mappatura da inserire
     */
    public void insertMappaturaDocumento(Documento documento) {
        Map<String,Integer> mappatura = documento.getMappaQuiz();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            for (Map.Entry<String, Integer> entry : mappatura.entrySet()) {
                String parola = entry.getKey().toLowerCase();
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

    /**
     * Recupera tutti i documenti presenti nel database.
     *
     * @return lista di tutti i documenti
     */
    @Override
    public List<Documento> getAllDocuments(){
        List<Documento> allDocuments = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()){

            String query = "select * from documento;";
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                allDocuments.add(new Documento(rs.getInt("id"),rs.getString("titolo") ,  rs.getString("contenuto")));
            }

        }catch(SQLException ex){

        }
        return allDocuments;
    }


    /**
     * Elimina un documento dal database dato il suo ID.
     *
     * @param idDocumento l'identificativo del documento da eliminare
     */
    public void eliminaDocumento(int idDocumento){
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()){

            String query = "delete from documento where id="+idDocumento;
            stmt.executeUpdate(query);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}