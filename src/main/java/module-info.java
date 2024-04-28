module com.example.nutrition {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens Boundary to javafx.fxml;
    exports Boundary;
}