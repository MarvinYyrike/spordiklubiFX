module com.example.spordiklubifx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.spordiklubifx to javafx.fxml;
    exports com.example.spordiklubifx;
}