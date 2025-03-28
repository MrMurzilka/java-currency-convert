module com.example.currency_convert {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires jdk.jsobject;
    requires org.json;

    opens com.example.currency_convert to javafx.fxml;
    exports com.example.currency_convert;
}