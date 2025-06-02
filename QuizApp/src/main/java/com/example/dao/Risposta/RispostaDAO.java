package com.example.dao.Risposta;

import com.example.dao.ConnectionConfig;
import com.example.models.Documento;
import com.example.models.Risposta;

import java.util.List;

public interface RispostaDAO extends ConnectionConfig {


    Risposta selectRispostaCorrettaRipetizioneParolaDocumento(Documento documento,String parola);
    Risposta selectRispostaCorrettaNonPresente(List<Documento> documenti);
    Risposta selectRispostaCorrettaPiuFrequenteInTutti(List<Documento> documenti);
    Risposta selectRispostaCorrettaPiuFrequenteDocumento(Documento documento);
    List<Risposta> selectParoleNonPiuFrequenti(Documento documento , String parola);
    List<Risposta> selectParoleNonPiuFrequentiInTutti(List<Documento> documenti, String parolaCorretta);
    List<Risposta> selectParolePresentiInAlmenoUnDocumento(List<Documento> docs);

}
