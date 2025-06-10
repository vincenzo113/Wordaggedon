package com.example.models;

import com.example.dao.Risposta.RispostaDAOPostgres;

import java.util.*;

/**
 * Questa classe è responsabile della generazione delle opzioni di risposta per le domande di un quiz,
 * interagendo con il database tramite {@link RispostaDAOPostgres}.
 * Offre metodi per creare risposte basate su diversi criteri, come la frequenza delle parole nei documenti.
 */
public class GeneratoreRisposte {

    // Istanza del DAO per accedere ai dati delle risposte dal database.
    private RispostaDAOPostgres rispostaDAOPostgres = new RispostaDAOPostgres();

    /**
     * Genera un elenco di risposte per una domanda che chiede quante volte una specifica parola
     * si presenta in un dato documento.
     * La risposta corretta è il conteggio effettivo, mentre le risposte false sono numeri casuali
     * diversi dal conteggio corretto.
     *
     * @param documento Il {@link Documento} in cui cercare la parola.
     * @param parola La parola di cui contare le ripetizioni.
     * @return Una {@link List} di oggetti {@link Risposta}, inclusa la risposta corretta e tre risposte false, mescolate casualmente.
     */
    public List<Risposta> RipetizioneParolaDocumento(Documento documento, String parola) {
        // Recupera la risposta corretta dal database (il conteggio della parola)
        Risposta rispostaCorretta = rispostaDAOPostgres.selectRispostaCorrettaRipetizioneParolaDocumento(documento, parola);

        // Converte il testo della risposta corretta (che è una stringa) in un intero.
        int corretta = Integer.parseInt(rispostaCorretta.getTesto());

        Random rand = new Random();
        Set<Integer> alternative = new HashSet<>(); // Usa un Set per garantire risposte false uniche.

        // Genera tre risposte false (numeri casuali tra 1 e 10) che non siano uguali alla risposta corretta.
        while (alternative.size() < 3) {
            int fake = rand.nextInt(10) + 1; // Range: 1-10
            if (fake != corretta) {
                alternative.add(fake);
            }
        }

        List<Risposta> risposte = new ArrayList<>();
        risposte.add(rispostaCorretta); // Aggiunge la risposta corretta.

        // Aggiunge le risposte false.
        for (int n : alternative) {
            risposte.add(new Risposta(String.valueOf(n), false));
        }

        Collections.shuffle(risposte); // Mescola le risposte per randomizzare l'ordine.
        return risposte;
    }

    /**
     * Genera un elenco di risposte per una domanda che chiede quale sia la parola più frequente
     * all'interno di un singolo documento.
     *
     * @param documento Il {@link Documento} in cui cercare la parola più frequente.
     * @return Una {@link List} di oggetti {@link Risposta}, inclusa la risposta corretta e risposte false generate dal database.
     * Le risposte sono mescolate casualmente.
     */
    public List<Risposta> PiuFrequenteDocumento(Documento documento) {
        // Recupera la parola più frequente nel documento dal database.
        Risposta rispostaCorretta = rispostaDAOPostgres.selectRispostaCorrettaPiuFrequenteDocumento(documento);

        // Recupera parole "non più frequenti" dal database da usare come risposte false.
        List<Risposta> fakes = rispostaDAOPostgres.selectParoleNonPiuFrequenti(documento, rispostaCorretta.getTesto());
        fakes.add(rispostaCorretta); // Aggiunge la risposta corretta alla lista delle risposte.

        Collections.shuffle(fakes); // Mescola le risposte.
        return fakes;
    }

    /**
     * Genera un elenco di risposte per una domanda che chiede quale sia la parola più frequente
     * tra un insieme di documenti.
     *
     * @param documenti Una {@link List} di oggetti {@link Documento} su cui basare la ricerca della parola più frequente.
     * @return Una {@link List} di oggetti {@link Risposta}, inclusa la risposta corretta e risposte false generate dal database.
     * Le risposte sono mescolate casualmente.
     */
    public List<Risposta> PiuFrequenteInTutti(List<Documento> documenti) {
        // Recupera la parola più frequente tra tutti i documenti dal database.
        Risposta rispostaCorretta = rispostaDAOPostgres.selectRispostaCorrettaPiuFrequenteInTutti(documenti);

        // Recupera parole "non più frequenti" dal database da usare come risposte false.
        List<Risposta> fakes = rispostaDAOPostgres.selectParoleNonPiuFrequentiInTutti(documenti, rispostaCorretta.getTesto());
        fakes.add(rispostaCorretta); // Aggiunge la risposta corretta alla lista delle risposte.

        Collections.shuffle(fakes); // Mescola le risposte.
        return fakes;
    }

    /**
     * Genera un elenco di risposte per una domanda che chiede quale parola non sia presente
     * in nessuno dei documenti forniti.
     *
     * @param documenti Una {@link List} di oggetti {@link Documento} da cui verificare l'assenza della parola.
     * @return Una {@link List} di oggetti {@link Risposta}, inclusa la risposta corretta e risposte false generate dal database.
     * Le risposte sono mescolate casualmente.
     */
    public List<Risposta> NonPresente(List<Documento> documenti) {
        // Recupera una parola non presente in nessun documento dal database.
        Risposta rispostaCorretta = rispostaDAOPostgres.selectRispostaCorrettaNonPresente(documenti);

        // Recupera parole presenti in almeno un documento dal database da usare come risposte false.
        List<Risposta> fakes = rispostaDAOPostgres.selectParolePresentiInAlmenoUnDocumento(documenti);
        fakes.add(rispostaCorretta); // Aggiunge la risposta corretta alla lista delle risposte.

        Collections.shuffle(fakes); // Mescola le risposte.
        return fakes;
    }
}