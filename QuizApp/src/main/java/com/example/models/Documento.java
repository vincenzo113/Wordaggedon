package com.example.models;


import com.example.difficultySettings.DifficultyEnum;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Rappresenta un documento con titolo, contenuto, difficoltà e una mappa delle parole contenute.
 * La classe fornisce metodi per accedere e modificare le sue proprietà,
 * oltre a generare una mappatura delle parole presenti nel contenuto.
 */
public class  Documento implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String titolo;
    private String contenuto;
    private Map<String, Integer> mappaQuiz;
    private DifficultyEnum difficolta;
    private LocalDate dataCaricamento ;

    /**
     * Costruttore della classe Documento.
     *
     * @param id        l'identificativo univoco del documento
     * @param titolo    il titolo del documento
     * @param contenuto il contenuto testuale del documento
     */
    public Documento(int id, String titolo, String contenuto) {
        this.id = id;
        this.titolo = titolo;
        this.contenuto = contenuto;
        this.mappaQuiz = setMappaturaQuiz();
    }

    /**
     * Costruttore della classe Documento senza specificare l'id.
     *
     * @param titolo    il titolo del documento
     * @param contenuto il contenuto testuale del documento
     */
    public Documento(String titolo, String contenuto) {
        this.titolo = titolo;
        this.contenuto = contenuto;
        this.mappaQuiz = setMappaturaQuiz();

    }


    public Documento(int id , String titolo , String contenuto , DifficultyEnum difficolta, LocalDate dataCaricamento){
        this.id=id;
        this.titolo = titolo;
        this.contenuto = contenuto;
        this.difficolta = difficolta;
        this.dataCaricamento = dataCaricamento;
    }

    // Getter e setter
    /**
     * Restituisce l'id del documento.
     *
     * @return l'identificativo del documento
     */
    public int getId() { return id; }

    /**
     * Restituisce il titolo del documento, racchiuso tra apici singoli.
     *
     * @return il titolo del documento formattato
     */
    public String getTitolo() { return "'"+titolo+"'"; }

    /**
     * Restituisce il contenuto del documento.
     *
     * @return il contenuto testuale del documento
     */
    public String getContenuto() { return contenuto; }

    /**
     * Restituisce la difficoltà associata al documento.
     *
     * @return la difficoltà del documento
     */
    public DifficultyEnum getDifficolta() { return difficolta; }

    public LocalDate getDataCaricamento() {
        return dataCaricamento;
    }

    public void setDataCaricamento(LocalDate dataCaricamento) {
        this.dataCaricamento = dataCaricamento;
    }

    /**
     * Imposta l'id del documento.
     *
     * @param id l'identificativo da assegnare
     */
    public void setId(int id) {this.id = id;}

    /**
     * Imposta il titolo del documento.
     *
     * @param titolo il titolo da assegnare
     */
    public void setTitolo(String titolo) {this.titolo = titolo;}

    /**
     * Imposta il contenuto del documento.
     *
     * @param contenuto il contenuto testuale da assegnare
     */
    public void setContenuto(String contenuto) {this.contenuto = contenuto;}

    /**
     * Imposta la difficoltà del documento.
     *
     * @param difficolta la difficoltà da assegnare
     */
    public void setDifficolta(DifficultyEnum difficolta) { this.difficolta = difficolta; }

    /**
     * Crea e restituisce una mappatura delle parole presenti nel contenuto del documento.
     * Ogni parola viene associata al numero di occorrenze nel testo.
     *
     * @return una mappa contenente le parole come chiavi e il numero di occorrenze come valori
     */
    public Map<String, Integer> setMappaturaQuiz(){
        Stream<String> parole = Arrays.stream(contenuto.toLowerCase().split("[\\p{Punct}\\s]+")).filter(s->!s.isEmpty()); //Splitta il contenuto anche per punteggiatura
        return parole.collect(Collectors.groupingBy(String::toString,
                Collectors.summingInt(p->1)));
    }

    /**
     * Restituisce la mappa delle parole presenti nel documento e le rispettive occorrenze.
     *
     * @return la mappa delle parole e conteggi
     */
    public Map<String, Integer> getMappaQuiz() {
        return mappaQuiz;
    }

    /**
     * Restituisce una rappresentazione in stringa del documento.
     *
     * @return una stringa che rappresenta il documento
     */
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

    // Metodi per poter far capire al contains di set , che si deve basare solo sull'id del documento
    /**
     * Determina se questo documento è uguale a un altro oggetto, confrontando solo l'id.
     *
     * @param obj l'oggetto da confrontare
     * @return true se l'id dei due documenti coincide, false altrimenti
     */
    @Override
    public boolean equals(Object obj){
        if(obj == null) return false;
        if(this == obj) return true;
        if(!(obj instanceof Documento))return false;

        Documento doc = (Documento) obj;
        return doc.getId() == this.id;
    }

    /**
     * Restituisce il valore hash del documento, basato sull'id.
     *
     * @return l'hash code generato dall'id
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}

