package com.example.dao.User;

import com.example.difficultySettings.DifficultyEnum;
import com.example.exceptions.NessunaModificaException;
import com.example.exceptions.PasswordNonCorrettaException;
import com.example.exceptions.UsernameGiaPreso;
import com.example.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementazione dell'interfaccia {@link UserDAO} per l'interazione con un database PostgreSQL.
 * Questa classe fornisce i metodi per la gestione degli utenti, inclusi registrazione, login,
 * recupero di statistiche sulle sessioni di quiz (punteggio medio, miglior punteggio, numero di partite)
 * e modifica delle credenziali dell'utente.
 */
public class UserDAOPostgres implements UserDAO<User> {


    /**
     * Non implementato. Questo metodo dovrebbe selezionare un utente per username.
     * Attualmente restituisce sempre un {@link Optional#empty()}.
     *
     * @param username Lo username dell'utente da cercare.
     * @return Sempre un {@link Optional#empty()}.
     */
    @Override
    public Optional<User> select(String username) {
        return Optional.empty();
    }

    /**
     * Tenta di registrare un nuovo utente nel database.
     * Prima di inserire il nuovo utente, verifica se l'username è già stato preso.
     * Se l'username è disponibile, l'utente viene inserito.
     *
     * @param user L'oggetto {@link User} da registrare, contenente username e password.
     * @return {@code true} se la registrazione ha successo (username disponibile e utente inserito),
     * {@code false} se l'username è già preso.
     * @throws SQLException se si verifica un errore SQL durante l'esecuzione della query.
     */
    public boolean register(User user) throws SQLException {
        ResultSet rs = null;
        try(
                Connection c = DriverManager.getConnection(URL, USER, PASS);
                Statement s = c.createStatement();
        ){
            String query = String.format("select * from public.users where username = '%s'" , user.getUsername());
            rs = s.executeQuery(query);
            //Se non è già registrato ok
            if(!rs.next()) {
                insert(user);
                return true;
            }

        }catch(Exception ex){
            ex.printStackTrace(); // In un'applicazione reale, gestire le eccezioni in modo più robusto (es. logging).
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    /**
     * Calcola il punteggio medio delle sessioni di quiz di un utente per una specifica difficoltà.
     *
     * @param user L'oggetto {@link User} per cui calcolare il punteggio medio.
     * @param difficultyEnum L'enumerazione {@link DifficultyEnum} che rappresenta la difficoltà delle sessioni.
     * @return Il punteggio medio (float) dell'utente per la difficoltà specificata. Ritorna 0 se non ci sono sessioni o in caso di errore.
     */
    @Override
    public float punteggioAvg(User user, DifficultyEnum difficultyEnum) {
        ResultSet rs = null;
        try(
                Connection c = DriverManager.getConnection(URL, USER, PASS);
                Statement s = c.createStatement();
        ){
            String query = String.format("select avg(punteggio) as media from sessione where utente = '%s' and difficolta = '%s'" , user.getUsername(), difficultyEnum.toString() );
            rs = s.executeQuery(query);

            if(rs.next()) {
                return rs.getFloat("media");
            }

        }catch(Exception ex){
            ex.printStackTrace(); // In un'applicazione reale, gestire le eccezioni in modo più robusto.
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

    /**
     * Recupera il miglior punteggio (massimo) ottenuto da un utente per una specifica difficoltà.
     *
     * @param user L'oggetto {@link User} per cui recuperare il miglior punteggio.
     * @param difficultyEnum L'enumerazione {@link DifficultyEnum} che rappresenta la difficoltà delle sessioni.
     * @return Il punteggio massimo (int) ottenuto dall'utente per la difficoltà specificata. Ritorna 0 se non ci sono sessioni o in caso di errore.
     */
    @Override
    public int punteggioBest(User user, DifficultyEnum difficultyEnum) {
        ResultSet rs = null;
        try(
                Connection c = DriverManager.getConnection(URL, USER, PASS);
                Statement s = c.createStatement();
        ){
            String query = String.format("select max(punteggio) as massimo from sessione where utente = '%s' and difficolta = '%s'" , user.getUsername(), difficultyEnum.toString() );
            rs = s.executeQuery(query);

            if(rs.next()) {
                return rs.getInt("massimo");
            }

        }catch(Exception ex){
            ex.printStackTrace(); // In un'applicazione reale, gestire le eccezioni in modo più robusto.
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

    /**
     * Conta il numero totale di partite (sessioni di quiz) giocate da un utente.
     *
     * @param user L'oggetto {@link User} per cui contare le partite.
     * @return Il numero totale di partite (int) giocate dall'utente. Ritorna 0 se l'utente non ha giocato partite o in caso di errore.
     */
    @Override
    public int contPartite(User user) {
        ResultSet rs = null;
        try(
                Connection c = DriverManager.getConnection(URL, USER, PASS);
                Statement s = c.createStatement();
        ){
            String query = String.format("select count(*) as numpartite from sessione where utente = '%s'" , user.getUsername());
            rs = s.executeQuery(query);

            if(rs.next()) {
                return rs.getInt("numpartite");
            }

        }catch(Exception ex){
            ex.printStackTrace(); // In un'applicazione reale, gestire le eccezioni in modo più robusto.
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

    /**
     * Esegue l'operazione di login verificando username e password di un utente nel database.
     * Se le credenziali sono corrette, l'oggetto {@link User} fornito viene aggiornato con lo stato di amministratore.
     *
     * @param user L'oggetto {@link User} contenente lo username e la password da verificare.
     * @return L'oggetto {@link User} con lo stato di amministratore aggiornato se il login ha successo,
     * altrimenti {@code null}.
     * @throws SQLException se si verifica un errore SQL durante l'esecuzione della query.
     */
    @Override
    public User login(User user) throws SQLException {
        ResultSet rs = null;
        try(
                Connection c = DriverManager.getConnection(URL, USER, PASS);
                Statement s = c.createStatement()
        ){
            String query = String.format("select * from users where username = '%s' and password = '%s' ", user.getUsername(), user.getPassword());
            rs = s.executeQuery(query);

            if(rs.next()) {
                boolean admin = rs.getBoolean("admin");
                user.setAdmin(admin);
                return user;
            }

        }catch(Exception ex){
            ex.printStackTrace(); // In un'applicazione reale, gestire le eccezioni in modo più robusto.
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * Seleziona tutti gli utenti presenti nel database.
     * Nota: La query di selezione è un `executeUpdate` invece di `executeQuery`, il che potrebbe causare un errore.
     * Inoltre, il `ResultSet` non viene inizializzato correttamente prima del ciclo `while`, portando a un `NullPointerException`.
     *
     * @return Una {@link List} di oggetti {@link User} presenti nel database.
     * @throws SQLException se si verifica un errore SQL durante l'esecuzione della query.
     */
    @Override
    public List<User> selectAll() throws SQLException {
        ResultSet rs = null; // Dovrebbe essere inizializzato da s.executeQuery
        List<User> users = new ArrayList<>();
        try(
                Connection c = DriverManager.getConnection(URL, USER, PASS);
                Statement s = c.createStatement();
        ) {
            String select = "SELECT * FROM public.users"; // Query corretta
            rs = s.executeQuery(select); // Deve essere executeQuery
            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                Boolean admin = rs.getBoolean("admin");
                User user = new User(username, password, admin);
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace(); // In un'applicazione reale, gestire le eccezioni in modo più robusto.
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return users ;
    }

    /**
     * Inserisce un nuovo utente nel database.
     *
     * @param user L'oggetto {@link User} da inserire.
     */
    @Override
    public void insert(User user) {
        try(
                Connection c = DriverManager.getConnection(URL, USER, PASS);
                Statement s = c.createStatement();
        ) {
            String insert = String.format("INSERT INTO public.users(username, password, admin) VALUES ('%s', '%s', '%s')", user.getUsername(), user.getPassword(), user.isAdmin());
            s.executeUpdate(insert);
        } catch (Exception e) {
            e.printStackTrace(); // In un'applicazione reale, gestire le eccezioni in modo più robusto.
        }
    }

    /**
     * Elimina un utente dal database in base al suo username.
     *
     * @param user L'oggetto {@link User} da eliminare (viene usato solo lo username).
     */
    @Override
    public void delete(User user) {
        try(
                Connection c = DriverManager.getConnection(URL, USER, PASS);
                Statement s = c.createStatement();
        ) {
            String delete = String.format("DELETE FROM public.users WHERE username = '%s'", user.getUsername());
            s.executeUpdate(delete);
        } catch (Exception e) {
            e.printStackTrace(); // In un'applicazione reale, gestire le eccezioni in modo più robusto.
        }
    }

    /**
     * Modifica lo username di un utente nel database.
     * Prima di procedere con la modifica, verifica se il nuovo username è già in uso.
     *
     * @param user L'oggetto {@link User} dell'utente attualmente loggato, contenente lo username da modificare.
     * @param nuovoUsername La nuova stringa dello username da assegnare all'utente.
     * @throws UsernameGiaPreso se il {@code nuovoUsername} è già preso nel database.
     * @throws RuntimeException se si verifica un errore SQL durante l'operazione (l'eccezione SQL viene soppressa e non rilanciata direttamente).
     */
    @Override
    public void modificaUsername(User user , String nuovoUsername) {
        try(
                Connection c = DriverManager.getConnection(URL, USER, PASS);
                Statement s = c.createStatement();
        ){
            // Verifica se il nuovo username è già in uso
            if(!isUsernameTaken(nuovoUsername)){
                String query = String.format("UPDATE users SET username = '%s' WHERE username = '%s'", nuovoUsername, user.getUsername());
                System.out.println("Query per modificare: "+query); // Per debugging
                s.executeUpdate(query);
                System.out.println("Eseguendo la query..."); // Per debugging
                user.setUsername(nuovoUsername); // Aggiorna anche il modello in memoria
            }
            else {
                throw new UsernameGiaPreso("L'username "+ nuovoUsername + " non è disponibile, prova con un altro.");
            }
        }catch (SQLException ex){
            // In un'applicazione reale, qui si dovrebbe loggare l'errore o rilanciare una RuntimeException
            // o SQLException per una gestione a livello superiore.
            System.err.println("Errore SQL durante la modifica dello username: " + ex.getMessage());
        }
    }

    /**
     * Verifica se un dato username è già presente nel database.
     *
     * @param username Lo username da controllare.
     * @return {@code true} se l'username è già preso, {@code false} altrimenti.
     */
    private boolean isUsernameTaken(String username){
        try(
                Connection c = DriverManager.getConnection(URL, USER, PASS);
                Statement s = c.createStatement();
        ){
            String query = String.format("select * from users where username='%s'", username);
            ResultSet resultSet =s.executeQuery(query);
            return  resultSet.next(); // true se trova una riga, false altrimenti

        }catch(SQLException ex){
            // In un'applicazione reale, qui si dovrebbe loggare l'errore o rilanciare un'eccezione.
            System.err.println("Errore SQL durante la verifica dello username: " + ex.getMessage());
        }
        return false;
    }

    /**
     * Modifica la password di un utente nel database.
     * Vengono effettuati controlli per assicurarsi che la nuova password sia diversa da quella vecchia
     * e che la vecchia password fornita corrisponda a quella attuale dell'utente.
     *
     * @param userLogged L'oggetto {@link User} dell'utente attualmente loggato.
     * @param vecchiaPassInserita La vecchia password inserita dall'utente per la verifica.
     * @param nuovaPassInserita La nuova password da impostare.
     * @throws NessunaModificaException se la nuova password è uguale a quella vecchia.
     * @throws PasswordNonCorrettaException se la vecchia password inserita non corrisponde a quella attuale dell'utente.
     * @throws RuntimeException se si verifica un errore SQL durante l'operazione (l'eccezione SQL viene soppressa e non rilanciata direttamente).
     */
    public void modificaPassword(User userLogged , String vecchiaPassInserita  , String nuovaPassInserita){
        try(
                Connection c = DriverManager.getConnection(URL, USER, PASS);
                Statement s = c.createStatement();
        ){
            if(vecchiaPassInserita.equals(nuovaPassInserita)) {
                throw new NessunaModificaException("La nuova password non può essere uguale alla vecchia.");
            }

            if(!isCorrectPasswordForUser(userLogged , vecchiaPassInserita)) {
                throw new PasswordNonCorrettaException("La password attuale inserita non è corretta.");
            }

            // Se le password corrispondono, fai l'update
            String query = String.format("update users set password = '%s' where username = '%s'" , nuovaPassInserita , userLogged.getUsername());
            System.out.println("Eseguendo la query di update password "+query); // Per debugging
            s.executeUpdate(query);
            userLogged.setPassword(nuovaPassInserita); // Aggiorna anche il modello in memoria

        }catch(SQLException ex){
            // In un'applicazione reale, qui si dovrebbe loggare l'errore o rilanciare una RuntimeException
            // o SQLException per una gestione a livello superiore.
            System.err.println("Errore SQL durante la modifica della password: " + ex.getMessage());
        }
    }

    /**
     * Verifica se la password fornita corrisponde a quella memorizzata per un dato utente nel database.
     *
     * @param user L'oggetto {@link User} per cui verificare la password (viene usato solo lo username).
     * @param vecchiaPassInserita La password da confrontare con quella memorizzata.
     * @return {@code true} se la password fornita corrisponde, {@code false} altrimenti o in caso di errore.
     */
    private boolean isCorrectPasswordForUser(User user , String vecchiaPassInserita){
        ResultSet rs = null;
        try(
                Connection c = DriverManager.getConnection(URL, USER, PASS);
                Statement s = c.createStatement();
        )
        {
            String query = String.format("select password from users where username='%s'",user.getUsername());
            rs = s.executeQuery(query);
            if(rs.next()){
                String pass = rs.getString("password");
                return pass.equals(vecchiaPassInserita);
            }
        }catch(SQLException ex){
            // In un'applicazione reale, qui si dovrebbe loggare l'errore o rilanciare un'eccezione.
            System.err.println("Errore SQL durante la verifica della password: " + ex.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false; // Ritorna false in caso di errore o se l'utente non è trovato
    }
}