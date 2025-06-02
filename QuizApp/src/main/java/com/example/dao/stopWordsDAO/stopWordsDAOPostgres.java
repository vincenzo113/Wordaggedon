package com.example.dao.stopWordsDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class stopWordsDAOPostgres implements stopWordsDAO{

    @Override
    public void inserisciStopWords(List<String> stopWords){
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            //Per ogni stopWord dobbiamo fare l'inserimento
            String query = "";
            for(String stopWord : stopWords){
                query = String.format("INSERT INTO stopwords(parola) values('%s')",stopWord);
                stmt.executeUpdate(query);
            }


        }catch(SQLException ex){}
    }
}
