package com.example.dao.Domande;

import com.example.models.Documento;
import com.example.models.Domanda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DomandeDAOPostgres implements DomandeDAO{
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
        return "";
    }
}
