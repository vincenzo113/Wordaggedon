package com.example.models;

import com.example.dao.QuizDAOPostgres;
import com.example.difficultySettings.DifficultyEnum;

import java.util.Random;

public class Quiz {
    private Documento[] documenti;
    private Domanda[] domande;
    private DifficultyEnum difficolta;/**/
    private QuizDAOPostgres quizDAOPostgres;

    public Quiz( Documento[] documenti , Domanda[] domande, DifficultyEnum difficolta ) {
        this.documenti = documenti ;
        this.domande = domande;
        this.difficolta = difficolta;
    }
    public Documento[] getTesti() {
        return documenti;
    }
    public void setTesti(Documento[] testi) {
        this.documenti = testi;
    }
    public DifficultyEnum getDifficolta(){
        return difficolta;
    }

    public Domanda[] getDomande() {
        return domande;
    }

    public void generaDomande() {
        //PRELIEVO DI DOMANDE BASE DAL DATABASE

        domande = quizDAOPostgres.selectDomande(); // Esegui la query per ottenere le domande

        for(int i=0;i<domande.length;i++) {

            //FARE ENUM PER DISTINGUERE LE DOMANDE
            if (domande[i].getTesto().equals("Quante volte si ripete una parola")) {
                String parolaDaCercare = null; //QUERY PER CERCARE UNA PAROLA A CASO NEI TESTI
                Random random = new Random();
                int indiceTesto = random.nextInt(documenti.length);
                domande[i].setTesto("Quante volte si ripete la parola " + parolaDaCercare + " nel testo del quiz " + random + 1 + "?");
                domande[i].setRisposte(documenti,parolaDaCercare,indiceTesto); // Imposta le risposte per questa domanda
                //GLI DOVRESTI PASSARE LA MAPPAZIONE DELLE PAROLE E IL CONTEGGIO
            };


            if (domande[i].getTesto().equals("Qual è la parola più frequente nel quiz ?")) {
                Random random = new Random();
                int indiceTesto = random.nextInt(documenti.length);
                domande[i].setTesto("Qual è la parola più frequente nel testo del quiz " + random + 1 + "?");
                domande[i].setRisposte(documenti,indiceTesto); // Imposta le risposte per questa domanda
                //PASSEREI ID TESTO A SET RISPOSTE PER PERMETTERGLI DI FARE UNA QUERY SULLA MAPPATURA DELLE PAROLE
            }

            if (domande[i].getTesto().equals("Qual è la parola più frequente in tutti i documenti")) {

                domande[i].setTesto("Qual è la parola più frequente in tutti i documenti ?");
                domande[i].setRisposte(documenti); // Imposta le risposte per questa domanda
                //PASSEREI ID TESTO A SET RISPOSTE PER PERMETTERGLI DI FARE UNA QUERY SULLA MAPPATURA DELLE PAROLE
            }

            if (domande[i].getTesto().equals("Quale parola non appare mai in nessun documento")) {

                domande[i].setTesto("Quale parola non appare mai in nessun documento?");
                domande[i].setRisposte(documenti); // Imposta le risposte per questa domanda
                //PASSEREI ID TESTO A SET RISPOSTE PER PERMETTERGLI DI FARE UNA QUERY SULLA MAPPATURA DELLE PAROLE
            }

        }

    }
}
