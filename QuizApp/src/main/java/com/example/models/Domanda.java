package com.example.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
    public void setRisposte(Testo[] testi , String parola , int random) {

            if(testo.startsWith("Quante volte si ripete la parola")){
                //CHIAMI UNA FUNZIONE SPECIFICA PER CALCOLARE RISPOSTA DI CALCOLATORE RISPOSTE

                risposte = CalcolatoreRisposte.calcolaRispostePerConteggio(testi,parola);
            }



    }
    public void setRisposte(Testo[] testi){
        if(testo.startsWith("Quale parola non appare mai in nessun documento")){
            //CHIAMI UNA FUNZIONE SPECIFICA PER CALCOLARE RISPOSTA DI CALCOLATORE RISPOSTE

            risposte = CalcolatoreRisposte.calcolaParolaCheNonAppareMai(testi);
        }
        if(testo.startsWith("Qual è la parola più frequente in tutti i documenti")){
            //CHIAMI UNA FUNZIONE SPECIFICA PER CALCOLARE RISPOSTA DI CALCOLATORE RISPOSTE

            risposte = CalcolatoreRisposte.calcolaParolaPiuFrequenteInTuttiIDocumenti(testi);
        }
    }

    public void setRisposte(Testo[] testi,int random){
        if(testo.startsWith("Qual è la parola più frequente nel testo del quiz ")){
            //CHIAMI UNA FUNZIONE SPECIFICA PER CALCOLARE RISPOSTA DI CALCOLATORE RISPOSTE

            risposte = CalcolatoreRisposte.calcolaParolaPiuFrequente(testi);
        }
    }



}





