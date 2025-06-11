package com.example.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Rappresenta una domanda con testo e una lista di risposte associate.
 * La classe permette di impostare le risposte in base a criteri specifici
 * relativi ai documenti forniti.
 */
public class Domanda implements Serializable {

    private String testo;

    private List<Risposta> risposte;

    private  transient  GeneratoreRisposte generatoreRisposte = new GeneratoreRisposte();

    /**
     * Costruisce una nuova domanda con testo e lista di risposte.
     *
     * @param testo Il testo della domanda.
     * @param risposte La lista di risposte associate alla domanda.
     */
    public Domanda(String testo, List<Risposta> risposte) {
        this.testo = testo;
        this.risposte = risposte;
    }

    /**
     * Restituisce il testo della domanda.
     *
     * @return Il testo della domanda.
     */
    public String getTesto() {
        return testo;
    }

    /**
     * Imposta il testo della domanda.
     *
     * @param testo Il testo da impostare.
     */
    public void setTesto(String testo) {

        this.testo = testo;
    }

    /**
     * Restituisce la lista delle risposte associate alla domanda.
     *
     * @return La lista delle risposte.
     */
    public  List<Risposta> getRisposte() {
        return risposte;
    }

    /**
     * Imposta la lista delle risposte basandosi sulla frequenza di una parola
     * specifica all'interno di un documento.
     *
     * @param documento Il documento su cui basare la generazione delle risposte.
     * @param parola La parola di cui calcolare la frequenza.
     */
    public void setRisposteQuanteVolteSiPresentaInUnTesto(Documento documento , String parola) {
        risposte = generatoreRisposte.RipetizioneParolaDocumento(documento, parola);
    }

    /**
     * Imposta la lista delle risposte basandosi sulle parole più frequenti
     * all'interno di un documento.
     *
     * @param documento Il documento su cui basare la generazione delle risposte.
     */
    public void setRispostePiuFrequenteNelTesto(Documento documento) {
            risposte = generatoreRisposte.PiuFrequenteDocumento(documento);
    }

    /**
     * Imposta la lista delle risposte basandosi sulle parole più frequenti
     * tra una lista di documenti.
     *
     * @param docs La lista dei documenti su cui basare la generazione delle risposte.
     */
    public void setRispostePiuFrequenteInTutti(List<Documento> docs){
        this.risposte = generatoreRisposte.PiuFrequenteInTutti(docs);

    }

    /**
     * Imposta la lista delle risposte basandosi sulle parole mai presenti
     * in una lista di documenti.
     *
     * @param docs La lista dei documenti su cui basare la generazione delle risposte.
     */
    public void setRisposteMaiPresente(List<Documento> docs){
        risposte = generatoreRisposte.NonPresente(docs);
    }

    /**
     * Restituisce una rappresentazione in stringa della domanda.
     *
     * @return Una stringa che rappresenta la domanda.
     */
    @Override
    public String toString() {
        return "Domanda{" +
                "testo='" + testo + '\'' +
                ", risposte=" + risposte +
                '}';
    }
}






