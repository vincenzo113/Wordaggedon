module com.gruppo2.quiz {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.gruppo2.quiz to javafx.fxml;
    exports com.gruppo2.quiz;
}