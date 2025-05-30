package com.example.controller;
import com.example.dao.User.UserDAOPostgres;
import com.example.models.User;

import java.sql.SQLException;

public class LoginController {
    UserDAOPostgres udp = new UserDAOPostgres();

    public boolean hasLoginSuccess(User user) {
        try {
            return udp.login(user);
        } catch (SQLException e) {
            return false;
        }
    }
}
