package photos;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class UserController{
    @FXML
    private TextField enterAlbum;
    @FXML
    private TextField searchPhotos;
    @FXML
    private Button searchDateRange;
    @FXML
    private Button searchTags;
    @FXML
    private Button addTag;
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
    @FXML
    private ListView<Photo> photos;
    private ArrayList<Photo> photos_list;

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

    public void checkTags(ActionEvent actionEvent){
        searchPhotos.setDisable(true);
        String tag_string = searchPhotos.getText().trim();
        String tag_type;
        String tag_details;
        String[] sub = tag_string.split("=");
        if(!tag_string.contains("=") || sub.length != 2){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Incorrect format. Tags need to be in the form [tag type]=[tag name]");
            alert.show();
            searchPhotos.setDisable(false);
            return;
        }
        //Checks if there is exactly 1 tag type and 1 tag name
        if(sub.length != 2){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Incorrect format. Tags need to be in the format [tag type]=[tag name]");
            alert.show();
            searchPhotos.setDisable(false);
            return;
        }else{
            tag_type = sub[0];
            tag_details = sub[1];


        }




        createAlbum.setDisable(false); //Enables option to create an album based on the search results

        searchPhotos.setDisable(false);
    }

    //Note that date length must first be validated by checkDateRange, in order for this method to function
    public boolean checkDate(String[] date_full){
        String month = date_full[0];
        String date = date_full[1];
        String year = date_full[2];
        //Checks MM  DD  and YYYY respectively
        if(month.length() != 2 || date.length() != 2 || year.length() != 4){
            return false;
        }

        int month_int = Integer.parseInt(month);
        int date_int = Integer.parseInt(date);
        int year_int = Integer.parseInt(year);
        if(month_int < 1 || month_int > 12){
            return false;
        }
        else{

        }

        if(year_int < 0 || year_int > 2022){
            return false;
        }

        return true;
    }

    public void checkDateRange(ActionEvent actionEvent){
        searchPhotos.setDisable(true);
        String date_string = searchPhotos.getText().trim();
        String dates[];
        String date1[];
        String date2[];
        boolean incorrect_format = false;

        //Determines if the searchfield is for a specific date, or range of dates
        if(!date_string.contains("-")){ //Range of dates
            dates = date_string.split("-");
            if(dates.length != 2){
                incorrect_format = true;
            }
            else{
                date1 = dates[0].split("/");
                date2 = dates[1].split("/");
                if(date1.length != 3 || date2.length != 3){
                    incorrect_format = true;
                }
                boolean date1_valid = checkDate(date1);
                boolean date2_valid = checkDate(date2);
                if(!date1_valid || !date2_valid){
                    incorrect_format = true;
                }

            }

        }
        else{

            dates = date_string.split("/");
            if(dates.length != 3){ //Only 1 date, so there must be 3 fields
                incorrect_format = true;
            }
            incorrect_format = checkDate(dates);

        }

        //Format was determined to be false. Set alert and return
        if(!incorrect_format){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Incorrect format. Tags need to be in the format [tag type]=[tag name]");
            alert.show();
            searchPhotos.setDisable(false);
            searchDateRange.setDisable(true);
            return;
        }

        ArrayList<Album> albums_user = current.getAlbums();
        photos_list = new ArrayList<Photo>();

        //Adds all matched photos to photos_matched
        for(int index = 0; index < albums_user.size(); index++){ //Goes through all albums
            Album current_album = albums_user.get(index);
            for(int indexP = 0; indexP < current_album.getPhotos().size(); indexP++){
                Photo current_photo = current_album.getPhotos().get(indexP);
                //Date range
                if(dates.length == 2){

                }
                else{ //Singular date

                }
            }


        }
        //Sets and displays
        photos.setItems(FXCollections.observableList(photos_list));
        //Display the photos in the date range, and disables the option to create an Album out of the results
        createAlbum.setDisable(false);





    }
    public void addTagSearch(ActionEvent actionEvent){
        String textfield = searchPhotos.getText().trim();
        StringTokenizer tokenizer = new StringTokenizer(textfield);
        if(tokenizer.countTokens() < 2){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Not enough parameters entered");
            alert.show();
            return;
        }

        //Checks the tag type
        String tag

    }

    public void photoSearch(ActionEvent actionEvent) throws IOException {
        if(searchPhotos.getText().trim().length() > 0){
            searchTags.setDisable(false);
            addTag.setDisable(false);
            searchDateRange.setDisable(false);
        }
        if(searchPhotos.getText().length() == 0){
            searchTags.setDisable(true);
            addTag.setDisable(true);
            searchDateRange.setDisable(true);
        }

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
