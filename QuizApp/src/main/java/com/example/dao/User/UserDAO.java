package com.example.dao.User;

import com.example.dao.URL;
import com.example.models.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserDAO<T> extends URL {

    Optional<T> select(String username);
    List<T> selectAll() throws SQLException;
    void insert(T t);
    //void update(T t);
    void delete(T t);
    boolean login(User user) throws SQLException;

    boolean register(User user) throws SQLException;
}
