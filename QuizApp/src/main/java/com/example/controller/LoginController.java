package com.example.controller;
import com.example.alerts.Alert;
import com.example.alerts.AlertList;
import com.example.dao.UserDAOPostgres;
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
                Alert.showAlert(AlertList.LOGIN_FAILURE);
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }
}
