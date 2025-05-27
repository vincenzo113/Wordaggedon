package com.example.controller;
import com.example.dao.UserDAOPostgresImp;
import com.example.models.User;
import javafx.scene.control.Alert;

import java.sql.SQLException;

public class LoginController {
    UserDAOPostgresImp udp = new UserDAOPostgresImp();




    public void login(User user) {
        try {
            if(udp.isRegistered(user)) {
                //Va alla pagina principale

            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login fallito");
                alert.setHeaderText(null);
                alert.setContentText("Username o password non validi.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            //Inserire un alert di default per ogni catch
        }

    }
}
