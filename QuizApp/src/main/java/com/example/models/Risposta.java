package com.example.models;

import java.io.Serializable;

/**
 * Rappresenta una singola opzione di risposta per una domanda di quiz.
 * Include il testo della risposta, un flag che indica se è la risposta corretta,
 * e un flag per tenere traccia se è stata selezionata dall'utente.
 * Implementa {@link Serializable} per permettere la persistenza o la trasmissione dell'oggetto.
 */
public class Risposta implements Serializable {
    /**
     * Il serialVersionUID per la serializzazione.
     */
    private static final long serialVersionUID = 1L;

    private String testo;
    private boolean corretta;
    private boolean selected;

    /**
     * Costruisce una nuova istanza di {@code Risposta}.
     * Inizializza lo stato di selezione a {@code false}.
     *
     * @param testo Il testo della risposta.
     * @param corretta Un valore booleano che indica se questa risposta è quella corretta per la domanda.
     */
    public Risposta(String testo, boolean corretta) {
        this.testo = testo;
        this.corretta = corretta;
        this.selected = false; // Di default, una risposta non è selezionata.
    }

    /**
     * Restituisce il testo di questa risposta.
     *
     * @return Il testo della risposta.
     */
    public String getTesto() {
        return testo;
    }

    /**
     * Restituisce lo stato di correttezza di questa risposta.
     *
     * @return {@code true} se questa è la risposta corretta; {@code false} altrimenti.
     */
    public boolean getCorretta() {
        return corretta;
    }

    /**
     * Verifica se questa risposta è stata selezionata dall'utente.
     *
     * @return {@code true} se la risposta è stata selezionata; {@code false} altrimenti.
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Imposta lo stato di selezione di questa risposta.
     *
     * @param selected Il valore booleano per impostare lo stato di selezione.
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Restituisce una rappresentazione in stringa dell'oggetto {@code Risposta}.
     *
     * @return Una stringa che include il testo della risposta, il suo stato di correttezza
     * e se è stata selezionata.
     */
    @Override
    public String toString() {
        return "Risposta{" +
                "testo='" + testo + '\'' +
                ", corretta=" + corretta +
                ", selected=" + selected +
                '}';
    }
}
