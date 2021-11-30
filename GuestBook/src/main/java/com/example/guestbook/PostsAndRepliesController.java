package com.example.guestbook;

import com.jfoenix.controls.JFXButton;
import com.mysql.cj.jdbc.JdbcPreparedStatement;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.example.guestbook.main.connection;

public class PostsAndRepliesController implements Initializable {
    private static Author author;
    private static JdbcPreparedStatement preparedStatement;
    private static Stage selfStage ;
    private static Stage senderStage;

    private ArrayList<Post> postList = new ArrayList<Post>();

    @FXML
    private VBox messages_vbox;

    @FXML
    private JFXButton backButton;

    public PostsAndRepliesController() {
    }

    public PostsAndRepliesController(Author author) {
        this.author = author;
    }

    @FXML
    void goBack(ActionEvent event) {
        hideself();
        senderStage.show();
    }

    private void hideself(){
        selfStage = (Stage) messages_vbox.getScene().getWindow();
        selfStage.hide();
    }

    public static void setSenderStage(Stage stage){
        senderStage = stage;
    }


    public void deletePost(int post_id) throws SQLException {
        System.out.println("deleted");
        String deleteStatement = "DELETE FROM posts where post_id = ?";
        preparedStatement = (JdbcPreparedStatement) connection.prepareStatement(deleteStatement);
        preparedStatement.setInt(1, post_id);
        preparedStatement.executeUpdate();
    }

    private List<String> getReplies(int post_id) throws SQLException {
        List<String> replies = new ArrayList<String>();
        String queryStatement = "SELECT * FROM replies where post_id = ?";
        preparedStatement = (JdbcPreparedStatement) connection.prepareStatement(queryStatement);
        preparedStatement.setInt(1, post_id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            replies.add(resultSet.getString(2));
        }
        return replies;
    }

    private void addReply(int post_id, String reply) throws SQLException {
        String insertStatement = "INSERT INTO replies (body, date, reply_author_id, post_id)" +
                "VALUES (?, ?, ?, ?)";
        LocalDate date = LocalDate.now();
        preparedStatement = (JdbcPreparedStatement) connection.prepareStatement(insertStatement);
        preparedStatement.setString(1, reply);
        preparedStatement.setDate(2, Date.valueOf(date));
        preparedStatement.setInt(3, author.getId());
        preparedStatement.setInt(4, post_id);
        preparedStatement.executeUpdate();
    }

    private void editDatabase(int post_id, String editedText) throws SQLException {
        String updateQuery = "update posts set body = ? where post_id = ?";
        preparedStatement = (JdbcPreparedStatement) connection.prepareStatement(updateQuery);
        preparedStatement.setString(1, editedText);
        preparedStatement.setInt(2, post_id);
        preparedStatement.executeUpdate();

    }

    public void readFromDB() throws SQLException {
        ScrollPane sp = new ScrollPane();
        String queryStatement = "SELECT * FROM posts";
        preparedStatement = (JdbcPreparedStatement) connection.prepareStatement(queryStatement);
        ResultSet resultSet = preparedStatement.executeQuery();
        // Get all posts
        while (resultSet.next()){
            postList.add(new Post(resultSet.getInt(1), resultSet.getString(2),
                    resultSet.getDate(3).toLocalDate(), resultSet.getInt(4)));
        }

        for(Post p :postList){
            System.out.println(p.toString());
            VBox post_vbox = new VBox();
            Label author_info = new Label(p.getAuthor_id()+"");
            author_info.setTextFill(Color.WHITE);

            List<String> replies = getReplies(p.getPost_id());
            VBox replies_vbox = new VBox();
            // Fill replies vbox
            for(String reply: replies){
                Label replyLabel = new Label(reply);
                replyLabel.setTextFill(Color.WHITE);
                replies_vbox.getChildren().add(replyLabel);
            }
            replies_vbox.setMaxWidth(Double.MAX_VALUE);
            replies_vbox.setPadding(new Insets(0, 40.0f, 0, 0));



            //Label post_data = new Label(p.getBody());
            TextField post_data = new TextField(p.getBody());
            post_data.setStyle("-fx-background-color: #303f9f;"+ "-fx-text-fill: #ffffff;" + "-fx-border-color: #ffffff;");
            post_data.setEditable(false);
            // Create an ok button for Editing messages

            JFXButton okButton = new JFXButton("Ok");
            okButton.getStyleClass().add("button-raised");
            okButton.setStyle("-fx-background-color: #001970");
            okButton.setTextFill(Color.WHITE);
            okButton.setVisible(false);  // By default it's hidden

            // Create a HBox to carry the post and the button
            HBox editHbox = new HBox();
            editHbox.getChildren().add(post_data);
            editHbox.getChildren().add(okButton);


            post_vbox.getChildren().add(author_info);
            post_vbox.getChildren().add(editHbox);
            // Add reply text field

            TextField newReply = new TextField();
            newReply.setPromptText("Enter Your Reply");
            post_vbox.getChildren().add(newReply);
            // Add replies vbox
            post_vbox.getChildren().add(replies_vbox);


            JFXButton editButton = new JFXButton("Edit");
            editButton.getStyleClass().add("button-raised");
            editButton.setStyle("-fx-background-color: #001970");
            editButton.setTextFill(Color.WHITE);
            editButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    System.out.println(p.toString());
                    post_data.setEditable(true);
                    post_data.setStyle("-fx-background-color: #ffffff;"+ "-fx-text-fill: #000000;");

                    okButton.setVisible(true);
                    okButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            String editedText = post_data.getText();
                            try {
                                editDatabase(p.getPost_id(), editedText);
                                post_data.setStyle("-fx-background-color: #303f9f;"+ "-fx-text-fill: #ffffff;" + "-fx-border-color: #ffffff;");
                                post_data.setEditable(false);
                                okButton.setVisible(false);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }
            });


            JFXButton replyButton = new JFXButton("Reply");
            replyButton.getStyleClass().add("button-raised");
            replyButton.setStyle("-fx-background-color: #001970");
            replyButton.setTextFill(Color.WHITE);
            replyButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    System.out.println(p.toString());
                    String replyString = newReply.getText();
                    try {
                        addReply(p.getPost_id(), replyString);
                        newReply.setText("");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });


            JFXButton deleteButton = new JFXButton("Delete");
            deleteButton.getStyleClass().add("button-raised");
            deleteButton.setStyle("-fx-background-color: #001970");
            deleteButton.setTextFill(Color.WHITE);
            deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    System.out.println(p.toString());
                    try {
                        deletePost(p.getPost_id());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });

            // Check The author of the post

            if(author.getId() != p.getAuthor_id()){
                editButton.setVisible(false);
                deleteButton.setVisible(false);
            }

            HBox buttonsList = new HBox();
            buttonsList.getChildren().add(editButton);
            buttonsList.getChildren().add(replyButton);
            buttonsList.getChildren().add(deleteButton);

            buttonsList.setMaxWidth(Double.MAX_VALUE);
            buttonsList.setAlignment(Pos.CENTER_RIGHT);
            post_vbox.getChildren().add(buttonsList);

            post_vbox.setStyle("-fx-border-color: #ffffff;" +
                    "-fx-padding: 5px;"+ "-fx-border-insets: 5px;");


            messages_vbox.getChildren().add(post_vbox);

        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println(author.getId());
        try {
            readFromDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
