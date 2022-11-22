package photos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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

public class AdminController implements Serializable {
    public static final String storeFile = "users.dat";
    public static final String storeDir = "docs";
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

    public static void initialize() throws IOException, ClassNotFoundException {

        admin = readAdmin();
        writeAdmin(admin);


        //AdminController current = AdminController.readAdmin();



        //Checks if the button is pressed


    }

    public static void writeAdmin(Admin curr) throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(storeDir + File.separator + storeFile));


        oos.writeObject(curr);

        oos.close();

    }


    public static Admin readAdmin() throws ClassNotFoundException, IOException{

        ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(storeDir + File.separator + storeFile));

        try{
            while(true){
                admin = (Admin)ois.readObject();
            }
        } catch (EOFException e) {
        //System.out.println("End of File.");
        } catch (Exception e) {
        //System.out.println("Here be error..."+e.getMessage());
    }

        ois.close();
        return admin;
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

        int selectedIndex = userView.getSelectionModel().getSelectedIndex();
        User selectedUser;
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

        obsList.remove(selectedIndex);

        //Updates the file
        writeAdmin(admin);
        userView.setItems(FXCollections.observableList(obsList));

    }




}
