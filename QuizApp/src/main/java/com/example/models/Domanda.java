package com.example.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Rappresenta una singola domanda all'interno di un quiz, inclusivo del testo della domanda
 * e delle opzioni di risposta. Implementa {@link Serializable} per permettere la persistenza
 * o la trasmissione dell'oggetto.
 */
public class Domanda implements Serializable {
    /**
     * Il serialVersionUID per la serializzazione.
     */
    private static final long serialVersionUID = 1L;

    private String testo;
    private List<Risposta> risposte;

    // Il campo generatoreRisposte è transient per evitare che venga serializzato.
    // Viene creato un nuovo GeneratoreRisposte quando l'oggetto viene deserializzato.
    private transient GeneratoreRisposte generatoreRisposte = new GeneratoreRisposte();

    /**
     * Costruisce un nuovo oggetto {@code Domanda} con il testo e una lista di risposte predefinite.
     *
     * @param testo Il testo della domanda.
     * @param risposte Una {@link List} di oggetti {@link Risposta} che rappresentano le opzioni per questa domanda.
     */
    public Domanda(String testo, List<Risposta> risposte) {
        this.testo = testo;
        this.risposte = risposte;
    }

    /**
     * Restituisce il testo della domanda.
     * @return Il testo della domanda.
     */
    public String getTesto() {
        return testo;
    }

    /**
     * Imposta il testo della domanda.
     * @param testo Il nuovo testo della domanda.
     */
    public void setTesto(String testo) {
        this.testo = testo;
    }

    /**
     * Restituisce la lista delle risposte associate a questa domanda.
     * @return Una {@link List} di oggetti {@link Risposta}.
     */
    public List<Risposta> getRisposte() {
        return risposte;
    }

    /**
     * Imposta le risposte per la domanda basandosi su quante volte una specifica parola
     * si presenta all'interno di un dato documento. Questo metodo utilizza
     * {@link GeneratoreRisposte#RipetizioneParolaDocumento(Documento, String)} per generare le risposte.
     *
     * @param documento Il {@link Documento} su cui basare la ricerca della parola.
     * @param parola La parola da cercare e contare nel documento.
     */
    public void setRisposteQuanteVolteSiPresentaInUnTesto(Documento documento , String parola) {
        risposte = generatoreRisposte.RipetizioneParolaDocumento(documento, parola);
    }

    /**
     * Imposta le risposte per la domanda basandosi sulla parola più frequente
     * all'interno di un dato documento. Questo metodo utilizza
     * {@link GeneratoreRisposte#PiuFrequenteDocumento(Documento)} per generare le risposte.
     *
     * @param documento Il {@link Documento} su cui basare la ricerca della parola più frequente.
     */
    public void setRispostePiuFrequenteNelTesto(Documento documento) {
        risposte = generatoreRisposte.PiuFrequenteDocumento(documento);
    }

    /**
     * Imposta le risposte per la domanda basandosi sulla parola più frequente
     * tra tutti i documenti forniti. Questo metodo utilizza
     * {@link GeneratoreRisposte#PiuFrequenteInTutti(List)} per generare le risposte.
     *
     * @param docs Una {@link List} di oggetti {@link Documento} su cui basare la ricerca della parola più frequente.
     */
    public void setRispostePiuFrequenteInTutti(List<Documento> docs){
        this.risposte = generatoreRisposte.PiuFrequenteInTutti(docs);
    }

    /**
     * Imposta le risposte per la domanda basandosi su una parola che non è mai presente
     * in nessuno dei documenti forniti. Questo metodo utilizza
     * {@link GeneratoreRisposte#NonPresente(List)} per generare le risposte.
     *
     * @param docs Una {@link List} di oggetti {@link Documento} da cui verificare l'assenza della parola.
     */
    public void setRisposteMaiPresente(List<Documento> docs){
        risposte = generatoreRisposte.NonPresente(docs);
    }

    /**
     * Restituisce una rappresentazione in stringa dell'oggetto {@code Domanda}.
     *
     * @return Una stringa che include il testo della domanda e la lista delle risposte.
     */
    @Override
    public String toString() {
        return "Domanda{" +
                "testo='" + testo + '\'' +
                ", risposte=" + risposte +
                '}';
    }
}





