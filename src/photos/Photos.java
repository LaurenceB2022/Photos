//Contributors: Laurence Bartram and and Ismaeel Abdulghani
package photos;

import photos.controllers.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;
import photos.model.Admin;

import java.io.*;

/**
 * <h1>The Main Photos Class</h1>
 * Class that is called at the start
 * @author  Ismaeel Abdulghani and Laurence Bartram
 * @version 1.0
 * @since   2022-11-30
 */
public class Photos extends Application {

    private static final String storeFile = "users.dat";
    private static final String storeDir = "docs";
    public static Admin admin;
    /**
     * Sets the stages and starts the scenes
     * @return Nothing
     */
    @Override
    public void start(Stage stage) throws IOException {




        UserController.setStage(stage);
        AdminController.setStage(stage);
        AlbumController.setStage(stage);
        SlideshowController.setStage(stage);

        LoginController.setStage(stage);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("views/Login.fxml"));
        AnchorPane root = (AnchorPane)loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * The main method
     * @param
     * @return Nothing
     */
    public static void main(String[] args) throws IOException {

        readAdmin();
        if(admin==null){
            admin = new Admin();
        }

        AdminController.setAdmin(admin);
        LoginController.setAdmin(admin);

        launch();
    }

    /**
     * Used to serialize data whenever we update any object. This method is accessible
     * to the Controller classes.
     * @return Nothing
     */
    public static void writeAdmin() throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(storeDir + File.separator + storeFile));


        oos.writeObject(admin);

        oos.close();

    }

    /**
     * This method reads from the data serialized in the users.dat file
     * and un-serializes it. It then stores the data in the static admin object
     * for later use.
     * @throws IOException
     */
    public static void readAdmin() throws IOException {


        try{
            ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(storeDir + File.separator + storeFile));
            admin = (Admin)ois.readObject();
            ois.close();

        } catch (EOFException e) {
            //End of file
        } catch (Exception e) {

        }

    }



}