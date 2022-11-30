package photos.controllers;

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
import javafx.stage.Stage;
import photos.model.Album;
import photos.model.Photo;
import photos.model.User;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static photos.Photos.writeAdmin;

/**
 * <h1>The User Screen Controller</h1>
 * This class that is used to manage the User.fxml file,
 * switch the stage to the User stage, handle button and text inputs,
 * and manage the ListViews and ImageViews present in the current scene.
 *
 * @author  Ismaeel Abdulghani and Laurence Bartram
 * @version 1.0
 * @since   2022-11-29
 */

public class UserController{
    @FXML
    private TextField enterAlbum;
    @FXML
    private TextField searchPhotos;
    @FXML
    private Button searchDateRange;

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
    private Button clear;



    @FXML
    private ListView<Album> albums;
    @FXML
    private TilePane photos;
    private ArrayList<Photo> photos_list;

    private static User current;
    private static Stage stage;

    /**
     * This method takes in a Stage parameter, and sets the UserController stage variable using it.
     * @param stage The inherited Stage object.
     * @return nothing
     */

    public static void setStage(Stage stage){
        UserController.stage = stage;
    }

    /**
     * This method takes in a User parameter, and sets the current User object to it.
     * @param user The inherited User object.
     */
    public static void setCurrent(User user){
        current = user;
    }

    /**
     * This method initializes the observable list using the User object current.
     * @throws IOException
     * @returns Nothing
     *
     */
    public void initialize() throws IOException {

        albums.setItems(FXCollections.observableList((current.getAlbums())));
    }

    /**
     * This method deletes the selected item after receiving the action event.
     * Disables the Album modification Buttons depending on the selected items available.
     * @param actionEvent The action event tied to the deleteAlbum Button.
     */
    public void deleteAlbum(ActionEvent actionEvent) {
        current.deleteAlbum(albums.getSelectionModel().getSelectedItem());
        albums.setItems(FXCollections.observableList((current.getAlbums())));
        if(albums.getSelectionModel().getSelectedItem()==null){
            deleteAlbum.setDisable(true);
            openAlbum.setDisable(true);
            renameAlbum.setDisable(true);
        }
    }

    /**
     * This method handles the disabling and re-enabling of Buttons.
     * @param mouseEvent The mouse event tied to the selection of the observable list.
     */
    public void albumButtons(MouseEvent mouseEvent) {
        if(albums.getSelectionModel().getSelectedItem()!=null){
            deleteAlbum.setDisable(false);
            openAlbum.setDisable(false);
            if(enterAlbum.getText().trim().length()>0){
                renameAlbum.setDisable(false);
            }
        }

    }


    /**
     * This method handles modifying an existing Album's name String. Handles disabling and re-enabling
     * Button options.
     * @param keyEvent the event tied to the enterAlbum TextField.
     */
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

    /**
     * This method creates an Album object, and adds it to the current User.
     * Depends on the value in the enterAlbum TextField.
     * @param actionEvent The action event tied to the createAlbum Button.
     * @throws IOException
     */
    public void createAlbum(ActionEvent actionEvent) throws IOException {
        String album_name = enterAlbum.getText().trim();
        if(album_name.length() < 1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid name");
            alert.show();

            createAlbum.setDisable(true);
            return;
        }
        current.createAlbum(album_name);
        enterAlbum.clear();

        //Checks if we're creating an Album from search results. Condition being if the photo view is enabled and there
        //is 1 or more photos found from the search results.
        if(!photos.isDisable() && photos_list.size() > 0){
            Album current_album = current.getAlbum(album_name);
            for(int index = 0; index < photos_list.size(); index++){
                current_album.addPhoto(photos_list.get(index));
            }
            photos.setDisable(true);

        }

        albums.setItems(FXCollections.observableList((current.getAlbums())));

        createAlbum.setDisable(true);
        writeAdmin();
    }

    /**
     * This method renames the selected Album using the String stored in the
     * enterAlbum TextField. Updates the observableList to reflect the changes.
     * @param actionEvent The action event tied to the renameAlbum Button.
     * @throws IOException
     */
    public void renameAlbum(ActionEvent actionEvent) throws IOException {
        albums.getSelectionModel().getSelectedItem().rename(enterAlbum.getText().trim());
        albums.setItems(FXCollections.observableList((current.getAlbums())));
        writeAdmin();
    }

    /**
     * This method opens the Album selected in the albums ListView.
     * @param actionEvent The action event tied to the openAlbum Button.
     * @throws IOException
     */
    public void openAlbum(ActionEvent actionEvent) throws IOException {

        AlbumController.setCurrentAlbum(albums.getSelectionModel().getSelectedItem());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../views/Album.fxml"));
        AnchorPane root = (AnchorPane)loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method performs a conjunctive search on the String in the searchPhotos TextField.
     * It compares said String against the String tags stored in all stored Photos for the current
     * User. Displays all photos containing both tag strings.
     * @param actionEvent The action event tied to the conjunctiveSearch Button.
     * @throws IOException
     */
    public void conjunctiveSearch(ActionEvent actionEvent) throws IOException {
        photos_list = new ArrayList<Photo>();
        photos.getChildren().clear();
        String tag_string = searchPhotos.getText().trim();
        String tag1;
        String tag2;
        String[] sub = tag_string.split(" ");

        //Checks if the tag is in the correct form
        if(!tag_string.contains(" ") || sub.length != 2 ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Incorrect format. Tags need to be in the form [tag1_type=tag1_name] [tag2_type=tag2_name]. For example: 'location=blue person=red'");
            alert.show();

            return;
        }
        //Assigns the tag type and tag value
        tag1 = sub[0];
        tag2 = sub[1];
        boolean exists = false;
        Photo current_photo;

        //Goes through all albums and adds photos that matches the tag (Note: Bug will occur with photo that occurs in multiple albums

        //Checks first tag
        for(Photo e:current.getUserPhotos()){

            for(String tag_1: e.getTags()){
                //Checks if first tag exists
                if(tag_1.equals(tag1)){

                    //Checks for the existence of the second tag
                    for(String tag_2: e.getTags()){
                        if(tag_2.equals(tag2)){
                            photos_list.add(e);
                            exists = true;
                            break;
                        }
                    }
                }
            }
        }

        //Checks if both tags were found, if not displays alert and returns
        if(!exists){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Tag(s) entered does not exist");
            alert.show();

            return;
        }

        photos.setDisable(false);

        for(Photo photo:photos_list){ //Displays photos

            Image image = new Image(photo.getPath(),50, 50, false, false);

            ImageView imageView = new ImageView(image);

            photos.getChildren().addAll(imageView);

        }
        createAlbum.setDisable(false); //Enables option to create an album based on the search results

        writeAdmin();
    }

    /**
     * This method performs a disjunctive search on the String in the searchPhotos TextField.
     * It compares said String against the String tags stored in all stored Photos for the current
     * User. Displays all photos containing either one or both of the two Strings in
     * the ImageView.
     * @param actionEvent The action event tied to the disjunctiveSearch Button.
     * @throws IOException
     */
    public void disjunctiveSearch(ActionEvent actionEvent) throws IOException {
        photos_list = new ArrayList<Photo>();
        photos.getChildren().clear();
        String tag_string = searchPhotos.getText().trim();
        String tag1;
        String tag2;
        String[] sub = tag_string.split(" ");

        //Checks if the tag is in the correct form
        if(!tag_string.contains(" ") || sub.length != 2 ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Incorrect format. Tags need to be in the form [tag1_type=tag1_name] [tag2_type=tag2_name]. For example: 'location=blue person=red'");
            alert.show();

            return;
        }
        //Assigns the tag type and tag value
        tag1 = sub[0];
        tag2 = sub[1];
        Photo current_photo;


        //Checks first tag
        for(Photo e:current.getUserPhotos()){

            boolean exists = false;

            for(String tag_1: e.getTags()){
                //Checks if first tag exists
                if(tag_1.equals(tag1)){
                    photos_list.add(e);
                    exists = true;
                    break; //Continues to next photo, since tag was found
                }
            }
            if(!exists){
                //Checks for the existence of the second tag. This only runs if the first tag wasn't found
                for(String tag_2: e.getTags()){
                    if(tag_2.equals(tag2)){
                        photos_list.add(e);
                        break;
                    }
                }
            }

        }
        photos.setDisable(false);

        for(Photo photo:photos_list){
            Image image = new Image(photo.getPath(),50, 50, false, false);
            ImageView imageView = new ImageView(image);
            photos.getChildren().addAll(imageView);

        }
        createAlbum.setDisable(false); //Enables option to create an album based on the search results

        writeAdmin();
    }

    /**
     * This method enables the Photo search Buttons.
     * @param actionEvent The action event tied to the addTagSearch Button.
     */


    /**
     * This method enables the addTagSearch Button if there exists text in the searchPhotos field.
     * @param actionEvent The action event tied to the searchPhotos TextField.
     * @throws IOException
     */
    public void photoSearch(KeyEvent actionEvent) throws IOException {
        if(searchPhotos.getText().trim().length()>0){
            searchDateRange.setDisable(false);
            singularSearch.setDisable(false);
            conjunctiveSearch.setDisable(false);
            disjunctiveSearch.setDisable(false);
            clear.setDisable(false);
        }
        else{
            searchDateRange.setDisable(true);
            singularSearch.setDisable(true);
            conjunctiveSearch.setDisable(true);
            disjunctiveSearch.setDisable(true);
            clear.setDisable(true);
        }


    }

    public void clearPhotoSearch(ActionEvent actionEvent){
        photos_list = new ArrayList<Photo>();
        photos.getChildren().clear();

    }

    /**
     * This method checks the singular tag String entered, and displays the photos containing it
     * in the ImageView.
     * @param actionEvent The action event tied with the singularSearch Button.
     * @throws IOException
     */
    public void checkTagsSingular(ActionEvent actionEvent) throws IOException {
        //Disables other buttons

        photos_list = new ArrayList<Photo>();
        photos.getChildren().clear();
        //Gets the textfield string
        String tag_string = searchPhotos.getText().trim();
        String tag_type;
        String tag_details;
        String[] sub = tag_string.split("=");
        //Checks if the tag is in the correct form
        if(!tag_string.contains("=") || sub.length != 2 ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Incorrect format. Singular tags need to be in the form [tag type]=[tag name]. For example: 'location=blue'");
            alert.show();

            return;
        }
        //Assigns the tag type and tag value
        tag_type = sub[0];
        tag_details = sub[1];


        //Goes through all albums and adds photos that matches the tag (Note: Bug will occur with photo that occurs in multiple albums

        for(Photo e:current.getUserPhotos()){
            for(String tag: e.getTags()){
                if(tag.equals(tag_string)){

                    photos_list.add(e);
                    break;
                }
            }
        }

        //Sets the photos that match the entered tag to the listview
        photos.setDisable(false);
        photos.getChildren().clear();
        for(Photo photo:photos_list){
            Image image = new Image(photo.getPath(),50, 50, false, false);

            ImageView imageView = new ImageView(image);

            photos.getChildren().addAll(imageView);

        }

        writeAdmin();
        createAlbum.setDisable(false); //Enables option to create an album based on the search results


    }

    /**
     * This method checks if the previously split String is a valid date. The split String array will
     * always be of length 3.
     * @param date_full The String array of the entered date.
     * @return True if date_full is a valid date, False if date_full is not a valid date.
     */
    public boolean checkDate(String[] date_full){
        //Note that date length must first be validated by checkDateRange, in order for this method to function
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
        if(year_int < 0 || year_int > 2022){
            return false;
        }
        if(month_int < 1 || month_int > 12){
            return false;
        }else{
            if(date_int < 1){
                return false;
            }

            //Checks if date is greater than max date (31) for corresponding month
            if( ((month_int == 1 || month_int == 3 || month_int == 5 || month_int == 7 || month_int == 8 || month_int == 10 || month_int == 12))
               && (date_int > 31) ){
                return false;
            } //Checks if date is greater than 29 for leap year
            if((month_int == 2) && ((year_int%4 == 0) || (year_int%100 == 0)) && (date_int > 29) ){
                return false;
            } //Checks if date is greater than 28 for non-leap year
            if((month_int == 2) && (year_int%4 != 0) && date_int > 28){
                return false;
            } //Checks if date is greater than max date (3) for corresponding month
            if((month_int == 4 || month_int == 6 || month_int == 9 || month_int == 11) && (date_int > 30)){
                return false;
            }

        }
        return true;
    }

    /**
     * This method displays the Alert error for an incorrectly entered date String.
     */
    public void displayDateAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(
                "Incorrect format. Tags need to be in the format [MM/DD/YYYY] or [MM/DD/YYYY-MM/DD/YYYY]. For Example: '10/19/2022-10/21-2022'" );
        alert.show();

        return;
    }

    /**
     * This method checks if the date(s) in the searchPhotos TextField in String format
     * is a valid date or not. If the date or range is valid, it displays the matching Photos in
     * the ImageView.
     * @param actionEvent
     * @throws IOException
     */
    public void checkDateRange(ActionEvent actionEvent) throws IOException {

        photos_list = new ArrayList<Photo>();
        photos.getChildren().clear();
        String date_string = searchPhotos.getText().trim();
        String[] dates;
        LocalDate date_start;
        LocalDate date_end;
        dates = date_string.split("-");
        int length = dates.length;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
        ArrayList<Album> albums_user = current.getAlbums();
        photos_list = new ArrayList<Photo>();

        //Checks if input is in date - date format
        if(dates.length == 2){
            String date_start_s = dates[0];
            String date_finish_s = dates[1];
            String[] date1 = dates[0].split("/");
            String[] date2 = dates[1].split("/");
            //Checks if "MM" / "DD" / "YYYY" exists
            if(date1.length != 3 || date2.length != 3){
                displayDateAlert();
                return;
            }
            boolean date1_valid = checkDate(date1);
            boolean date2_valid = checkDate(date2);
            //Checks if any date month or year is invalid
            if(!date1_valid || !date2_valid){
                displayDateAlert();
                return;
            }


            //Valid date, formats the strings to a LocalDate
            date_start = LocalDate.parse(date_start_s, formatter);
            date_end = LocalDate.parse(date_finish_s, formatter);

        }
        else if(dates.length == 1){ //1 Date entered

            String date_start_s = dates[0];
            String[] date = date_string.split("/");
            if(date.length<3){
                displayDateAlert();
                return;
            }
            boolean date_valid = checkDate(date);
            if(date.length != 3 || !date_valid){ //Only 1 date, so there must be 3 fields
                displayDateAlert();
                return;
            }
            //Valid date, converts to LocalDate
            date_start = LocalDate.parse(date_start_s, formatter);
            date_end = LocalDate.parse(date_start_s, formatter);

        }else{ // Too many arguments entered, displays error and returns
            displayDateAlert();
            return;
        }


        for(Photo e:current.getUserPhotos()){

            LocalDate current_date = e.getLast_date_modified();

            //Date range
            if (dates.length == 2) {
                //Current photo date falls outside of range, continues to next photo
                if(!current_date.isAfter(date_start) || !current_date.isBefore(date_end)){
                    continue;
                }
            } else{ //Singular date is not on the specified day
                if(!current_date.isEqual(date_start)){
                    continue;
                }
            }
            //Passes all checks, adds the photo to the list
            photos_list.add(e);
            writeAdmin();
        }

        //Adds all matched photos to photos_matched

        //Sets and displays photos
        //Display the photos in the date range, and disables the option to create an Album out of the results

        photos.setDisable(false);
        photos.getChildren().clear();
        for(Photo photo:photos_list){

            Image image = new Image(photo.getPath(),50, 50, false, false);

            ImageView imageView = new ImageView(image);


            photos.getChildren().addAll(imageView);

        }

        createAlbum.setDisable(false);

        writeAdmin();

    }

    /**
     * This method exits out to the Login Screen upon the selection of the LogoutB Button.
     * @throws IOException
     */

    public void closeAndLogout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../views/Login.fxml"));
        AnchorPane root = (AnchorPane)loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
