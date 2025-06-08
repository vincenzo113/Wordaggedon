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

public class DocumentoDAOPostgres implements DocumentoDAO<Documento>{


    //Metodo per ottenere il numero di documenti da mostrare all'utente in base alla difficolta scelta dall'utente stesso
    @Override
    public List<Documento> getDocumentiPerDifficolta(DifficultyEnum difficolta) throws NotEnoughDocuments {
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