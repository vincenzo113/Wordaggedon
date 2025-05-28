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
        setDomande();
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

    public void setDomande() {
        //PRELIEVO DI DOMANDE BASE DAL DATABASE

        domande = new Domanda[]{
                new Domanda("Quante volte si ripete una parola",new Risposta[4]),
                new Domanda("Qual è la parola più frequente",new Risposta[4]),
                new Domanda("Qual è la parola più frequente in tutti i documenti",new Risposta[4]),
                new Domanda("Quale parola non appare mai in nessun documento", new Risposta[4])
        }; //DA ELIMINARE E SOSTITUIRE CON PRELIEVO DI DOMANDE CASUALI DAL DATABASE


        for(int i=0;i<domande.length;i++) {

            if (domande[i].getTesto().equals("Quante volte si ripete una parola")) {
                String parolaDaCercare = null; //QUERY PER CERCARE UNA PAROLA A CASO NEL TESTO DEL QUIZ
                domande[i].setTesto("Quante volte si ripete la parola " + parolaDaCercare + " nel testo del quiz " + i + "?");
                domande[i].setRisposte(); // Imposta le risposte per questa domanda
                //GLI DOVRESTI PASSARE LA MAPPAZIONE DELLE PAROLE E IL CONTEGGIO
            };


            if (domande[i].getTesto().equals("Qual è la parola più frequente nel quiz" + i + "?")) {
                String parolaDaCercare = null; //QUERY PER CERCARE LA PAROLA PIU' FREQUENTE NEL TESTO DEL QUIZ
                domande[i].setTesto("Qual è la parola più frequente nel testo del quiz " + i + "?");
                domande[i].setRisposte(); // Imposta le risposte per questa domanda
            }

            if (domande[i].getTesto().equals("Qual è la parola più frequente in tutti i documenti")) {
                String parolaDaCercare = null; //QUERY PER CERCARE LA PAROLA PIU' FREQUENTE NEL TESTO DEL QUIZ
                domande[i].setTesto("Qual è la parola più frequente in tutti i documenti ?");
                domande[i].setRisposte(); // Imposta le risposte per questa domanda
            }

            if (domande[i].getTesto().equals("Quale parola non appare mai in nessun documento")) {
                String parolaDaCercare = null; //QUERY PER CERCARE LA PAROLA PIU' FREQUENTE NEL TESTO DEL QUIZ
                domande[i].setTesto("Quale parola non appare mai in nessun documento?");
                domande[i].setRisposte(); // Imposta le risposte per questa domanda
            }

        }

    }
}
