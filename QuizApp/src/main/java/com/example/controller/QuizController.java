package com.example.controller;

import com.example.models.Domanda;
import com.example.models.Quiz;
import com.example.models.Risposta;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QuizController {

    Quiz quiz;
    Map<String, Integer> conteggio ;

    public Quiz getQuiz(String difficolta) {
        if (difficolta.equals("facile")) {
            // QUERY PER PRENDERTI UN QUIZ FACILE

        }

         else if (difficolta.equals("medio")) {
            //QUERY PER PRENDERTI UN QUIZ MEDIO
        } else if (difficolta.equals("difficile")) {
            //QUERY PER PRENDERTI UN QUIZ DIFFICILE
        }

        return null ; // Restituisce il quiz ottenuto dalla query

    }

    public void setMappaturaQuiz(){
        Stream<String> parole = Arrays.stream(quiz.getTesto().split(" "));
        Map<String, Integer> conteggio = parole.collect(Collectors.groupingBy(String::toString,
                Collectors.summingInt(p->1))); //per ogni stringa somma 1 al conteggio

        Domanda [] domande = new Domanda[4]; // Supponiamo di avere quattro domande per il quiz facile
        Random random = new Random();

        List<String> chiavi = new ArrayList<>(conteggio.keySet());
        String parola = chiavi.get(random.nextInt(chiavi.size()));
        int rispostaCorretta = conteggio.get(parola);
    }
}
