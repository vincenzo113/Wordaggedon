package com.example.dao.Domande;

import com.example.models.Documento;
import com.example.models.Domanda;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DomandeDAOPostgres implements DomandeDAO {
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

    @Override
    public String selectParolaCasuale(Documento documento) {
        //Prendiamo il contenuto del documento passato e selezioniamo una parola a caso
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
            if (resultSet.next()) return resultSet.getString("valore");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "";

    }
}
