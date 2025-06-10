package com.example.dao.Domande;

import com.example.models.Documento;
import com.example.models.Domanda;
import com.example.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementazione PostgreSQL dell'interfaccia {@link DomandeDAO}.
 * Gestisce le operazioni di accesso ai dati per le domande e le parole
 * utilizzando un database PostgreSQL.
 *
 * @author [Il tuo nome]
 * @version 1.0
 * @since 1.0
 * @see DomandeDAO
 * @see Domanda
 * @see Documento
 */
public class DomandeDAOPostgres implements DomandeDAO {

    /** {@inheritDoc} */
    @Override
    public List<Domanda> selectDomande() throws DatabaseException {
        List<Domanda> domande = new ArrayList<>();
        String query = "SELECT * FROM domanda ORDER BY RANDOM() LIMIT 4";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Domanda domanda = new Domanda(
                            rs.getString("testoDomanda"),
                            null
                    );
                    domande.add(domanda);
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("Errore durante il recupero delle domande", e);
        }

        return domande;
    }

    /** {@inheritDoc} */
    @Override
    public String selectParolaCasuale(Documento documento) throws DatabaseException {
        if (documento == null || documento.getId() <= 0) {
            throw new IllegalArgumentException("Documento non valido o ID non impostato");
        }

        String query = "SELECT valore FROM parola " +
                      "WHERE documento = ? " +
                      "AND valore NOT IN (SELECT parola FROM stopwords) " +
                      "ORDER BY RANDOM() LIMIT 1";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, documento.getId());
            
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("valore");
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("Errore durante la selezione della parola casuale", e);
        }

        return "";
    }
}