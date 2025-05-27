package com.example.dao;

import com.example.models.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserDAOPostgres<T> {
    public static final String URL = "jdbc:postgresql://localhost:5432/quiz";

    Optional<T> select(String username);
    List<T> selectAll() throws SQLException;
    void insert(T t);
    //void update(T t);
    void delete(T t);
    boolean login(User user) throws SQLException;

    boolean checkRegistered(User user) throws SQLException;
}
