package photos;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

public class UserController{
    @FXML
    private TextField enterAlbum;
    @FXML
    private TextField searchPhotos;
    @FXML
    private Button searchDateRange;
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
    private Button conjunctiveSearch;
    @FXML
    private Button disjunctiveSearch;
    @FXML
    private Button singularSearch;
    @FXML
    private Button addTagSearch;



    @FXML
    private ListView<Album> albums;
    @FXML
    private TilePane photos;
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

    public void conjunctiveSearch(ActionEvent actionEvent){

    }
    public void disjunctiveSearch(ActionEvent actionEvent){

    }

    public void addTagSearch(ActionEvent actionEvent){
        singularSearch.setDisable(false);
        disjunctiveSearch.setDisable(false);
        conjunctiveSearch.setDisable(false);
        searchDateRange.setDisable(false);

    }

    public void photoSearch(ActionEvent actionEvent) throws IOException {
        if(searchPhotos.getText() ==null){

            addTagSearch.setDisable(true);
        }
        if(searchPhotos.getText().trim().length() > 0){
            addTagSearch.setDisable(false);
        }



    }

    public void checkTagsSingular(ActionEvent actionEvent){
        //Disables other buttons
        searchPhotos.setDisable(true);
        disjunctiveSearch.setDisable(false);
        conjunctiveSearch.setDisable(false);
        searchDateRange.setDisable(false);
        photos_list = new ArrayList<Photo>();
        //Gets the textfield string
        String tag_string = searchPhotos.getText().trim();
        String tag_type;
        String tag_details;
        String[] sub = tag_string.split("=");
        //Checks if the tag is in the correct form
        if(!tag_string.contains("=") || sub.length != 2 ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Incorrect format. Singular tags need to be in the form [tag type]=[tag name]");
            alert.show();
            searchPhotos.setDisable(false);
            singularSearch.setDisable(true);
            return;
        }
        //Assigns the tag type and tag value
        tag_type = sub[0];
        tag_details = sub[1];


        //Goes through all albums and adds photos that matches the tag (Note: Bug will occur with photo that occurs in multiple albums

        for(Photo e:current.userPhotos){

            for(String tag: e.getTags()){
                if(tag.equals(tag_string)){

                    photos_list.add(e);
                    break;
                }
            }
        }
//        for(int index = 0; index < albums_user.size(); index++){
//            Album current_album = albums_user.get(index);
//            //Goes through all photos in the current album
//            for(int indexP = 0; indexP < current_album.getPhotos().size(); indexP++){
//                Photo current_photo = current_album.getPhotos().get(indexP);
//                ArrayList<Tag> current_tags = current_photo.getObj_tags();
//                //Goes through all the current tag values
//                for(int indexT = 0; indexT < current_tags.size(); indexT++){
//                    Tag current_tag = current_tags.get(indexT);
//                    if(current_tag.compareTo(tag_type, tag_details)){
//                        photos_list.add(current_photo);
//                        break; //breaks out of tag loop
//                    }
//                }
//            }
//
//
//        }

        //Sets the photos that match the entered tag to the listview
        photos.setDisable(false);
        photos.getChildren().clear();
        for(Photo photo:photos_list){

            Image image = new Image(photo.getPath(),50, 50, false, false);

            ImageView imageView = new ImageView(image);






            photos.getChildren().addAll(imageView);

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
    public void displayDateAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Incorrect format. Tags need to be in the format [tag type]=[tag name]");
        alert.show();
        searchPhotos.setDisable(false);
        searchDateRange.setDisable(true);
        return;
    }

//    public void checkDateRange(ActionEvent actionEvent){
//        searchPhotos.setDisable(true);
//        String date_string = searchPhotos.getText().trim();
//        String dates[];
//        String date1[];
//        String date2[];
//        LocalDate date_start;
//        LocalDate date_end;
//        dates = date_string.split("-");
//        boolean incorrect_format;
//
//
//        //Determines if the searchfield is for a specific date, or range of dates
//        if(date_string.contains("-") && dates.length == 2 && date1.length == 3 && date2.length == 3){ //Range of dates
//            date1 = dates[0].split("/");
//            date2 = dates[1].split("/");
//            boolean date1_valid = checkDate(date1);
//            boolean date2_valid = checkDate(date2);
//            if(!date1_valid || !date2_valid){
//                incorrect_format = true;
//            }
//            else{
//                incorrect_format = false;
//            }
//
//        }
//        else if(dates){
//            dates = date_string.split("/");
//            if(dates.length != 3){ //Only 1 date, so there must be 3 fields
//                incorrect_format = true;
//            }
//            incorrect_format = checkDate(dates);
//
//        }
//
//        //Format was determined to be false. Set alert and return
//        if(incorrect_format){
//            displayDateAlert();
//            return;
//        }
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
//        ArrayList<Album> albums_user = current.getAlbums();
//        photos_list = new ArrayList<Photo>();
//        if(dates.length == 2){
//
//            String date_start_s = date1[0] + "/" + date1[1] + date1[2];
//            date_start = LocalDate.parse();
//        }
//
//        ArrayList<Album> albums_user2 = current.getAlbums();
//        for(int index = 0; index < albums_user2.size(); index++){ //Goes through all albums
//            Album current_album = albums_user2.get(index);
//            for(int indexP = 0; indexP < current_album.getPhotos().size(); indexP++){
//                Photo current_photo = current_album.getPhotos().get(indexP);
//                Date current_date = current_photo.getLastModified();
//                //Date range
//                if(dates.length == 2){
//
//                }
//                else{ //Singular date
//
//                }
//
//            }
//
//
//        }

//        //Adds all matched photos to photos_matched
//
//        //Sets and displays
//        photos.setItems(FXCollections.observableList(photos_list));
//        //Display the photos in the date range, and disables the option to create an Album out of the results
//        albums.setDisable(true);
//        createAlbum.setDisable(false);
//
//        searchPhotos.setDisable(true);
//
//
//    }

    public void closeAndLogout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Login.fxml"));
        AnchorPane root = (AnchorPane)loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
