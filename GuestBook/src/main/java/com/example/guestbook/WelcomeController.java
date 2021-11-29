package com.example.guestbook;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeController {

    @FXML
    private TextField username_text;

    @FXML
    private PasswordField password_text;

    @FXML
    private JFXButton login_button;

    @FXML
    private JFXButton register_button;

    @FXML
    void login(ActionEvent event) {

    }

    private void hideself(){
        Stage selfStage = (Stage) register_button.getScene().getWindow();
        selfStage.hide();
    }

    private void showRegisterScreen() throws IOException {
        Stage registerStage =new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Register-screen.fxml"));
        Scene scene = new Scene(loader.load(), 600, 400);
        registerStage.setTitle("Register Screen");
        registerStage.setScene(scene);
        registerStage.show();
    }

    @FXML
    void register(ActionEvent event) throws IOException {
        System.out.println("Register Button Pressed");
        hideself();
        showRegisterScreen();
    }

}
