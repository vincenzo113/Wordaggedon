package com.example.quizapp;

import com.example.database.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("registrazione-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        stage.setMinHeight(400);
        stage.setMinWidth(400);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        DatabaseConnection.createTables();

    }

    public static void main(String[] args) {
        launch();
    }
}