package com.example.models;

import com.example.dao.Risposta.RispostaDAOPostgres;

import java.util.*;

public class GeneratoreRisposte {

     private RispostaDAOPostgres rispostaDAOPostgres = new RispostaDAOPostgres();



    public  List<Risposta> RipetizioneParolaDocumento(Documento documento, String parola) {

        Risposta rispostaCorretta = rispostaDAOPostgres.selectRispostaCorrettaRipetizioneParolaDocumento(documento, parola);
        //System.out.println("Risposta corretta: "+rispostaCorretta);
        int corretta = Integer.parseInt(rispostaCorretta.getTesto());
        Random rand = new Random();
        Set<Integer> alternative = new HashSet<>();

        while (alternative.size() < 3) {
            int fake = rand.nextInt(10) + 1; // range: 1–10
            if (fake != corretta) {
                alternative.add(fake);
            }
        }

        List<Risposta> risposte = new ArrayList<>();
        risposte.add(rispostaCorretta);
        for (int n : alternative) {
            risposte.add(new Risposta(String.valueOf(n), false));
        }

        Collections.shuffle(risposte);
        return risposte;
    }

    public  List<Risposta> PiuFrequenteDocumento(Documento documento) {
        Risposta rispostaCorretta = rispostaDAOPostgres.selectRispostaCorrettaPiuFrequenteDocumento(documento);
       /* if (rispostaCorretta == null) {
            System.err.println("Errore: nessuna risposta corretta trovata per documento id=" + documento.getId());
            return Collections.emptyList(); // oppure ritorna una lista con risposte placeholder
        }*/

        //System.out.println("Risposta piu frequente nel documento: " + rispostaCorretta);
        List<Risposta> fakes = rispostaDAOPostgres.selectParoleNonPiuFrequenti(documento, rispostaCorretta.getTesto());
        fakes.add(rispostaCorretta);
        //System.out.println("Risposte piu frequente" + fakes);
        return fakes;
    }

    public  List<Risposta>  PiuFrequenteInTutti(List<Documento> documenti) {
        Risposta rispostaCorretta = rispostaDAOPostgres.selectRispostaCorrettaPiuFrequenteInTutti(documenti);
        List<Risposta> fakes = rispostaDAOPostgres.selectParoleNonPiuFrequentiInTutti(documenti , rispostaCorretta.getTesto());
        fakes.add(rispostaCorretta);
       // System.out.println("Risposte piu frequente in tutti "+fakes);

        return fakes;
    }

    public  List<Risposta>  NonPresente(List<Documento> documenti) {
        Risposta rispostaCorretta = rispostaDAOPostgres.selectRispostaCorrettaNonPresente(documenti);
        List<Risposta> fakes = rispostaDAOPostgres.selectParolePresentiInAlmenoUnDocumento(documenti);
        fakes.add(rispostaCorretta);
       // System.out.println("Contenuto finale di fakes: " + fakes);
        return fakes;
    }
}
