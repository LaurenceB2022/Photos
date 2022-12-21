package photos.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import photos.model.Admin;
import photos.model.User;

//import javax.swing.text.html.ListView;
import javafx.collections.FXCollections;
import java.io.*;
import java.util.ArrayList;

import static photos.Photos.writeAdmin;

/**
 * <h1>The Admin Controller Class</h1>
 * Controller class for the admin screen
 * @author  Ismaeel Abdulghani and Laurence Bartram
 * @version 1.0
 * @since   2022-11-30
 */
public class AdminController {

    private static Admin admin;
    @FXML
    private ListView<User> userView;

    @FXML
    private Button Add_User;
    @FXML
    private Button Remove_User;
    @FXML
    private Button Show_Users;
    @FXML
    private TextField User_Entry;

    private ArrayList<User> obsList;

    private static Stage stage;

    /**
     * Sets the stage for this class
     * @param stage The current Stage
     * @return Nothing
     */
    public static void setStage(Stage stage){
        AdminController.stage = stage;
    }
    /**
     * Sets the admin for the session
     * @param current The current Admin
     * @return Nothing
     */
    public static void setAdmin(Admin current){
        AdminController.admin = current;}

    /**
     * Called when the scene is first loaded. Invokes the static writeAdmin method to
     *
     * @return Nothing
     */
    public void initialize() throws IOException, ClassNotFoundException {
        obsList = new ArrayList<>();

        writeAdmin();

    }


    /**
     * Used when the "Add User" button is pressed to possibly add a new user
     * @param e The ActionEvent tied to the Add_User Button.
     * @return Nothing
     */
    public void addUser(ActionEvent e) throws IOException {
        String username = User_Entry.getText().trim();
        User_Entry.clear();
        if(username.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Field is empty.");
            alert.show();
            return;
        }
        else{
            User user = new User(username);

            //checks to see if the user already exists
            for (int i = 0; i < admin.getRegistered_users().size(); i++) {
                User currentUser = admin.getRegistered_users().get(i);

                int cmp = currentUser.getUsername().compareToIgnoreCase(username);

                if (cmp == 0 ){ //Duplicate user
                        Alert duplicate = new Alert(Alert.AlertType.ERROR);
                        duplicate.setHeaderText("Unable To Add Duplicate User.");
                        duplicate.show();
                        return;
                }
            }

            admin.addUser(user);
            writeAdmin();
            userView.setItems(FXCollections.observableList(obsList));
            userView.getSelectionModel().select(user);
            Add_User.setDisable(true);
        }

    }

    /**
     * Used to show the different users in the list
     * @param e The ActionEvent tied to the Show_Users button.
     * @return Nothing
     */
    public void showUsers(ActionEvent e){
        obsList = admin.getRegistered_users();
        userView.setItems(FXCollections.observableList(obsList));
        Remove_User.setDisable(false);
    }

    /**
     * Used when the Delete User button is pressed to possibly remove the user from the app
     * @param e The ActionEvent tied to the Remove_User Button.
     * @return Nothing
     */
    public void deleteUser(ActionEvent e) throws IOException {
        //Checks if any users exists
        if(obsList.size() == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No available users to delete");
            alert.show();
            return;
        }

        //Deletes the user from the admin object
        int selectedIndex = userView.getSelectionModel().getSelectedIndex();
        if(selectedIndex==-1){
            return;
        }
        User selectedUser = obsList.get(selectedIndex);
        for(int index = 0; index < admin.getRegistered_users().size(); index++){

            if(selectedUser.toString().equals(admin.getRegistered_users().get(index).toString())){
                admin.registered_users.remove(index);
                //Updates the file
                writeAdmin();
            }
        }


        //Handles selecting the next user in the observable list
        int newIndex;

        //Checks if there is a user after this one
        if (selectedIndex < obsList.size() - 1) {
            newIndex = selectedIndex + 1;
            selectedUser = obsList.get(newIndex);
            userView.getSelectionModel().select(selectedUser);

        } else if ((selectedIndex - 1) >= 0) { //Checks if there is a song before this one
            newIndex = selectedIndex - 1;
            selectedUser = obsList.get(newIndex);
            userView.getSelectionModel().select(selectedUser);

        }

        //updates the observable list
        obsList=admin.getRegistered_users();
        userView.setItems(FXCollections.observableList(obsList));

    }

    /**
     * Used to switch back the login screen
     * @return Nothing
     */
    public void closeAndLogout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../views/Login.fxml"));
        AnchorPane root = FXMLLoader.load(getClass().getResource("../views/Login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Used to enable the login button
     * @param e The KeyEvent tied to the User_Entry TextField.
     * @return Nothing
     */
    public void enableButton(KeyEvent e){
        if(User_Entry.getText().trim().length()==0){
            Add_User.setDisable(true);
        }
        else{
            Add_User.setDisable(false);
        }
    }






}
