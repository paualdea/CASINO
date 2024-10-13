package gui;

import casino.CASINO;
import java.io.IOException;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    
    public static CASINO casino;
    
    public static void main(String[] args) throws IOException, SQLException {
        casino = new CASINO();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("PaginaInicio.fxml"));
        primaryStage.getIcons().add(new Image("/img/favicon.png"));
        
        primaryStage.setTitle("CASINO");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    
    public static CASINO getCasino() {
        return casino;
    }
}