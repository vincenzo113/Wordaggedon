package com.example.models;

import java.util.Random;

public class Quiz {
    private String[] testi;
    private Domanda[] domande;
    private String difficolta;/**/
    private String titolo;

    public Quiz(String titolo, String[] testi , Domanda[] domande, String difficolta ) {
        this.titolo = titolo;
        this.testi = testi ;
        this.domande = domande;
        this.difficolta = difficolta;
        setDomande();
    }
    public String[] getTesti() {
        return testi;
    }
    public void setTesti(String[] testi) {
        this.testi = testi;
    }
    public String getDifficolta(){
        return difficolta;
    }
    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
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
                Random random = new Random();
                int indiceTesto = random.nextInt(testi.length);
                domande[i].setTesto("Quante volte si ripete la parola " + parolaDaCercare + " nel testo del quiz " + random + 1 + "?");
                domande[i].setRisposte(); // Imposta le risposte per questa domanda
                //GLI DOVRESTI PASSARE LA MAPPAZIONE DELLE PAROLE E IL CONTEGGIO
            };


            if (domande[i].getTesto().equals("Qual è la parola più frequente nel quiz ?")) {
                Random random = new Random();
                int indiceTesto = random.nextInt(testi.length);
                domande[i].setTesto("Qual è la parola più frequente nel testo del quiz " + random + 1 + "?");
                domande[i].setRisposte(); // Imposta le risposte per questa domanda
                //PASSEREI ID TESTO A SET RISPOSTE PER PERMETTERGLI DI FARE UNA QUERY SULLA MAPPATURA DELLE PAROLE
            }

            if (domande[i].getTesto().equals("Qual è la parola più frequente in tutti i documenti")) {

                domande[i].setTesto("Qual è la parola più frequente in tutti i documenti ?");
                domande[i].setRisposte(); // Imposta le risposte per questa domanda
                //PASSEREI ID TESTO A SET RISPOSTE PER PERMETTERGLI DI FARE UNA QUERY SULLA MAPPATURA DELLE PAROLE
            }

            if (domande[i].getTesto().equals("Quale parola non appare mai in nessun documento")) {

                domande[i].setTesto("Quale parola non appare mai in nessun documento?");
                domande[i].setRisposte(); // Imposta le risposte per questa domanda
                //PASSEREI ID TESTO A SET RISPOSTE PER PERMETTERGLI DI FARE UNA QUERY SULLA MAPPATURA DELLE PAROLE
            }

        }

    }
}
