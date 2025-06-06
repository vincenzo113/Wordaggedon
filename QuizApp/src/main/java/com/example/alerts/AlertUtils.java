package com.example.alerts;

import javafx.stage.Stage;

public class AlertUtils {

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

            case  USERNAME_ALREADY_TAKEN:
                alert.setTitle("Username non disponibiles");
                break;
            case  MODIFICA_USERNAME_SUCCESS:
                alert.setTitle("Modifica a buon fine");
                break;
            case MODIFICA_USERNAME_NON_AVVENUTA:
                alert.setTitle("Modifica non avvenuta,prova a cambiare l'username e riprova");
                break;

            case USERNAME_FORMAT_NON_CORRETTO:
                    alert.setTitle("Inserisci correttamente l'username , sono ammessi solo caratteri alfanumerici");
                    break;



            case PASSWORD_NON_CORRETTA:
                alert.setTitle("La vecchia password inserita non è corretta");
                break;

            case CAMBIO_PASSWORD_SUCCESS:
                alert.setTitle("Password cambiata correttamente");
                break;

            case NESSUNA_MODIFICA_DI_PASSWORD:
                alert.setTitle("Non hai fatto alcuna modifica");
                break;

            default:
                alert.setTitle("Alert");
                break;


        }

        alert.setContentText(alertType.getMessage());
        alert.showAndWait();
    }
}
