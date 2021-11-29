package com.example.guestbook;

import com.jfoenix.controls.JFXButton;
import com.mysql.cj.jdbc.JdbcPreparedStatement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import static com.example.guestbook.main.connection;

public class WelcomeController {

    private  Stage selfStage ;


    @FXML
    private TextField username_text;

    @FXML
    private PasswordField password_text;

    @FXML
    private JFXButton login_button;

    @FXML
    private JFXButton register_button;

    private static JdbcPreparedStatement preparedStatement;

    @FXML
    void login(ActionEvent event) throws IOException, SQLException {
        System.out.println("Login Button Pressed");

        String username = username_text.getText();
        String password = password_text.getText();

        String queryStatement = "SELECT * FROM accounts where username = ? and password = ?";
        preparedStatement = (JdbcPreparedStatement) connection.prepareStatement(queryStatement);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()){
            System.out.println("found something");
            Author author = new Author(resultSet.getInt(1),resultSet.getString(2));
            System.out.println(author.toString());
            PostsController postsController = new PostsController(author);
            hideself();
            showPostsScreen();

        }
        else{
            System.out.println("Invalid Username or Password!");
        }

    }

    private void hideself(){
        selfStage = (Stage) register_button.getScene().getWindow();
        selfStage.hide();
    }

    private void showPostsScreen() throws IOException {
        Stage postsStage =new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Posts-screen.fxml"));
        Scene scene = new Scene(loader.load(), 600, 400);
        postsStage.setTitle("Posts Screen");
        postsStage.setScene(scene);
        postsStage.show();
    }

    private void showRegisterScreen() throws IOException {
        Stage registerStage =new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Register-screen.fxml"));
        Scene scene = new Scene(loader.load(), 600, 400);
        registerStage.setTitle("Register Screen");
        registerStage.setScene(scene);
        registerStage.show();

        // Set sender stage in the register controller
        RegisterController registerController = new RegisterController();
        selfStage = (Stage) register_button.getScene().getWindow();
        registerController.setSenderStage(selfStage);
    }

    @FXML
    void register(ActionEvent event) throws IOException {
        System.out.println("Register Button Pressed");
        hideself();
        showRegisterScreen();


    }

}
