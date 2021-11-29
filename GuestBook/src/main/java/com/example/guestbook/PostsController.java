package com.example.guestbook;

import com.jfoenix.controls.JFXButton;
import com.mysql.cj.jdbc.JdbcPreparedStatement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static com.example.guestbook.main.connection;

public class PostsController implements Initializable {
    private static Author author;
    @FXML
    private Label WelcomeLabel;

    @FXML
    private JFXButton writeMessage_Button;

    @FXML
    private TextArea message_text_area;

    private static JdbcPreparedStatement preparedStatement;

    public PostsController() {
    }

    public PostsController(Author author) {
        this.author = author;
    }






    @FXML
    void writeMessage(ActionEvent event) throws SQLException {
        String message = message_text_area.getText();
        System.out.println(message);
        LocalDate date = LocalDate.now();
        String insertStatement = "INSERT INTO posts (body,date,author_id)" +
                "VALUES (?,?,?)";

        preparedStatement = (JdbcPreparedStatement) connection.prepareStatement(insertStatement);

        preparedStatement.setString(1, message);
        preparedStatement.setDate(2, Date.valueOf(date));
        preparedStatement.setInt(3, author.getId());

        preparedStatement.executeUpdate();

        System.out.println("Inserted Post Successfully");


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.WelcomeLabel.setText("Welcome " + this.author.getName());
        this.WelcomeLabel.setAlignment(Pos.CENTER);
    }
}
