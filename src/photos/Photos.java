//Contributors: Laurence Bartram and and Ismaeel Abdulghani
package photos;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import photos.users.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Photos extends Application {

    @Override
    public void start(Stage stage) throws IOException {




        UserController.setStage(stage);
        AdminController.setStage(stage);
        AlbumController.setStage(stage);
        SlideshowController.setStage(stage);

        LoginController.setStage(stage);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Login.fxml"));
        AnchorPane root = (AnchorPane)loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }




}