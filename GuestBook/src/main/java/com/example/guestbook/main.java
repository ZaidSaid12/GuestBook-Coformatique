package com.example.guestbook;

import com.example.guestbook.Helper.DBHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class main extends Application {
    public static Connection connection;

    public void connect_to_database() throws SQLException, ClassNotFoundException {
        DBHandler dbHandler = new DBHandler();
        connection = dbHandler.getDbConnection();
        System.out.println("Connected to the "+connection.getCatalog()+" database successfully!");
    }

    @Override
    public void start(Stage stage) throws IOException, SQLException, ClassNotFoundException {
        // Connect to the database
        connect_to_database();

        // Create Stage for the welcome screen then load it and show it.
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("Welcome-Screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Welcome Screen!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}