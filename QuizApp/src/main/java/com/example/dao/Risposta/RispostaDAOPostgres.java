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

            String query = "SELECT * FROM parola WHERE documento = " + documento.getId() + " AND valore = '" + parola + "' ";

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
                query = "SELECT valore FROM parola WHERE parola NOT IN (SELECT valore FROM mappatura m1" +
                        " WHERE m1.documento = " + documenti.get(0).getId() +")";
            }
            else if(documenti.size()==2){
                query = "SELECT valore FROM parola WHERE parola NOT IN (SELECT valore FROM mappatura m1" +
                        " WHERE m1.documento = " + documenti.get(0).getId() +" OR" +
                        " m1.documento = " + documenti.get(1).getId() +")" ;
            }
            else if(documenti.size()==3){
                query = "SELECT valore FROM parola WHERE parola NOT IN (SELECT parola FROM mappatura m1" +
                        " WHERE m1.documento = " + documenti.get(0).getId() +" OR" +
                        " m1.documento = " + documenti.get(1).getId() +") OR m1.documento = " + documenti.get(2).getId() +")";
            }


            ResultSet rs = stmt.executeQuery(query);
            return new Risposta(rs.getInt("conteggio")+"", true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Risposta selectRispostaCorrettaPiuFrequenteInTutti(List<Documento> documenti) {
        return null;
    }

    @Override
    public Risposta selectRispostaCorrettaPiuFrequenteDocumento(Documento documento) {
        return null;
    }
}
