package com.example.guestbook;

import com.jfoenix.controls.JFXButton;
import com.mysql.cj.jdbc.JdbcPreparedStatement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.example.guestbook.main.connection;

public class RegisterController {

    private static Stage selfStage ;
    private static Stage senderStage;

    public RegisterController() {
    }

    @FXML
    private TextField registerUsername_text;

    @FXML
    private PasswordField registerPassword_text;
    @FXML
    private TextField registerEmail_text;

    @FXML
    private JFXButton registerAccount_button;
    private static JdbcPreparedStatement preparedStatement;


    public static void setSenderStage(Stage stage){
        senderStage = stage;
    }

    private void hideself(){
        selfStage = (Stage) registerAccount_button.getScene().getWindow();
        selfStage.hide();
    }



    @FXML
    void Create_Account(ActionEvent event) throws SQLException {

        // Get User's info
        String username = registerUsername_text.getText();
        String password = registerPassword_text.getText();
        String email = registerEmail_text.getText();
        System.out.println(username + " " + password + " " + email);

        String insertStatement = "INSERT INTO accounts (username,password,email)" +
                "VALUES (?,?,?)";

        preparedStatement = (JdbcPreparedStatement) connection.prepareStatement(insertStatement);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        preparedStatement.setString(3, email);
        preparedStatement.executeUpdate();

        System.out.println("Inserted Successfully");

        hideself();
        senderStage.show();


    }

}
