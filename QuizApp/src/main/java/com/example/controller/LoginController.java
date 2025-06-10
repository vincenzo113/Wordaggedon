package com.example.controller;
import com.example.dao.User.UserDAO;
import com.example.dao.User.UserDAOPostgres;
import com.example.exceptions.DatabaseException;
import com.example.models.User;

import javax.xml.crypto.Data;
import java.sql.SQLException;

public class LoginController {

    public static User hasLoginSuccess(User user) {
        UserDAO<User> userDAOPostgres = new UserDAOPostgres();
        try {
            return userDAOPostgres.login(user);
        } catch (DatabaseException e) {
                e.printStackTrace();
        }

        return null;
    }
}
