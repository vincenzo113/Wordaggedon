package com.example.dao;

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
            String query = String.format("select * from public.user where username = '%s'" , user.getUsername());
            rs = s.executeQuery(query);

            if(!rs.next()) {
                query = String.format("insert into public.user(username, password) values('%s','%s')", user.getUsername(), user.getPassword());
                s.executeUpdate(query);
                return true;
            }

            return false;

        }catch(Exception ex){
            ex.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean login(User user) throws SQLException {
        ResultSet rs = null;
        try(
                Connection c = DriverManager.getConnection(URL, USER, PASS);
                Statement s = c.createStatement()
        ){
        String query = String.format("select * from user where user.username = %s and user.password = %s ", user.getUsername(), user.getPassword());
        rs = s.executeQuery(query);

        }catch(Exception ex){
            ex.printStackTrace();
        }

        return (rs.next()) ?  true :false;

    }




    @Override
    public List<User> selectAll() throws SQLException {
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        try(
                Connection c = DriverManager.getConnection(URL, USER, PASS);
                Statement s = c.createStatement();
        ) {
            String select = String.format("SELECT * FROM public.user");
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
            String insert = String.format("INSERT INTO public.user(username, password, role) VALUES ('%s', '%s', '%s')", user.getUsername(), user.getPassword(), user.isAdmin());
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
            String delete = String.format("DELETE FROM public.user WHERE username = '%s'", user.getUsername());
            s.executeUpdate(delete);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}