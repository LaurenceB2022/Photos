package photos;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import photos.users.User;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class AlbumController {

    @FXML
    public Button copyPhoto;
    @FXML
    public Button movePhoto;
    @FXML
    public Button removeTag;
    @FXML
    public Button addTag;
    @FXML
    public Button addPhoto;
    @FXML
    public Button removePhoto;
    @FXML
    public Button captionPhoto;
    @FXML
    public Button viewSlides;
    @FXML
    private TilePane tilePane;
    @FXML
    private TilePane display;
    private static Album currentAlbum;
    private static Photo currentPhoto;
    private static User current;
    private static Stage stage;

    public static void setStage(Stage stage){
        AlbumController.stage = stage;
    }

    public static void setCurrent(User user){
        current = user;
    }
    public static void setCurrentAlbum(Album album){
        currentAlbum =album;
    }

    public void initialize() throws IOException {
        currentPhoto=null;
        display();

    }

    public void addPhoto(ActionEvent actionEvent) {
                FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("images", "*.jpg", "*.png", "*.jpeg","*.bmp","*.gif")
        );
        File file = fileChooser.showOpenDialog(stage);

        if(file!=null){
            Photo photo = new Photo((file.toURI().toString()));

            currentAlbum.addPhoto(photo);
        }
        display();
    }

    public void display(){
        tilePane.getChildren().clear();
        for(Photo photo:currentAlbum.getPhotos()){

            Image image = new Image(photo.getPath(),50, 50, false, false);

            ImageView imageView = new ImageView(image);
            imageView.setOnMouseClicked(e ->{
                currentPhoto = photo;
                Image image2 = new Image(photo.getPath(),150, 150, false, false);

                ImageView imageView2 = new ImageView(image2);
                display.getChildren().clear();
                display.getChildren().addAll(imageView2);

                copyPhoto.setDisable(false);
                movePhoto.setDisable(false);
                removePhoto.setDisable(false);
                captionPhoto.setDisable(false);


                    });



            tilePane.getChildren().addAll(imageView);

        }
    }

    public void removePhoto(ActionEvent actionEvent) {
        display.getChildren().clear();
        currentAlbum.getPhotos().remove(currentPhoto);
        currentPhoto = null;
        copyPhoto.setDisable(true);
        movePhoto.setDisable(true);
        removePhoto.setDisable(true);
        captionPhoto.setDisable(true);
        display();
    }
}
