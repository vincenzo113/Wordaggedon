package com.example.dao.Domande;

import com.example.models.Documento;
import com.example.models.Domanda;

import java.sql.*;
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
        //Prendiamo il contenuto del documento passato e selezioniamo una parola a caso
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()){
            String query = String.format("select contenuto from documento where id = '%s'" , documento.getId());
            ResultSet resultSet = stmt.executeQuery(query);
            String content = resultSet.getString("contenuto");
            //Divido il contenuto del testo appositamente
            String [] parole = content.split("\\W+");
            //Scorro tutte le parole del testo e prendo solo quella che soddisfa le condizioni imposte(stopwords...)
            for(String parola : parole) {
                int indiceRandom = (int) (Math.random() * parole.length);
                //Se la parola è accettabile , ovvero non è una stopword , allora la restituiamo , altrimenti facciamo un altro giro
                //La condizione va cambiata con il check delle stopwords
                if(parole[indiceRandom].length() >= 4) return parole[indiceRandom];
            }

        }catch(SQLException sqlException){

        }
        return "";
    }
}
