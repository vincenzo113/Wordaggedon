package com.example.models;

public class Documento {

    private int id;
    private String titolo;
    private String contenuto;
    private String difficolta;

    public Documento(int id, String titolo, String contenuto, String difficolta) {
        this.id = id;
        this.titolo = titolo;
        this.contenuto = contenuto;
        this.difficolta = difficolta;
    }

    // Getter e setter
    public int getId() { return id; }
    public String getTitolo() { return titolo; }
    public String getContenuto() { return contenuto; }
    public String getDifficolta() { return difficolta; }
}
