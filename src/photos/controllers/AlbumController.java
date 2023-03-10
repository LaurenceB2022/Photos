package photos.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import photos.model.Album;
import photos.model.Photo;
import photos.model.User;

import java.io.File;
import java.io.IOException;

import static photos.Photos.writeAdmin;

/**
 * <h1>The Album Controller Class</h1>
 * Class used to control the scene that displays album information
 * @author  Ismaeel Abdulghani and Laurence Bartram
 * @version 1.0
 * @since   2022-11-30
 */
public class AlbumController {

    @FXML
    private Button copyPhoto;
    @FXML
    private Button movePhoto;
    @FXML
    private Button removeTag;
    @FXML
    private Button addTag;
    @FXML
    private Button addPhoto;
    @FXML
    private Button removePhoto;
    @FXML
    private Button captionPhoto;
    @FXML
    private Button viewSlides;
    @FXML
    private Button textAdder;
    @FXML
    private TextField text;
    @FXML
    private ComboBox tagType;
    @FXML
    private TilePane tilePane;
    @FXML
    private VBox display;
    private static Album currentAlbum;
    private static Photo currentPhoto;
    private static User current;
    private static Stage stage;
    private boolean captionMode;
    private boolean tagMode;
    private boolean copyMode;
    private boolean moveMode;
    private String currentTag;


    /**
     * Sets the stage of this scene
     * @param stage The stage
     * @return Nothing
     */
    public static void setStage(Stage stage){
        AlbumController.stage = stage;
    }

    /**
     * Sets the user of the current album beforehand
     * @param user The User
     * @return Nothing
     */

    public static void setCurrent(User user){
        current = user;
    }

    /**
     * Sets album of the scene beforehand
     * @param album The un-serialized album
     * @return Nothing
     */
    public static void setCurrentAlbum(Album album){
        currentAlbum =album;
    }

    /**
     * Called when the scene is first loaded in to set it up. Sets the observable list.
     * @return Nothing
     */

    public void initialize() throws IOException {
        tagType.setItems(FXCollections.observableList((current.tagTypes)));
        currentPhoto=null;
        display();

    }

    /**
     * Called to get a photo from the computer to add to the app
     * @param actionEvent The action event tied to the addPhoto Button.
     * @return Nothing
     */
    public void addPhoto(ActionEvent actionEvent) throws IOException {
                FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("images", "*.jpg", "*.png", "*.jpeg","*.bmp","*.gif")
        );
        File file = fileChooser.showOpenDialog(stage);

        if(file!=null){
            Photo photo=null;
            for(Photo userPhoto: current.getUserPhotos()){
                if(userPhoto.getPath().equals(file.toURI().toString())){
                    photo = userPhoto;
                }
            }
            if(photo==null){
                photo = new Photo((file.toURI().toString()));
            }

            current.addPhoto(photo);
            currentAlbum.addPhoto(photo);
        }
        display();
        writeAdmin();
    }

    /**
     * Called to display all the different photos in the scene.
     * @return Nothing
     */
    public void display(){
        tilePane.getChildren().clear();
        for(Photo photo:currentAlbum.getPhotos()){

            Image image = new Image(photo.getPath(),50, 50, false, false);

            ImageView imageView = new ImageView(image);

            imageView.setOnMouseClicked(e ->{
                currentPhoto = photo;
                showCurrentPhoto();
                    });

            VBox imageWithCaption = new VBox(imageView);
            Label caption = new Label(photo.getCaption());
            imageWithCaption.getChildren().add(caption);


            tilePane.getChildren().addAll(imageWithCaption);

        }
        if(currentPhoto!=null){
            showCurrentPhoto();
        }

    }

    /**
     * Called to display the current selected photo in the screen.
     * @return Nothing
     */
    public void showCurrentPhoto(){
        Photo photo = currentPhoto;
        Image image2 = new Image(photo.getPath(),100, 100, false, false);

        ImageView imageView2 = new ImageView(image2);
        Label caption = new Label(photo.getCaption());

        Label date = new Label(photo.lastModified.toString());
        display.getChildren().clear();
        display.getChildren().add(imageView2);
        display.getChildren().add(caption);
        display.getChildren().add(date);
        for(String tag: currentPhoto.getTags()){
            Label tagLabel = new Label(tag);
            tagLabel.setOnMouseClicked(f->{
                currentTag = tag;
                removeTag.setDisable(false);

            });
            display.getChildren().add(tagLabel);

        }

        copyPhoto.setDisable(false);
        movePhoto.setDisable(false);
        removePhoto.setDisable(false);
        captionPhoto.setDisable(false);
        addTag.setDisable(false);
        tagType.setDisable(false);
        removeTag.setDisable(true);

    }

    /**
     * Called to remove the photo from the album
     * @param actionEvent The action event tied to the removePhoto Button.
     * @return Nothing
     */
    public void removePhoto(ActionEvent actionEvent) throws IOException {
        display.getChildren().clear();
        currentAlbum.removePhoto(currentPhoto);
        currentPhoto = null;
        copyPhoto.setDisable(true);
        movePhoto.setDisable(true);
        removePhoto.setDisable(true);
        captionPhoto.setDisable(true);
        addTag.setDisable(true);
        tagType.setDisable(true);
        removeTag.setDisable(true);
        addPhoto.setDisable(false);
        display();
        writeAdmin();
    }

    /**
     * Called to enable the caption button
     * @param actionEvent The action event tied to captionPhoto Button.
     * @return Nothing
     */
    public void enableCaption(ActionEvent actionEvent) {
        if(captionMode){
            captionMode = false;
            textAdder.setVisible(false);
            copyPhoto.setDisable(false);
            movePhoto.setDisable(false);
            removePhoto.setDisable(false);
            addTag.setDisable(false);
            tagType.setDisable(false);
            removeTag.setDisable(true);
            addPhoto.setDisable(false);

        }
        else{
            captionMode = true;
            textAdder.setVisible(true);
            textAdder.setText("Add Caption");
            copyPhoto.setDisable(true);
            movePhoto.setDisable(true);
            removePhoto.setDisable(true);
            addTag.setDisable(true);
            tagType.setDisable(true);
            removeTag.setDisable(true);
            addPhoto.setDisable(true);
        }
        display();
    }

    /**
     * Called to add a tag or caption, handles the disabling and re-enabling of Buttons.
     * @param actionEvent The action event tied to the textAdder Button.
     * @return Nothing
     */

    public void addText(ActionEvent actionEvent) throws IOException {
        if(captionMode){
            currentPhoto.setCaption(text.getText().trim());
            captionMode = false;
            textAdder.setVisible(false);
            copyPhoto.setDisable(false);
            movePhoto.setDisable(false);
            removePhoto.setDisable(false);
            addTag.setDisable(false);
            tagType.setDisable(false);
            removeTag.setDisable(true);
            addPhoto.setDisable(true);
        }
        if(tagMode){
            if(tagType.getSelectionModel().getSelectedItem()==null){
                return;
            }
            String type = ((String) tagType.getSelectionModel().getSelectedItem()).trim();
            if(type.equals("")||(text.getText().trim().equals(""))){
                return;
            }
            if(current.tagTypes.contains(type)==false){
                current.tagTypes.add(type);
                tagType.setItems(FXCollections.observableList((current.tagTypes)));
            }
            currentPhoto.addTag(tagType.getSelectionModel().getSelectedItem()+"="+text.getText().trim());
            tagMode = false;
            textAdder.setVisible(false);
            copyPhoto.setDisable(false);
            movePhoto.setDisable(false);
            removePhoto.setDisable(false);
            captionPhoto.setDisable(false);
            removeTag.setDisable(true);
            addPhoto.setDisable(false);
        }
        text.clear();
        display();
        writeAdmin();
    }

    /**
     * Called to enable the tag buttons.
     * @param actionEvent The action event tied to the addTag Button.
     * @return Nothing
     */
    public void enableTag(ActionEvent actionEvent) {
        if(tagMode){
            tagMode = false;
            textAdder.setVisible(false);
            copyPhoto.setDisable(false);
            movePhoto.setDisable(false);
            removePhoto.setDisable(false);
            captionPhoto.setDisable(false);
            removeTag.setDisable(true);
            addPhoto.setDisable(false);

        }
        else{
            tagMode = true;
            textAdder.setVisible(true);
            textAdder.setText("Add Tag");
            copyPhoto.setDisable(true);
            movePhoto.setDisable(true);
            removePhoto.setDisable(true);
            captionPhoto.setDisable(true);
            removeTag.setDisable(true);
            addPhoto.setDisable(true);
        }
        display();
    }

    /**
     * Called to remove a selected tag from a photo
     * @param actionEvent The action event tied to the removeTag Button.
     * @return Nothing
     */
    public void removeTag(ActionEvent actionEvent) throws IOException {
        currentPhoto.getTags().remove(currentTag);

        textAdder.setVisible(false);
        copyPhoto.setDisable(false);
        movePhoto.setDisable(false);
        removePhoto.setDisable(false);
        addTag.setDisable(false);
        addPhoto.setDisable(false);
        removeTag.setDisable(true);
        currentTag = null;
        display();
        writeAdmin();
    }

    /**
     * Called to copy a photo to a different album
     * @param actionEvent The action event tied to the copyPhoto Button.
     * @return Nothing
     */
    public void copyPhoto(ActionEvent actionEvent) throws IOException {
        if(copyMode){
            display.getChildren().clear();
            copyMode = false;
            copyPhoto.setDisable(true);
            addPhoto.setDisable(false);

        }
        else{
            copyMode = true;
            ListView<Album> listView= new ListView();
            display.getChildren().clear();

            listView.setItems(FXCollections.observableList((current.getAlbums())));


            listView.setOnMouseClicked(e->{
                if(listView.getSelectionModel().getSelectedItem()==currentAlbum){
                    return;
                }
                Album copyTo = listView.getSelectionModel().getSelectedItem();
                copyTo.addPhoto(currentPhoto);
                copyPhoto.setDisable(true);
                copyMode = false;
                display.getChildren().clear();
            });

            display.getChildren().add(listView);

            captionMode = false;
            tagMode = false;
            textAdder.setVisible(false);

            movePhoto.setDisable(true);
            removePhoto.setDisable(true);
            addTag.setDisable(true);
            removeTag.setDisable(true);
            addPhoto.setDisable(true);
            captionPhoto.setDisable(true);
        }

        writeAdmin();
    }

    /**
     * Called to move a photo to a different album
     * @param actionEvent The action event tied to the movePhoto Button.
     * @return Nothing
     */
    public void movePhoto(ActionEvent actionEvent) throws IOException {
        if(moveMode){
            display.getChildren().clear();
            moveMode = false;
            movePhoto.setDisable(true);
            addPhoto.setDisable(false);

        }
        else{
            moveMode = true;
            ListView<Album> listView= new ListView();
            display.getChildren().clear();

            listView.setItems(FXCollections.observableList((current.getAlbums())));


            listView.setOnMouseClicked(e->{
                if(listView.getSelectionModel().getSelectedItem()==currentAlbum){
                    return;
                }

                Album copyTo = listView.getSelectionModel().getSelectedItem();
                copyTo.addPhoto(currentPhoto);
                currentAlbum.removePhoto(currentPhoto);
                movePhoto.setDisable(true);
                moveMode = false;
                display.getChildren().clear();
                currentPhoto = null;
                display();
            });

            display.getChildren().add(listView);

            captionMode = false;
            tagMode = false;
            textAdder.setVisible(false);

            movePhoto.setDisable(true);
            removePhoto.setDisable(true);
            addTag.setDisable(true);
            removeTag.setDisable(true);
            addPhoto.setDisable(true);
            captionPhoto.setDisable(true);
        }
        writeAdmin();

    }

    /**
     * Called to move to the Slideshow scene
     * @param actionEvent The action event tied to the viewSlides Button
     * @return Nothing
     */
    public void viewSlideshow(ActionEvent actionEvent) throws IOException {
        SlideshowController.setCurrentAlbum(currentAlbum);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../views/Slideshow.fxml"));

        AnchorPane root = (AnchorPane)loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Called to move back to the User Screen
     * @param actionEvent The action event tied to the goBack Button.
     * @return Nothing
     */
    public void goBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../views/User.fxml"));

        AnchorPane root = (AnchorPane)loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
