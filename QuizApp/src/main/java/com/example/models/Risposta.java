package com.example.models;

public class Risposta {
    private String testo;
    private boolean corretta;
    private boolean selected;

    public Risposta(String testo, boolean corretta) {
        this.testo = testo;
        this.corretta = corretta;
        this.selected = false;
    }

    public String getTesto() {
        return testo;
    }
    public boolean getCorretta() {
        return corretta;
    }
    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "Risposta{" +
                "testo='" + testo + '\'' +
                ", corretta=" + corretta +
                ", selected=" + selected +
                '}';
    }
}
