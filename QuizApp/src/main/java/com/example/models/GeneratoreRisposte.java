package com.example.models;

import com.example.dao.Risposta.RispostaDAOPostgres;

import java.util.List;

public class GeneratoreRisposte {

    static RispostaDAOPostgres rispostaDAOPostgres = new RispostaDAOPostgres();
    public static List<Risposta> RipetizioneParolaDocumento(Documento documento, String parola) {
        rispostaDAOPostgres.selectRispostaCorrettaRipetizioneParolaDocumento(documento, parola);

        return null;
    }

    public static List<Risposta>  PiuFrequenteDocumento(Documento documento) {
        rispostaDAOPostgres.selectRispostaCorrettaPiuFrequenteDocumento(documento);

        return null;
    }

    public static List<Risposta>  PiuFrequenteInTutti(List<Documento> documenti) {
        rispostaDAOPostgres.selectRispostaCorrettaPiuFrequenteInTutti(documenti);

        return null;
    }

    public static List<Risposta>  NonPresente(List<Documento> documenti) {
        rispostaDAOPostgres.selectRispostaCorrettaNonPresente(documenti);

        return null;
    }
}
