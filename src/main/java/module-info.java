module org.example.assigment1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.google.gson;

    opens org.example.assigment1 to javafx.fxml;
    exports org.example.assigment1;
}