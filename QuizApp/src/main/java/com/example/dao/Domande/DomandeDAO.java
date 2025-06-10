package com.example.dao.Domande;

import com.example.dao.ConnectionConfig;
import com.example.models.Documento;
import com.example.models.Domanda;

import java.util.List;

/**
 * Interfaccia per l'accesso ai dati relativi alle domande.
 * Estende la configurazione di connessione al database.
 *
 * Fornisce metodi per selezionare tutte le domande e per
 * ottenere una parola casuale da un documento specifico.
 */
public interface DomandeDAO extends ConnectionConfig {

    /**
     * Recupera la lista completa delle domande disponibili.
     *
     * @return una lista di oggetti {@link Domanda} contenenti tutte le domande.
     */
    public List<Domanda> selectDomande();

    /**
     * Seleziona una parola casuale all'interno del contenuto di un documento specifico.
     *
     * @param documento il documento dal quale estrarre la parola casuale.
     * @return una parola casuale contenuta nel documento.
     */
    public String selectParolaCasuale(Documento documento);
}
