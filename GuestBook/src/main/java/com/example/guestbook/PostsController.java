package com.example.guestbook;

import com.jfoenix.controls.JFXButton;
import com.mysql.cj.jdbc.JdbcPreparedStatement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
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

    @FXML
    private JFXButton showMessages_button;

    private static JdbcPreparedStatement preparedStatement;

    private  Stage selfStage ;

    public PostsController() {
    }

    public PostsController(Author author) {
        this.author = author;
    }


    private void hideself(){
        selfStage = (Stage) writeMessage_Button.getScene().getWindow();
        selfStage.hide();
    }

    private void showPostsAndRepliesScreen() throws IOException {
        Stage messagesStage =new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PostsAndReplies.fxml"));
        Scene scene = new Scene(loader.load(), 700, 500);
        messagesStage.setTitle("Messages Screen");
        messagesStage.setScene(scene);
        messagesStage.show();
    }
    @FXML
    void showMessages(ActionEvent event) throws IOException {
        hideself();
        PostsAndRepliesController messagesController = new PostsAndRepliesController(author);
        showPostsAndRepliesScreen();
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
