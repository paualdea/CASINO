package gui;

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        File file = new File("img/favicon.png");
        
        Image favicon = new Image(file.toURI().toString());
        primaryStage.getIcons().add(favicon);
        
        primaryStage.setTitle("CASINO");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
