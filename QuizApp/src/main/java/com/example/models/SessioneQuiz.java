package com.example.models;

import com.example.dao.Domande.DomandeDAOPostgres;
import com.example.difficultySettings.DifficultyEnum;

import java.util.List;
import java.util.Random;

public class SessioneQuiz {
        private List<Documento> documenti;
        private List<Domanda>  domande ;
        private DifficultyEnum difficolta;
        private User user;
        private int score;
        private DomandeDAOPostgres domandeDAOPostgres = new DomandeDAOPostgres();

        public SessioneQuiz(List<Documento> documenti, DifficultyEnum difficolta, User user) {
            this.documenti = documenti;
            this.difficolta = difficolta;
            this.user = user;
            this.score = 0; // Inizializza il punteggio a 0
            this.domande = null; // Inizializza la lista delle domande a null
        }

        public SessioneQuiz(User user, DifficultyEnum difficolta, int score) {
        this.difficolta = difficolta;
        this.user = user;
        this.score = score;
        }


        public List<Documento> getDocumenti() {
            return documenti;
        }

        public List<Domanda> getDomande() {
            return domande;
        }

        public DifficultyEnum getDifficolta() {
            return difficolta;
        }

        public User getUser() {
            return user;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) { this.score = score; }


        public void generaDomande() {
            //PRELIEVO DI DOMANDE BASE DAL DATABASE

            domande = domandeDAOPostgres.selectDomande(); // Esegui la query per ottenere le domande

            /*
            Random random = new Random();
            for(int i=0;i<domande.size();i++) {

                //FARE ENUM PER DISTINGUERE LE DOMANDE
                if (domande.get(i).getTesto().equals("Quante volte si ripete una parola")) {


                    int indiceTesto = random.nextInt(documenti.size());
                    String parolaDaCercare = domandeDAOPostgres.selectParolaCasuale(documenti.get(indiceTesto));
                    domande.get(i).setTesto("Quante volte si ripete la parola " + parolaDaCercare + " nel testo del quiz " + random + 1 + "?");
                    domande.get(i).setRisposte(documenti.get(indiceTesto),parolaDaCercare);
                };


                if (domande.get(i).getTesto().equals("Qual è la parola più frequente nel quiz")) {

                    int indiceTesto = random.nextInt(documenti.size());
                    domande.get(i).setTesto("Qual è la parola più frequente nel testo del quiz " + random + 1 + "?");
                    domande.get(i).setRisposte(documenti.get(indiceTesto));
                }

                if (domande.get(i).getTesto().equals("Qual è la parola più frequente in tutti i documenti")) {

                    domande.get(i).setTesto("Qual è la parola più frequente in tutti i documenti ?");
                    domande.get(i).setRisposte(documenti);
                }

                if (domande.get(i).getTesto().equals("Quale parola non appare mai in nessun documento")) {
                    domande.get(i).setTesto("Quale parola non appare mai in nessun documento?");
                    domande.get(i).setRisposte(documenti);
                }
            }*/



        }


}
