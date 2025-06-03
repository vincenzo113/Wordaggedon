package com.example.alerts;

import javafx.stage.Stage;

public class Alert {

    public static void showAlert(AlertList alertType, Stage stage) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.initOwner(stage);
        alert.setHeaderText(null);

        switch (alertType) {
            case LOGIN_FAILURE:
                alert.setTitle("Login Fallito");
                break;
            case REGISTER_FAILURE:
                alert.setTitle("Registration fallita , riprova");
                break;
            case FIELDS_EMPTY:
                alert.setTitle("Errore , campi non compilati");
                break;
            case DATABASE_ERROR:
                alert.setTitle("Errore");
                break;
            case USER_NOT_FOUND:
                alert.setTitle("Utente non trovato");
                break;
            case PASSWORD_MISMATCH:
                alert.setTitle("Password non corrispondono");
                break;
            default:
                alert.setTitle("Alert");
                break;
        }

        alert.setContentText(alertType.getMessage());
        alert.showAndWait();
    }
}
