package com.example.models;

import java.io.Serializable;


/**
 * Rappresenta una risposta a una domanda nel quiz.
 * Contiene il testo della risposta, se è corretta e se è stata selezionata dall'utente.
 */
public class Risposta implements Serializable {
    private String testo;
    private boolean corretta;
    private boolean selected;

    /**
     * Costruisce una nuova risposta con il testo e lo stato di correttezza.
     *
     * @param testo Il testo della risposta.
     * @param corretta Indica se la risposta è corretta.
     */
    public Risposta(String testo, boolean corretta) {
        this.testo = testo;
        this.corretta = corretta;
        this.selected = false;
    }

    /**
     * Restituisce il testo della risposta.
     *
     * @return Il testo della risposta.
     */
    public String getTesto() {
        return testo;
    }

    /**
     * Indica se la risposta è corretta.
     *
     * @return true se la risposta è corretta, false altrimenti.
     */
    public boolean isCorretta() {
        return corretta;
    }

    /**
     * Indica se la risposta è stata selezionata dall'utente.
     *
     * @return true se selezionata, false altrimenti.
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Imposta lo stato di selezione della risposta.
     *
     * @param selected true per selezionare la risposta, false per deselezionarla.
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    /**
     * Restituisce una rappresentazione in stringa della risposta.
     *
     * @return Una stringa che rappresenta la risposta.
     */
    public String toString() {
        return "Risposta{" +
                "testo='" + testo + '\'' +
                ", corretta=" + corretta +
                ", selected=" + selected +
                '}';
    }
}
