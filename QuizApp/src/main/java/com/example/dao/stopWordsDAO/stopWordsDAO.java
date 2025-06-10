package com.example.dao.stopWordsDAO;

import com.example.dao.ConnectionConfig;

import java.util.List;
import java.util.Set;

public interface stopWordsDAO extends ConnectionConfig {
    void inserisciStopWords(List<String> stopWords);
    Set<String>  getStopWords();


}
