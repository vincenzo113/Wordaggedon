package com.example.dao;

import com.example.models.User;

import java.sql.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserDAOPostgresImp implements UserDAOPostgres<User> {

    private final String USER = "postgres";
    private final String PASS = "tw2024";

    @Override
    public Optional<User> select(String username) {
        return Optional.empty();
    }


    @Override
    public boolean isRegistered(User user) throws SQLException {
        ResultSet rs = null;
        try(
                Connection c = DriverManager.getConnection(URL, USER, PASS);
                Statement s = c.createStatement();
        ){
        String query = String.format("select * from user where user.username = %s and user.password = %s ",user.getUsername(),user.getPassword() );
        rs = s.executeQuery(query);

        }catch(Exception ex){
            ex.printStackTrace();
        }

        return (rs.next()) ?  true :false;

    }




    @Override
    public List<User> selectAll() {

    }

    @Override
    public void insert(User user) {
        try(
                Connection c = DriverManager.getConnection(URL, USER, PASS);
                Statement s = c.createStatement();
        ) {
            String insert = String.format("INSERT INTO public.user(username, password, role) VALUES ('%s', '%s', '%s')", user.getUsername(), user.getPassword(), user.getRole());
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