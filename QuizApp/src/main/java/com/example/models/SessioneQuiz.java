package com.example.models;

import com.example.dao.Domande.DomandeDAOPostgres;
import com.example.difficultySettings.DifficultyEnum;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

/**
 * Rappresenta una singola sessione di quiz per un utente.
 * Contiene i documenti utilizzati, le domande generate, il livello di difficoltà, l'utente che partecipa,
 * il punteggio ottenuto e un flag che indica se la sessione è stata completata.
 * Implementa {@link Serializable} per consentire il salvataggio e il recupero dello stato della sessione.
 */
public class SessioneQuiz implements Serializable {
    /**
     * Il serialVersionUID per la serializzazione.
     */
    private static final long serialVersionUID = 1L;

    private List<Documento> documenti;
    private List<Domanda> domande;
    private DifficultyEnum difficolta;
    private User user;
    private int score;

    // Il campo domandeDAOPostgres è transient perché non è parte dello stato persistente della sessione,
    // ma un'interfaccia di accesso al database che viene re-iniettata o creata al bisogno.
    private transient DomandeDAOPostgres domandeDAOPostgres;
    private boolean isCompleta;

    /**
     * Costruisce una nuova sessione di quiz.
     * Inizializza il punteggio a 0 e la lista delle domande a null.
     *
     * @param documenti La lista di {@link Documento} da cui verranno generate le domande del quiz.
     * @param difficolta Il livello di {@link DifficultyEnum} per questa sessione.
     * @param user L'{@link User} che partecipa a questa sessione di quiz.
     */
    public SessioneQuiz(List<Documento> documenti, DifficultyEnum difficolta, User user) {
        this.documenti = documenti;
        this.difficolta = difficolta;
        this.user = user;
        this.score = 0; // Il punteggio viene inizializzato a 0 all'inizio della sessione.
        this.domande = null; // Le domande verranno generate successivamente.
        this.isCompleta = false; // La sessione non è completa all'inizio.
    }

    /**
     * Costruisce una sessione di quiz con un utente, difficoltà e punteggio già definiti.
     * Questo costruttore è utile per la ricostruzione di sessioni, ad esempio per visualizzare classifiche.
     *
     * @param user L'{@link User} associato alla sessione.
     * @param difficolta Il livello di {@link DifficultyEnum} della sessione.
     * @param score Il punteggio ottenuto in questa sessione.
     */
    public SessioneQuiz(User user, DifficultyEnum difficolta, int score) {
        this.difficolta = difficolta;
        this.user = user;
        this.score = score;
        this.documenti = null; // I documenti non sono necessari per rappresentare una sessione completata.
        this.domande = null; // Le domande non sono necessarie per rappresentare una sessione completata.
        this.isCompleta = true; // Si presume sia completa se si ricostruisce da un punteggio.
    }

    /**
     * Restituisce la lista dei documenti utilizzati in questa sessione di quiz.
     *
     * @return Una {@link List} di oggetti {@link Documento}.
     */
    public List<Documento> getDocumenti() {
        return documenti;
    }

    /**
     * Restituisce la lista delle domande generate per questa sessione di quiz.
     *
     * @return Una {@link List} di oggetti {@link Domanda}.
     */
    public List<Domanda> getDomande() {
        return domande;
    }

    /**
     * Restituisce il livello di difficoltà di questa sessione.
     *
     * @return Il {@link DifficultyEnum} della sessione.
     */
    public DifficultyEnum getDifficolta() {
        return difficolta;
    }

    /**
     * Restituisce l'utente che partecipa a questa sessione di quiz.
     *
     * @return L'{@link User} della sessione.
     */
    public User getUser() {
        return user;
    }

    /**
     * Restituisce il punteggio corrente di questa sessione.
     *
     * @return Il punteggio intero.
     */
    public int getScore() {
        return score;
    }

    /**
     * Imposta il punteggio di questa sessione.
     *
     * @param score Il nuovo punteggio.
     */
    public void setScore(int score) { this.score = score; }

    /**
     * Genera le domande per questa sessione di quiz basandosi sui documenti disponibili
     * e sui tipi di domanda predefiniti.
     * Questo metodo interagisce con {@link DomandeDAOPostgres} per recuperare i modelli di domanda
     * e con {@link GeneratoreRisposte} (tramite {@link Domanda}) per popolare le risposte.
     *
     * È fondamentale che {@link #setDomandeDAOPostgres(DomandeDAOPostgres)} sia stato chiamato prima
     * di invocare questo metodo, altrimenti {@code domandeDAOPostgres} sarà {@code null} e causerà un {@link NullPointerException}.
     */
    public void generaDomande() {
        // È importante che domandeDAOPostgres sia stato inizializzato tramite setDomandeDAOPostgres
        // prima di chiamare questo metodo.
        if (domandeDAOPostgres == null) {
            // Considera di lanciare un'eccezione o di inizializzare qui se appropriato per il tuo design.
            System.err.println("Errore: DomandeDAOPostgres non è stato inizializzato nella sessione quiz.");
            return;
        }

        domande = domandeDAOPostgres.selectDomande(); // Preleva i modelli di domanda dal database.
        Random random = new Random();

        for (Domanda domanda : domande) {
            switch (domanda.getTesto()) {
                case "Quante volte si ripete una parola":
                {
                    int indiceTesto = random.nextInt(documenti.size());
                    Documento docCorrente = documenti.get(indiceTesto);
                    String parolaDaCercare = domandeDAOPostgres.selectParolaCasuale(docCorrente);
                    domanda.setTesto("Quante volte si ripete la parola " + parolaDaCercare + " nel testo " + docCorrente.getTitolo() + "?");
                    domanda.setRisposteQuanteVolteSiPresentaInUnTesto(docCorrente, parolaDaCercare);
                    break;
                }
                case "Qual è la parola più frequente nel quiz":
                {
                    int indiceTesto = random.nextInt(documenti.size());
                    Documento docCorrente = documenti.get(indiceTesto);
                    domanda.setTesto("Qual è la parola più frequente nel testo " + docCorrente.getTitolo() + "?");
                    domanda.setRispostePiuFrequenteNelTesto(docCorrente);
                    break;
                }
                case "Qual è la parola più frequente in tutti i documenti":
                {
                    domanda.setTesto("Qual è la parola più frequente in tutti i documenti?");
                    domanda.setRispostePiuFrequenteInTutti(documenti);
                    break;
                }
                case "Quale parola non appare mai in nessun documento":
                {
                    domanda.setTesto("Quale parola non appare mai in nessun documento?");
                    domanda.setRisposteMaiPresente(documenti);
                    break;
                }
                // È buona pratica includere un default o gestire tipi di domanda sconosciuti.
                default:
                    System.out.println("Tipo di domanda non riconosciuto: " + domanda.getTesto());
                    break;
            }
        }
    }

    /**
     * Restituisce una rappresentazione in stringa dell'oggetto {@code SessioneQuiz}.
     *
     * @return Una stringa che include i documenti, le domande, la difficoltà, l'utente,
     * il punteggio e un riferimento all'istanza di {@code domandeDAOPostgres}.
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
                ", isCompleta=" + isCompleta +
                '}';
    }

    /**
     * Imposta l'istanza di {@link DomandeDAOPostgres} per questa sessione.
     * Questo metodo è cruciale per l'inizializzazione del DAO dopo la deserializzazione dell'oggetto
     * o per l'iniezione delle dipendenze.
     *
     * @param daoPostgres L'istanza di {@link DomandeDAOPostgres} da utilizzare.
     */
    public void setDomandeDAOPostgres(DomandeDAOPostgres daoPostgres){
        this.domandeDAOPostgres = daoPostgres;
    }

    /**
     * Imposta lo stato di completamento della sessione.
     *
     * @param value {@code true} se la sessione è completa, {@code false} altrimenti.
     */
    public void setIsCompleta(Boolean value){
        this.isCompleta = value;
    }

    /**
     * Restituisce lo stato di completamento della sessione.
     *
     * @return {@code true} se la sessione è completa, {@code false} altrimenti.
     */
    public boolean getIsCompleta(){
        return  this.isCompleta;
    }
}