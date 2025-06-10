package com.example.models;

import com.example.difficultySettings.DifficultyEnum;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Rappresenta un documento all'interno del sistema, che può essere utilizzato per generare quiz.
 * Un documento include un ID, un titolo, il contenuto testuale, una mappatura delle parole con le loro frequenze,
 * e un livello di difficoltà associato. Implementa {@link Serializable} per permettere la persistenza
 * o la trasmissione dell'oggetto.
 */
public class Documento implements Serializable {
    /**
     * Il serialVersionUID per la serializzazione.
     */
    private static final long serialVersionUID = 1L;

    private int id;
    private String titolo;
    private String contenuto;
    private Map<String, Integer> mappaQuiz;
    private DifficultyEnum difficolta;

    /**
     * Costruisce un nuovo oggetto {@code Documento} con un ID, un titolo e un contenuto specificati.
     * La mappatura delle parole per il quiz viene generata automaticamente dal contenuto.
     *
     * @param id L'identificatore unico del documento.
     * @param titolo Il titolo del documento.
     * @param contenuto Il contenuto testuale del documento.
     */
    public Documento(int id, String titolo, String contenuto) {
        this.id = id;
        this.titolo = titolo;
        this.contenuto = contenuto;
        this.mappaQuiz = setMappaturaQuiz();
    }

    /**
     * Costruisce un nuovo oggetto {@code Documento} con un titolo e un contenuto specificati.
     * L'ID sarà impostato in un secondo momento, ad esempio dopo l'inserimento nel database.
     * La mappatura delle parole per il quiz viene generata automaticamente dal contenuto.
     *
     * @param titolo Il titolo del documento.
     * @param contenuto Il contenuto testuale del documento.
     */
    public Documento(String titolo, String contenuto) {
        this.titolo = titolo;
        this.contenuto = contenuto;
        this.mappaQuiz = setMappaturaQuiz();
    }

    /**
     * Restituisce l'ID del documento.
     * @return L'ID del documento.
     */
    public int getId() { return id; }

    /**
     * Restituisce il titolo del documento, racchiuso tra apici singoli per un uso potenziale in query SQL.
     * @return Il titolo del documento con apici.
     */
    public String getTitolo() { return "'"+titolo+"'"; }

    /**
     * Restituisce il contenuto testuale del documento.
     * @return Il contenuto del documento.
     */
    public String getContenuto() { return contenuto; }

    /**
     * Restituisce il livello di difficoltà associato al documento.
     * @return Il {@link DifficultyEnum} del documento.
     */
    public DifficultyEnum getDifficolta() { return difficolta; }

    /**
     * Imposta l'ID del documento.
     * @param id Il nuovo ID del documento.
     */
    public void setId(int id) {this.id = id;}

    /**
     * Imposta il titolo del documento.
     * @param titolo Il nuovo titolo del documento.
     */
    public void setTitolo(String titolo) {this.titolo = titolo;}

    /**
     * Imposta il contenuto testuale del documento.
     * @param contenuto Il nuovo contenuto del documento.
     */
    public void setContenuto(String contenuto) {this.contenuto = contenuto;}

    /**
     * Imposta il livello di difficoltà per il documento.
     * @param difficolta Il nuovo {@link DifficultyEnum} per il documento.
     */
    public void setDifficolta(DifficultyEnum difficolta) { this.difficolta = difficolta; }

    /**
     * Genera e imposta la mappatura delle parole (e la loro frequenza) dal contenuto del documento.
     * Il contenuto viene convertito in minuscolo e diviso in parole usando punteggiatura e spazi come delimitatori.
     * Le parole vuote risultanti dalla divisione vengono filtrate.
     *
     * @return Una {@link Map} dove la chiave è una parola ({@code String}) e il valore è la sua frequenza ({@code Integer}).
     */
    public Map<String, Integer> setMappaturaQuiz(){
        Stream<String> parole = Arrays.stream(contenuto.toLowerCase().split("[\\p{Punct}\\s]+")).filter(s->!s.isEmpty()); //Splitta il contenuto anche per punteggiatura
        return parole.collect(Collectors.groupingBy(String::toString,
                Collectors.summingInt(p->1)));
    }

    /**
     * Restituisce la mappatura delle parole (e la loro frequenza) del documento,
     * utilizzata per la generazione di quiz.
     * @return La {@link Map} delle parole e delle loro frequenze.
     */
    public Map<String, Integer> getMappaQuiz() {
        return mappaQuiz;
    }

    /**
     * Restituisce una rappresentazione in stringa dell'oggetto {@code Documento}.
     *
     * @return Una stringa che include l'ID, il titolo, il contenuto, la mappa del quiz e la difficoltà del documento.
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

    /**
     * Indica se un altro oggetto è "uguale a" questo documento.
     * Due documenti sono considerati uguali se hanno lo stesso ID.
     *
     * @param obj L'oggetto di riferimento con cui confrontare.
     * @return {@code true} se questo documento è uguale all'oggetto {@code obj}; {@code false} altrimenti.
     */
    @Override
    public boolean equals(Object obj){
        if(obj == null) return false;
        if(this == obj) return true;
        if(!(obj instanceof Documento)) return false;

        Documento doc = (Documento) obj;
        return doc.getId() == this.id;
    }

    /**
     * Restituisce un valore di codice hash per l'oggetto.
     * Questo metodo è supportato per il beneficio delle tabelle hash come quelle fornite da {@link java.util.HashMap}.
     * Il codice hash si basa esclusivamente sull'ID del documento.
     *
     * @return Un valore di codice hash per questo oggetto.
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
