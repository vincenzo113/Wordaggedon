package com.example.models;


import com.example.difficultySettings.DifficultyEnum;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class  Documento implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String titolo;
    private String contenuto;
    private Map<String, Integer> mappaQuiz;
    private DifficultyEnum difficolta;


    public Documento(int id, String titolo, String contenuto) {
        this.id = id;
        this.titolo = titolo;
        this.contenuto = contenuto;
        this.mappaQuiz = setMappaturaQuiz();
    }

    public Documento(String titolo, String contenuto) {
        this.titolo = titolo;
        this.contenuto = contenuto;
        this.mappaQuiz = setMappaturaQuiz();

    }

    // Getter e setter
    public int getId() { return id; }
    public String getTitolo() { return "'"+titolo+"'"; }
    public String getContenuto() { return contenuto; }
    public DifficultyEnum getDifficolta() { return difficolta; }

    public void setId(int id) {this.id = id;}
    public void setTitolo(String titolo) {this.titolo = titolo;}
    public void setContenuto(String contenuto) {this.contenuto = contenuto;}
    public void setDifficolta(DifficultyEnum difficolta) { this.difficolta = difficolta; }

    public Map<String, Integer> setMappaturaQuiz(){
        Stream<String> parole = Arrays.stream(contenuto.toLowerCase().split("[\\p{Punct}\\s]+")).filter(s->!s.isEmpty()); //Splitta il contenuto anche per punteggiatura
        return parole.collect(Collectors.groupingBy(String::toString,
                Collectors.summingInt(p->1)));
    }

    public Map<String, Integer> getMappaQuiz() {

        return mappaQuiz;
    }

    @Override
    public String toString() {
        return "Documento{" +
                "id=" + id +
                ", titolo='" + titolo + '\'' +
                ", contenuto='" + contenuto + '\'' +
                ", mappaQuiz=" + mappaQuiz +
                ", difficolta=" + difficolta +
                '}';
    }


    @Override
    public boolean equals(Object obj){
        if(obj == null) return false;
        if(this == obj) return true;
        if(!(obj instanceof Documento))return false;

        Documento doc = (Documento) obj;
        return doc.getId() == this.id; 

    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}

