package com.example.dao.User;

import com.example.difficultySettings.DifficultyEnum;
import com.example.exceptions.NessunaModificaException;
import com.example.exceptions.PasswordNonCorrettaException;
import com.example.exceptions.UsernameGiaPreso;
import com.example.models.Risposta;
import com.example.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAOPostgres implements UserDAO<User> {

    private final String USER = "postgres";
    private final String PASS = "tw2024";

    @Override
    public Optional<User> select(String username) {
        return Optional.empty();
    }

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
            ex.printStackTrace();
        }

        return false;
    }

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
            ex.printStackTrace();
        }
        return 0;
    }

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
            ex.printStackTrace();
        }
        return 0;
    }

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
            ex.printStackTrace();
        }
        return 0;
    }

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
            ex.printStackTrace();
        }
        return null;
    }




    @Override
    public List<User> selectAll() throws SQLException {
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        try(
                Connection c = DriverManager.getConnection(URL, USER, PASS);
                Statement s = c.createStatement();
        ) {
            String select = String.format("SELECT * FROM public.users");
            s.executeUpdate(select);
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (rs.next()) {
            String username = rs.getString("username");
            String password = rs.getString("password");
            Boolean admin = rs.getBoolean("admin");
            User user = new User(username, password, admin);
            users.add(user);
        }
        return users ;
    }

    @Override
    public void insert(User user) {
        try(
                Connection c = DriverManager.getConnection(URL, USER, PASS);
                Statement s = c.createStatement();
        ) {
            String insert = String.format("INSERT INTO public.users(username, password, admin) VALUES ('%s', '%s', '%s')", user.getUsername(), user.getPassword(), user.isAdmin());
            s.executeUpdate(insert);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(User user) {
        try(
                Connection c = DriverManager.getConnection(URL, USER, PASS);
                Statement s = c.createStatement();
        ) {
            String delete = String.format("DELETE FROM public.users WHERE username = '%s'", user.getUsername());
            s.executeUpdate(delete);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void modificaUsername(User user , String nuovoUsername) {
        try(
                Connection c = DriverManager.getConnection(URL, USER, PASS);
                Statement s = c.createStatement();
        ){
            String query = String.format("UPDATE users SET username = '%s' WHERE username = '%s'", nuovoUsername, user.getUsername());            System.out.println("Query: " + query);
            System.out.println("Query per modificare: "+query);
            if(!isUsernameTaken(nuovoUsername)){
                s.executeUpdate(query);
                System.out.println("Eseguendo la query...");
                user.setUsername(nuovoUsername); //Aggiorno anche il modello
            }
            else {
                throw new UsernameGiaPreso("L'username "+ nuovoUsername + "non è disponibile , prova con un altro");
            }
        }catch (SQLException ex){}
    }



    private boolean isUsernameTaken(String username){
        try(
                Connection c = DriverManager.getConnection(URL, USER, PASS);
                Statement s = c.createStatement();
        ){
            String query = String.format("select * from users where username='%s'", username);
            ResultSet resultSet =s.executeQuery(query);
            return  resultSet.next();

        }catch(SQLException ex){}
       return false;
    }



    public void modificaPassword(User userLogged , String vecchiaPassInserita  , String nuovaPassInserita){
        try(
                Connection c = DriverManager.getConnection(URL, USER, PASS);
                Statement s = c.createStatement();
        ){
            if(vecchiaPassInserita.equals(nuovaPassInserita)) throw new NessunaModificaException("");

            if(!isCorrectPasswordForUser(userLogged , vecchiaPassInserita)) throw new PasswordNonCorrettaException(""); //Lancia eccezione

            //Se le password corrispondono , fai l'update
            String query = String.format("update users set password = '%s' where username = '%s'" , nuovaPassInserita , userLogged.getUsername());
            System.out.println("Eseguendo la query di update password "+query);
            s.executeUpdate(query);
            userLogged.setPassword(nuovaPassInserita);


        }catch(SQLException ex){}

    }


    private boolean isCorrectPasswordForUser(User user , String vecchiaPassInserita){
        try(
                Connection c = DriverManager.getConnection(URL, USER, PASS);
                Statement s = c.createStatement();
        )
        {

            String query = String.format("select password from users where username='%s'",user.getUsername());
            ResultSet rs = s.executeQuery(query);
            if(rs.next()){
                String pass = rs.getString("password");
                return (pass.equals(vecchiaPassInserita)) ? true : false;
            }


        }   catch(SQLException ex){

        }
        return false; //Callback
    }





}