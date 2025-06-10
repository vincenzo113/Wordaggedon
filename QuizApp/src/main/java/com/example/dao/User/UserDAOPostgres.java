package com.example.dao.User;

import com.example.difficultySettings.DifficultyEnum;
import com.example.exceptions.*;
import com.example.models.User;

import java.sql.*;

/**
 * Implementazione PostgreSQL del DAO per la gestione degli utenti.
 * Questa classe gestisce tutte le operazioni CRUD relative agli utenti nel database,
 * incluse registrazione, login, modifica dei dati e statistiche di gioco.
 */
public class UserDAOPostgres implements UserDAO<User> {

    private static final String USER = "postgres";
    private static final String PASS = "tw2024";

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean register(User user) throws DatabaseException {
        String checkQuery = "SELECT * FROM public.users WHERE username = ?";
        
        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = c.prepareStatement(checkQuery)) {
            ps.setString(1, user.getUsername());
            
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    insert(user);
                    return true;
                }
                return false;
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Errore durante la registrazione dell'utente", ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float punteggioAvg(User user, DifficultyEnum difficultyEnum) throws DatabaseException {
        String query = "SELECT AVG(punteggio) as media FROM sessione WHERE utente = ? AND difficolta = ?";

        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, difficultyEnum.toString());
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getFloat("media");
                }
                return 0;
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Errore nel calcolo del punteggio medio", ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int punteggioBest(User user, DifficultyEnum difficultyEnum) throws DatabaseException {
        String query = "SELECT MAX(punteggio) as massimo FROM sessione WHERE utente = ? AND difficolta = ?";

        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, difficultyEnum.toString());
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("massimo");
                }
                return 0;
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Errore nel recupero del punteggio migliore", ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int contPartite(User user) throws DatabaseException {
        String query = "SELECT COUNT(*) as numpartite FROM sessione WHERE utente = ?";

        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, user.getUsername());
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("numpartite");
                }
                return 0;
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Errore nel conteggio delle partite", ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User login(User user) throws DatabaseException {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user.setAdmin(rs.getBoolean("admin"));
                    return user;
                }
                return null;
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Errore durante il login", ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(User user) throws DatabaseException {
        String insert = "INSERT INTO public.users(username, password, admin) VALUES (?, ?, ?)";

        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = c.prepareStatement(insert)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setBoolean(3, user.isAdmin());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DatabaseException("Errore durante l'inserimento dell'utente", ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void modificaUsername(User user, String nuovoUsername) throws DatabaseException, UsernameGiaPreso {
        if (isUsernameTaken(nuovoUsername)) {
            throw new UsernameGiaPreso("L'username " + nuovoUsername + " non è disponibile, prova con un altro");
        }

        String query = "UPDATE users SET username = ? WHERE username = ?";

        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, nuovoUsername);
            ps.setString(2, user.getUsername());
            ps.executeUpdate();
            user.setUsername(nuovoUsername);
        } catch (SQLException ex) {
            throw new DatabaseException("Errore durante la modifica dell'username", ex);
        }
    }

    /**
     * Verifica se un username è già presente nel database.
     *
     * @param username l'username da verificare
     * @return true se l'username è già in uso, false altrimenti
     * @throws DatabaseException se si verifica un errore durante l'accesso al database
     */
    private boolean isUsernameTaken(String username) throws DatabaseException {
        String query = "SELECT * FROM users WHERE username = ?";

        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Errore durante la verifica dell'username", ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void modificaPassword(User userLogged, String vecchiaPassInserita, String nuovaPassInserita) 
            throws DatabaseException, NessunaModificaException, PasswordNonCorrettaException {
        if (vecchiaPassInserita.equals(nuovaPassInserita)) {
            throw new NessunaModificaException("La nuova password deve essere diversa dalla precedente");
        }

        if (!isCorrectPasswordForUser(userLogged, vecchiaPassInserita)) {
            throw new PasswordNonCorrettaException("La password inserita non è corretta");
        }

        String query = "UPDATE users SET password = ? WHERE username = ?";

        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, nuovaPassInserita);
            ps.setString(2, userLogged.getUsername());
            ps.executeUpdate();
            userLogged.setPassword(nuovaPassInserita);
        } catch (SQLException ex) {
            throw new DatabaseException("Errore durante la modifica della password", ex);
        }
    }

    /**
     * Verifica se la password inserita corrisponde alla password dell'utente.
     *
     * @param user l'utente di cui verificare la password
     * @param vecchiaPassInserita la password inserita da verificare
     * @return true se la password è corretta, false altrimenti
     * @throws DatabaseException se si verifica un errore durante l'accesso al database
     */
    private boolean isCorrectPasswordForUser(User user, String vecchiaPassInserita) throws DatabaseException {
        String query = "SELECT password FROM users WHERE username = ?";

        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, user.getUsername());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String pass = rs.getString("password");
                    return pass.equals(vecchiaPassInserita);
                }
                return false;
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Errore durante la verifica della password", ex);
        }
    }
}