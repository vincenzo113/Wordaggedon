package com.example.utils;

import javafx.scene.control.Alert;

public class AlertPrompt {

    public  static void compilaCorrettamenteICampi(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setContentText("Compila correttamente i campi seguenti");
        alert.showAndWait();
    }
}
