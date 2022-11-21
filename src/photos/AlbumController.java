package photos;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import photos.users.User;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Date;

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
    public Button textAdder;
    @FXML
    public TextField text;
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
                Image image2 = new Image(photo.getPath(),100, 100, false, false);

                ImageView imageView2 = new ImageView(image2);
                Label caption = new Label(photo.getCaption());
                Label date = new Label(""+new Date(1000*new File(photo.getPath()).lastModified()));
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
                removeTag.setDisable(true);


                    });

            VBox imageWithCaption = new VBox(imageView);
            Label caption = new Label(photo.getCaption());
            imageWithCaption.getChildren().add(caption);


            tilePane.getChildren().addAll(imageWithCaption);

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
        addTag.setDisable(true);
        removeTag.setDisable(true);
        addPhoto.setDisable(false);
        display();
    }

    public void enableCaption(ActionEvent actionEvent) {
        if(captionMode){
            captionMode = false;
            textAdder.setVisible(false);
            copyPhoto.setDisable(false);
            movePhoto.setDisable(false);
            removePhoto.setDisable(false);
            addTag.setDisable(false);
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
            removeTag.setDisable(true);
            addPhoto.setDisable(true);
        }

    }

    public void addText(ActionEvent actionEvent) {
        if(captionMode){
            currentPhoto.setCaption(text.getText().trim());
            captionMode = false;
            textAdder.setVisible(false);
            copyPhoto.setDisable(false);
            movePhoto.setDisable(false);
            removePhoto.setDisable(false);
            addTag.setDisable(false);
            removeTag.setDisable(true);
            addPhoto.setDisable(true);
        }
        if(tagMode){
            currentPhoto.addTag(text.getText().trim());
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
    }

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
    }

    public void removeTag(ActionEvent actionEvent) {
        currentPhoto.getTags().remove(currentTag);

        textAdder.setVisible(false);
        copyPhoto.setDisable(false);
        movePhoto.setDisable(false);
        removePhoto.setDisable(false);
        addTag.setDisable(false);
        addPhoto.setDisable(false);
        removeTag.setDisable(true);
        currentTag = null;
    }

    public void copyPhoto(ActionEvent actionEvent) {
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
            listView.getItems().remove(currentAlbum);

            listView.setOnMouseClicked(e->{
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


    }

    public void movePhoto(ActionEvent actionEvent) {
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
            listView.getItems().remove(currentAlbum);

            listView.setOnMouseClicked(e->{
                Album copyTo = listView.getSelectionModel().getSelectedItem();
                copyTo.addPhoto(currentPhoto);
                currentAlbum.getPhotos().remove(currentPhoto);
                movePhoto.setDisable(true);
                moveMode = false;
                display.getChildren().clear();
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


    }
}
