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

public class LoginController implements Serializable{
    private static Stage stage;
    private static Admin admin;
    public static final String storeFile = "users.dat";
    public static final String storeDir = "docs";

    @FXML
    public TextField text_entry;
    @FXML
    public Button enter;



    public static void setStage(Stage stage){
        LoginController.stage = stage;
    }

    public static void initialize() throws IOException, ClassNotFoundException {

        admin = readAdmin();

    }

    public static Admin readAdmin() throws ClassNotFoundException, IOException{

        ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(storeDir + File.separator + storeFile));

        try{
            while(true){
                admin = (Admin)ois.readObject();
            }
        } catch (EOFException e) {
            //End of file
        } catch (Exception e) {

        }

        ois.close();
        return admin;
    }

    public void enableLogin(ActionEvent e){
        enter.setDisable(false);
    }

    //Handles login
    public void login(ActionEvent e) throws IOException {
        String entered_input = text_entry.getText();

        if(entered_input.equals("admin")){
            AdminController.setAdmin(admin);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Admin.fxml"));
            AnchorPane root = (AnchorPane)loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

        for(int i = 0; i < admin.getRegistered_users().size(); i++){
            //Checks if the username matches a registered user
            if(admin.getRegistered_users().get(i).toString().compareTo(entered_input) == 0){
                UserController.setCurrent(admin.getRegistered_users().get(i));
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("User.fxml"));
                AnchorPane root = (AnchorPane)loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }

        //No valid users or admin was found,
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Invalid Username");
        alert.show();

        enter.setDisable(true);
        return;


    }




}
