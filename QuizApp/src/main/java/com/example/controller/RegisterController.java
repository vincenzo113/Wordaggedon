package com.example.controller;

import com.example.dao.User.UserDAO;
import com.example.dao.User.UserDAOPostgres;
import com.example.models.User;
import java.sql.SQLException;

public class RegisterController {

    public static boolean hasRegisterSuccess(User user) {
        UserDAO<User> userDAOPostgres = new UserDAOPostgres();
        boolean successRegistrazione = false;

        try {
            successRegistrazione = userDAOPostgres.register(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return successRegistrazione;

    }
}
