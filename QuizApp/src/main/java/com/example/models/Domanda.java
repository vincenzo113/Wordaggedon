package com.example.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
public class Domanda {
    private String testo;
    private List<Risposta> risposte;

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

    public void setRisposte(Documento documento , String parola) {
        if (getTesto().startsWith("Quante volte si ripete una parola")) {
            risposte = CalcolatoreRisposte.RipetizioneParolaDocumento(documento, parola);
        }
    }
    public void setRisposte(Documento documento) {
        if (getTesto().startsWith("Qual è la parola più frequente nel quiz")) {
            risposte = CalcolatoreRisposte.PiuFrequenteDocumento(documento);
        }
    }

    public void setRisposte(List<Documento> documenti){
            if(getTesto().startsWith("Qual è la parola più frequente in tutti i documenti")){
                risposte = CalcolatoreRisposte.PiuFrequenteInTutti(documenti);
            }
            else if(getTesto().startsWith("Quale parola non appare mai in nessun documento")){
                risposte = CalcolatoreRisposte.NonPresente(documenti);
            }
        }

}






