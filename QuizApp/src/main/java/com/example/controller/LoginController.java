package com.example.controller;
import com.example.alerts.Alert;
import com.example.alerts.AlertList;
import com.example.dao.UserDAOPostgresImp;
import com.example.models.User;

import java.sql.SQLException;

public class LoginController {
    UserDAOPostgresImp udp = new UserDAOPostgresImp();


    public boolean login(User user) {
        try {
            if (udp.login(user)) {
                //Va alla pagina principale

                return true;
            } else {
                Alert.showAlert(AlertList.LOGIN_FAILURE);


            }
        } catch (SQLException e) {
            //Inserire un alert di default per ogni catch
        }

        return false;
    }
}
