package com.example.models;

public class Quiz {
    private String testoQuiz;
    private Domanda[] domande;
    private String difficolta;/**/
    private String testo;

    public Quiz(String titolo, String testo , Domanda[] domande, String difficolta ) {
        this.testoQuiz = titolo;
        this.testo = testo ;
        this.domande = domande;
        this.difficolta = difficolta;
    }
    public String getTesto() {
        return testo;
    }
    public void setTesto(String testo) {
        this.testo = testo;
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
