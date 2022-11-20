package photos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import photos.users.User;

import java.io.File;
import java.io.IOException;

public class UserController {

    @FXML
    private TilePane tilePane;

    private static User current;
    private static Stage stage;

    public static void setStage(Stage stage){
        UserController.stage = stage;
    }

    public static void display(User user) throws IOException {
        current = user;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(UserController.class.getResource("User.fxml"));
        AnchorPane root = (AnchorPane)loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

//    public void uploadPicture(ActionEvent actionEvent) { Will be used elsewhere
//
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("images", "*.jpg", "*.png", "*.jpeg","*.bmp","*.gif")
//        );
//        File file = fileChooser.showOpenDialog(stage);
//
//        if(file!=null){
//            System.out.println(file.getAbsolutePath());
//            Image image = new Image((file.toURI().toString()));
//
//            ImageView imageView = new ImageView(image);
//
//            tilePane.getChildren().addAll(imageView);
//        }
//
//    }

}
