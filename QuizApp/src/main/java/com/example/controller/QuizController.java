package com.example.controller;

import com.example.models.Quiz;

public class QuizController {

    Quiz quiz;


    public Quiz getQuiz(String difficolta) {
        if (difficolta.equals("facile")) {
            // QUERY PER PRENDERTI UN QUIZ FACILE
        } else if (difficolta.equals("medio")) {
            //QUERY PER PRENDERTI UN QUIZ MEDIO
        } else if (difficolta.equals("difficile")) {
            //QUERY PER PRENDERTI UN QUIZ DIFFICILE
        }

    }
}
