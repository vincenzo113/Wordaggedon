package com.example.controller;
import com.example.dao.User.UserDAOPostgres;
import com.example.models.User;

import java.sql.SQLException;

public class LoginController {
    UserDAOPostgres udp = new UserDAOPostgres();

    public boolean hasLoginSuccess(User user) {
        try {
            boolean result = udp.login(user);

            if (result) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }
}
