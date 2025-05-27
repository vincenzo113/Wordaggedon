package com.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/quiz";
    private static final String USER = "postgres";
    private static final String PASSWORD = "tw2024";

    public static void createTables() {
        String sql = "CREATE TABLE IF NOT EXISTS public.user (" +
                "username VARCHAR(50) PRIMARY KEY," +
                "password VARCHAR(255) NOT NULL," +
                "role VARCHAR(50) NOT NULL" +
                ");";

        try (
                Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement s = c.createStatement()
        ) {
            s.execute(String.format(sql));
            System.out.println("Table user created/verified.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
