module com.example.viewtest {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens model to javafx.fxml;
    exports model;
}