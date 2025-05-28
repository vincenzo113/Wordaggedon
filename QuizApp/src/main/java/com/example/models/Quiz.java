package com.example.models;

public class Quiz {
    private String testoQuiz;
    private Domanda[] domande;
    private String difficolta;/**/

    public Quiz(String titolo, Domanda[] domande, String difficolta) {
        this.testoQuiz = titolo;
        this.domande = domande;
        this.difficolta = difficolta;
    }

    public String getDifficolta(){
        return difficolta;
    }
    public String getTitolo() {
        return testoQuiz;
    }

    public void setTitolo(String titolo) {
        this.testoQuiz = titolo;
    }

    public Domanda[] getDomande() {
        return domande;
    }

    public void setDomande(Domanda[] domande) {
        this.domande = domande;
    }
}
