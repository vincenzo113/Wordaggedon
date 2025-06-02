package com.example.dao.stopWordsDAO;

import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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



    @Override
    public Set<String> getStopWords(){
        Set<String> allStopWords = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()){
            String query = "select parola from stopwords";
            ResultSet resultSet = stmt.executeQuery(query);
            while(resultSet.next()){
                allStopWords.add(resultSet.getString("parola"));
            }
        }catch (SQLException ex){}
        return allStopWords;
    }
}
