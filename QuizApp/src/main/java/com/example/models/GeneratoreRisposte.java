package com.example.models;

import com.example.dao.Risposta.RispostaDAOPostgres;

import java.util.*;

/**
 * Classe che genera liste di risposte per domande basate su documenti e parole specifiche.
 * Utilizza il DAO RispostaDAOPostgres per ottenere risposte corrette e alternative (fake).
 */
public class GeneratoreRisposte {

     private RispostaDAOPostgres rispostaDAOPostgres = new RispostaDAOPostgres();



    /**
     * Genera una lista di risposte basata sul numero di volte che una parola
     * si ripete in un documento. Include la risposta corretta e tre alternative casuali.
     *
     * @param documento Il documento su cui basare la domanda.
     * @param parola La parola di cui si vuole conoscere la frequenza.
     * @return Una lista di risposte mescolate contenente la risposta corretta e alternative.
     */
    public  List<Risposta> RipetizioneParolaDocumento(Documento documento, String parola) {

        Risposta rispostaCorretta = rispostaDAOPostgres.selectRispostaCorrettaRipetizioneParolaDocumento(documento, parola);
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

    /**
     * Genera una lista di risposte basata sulle parole più frequenti in un documento.
     * Include la risposta corretta e alternative non frequenti.
     *
     * @param documento Il documento su cui basare la domanda.
     * @return Una lista di risposte contenente la risposta corretta e alternative.
     */
    public  List<Risposta> PiuFrequenteDocumento(Documento documento) {
        Risposta rispostaCorretta = rispostaDAOPostgres.selectRispostaCorrettaPiuFrequenteDocumento(documento);
        List<Risposta> fakes = rispostaDAOPostgres.selectParoleNonPiuFrequenti(documento, rispostaCorretta.getTesto());
        fakes.add(rispostaCorretta);
        return fakes;
    }

    /**
     * Genera una lista di risposte basata sulle parole più frequenti tra tutti i documenti forniti.
     * Include la risposta corretta e alternative non frequenti.
     *
     * @param documenti La lista dei documenti su cui basare la domanda.
     * @return Una lista di risposte contenente la risposta corretta e alternative.
     */
    public  List<Risposta>  PiuFrequenteInTutti(List<Documento> documenti) {
        Risposta rispostaCorretta = rispostaDAOPostgres.selectRispostaCorrettaPiuFrequenteInTutti(documenti);
        List<Risposta> fakes = rispostaDAOPostgres.selectParoleNonPiuFrequentiInTutti(documenti , rispostaCorretta.getTesto());
        fakes.add(rispostaCorretta);


        return fakes;
    }

    /**
     * Genera una lista di risposte basata sulle parole che non sono presenti in tutti i documenti.
     * Include la risposta corretta e alternative prese da parole presenti in almeno un documento.
     *
     * @param documenti La lista dei documenti su cui basare la domanda.
     * @return Una lista di risposte contenente la risposta corretta e alternative.
     */
    public  List<Risposta>  NonPresente(List<Documento> documenti) {
        Risposta rispostaCorretta = rispostaDAOPostgres.selectRispostaCorrettaNonPresente(documenti);
        List<Risposta> fakes = rispostaDAOPostgres.selectParolePresentiInAlmenoUnDocumento(documenti);
        fakes.add(rispostaCorretta);
        return fakes;
    }
}
