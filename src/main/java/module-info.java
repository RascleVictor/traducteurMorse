module com.example.traducteurmorse {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.traducteurmorse to javafx.fxml;
    exports com.example.traducteurmorse;
}