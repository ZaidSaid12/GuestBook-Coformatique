module com.example.guestbook {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.example.guestbook to javafx.fxml;
    exports com.example.guestbook;
}