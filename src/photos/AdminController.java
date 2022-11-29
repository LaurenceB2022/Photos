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

    public static void setStage(Stage stage){
        AdminController.stage = stage;
    }
    public static void setAdmin(Admin current){
        AdminController.admin = current;}

    public void initialize() throws IOException, ClassNotFoundException {

        writeAdmin(admin);

    }

    public static void writeAdmin(Admin curr) throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(storeDir + File.separator + storeFile));


        oos.writeObject(curr);

        oos.close();

    }

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
            for (int i = 0; i < obsList.size(); i++) {
                User currentUser = obsList.get(i);

                int cmp = currentUser.getUsername().compareToIgnoreCase(username);

                if (cmp == 0 ){ //Duplicate user
                        Alert duplicate = new Alert(Alert.AlertType.ERROR);
                        duplicate.setHeaderText("Unable To Add Duplicate User.");
                        duplicate.show();
                        return;
                }
            }
            obsList.add(user);
            admin.addUser(user);
            writeAdmin(admin);
            userView.setItems(FXCollections.observableList(obsList));
            userView.getSelectionModel().select(user);
            Add_User.setDisable(true);
        }

    }

    public void showUsers(ActionEvent e){
        obsList = admin.getRegistered_users();
        userView.setItems(FXCollections.observableList(obsList));
    }

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
                writeAdmin(admin);
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

    public void closeAndLogout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Login.fxml"));
        AnchorPane root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }




}
