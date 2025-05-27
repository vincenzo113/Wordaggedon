package com.example.alerts;

public class Alert {

    public static void showAlert(AlertList alertType) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);

        if(alertType == AlertList.LOGIN_SUCCESS) {
            alert.setTitle("Login Success");
            alert.setHeaderText(null);
            alert.setContentText(alertType.getMessage());
        } else if(alertType == AlertList.LOGIN_FAILURE) {
            alert.setTitle("Login Failure");
            alert.setHeaderText(null);
            alert.setContentText(alertType.getMessage());
        } else if(alertType == AlertList.REGISTER_SUCCESS) {
            alert.setTitle("Registration Success");
            alert.setHeaderText(null);
            alert.setContentText(alertType.getMessage());
        } else if(alertType == AlertList.REGISTER_FAILURE) {
            alert.setTitle("Registration Failure");
            alert.setHeaderText(null);
            alert.setContentText(alertType.getMessage());
        } else if(alertType == AlertList.FIELDS_EMPTY) {
            alert.setTitle("Fields Empty");
            alert.setHeaderText(null);
            alert.setContentText(alertType.getMessage());
        } else if(alertType == AlertList.DATABASE_ERROR) {
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText(alertType.getMessage());
        } else if(alertType == AlertList.USER_NOT_FOUND) {
            alert.setTitle("User Not Found");
            alert.setHeaderText(null);
            alert.setContentText(alertType.getMessage());
        } else if(alertType == AlertList.PASSWORD_MISMATCH) {
            alert.setTitle("Password Mismatch");
            alert.setHeaderText(null);
            alert.setContentText(alertType.getMessage());
        }
        alert.showAndWait();
    }
}
