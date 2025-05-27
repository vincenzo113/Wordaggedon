package com.example.controller;

import com.example.alerts.Alert;
import com.example.alerts.AlertList;
import com.example.dao.UserDAO;
import com.example.dao.UserDAOPostgres;
import com.example.models.User;
import java.sql.SQLException;

public class RegisterController {

    static public void registerUser(User user) {
        UserDAO<User> userDAOPostgres = new UserDAOPostgres();
        boolean isRegistered = false;

        try {
            isRegistered = userDAOPostgres.register(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (isRegistered) {
            Alert.showAlert(AlertList.REGISTER_SUCCESS);
        } else {
            Alert.showAlert(AlertList.REGISTER_FAILURE);
        }
    }
}
