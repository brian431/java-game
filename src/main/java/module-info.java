module com.example.viewtest {
    requires javafx.controls;
    requires javafx.fxml;

    opens model to javafx.fxml;
    exports model;
}