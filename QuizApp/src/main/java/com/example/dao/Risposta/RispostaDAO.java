package com.example.dao.Risposta;

import com.example.dao.ConnectionConfig;
import com.example.exceptions.DatabaseException;
import com.example.models.Documento;
import com.example.models.Risposta;

import java.util.List;

/**
 * Interfaccia per l'accesso ai dati relativi alle risposte.
 * Estende la configurazione di connessione al database.
 *
 * Fornisce metodi per selezionare risposte corrette e parole
 * con diverse condizioni basate su documenti e liste di documenti.
 */
public interface RispostaDAO extends ConnectionConfig {

    /**
     * Recupera la risposta corretta associata alla ripetizione di una parola
     * in un documento specifico.
     *
     * @param documento il documento di riferimento.
     * @param parola la parola da cercare.
     * @return la risposta corretta corrispondente.
     * @throws DatabaseException se si verifica un errore durante l'accesso al database
     */
    Risposta selectRispostaCorrettaRipetizioneParolaDocumento(Documento documento, String parola) 
            throws DatabaseException;

    /**
     * Recupera la risposta corretta quando non è presente alcuna corrispondenza
     * in una lista di documenti.
     *
     * @param documenti la lista di documenti da considerare.
     * @return la risposta corretta relativa al caso "non presente".
     * @throws DatabaseException se si verifica un errore durante l'accesso al database
     */
    Risposta selectRispostaCorrettaNonPresente(List<Documento> documenti) 
            throws DatabaseException;

    /**
     * Recupera la risposta corretta più frequente tra tutti i documenti forniti.
     *
     * @param documenti la lista di documenti da analizzare.
     * @return la risposta corretta più frequente in tutti i documenti.
     * @throws DatabaseException se si verifica un errore durante l'accesso al database
     */
    Risposta selectRispostaCorrettaPiuFrequenteInTutti(List<Documento> documenti) 
            throws DatabaseException;

    /**
     * Recupera la risposta corretta più frequente all'interno di un singolo documento.
     *
     * @param documento il documento da analizzare.
     * @return la risposta corretta più frequente nel documento.
     * @throws DatabaseException se si verifica un errore durante l'accesso al database
     */
    Risposta selectRispostaCorrettaPiuFrequenteDocumento(Documento documento) 
            throws DatabaseException;

    /**
     * Recupera una lista di risposte associate a parole che non sono più frequenti
     * in un documento specifico rispetto a una parola data.
     *
     * @param documento il documento di riferimento.
     * @param parola la parola di riferimento.
     * @return lista di risposte relative a parole meno frequenti.
     * @throws DatabaseException se si verifica un errore durante l'accesso al database
     */
    List<Risposta> selectParoleNonPiuFrequenti(Documento documento, String parola) 
            throws DatabaseException;

    /**
     * Recupera una lista di risposte associate a parole che non sono più frequenti
     * in tutti i documenti forniti rispetto a una parola corretta data.
     *
     * @param documenti la lista di documenti da considerare.
     * @param parolaCorretta la parola corretta di riferimento.
     * @return lista di risposte relative a parole meno frequenti in tutti i documenti.
     * @throws DatabaseException se si verifica un errore durante l'accesso al database
     */
    List<Risposta> selectParoleNonPiuFrequentiInTutti(List<Documento> documenti, String parolaCorretta) 
            throws DatabaseException;

    /**
     * Recupera una lista di risposte associate a parole che sono presenti
     * in almeno un documento della lista fornita.
     *
     * @param docs la lista di documenti da analizzare.
     * @return lista di risposte relative a parole presenti in almeno un documento.
     * @throws DatabaseException se si verifica un errore durante l'accesso al database
     */
    List<Risposta> selectParolePresentiInAlmenoUnDocumento(List<Documento> docs) 
            throws DatabaseException;
}