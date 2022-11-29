package photos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import photos.users.Admin;
import photos.users.User;

//import javax.swing.text.html.ListView;
import javafx.collections.FXCollections;
import java.io.*;
import java.util.ArrayList;
import java.util.*;

/**
 * <h1>The Admin Controller Class</h1>
 * Controller class for the admin screen
 * @author  Ismaeel Abdulghani and Laurence Bartram
 * @version 1.0
 * @since   2022-11-30
 */
public class AdminController {
    private static final String storeFile = "users.dat";
    private static final String storeDir = "docs";
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
     * @param stage The current stage
     * @return Nothing
     */
    public static void setStage(Stage stage){
        AdminController.stage = stage;
    }
    /**
     * Sets the admin for the session
     * @param current The admin
     * @return Nothing
     */
    public static void setAdmin(Admin current){
        AdminController.admin = current;}

    /**
     * Called when the scene is first loaded
     * @return Nothing
     */
    public void initialize() throws IOException, ClassNotFoundException {

        writeAdmin();

    }

    /**
     * Used to serialize data whenever we update
     * @return Nothing
     */
    public static void writeAdmin() throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(storeDir + File.separator + storeFile));


        oos.writeObject(admin);

        oos.close();

    }
    /**
     * Used when the "Add User" button is pressed to possibly add a new user
     * @param e
     * @return Nothing
     */
    public void addUser(ActionEvent e) throws IOException {
        String username = User_Entry.getText().trim();
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
                User currentUser = obsList.get(i);

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
     * @param e
     * @return Nothing
     */
    public void showUsers(ActionEvent e){
        obsList = admin.getRegistered_users();
        userView.setItems(FXCollections.observableList(obsList));
    }

    /**
     * Used when the Delete User button is pressed to possibly remove the user from the app
     * @param e
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
        obsList.remove(selectedIndex);
        userView.setItems(FXCollections.observableList(obsList));

    }

    /**
     * Used to switch back the login screen
     * @return Nothing
     */
    public void closeAndLogout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Login.fxml"));
        AnchorPane root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }






}
