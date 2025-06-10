package com.example.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
public class Domanda implements Serializable {

    private String testo;

    private List<Risposta> risposte;

    private  transient  GeneratoreRisposte generatoreRisposte = new GeneratoreRisposte();


    public Domanda(String testo, List<Risposta> risposte) {
        this.testo = testo;
        this.risposte = risposte;
    }


    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {

        this.testo = testo;
    }

    public  List<Risposta> getRisposte() {
        return risposte;
    }

    public void setRisposteQuanteVolteSiPresentaInUnTesto(Documento documento , String parola) {
        risposte = generatoreRisposte.RipetizioneParolaDocumento(documento, parola);
    }


    public void setRispostePiuFrequenteNelTesto(Documento documento) {
            risposte = generatoreRisposte.PiuFrequenteDocumento(documento);
    }


    public void setRispostePiuFrequenteInTutti(List<Documento> docs){
        this.risposte = generatoreRisposte.PiuFrequenteInTutti(docs);

    }


    public void setRisposteMaiPresente(List<Documento> docs){
        risposte = generatoreRisposte.NonPresente(docs);
    }


    @Override
    public String toString() {
        return "Domanda{" +
                "testo='" + testo + '\'' +
                ", risposte=" + risposte +
                '}';
    }
}






