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

        User stock = new User("stock");
        Photo first = new Photo("file:"+System.getProperty("user.dir")+"/"+"stockphotos/cherry-blossoms-korea-beautiful-34712339.jpeg");
        Photo second = new Photo("file:"+System.getProperty("user.dir")+"/"+"stockphotos/feet-bathroom-scale-isolated-792851.jpeg");
        Photo third = new Photo("file:"+System.getProperty("user.dir")+"/"+"stockphotos/horse-racing-hong-kong-34749739.jpeg");
        Photo fourth = new Photo("file:"+System.getProperty("user.dir")+"/"+"stockphotos/low-gi-foods-healthy-weight-loss-slimming-diet-29310784.jpeg");
        Photo fifth = new Photo("file:"+System.getProperty("user.dir")+"/"+"stockphotos/supermarket-refrigerated-shelves-december-located-parknshop-tseung-kwan-o-hong-kong-35867257.jpeg");
        Album stockAlbum = stock.createAlbum("stock");
        stockAlbum.addPhoto(first);
        stockAlbum.addPhoto(second);
        stockAlbum.addPhoto(third);
        stockAlbum.addPhoto(fourth);
        stockAlbum.addPhoto(fifth);

        UserController.setCurrent(stock);
        UserController.setStage(stage);
        AlbumController.setCurrent(stock);
        AlbumController.setStage(stage);
        SlideshowController.setStage(stage);

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