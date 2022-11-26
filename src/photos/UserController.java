package photos;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import photos.users.Admin;
import photos.users.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class UserController{
    @FXML
    private TextField enterAlbum;
    @FXML
    private Button createAlbum;
    @FXML
    private Button renameAlbum;
    @FXML
    private Button openAlbum;
    @FXML
    private Button deleteAlbum;



    @FXML
    private ListView<Album> albums;

    private static User current;
    private static Stage stage;

    public static void setStage(Stage stage){
        UserController.stage = stage;
    }

    public static void setCurrent(User user){
        current = user;
    }
    public void initialize() throws IOException {
        albums.setItems(FXCollections.observableList((current.getAlbums())));
    }

    public void deleteAlbum(ActionEvent actionEvent) {
        current.deleteAlbum(albums.getSelectionModel().getSelectedItem());
        albums.setItems(FXCollections.observableList((current.getAlbums())));
        if(albums.getSelectionModel().getSelectedItem()==null){
            deleteAlbum.setDisable(true);
            openAlbum.setDisable(true);
            renameAlbum.setDisable(true);
        }
    }

    public void albumButtons(MouseEvent mouseEvent) {
        if(albums.getSelectionModel().getSelectedItem()!=null){
            deleteAlbum.setDisable(false);
            openAlbum.setDisable(false);
            if(enterAlbum.getText().trim().length()>0){
                renameAlbum.setDisable(false);
            }
        }

    }


    public void albumNameChanged(KeyEvent keyEvent) {
        if(enterAlbum.getText().length()>0 && albums.getSelectionModel().getSelectedItem()!=null){

            createAlbum.setDisable(false);
            renameAlbum.setDisable(false);

        }
        if(enterAlbum.getText().trim().length()>0){

            createAlbum.setDisable(false);

        }
        if(enterAlbum.getText().trim().length()==0 ){

            createAlbum.setDisable(true);
            renameAlbum.setDisable(true);

        }


    }

    public void createAlbum(ActionEvent actionEvent) {
        current.createAlbum(enterAlbum.getText().trim());
        enterAlbum.clear();
        albums.setItems(FXCollections.observableList((current.getAlbums())));
        createAlbum.setDisable(true);
    }

    public void renameAlbum(ActionEvent actionEvent) {
        albums.getSelectionModel().getSelectedItem().rename(enterAlbum.getText().trim());
        albums.setItems(FXCollections.observableList((current.getAlbums())));
    }

    public void openAlbum(ActionEvent actionEvent) throws IOException {

        AlbumController.setCurrentAlbum(albums.getSelectionModel().getSelectedItem());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Album.fxml"));
        AnchorPane root = (AnchorPane)loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void photoSearch(ActionEvent actionEvent) throws IOException {

    }

    public void closeAndLogout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Login.fxml"));
        AnchorPane root = (AnchorPane)loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
