package com.example.dao.Domande;

import com.example.dao.ConnectionConfig;
import com.example.models.Documento;
import com.example.models.Domanda;
import com.example.exceptions.DatabaseException;

import java.util.List;

/**
 * Interfaccia per la gestione dell'accesso ai dati relativi alle domande nel database.
 * Questa interfaccia definisce le operazioni fondamentali per la manipolazione
 * delle domande e l'estrazione di parole casuali dai documenti.
 *
 * @author [Il tuo nome]
 * @version 1.0
 * @since 1.0
 * @see Domanda
 * @see Documento
 * @see ConnectionConfig
 */
public interface DomandeDAO extends ConnectionConfig {

    /**
     * Recupera tutte le domande presenti nel database.
     * Questo metodo esegue una query per ottenere l'elenco completo
     * delle domande memorizzate, inclusi tutti i loro attributi.
     *
     * @return List&lt;Domanda&gt; una lista contenente tutte le domande disponibili
     * @throws DatabaseException se si verificano errori durante l'accesso al database
     * @see Domanda
     */
    List<Domanda> selectDomande() throws DatabaseException;

    /**
     * Estrae una parola casuale dal contenuto di un documento specificato.
     * Il metodo analizza il testo del documento e seleziona in modo random
     * una delle parole presenti. Utile per la generazione di quiz o
     * esercizi basati sul contenuto del documento.
     *
     * @param documento il documento da cui estrarre la parola casuale
     * @return String la parola casuale estratta dal documento
     * @throws DatabaseException se si verificano errori durante l'accesso al database
     * @throws IllegalArgumentException se il documento è null o non contiene testo
     * @see Documento
     */
    String selectParolaCasuale(Documento documento) throws DatabaseException;
}