//Contributors: Laurence Bartram and and Ismaeel Abdulghani
package photos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Photos extends Application {




    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Photos.fxml"));
        AnchorPane root = (AnchorPane)loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

}