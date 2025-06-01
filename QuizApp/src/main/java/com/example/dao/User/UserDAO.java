package com.example.dao.User;


import com.example.models.User;
import com.example.dao.ConnectionConfig;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserDAO<T> extends ConnectionConfig {

    Optional<T> select(String username);
    List<T> selectAll() throws SQLException;
    void insert(T t);
    //void update(T t);
    void delete(T t);
    User login(User user) throws SQLException;

    boolean register(User user) throws SQLException;
}
