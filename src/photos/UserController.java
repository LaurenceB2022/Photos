package photos;

import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import photos.users.User;

import java.io.File;

public class UserController {
    private User current;
    private static Stage stage;

    public static void setStage(Stage stage){
        UserController.stage = stage;
    }

    public void uploadPicture(ActionEvent actionEvent) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("images", "*.jpg", "*.png", "*.jpeg","*.bmp","*.gif")
        );
        File file = fileChooser.showOpenDialog(stage);
        if(file!=null){Photo photo = new Photo(file.getPath());}




       

    }

}
