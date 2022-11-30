package photos.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import photos.model.Admin;

//import javax.swing.text.html.ListView;
import java.io.*;

/**
 * <h1>The Login Controller Class</h1>
 * Class used to control the scene displayed upon opening the app
 * @author  Ismaeel Abdulghani and Laurence Bartram
 * @version 1.0
 * @since   2022-11-30
 */
public class LoginController{
    private static Stage stage;
    private static Admin admin;
    public static final String storeFile = "users.dat";
    public static final String storeDir = "docs";

    @FXML
    private TextField text_entry;
    @FXML
    private Button enter;


    /**
     * Used to set the stage
     * @param stage The current stage
     * @return Nothing
     */
    public static void setStage(Stage stage){
        LoginController.stage = stage;
    }

    public static void setAdmin(Admin admin){
        LoginController.admin = admin;
    }

    /**
     * Called when the scene is first loaded
     * @return Nothing
     */
    public void initialize() throws IOException, ClassNotFoundException {



    }

    /**
     * Used to deserialize the data from the file and set the admin
     * @return Nothing
     */


    /**
     * Used to enable the login button
     * @param keyEvent The key event tied to the text_entry TextField.
     * @return Nothing
     */
    public void enableLogin(KeyEvent keyEvent) {
        if(text_entry.getText().trim().length()>0 ){

            enter.setDisable(false);

        }

        if(text_entry.getText().trim().length()==0 ){

            enter.setDisable(true);

        }


    }

    /**
     * Used to log in after entering a username String.
     * @param e The action event tied to the enter Button.
     * @return Nothing
     */
    public void login(ActionEvent e) throws IOException {
        String entered_input = text_entry.getText();

        if(entered_input.equals("admin")){

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../views/Admin.fxml"));
            AnchorPane root = (AnchorPane)loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            enter.setDisable(true);
            return;
        }

            for(int i = 0; i < admin.getRegistered_users().size(); i++){
                //Checks if the username matches a registered user
                if(admin.getRegistered_users().get(i).toString().compareTo(entered_input) == 0){
                    UserController.setCurrent(admin.getRegistered_users().get(i));
                    AlbumController.setCurrent(admin.getRegistered_users().get(i));
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../views/User.fxml"));
                    AnchorPane root = (AnchorPane)loader.load();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    enter.setDisable(true);
                    return;
                }
            }



        //No valid users or admin was found,
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Invalid Username");
        alert.show();

        enter.setDisable(true);



    }




}
