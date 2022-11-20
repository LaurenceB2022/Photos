package photos;

import javafx.stage.Stage;
import photos.users.User;

public class AlbumController {

    private static Album currentAlbum;
    private static User current;
    private static Stage stage;

    public static void setStage(Stage stage){
        AlbumController.stage = stage;
    }

    public static void setCurrent(User user){
        current = user;
    }
    public static void setCurrentAlbum(Album album){
        currentAlbum =album;
    }
}
