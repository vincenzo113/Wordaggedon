package com.example.dao.Risposta;

import com.example.models.Documento;
import com.example.models.Risposta;

import java.sql.*;
import java.util.List;

public class RispostaDAOPostgres implements RispostaDAO{
    @Override
    public Risposta selectRispostaCorrettaRipetizioneParolaDocumento(Documento documento, String parola) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            String query = "SELECT conteggio FROM parola WHERE documento = " + documento.getId() + " AND valore = '" + parola + "' ";

            ResultSet rs = stmt.executeQuery(query);
            return new Risposta(rs.getInt("conteggio")+"", true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Risposta selectRispostaCorrettaNonPresente(List<Documento> documenti) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            String query = null ;
            if(documenti.size()==1){
                query = "SELECT valore FROM parola WHERE parola NOT IN (SELECT valore FROM parola m1" +
                        " WHERE m1.documento = " + documenti.get(0).getId() +")";
            }
            else if(documenti.size()==2){
                query = "SELECT valore FROM parola WHERE parola NOT IN (SELECT valore FROM parola m1" +
                        " WHERE m1.documento = " + documenti.get(0).getId() +" OR" +
                        " m1.documento = " + documenti.get(1).getId() +")" ;
            }
            else if(documenti.size()==3){
                query = "SELECT valore FROM parola WHERE parola NOT IN (SELECT valore FROM parola m1" +
                        " WHERE m1.documento = " + documenti.get(0).getId() +" OR" +
                        " m1.documento = " + documenti.get(1).getId() +") OR m1.documento = " + documenti.get(2).getId() +")";
            }


            ResultSet rs = stmt.executeQuery(query);
            return new Risposta(rs.getString("conteggio"), true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Risposta selectRispostaCorrettaPiuFrequenteInTutti(List<Documento> documenti) {
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
                            "WHERE documento IN (%s) " +
                            "GROUP BY valore " +
                            "ORDER BY SUM(conteggio) DESC " +
                            "LIMIT 1", ids.toString());

            ResultSet rs = stmt.executeQuery(query);
            return new Risposta(rs.getString("valore"), true);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public Risposta selectRispostaCorrettaPiuFrequenteDocumento(Documento documento) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement())
        {
            //Trova la parola di quel documento che si presenta il massimo numero di volte
            String query = String.format(
                "SELECT valore FROM parola p WHERE documento='%s' AND p.conteggio=(" +
                "SELECT MAX(p1.conteggio) FROM parola p1 WHERE p1.documento='%s')",
                documento.getId(), documento.getId());

            ResultSet rs = stmt.executeQuery(query);

            return new Risposta(rs.getString("valore"), true);

        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
    }
}
