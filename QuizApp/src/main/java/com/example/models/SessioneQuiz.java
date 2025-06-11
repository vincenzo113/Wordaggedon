package com.example.models;

import com.example.dao.Domande.DomandeDAOPostgres;
import com.example.difficultySettings.DifficultyEnum;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

/**
 * Rappresenta una sessione di quiz contenente documenti, domande, difficoltà, utente e punteggio.
 * Gestisce la generazione delle domande basate sui documenti e la difficoltà selezionata.
 * Implementa Serializable per permettere il salvataggio dello stato della sessione.
 */
//Implementiamo serializable per permettere il salvataggio della sessione di gioco
public class SessioneQuiz implements Serializable {
        private List<Documento> documenti;
        private List<Domanda>  domande ;
        private DifficultyEnum difficolta;
        private User user;
        private int score;
        private transient DomandeDAOPostgres domandeDAOPostgres ;
        private boolean isCompleta;

    private static final long serialVersionUID = 1L;

        /**
         * Costruisce una nuova sessione di quiz con i documenti, la difficoltà e l'utente specificati.
         *
         * @param documenti La lista di documenti da utilizzare nel quiz.
         * @param difficolta Il livello di difficoltà della sessione.
         * @param user L'utente associato alla sessione.
         */
        public SessioneQuiz(List<Documento> documenti, DifficultyEnum difficolta, User user) {
            this.documenti = documenti;
            this.difficolta = difficolta;
            this.user = user;
        }

        /**
         * Costruisce una nuova sessione di quiz con utente, difficoltà e punteggio.
         *
         * @param user L'utente associato alla sessione.
         * @param difficolta Il livello di difficoltà della sessione.
         * @param score Il punteggio corrente della sessione.
         */
        public SessioneQuiz(User user, DifficultyEnum difficolta, int score) {
        this.difficolta = difficolta;
        this.user = user;
        this.score = score;
        }


        /**
         * Restituisce la lista dei documenti della sessione.
         *
         * @return La lista di documenti.
         */
        public List<Documento> getDocumenti() {
            return documenti;
        }

        /**
         * Restituisce la lista delle domande della sessione.
         *
         * @return La lista di domande.
         */
        public List<Domanda> getDomande() {
            return domande;
        }

        /**
         * Restituisce il livello di difficoltà della sessione.
         *
         * @return La difficoltà selezionata.
         */
        public DifficultyEnum getDifficolta() {
            return difficolta;
        }

        /**
         * Restituisce l'utente associato alla sessione.
         *
         * @return L'utente della sessione.
         */
        public User getUser() {
            return user;
        }

        /**
         * Restituisce il punteggio corrente della sessione.
         *
         * @return Il punteggio.
         */
        public int getScore() {
            return score;
        }

        /**
         * Imposta il punteggio della sessione.
         *
         * @param score Il punteggio da impostare.
         */
        public void setScore(int score) { this.score = score; }


        /**
         * Genera le domande per la sessione basate sui documenti e sul tipo di domanda.
         * Modifica il testo delle domande e imposta le risposte in base ai documenti.
         */
        public void generaDomande() {
            //PRELIEVO DI DOMANDE BASE DAL DATABASE

            domande = domandeDAOPostgres.selectDomande(); // Esegui la query per ottenere le domande

            Random random = new Random();
            for (Domanda domanda : domande) {

                //FARE ENUM PER DISTINGUERE LE DOMANDE
                if (domanda.getTesto().equals("Quante volte si ripete una parola")) {


                    int indiceTesto = random.nextInt(documenti.size());
                    String parolaDaCercare = domandeDAOPostgres.selectParolaCasuale(documenti.get(indiceTesto));
                    domanda.setTesto("Quante volte si ripete la parola " + parolaDaCercare + " nel testo  " + documenti.get(indiceTesto).getTitolo() + "?");
                    domanda.setRisposteQuanteVolteSiPresentaInUnTesto(documenti.get(indiceTesto), parolaDaCercare);
                }
                ;


                if (domanda.getTesto().equals("Qual è la parola più frequente nel quiz")) {

                    int indiceTesto = random.nextInt(documenti.size());
                    domanda.setTesto("Qual è la parola più frequente nel testo  " + documenti.get(indiceTesto).getTitolo() + "?");
                    domanda.setRispostePiuFrequenteNelTesto(documenti.get(indiceTesto));

                }

                if (domanda.getTesto().equals("Qual è la parola più frequente in tutti i documenti")) {

                    domanda.setTesto("Qual è la parola più frequente in tutti i documenti ?");
                    domanda.setRispostePiuFrequenteInTutti(documenti);

                }

                if (domanda.getTesto().equals("Quale parola non appare mai in nessun documento")) {
                    domanda.setTesto("Quale parola non appare mai in nessun documento?");
                    domanda.setRisposteMaiPresente(documenti);

                }
            }






        }

    /**
     * Restituisce una rappresentazione in stringa della sessione di quiz.
     *
     * @return Una stringa che rappresenta la sessione.
     */
    @Override
    public String toString() {
        return "SessioneQuiz{" +
                "documenti=" + documenti +
                ", domande=" + domande +
                ", difficolta=" + difficolta +
                ", user=" + user +
                ", score=" + score +
                ", domandeDAOPostgres=" + domandeDAOPostgres +
                '}';
    }


    /**
     * Imposta il DAO per l'accesso alle domande nel database Postgres.
     *
     * @param daoPostgres Il DAO da utilizzare.
     */
    public void setDomandeDAOPostgres(DomandeDAOPostgres daoPostgres){
            this.domandeDAOPostgres = daoPostgres;
    }


    /**
     * Imposta lo stato di completamento della sessione.
     *
     * @param value true se la sessione è completa, false altrimenti.
     */
    public void setIsCompleta(Boolean value){
            this.isCompleta = value;
    }

    /**
     * Indica se la sessione è stata completata.
     *
     * @return true se completa, false altrimenti.
     */
    public boolean isCompleta(){
            return  this.isCompleta;
    }
}
