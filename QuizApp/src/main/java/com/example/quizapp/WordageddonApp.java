package com.example.quizapp;

import com.example.controller.MainController;
import com.example.models.SessioneQuiz;
import com.example.utils.GestoreSalvataggioSessione;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WordageddonApp extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WordageddonApp.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        stage.setMinHeight(800);
        stage.setMinWidth(800);
        stage.setTitle("Wordageddon");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
            // Sostituisci questa riga con la tua sessione corrente, ad esempio:
            MainController controller = fxmlLoader.getController();

            SessioneQuiz sessioneCorrente  = controller.getSessioneCorrente();
            if (sessioneCorrente != null) {
                GestoreSalvataggioSessione.salvaSessione(sessioneCorrente);
                System.out.println("Sessione salvata prima della chiusura.");
            }
        });
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}