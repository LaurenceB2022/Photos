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

import java.io.IOException;
import java.util.ArrayList;

public class Photos extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        User stock = new User("stock");

        Photo first = new Photo("stockphotos/cherry-blossoms-korea-beautiful-34712339.jpeg");

        Album stockAlbum = stock.createAlbum("stock");
        stockAlbum.addPhoto(first);
        UserController.setCurrent(stock);
        UserController.setStage(stage);
        AlbumController.setCurrent(stock);
        AlbumController.setStage(stage);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("User.fxml"));
        AnchorPane root = (AnchorPane)loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }




}