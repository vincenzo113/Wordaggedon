package com.example.controller;

import com.example.alerts.Alert;
import com.example.alerts.AlertList;
import com.example.dao.UserDAOPostgres;
import com.example.dao.UserDAOPostgresImp;
import com.example.models.User;
import com.example.utils.AlertPrompt;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class RegisterController {
    UserDAOPostgres userDAOPostgres ;

    public boolean registerUser(String username , String password, String confirmPassword) throws SQLException {

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Alert.showAlert(AlertList.FIELDS_EMPTY);

            return false;
        }

        if (!password.equals(confirmPassword)) {
            Alert.showAlert(AlertList.PASSWORD_MISMATCH);

            return false;
        }
        User user = new User(username,password,"user");
        userDAOPostgres = new UserDAOPostgresImp();
        boolean isRegistered = userDAOPostgres.checkRegistered(user);

        if (!isRegistered) {
            Alert.showAlert(AlertList.REGISTER_SUCCESS);

            return true ;
        } else {
            Alert.showAlert(AlertList.REGISTER_FAILURE);

            return false;
        }
    }
}
