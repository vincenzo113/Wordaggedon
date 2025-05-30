package com.example.models;

public class  Documento
{

    private int id;
    private String titolo;
    private String contenuto;

    public Documento(int id, String titolo, String contenuto) {
        this.id = id;
        this.titolo = titolo;
        this.contenuto = contenuto;
    }

    public int getId() { return id; }
    public String getTitolo() { return titolo; }
    public String getContenuto() { return contenuto; }
}

