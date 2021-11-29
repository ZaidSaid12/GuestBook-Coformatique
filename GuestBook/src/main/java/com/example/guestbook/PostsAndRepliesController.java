package com.example.guestbook;

import com.mysql.cj.jdbc.JdbcPreparedStatement;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.example.guestbook.main.connection;

public class PostsAndRepliesController implements Initializable {
    private static Author author;
    private static JdbcPreparedStatement preparedStatement;
    private ArrayList<Post> postList = new ArrayList<Post>();
    public PostsAndRepliesController() {
    }

    public PostsAndRepliesController(Author author) {
        this.author = author;
    }

    public void readFromDB() throws SQLException {
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
