package com.example.controller;
import com.example.dao.User.UserDAO;
import com.example.dao.User.UserDAOPostgres;
import com.example.models.User;

import java.sql.SQLException;

public class LoginController {

    public static boolean hasLoginSuccess(User user) {
        UserDAO<User> userDAOPostgres = new UserDAOPostgres();
        boolean successLogin = false;
        try {
            successLogin = userDAOPostgres.login(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return successLogin;
    }
}
