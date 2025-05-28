package com.example.models;

public class Domanda {
    private String testo;
    private Risposta[] risposte;

    public Domanda(String testo, Risposta[] risposte) {
        this.testo = testo;
        this.risposte = risposte;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public Risposta[] getRisposte() {
        return risposte;
    }

    public void setRisposte(Risposta[] risposte) {
        this.risposte = risposte;
    }
}
