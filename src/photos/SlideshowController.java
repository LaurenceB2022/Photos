package photos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SlideshowController {

    private int index = 0;
    private static Album currentAlbum;
    private static Stage stage;
    @FXML
    public ImageView picture;

    public void goBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Album.fxml"));
        AnchorPane root = (AnchorPane)loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void initialize(){
        if(currentAlbum.getPhotos().size()==0){
            return;
        }
        picture.setImage(new Image(currentAlbum.getPhotos().get(0).path,200,200,false,false));
    }
    public static void setStage(Stage stage) {
        SlideshowController.stage = stage;
    }
    public static void setCurrentAlbum(Album album){
        currentAlbum = album;
    }

    public void prevPicture(ActionEvent actionEvent) {
        if(index==0){
            return;
        }
        index--;
        picture.setImage(new Image(currentAlbum.getPhotos().get(index).path,200,200,false,false));
    }
    public void nextPicture(ActionEvent actionEvent) {
        if(index==currentAlbum.getPhotos().size()-1){
            return;
        }
        index++;
        picture.setImage(new Image(currentAlbum.getPhotos().get(index).path,200,200,false,false));
    }
}
