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
/**
 * <h1>The Slideshow Screen Controller</h1>
 * This class that is mainly used to manage the Slideshow.fxml file,
 * switch the stage to the User stage, handle button and text inputs,
 * and manage the ListViews and ImageViews.
 *
 * @author  Ismaeel Abdulghani and Laurence Bartram
 * @version 1.0
 * @since   2022-11-29
 */

/**
 * <h1>The Slideshow Controller Class</h1>
 * Class that is used to control the Slideshow Scene
 * @author  Ismaeel Abdulghani and Laurence Bartram
 * @version 1.0
 * @since   2022-11-30
 */
public class SlideshowController {

    private int index = 0;
    private static Album currentAlbum;
    private static Stage stage;
    @FXML
    private ImageView picture;

    /**
     * Used to go back to the album screen
     * @param actionEvent
     * @return Nothing
     */

    public void goBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Album.fxml"));
        AnchorPane root = (AnchorPane)loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Called when scene is first loaded
     * @return Nothing
     */
    public void initialize(){
        if(currentAlbum.getPhotos().size()==0){
            return;
        }
        picture.setImage(new Image(currentAlbum.getPhotos().get(0).getPath(),200,200,false,false));
    }

    /**
     * Used to set the stage
     * @param stage
     * @return Nothing
     */
    public static void setStage(Stage stage) {
        SlideshowController.stage = stage;
    }

    /**
     * Used to set the album
     * @param album
     * @return Nothing
     */
    public static void setCurrentAlbum(Album album){
        currentAlbum = album;
    }


    /**
     * Used to go back to the previous picture
     * @param actionEvent
     * @return Nothing
     */
    public void prevPicture(ActionEvent actionEvent) {
        if(index==0){
            return;
        }
        index--;
        picture.setImage(new Image(currentAlbum.getPhotos().get(index).getPath(),200,200,false,false));
    }

    /**
     * Used to go to the next picture
     * @param actionEvent
     * @return Nothing
     */
    public void nextPicture(ActionEvent actionEvent) {
        if(index==currentAlbum.getPhotos().size()-1){
            return;
        }
        index++;
        picture.setImage(new Image(currentAlbum.getPhotos().get(index).getPath(),200,200,false,false));
    }

}
