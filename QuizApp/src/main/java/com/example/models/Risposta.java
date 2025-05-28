package com.example.models;

public class Risposta {

    private String testo;
    private boolean corretta;

    public Risposta(String testo, boolean corretta) {
        this.testo = testo;
        this.corretta = corretta;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public boolean isCorretta() {
        return corretta;
    }

    public void setCorretta(boolean corretta) {
        this.corretta = corretta;
    }
}
