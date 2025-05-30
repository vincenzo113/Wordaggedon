package com.example.dao.Domande;

import com.example.dao.ConnectionConfig;
import com.example.models.Documento;
import com.example.models.Domanda;

import java.util.List;

public interface DomandeDAO extends ConnectionConfig {

    public List<Domanda> selectDomande();

    public String selectParolaCasuale(Documento documento);
}
