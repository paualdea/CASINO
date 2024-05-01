package casino;

import gui.Main;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Apuesta {
    
    private int puntos;
    private CASINO casino = Main.getCasino();
    private String user = casino.getUser();
    
    public Apuesta () throws IOException {
        puntos = casino.getPuntos(user);
        
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("../gui/Apuesta.fxml"));
        Parent root2 = loader2.load();
        Scene scene2 = new Scene(root2);
        
        Stage stage2 = new Stage();
        stage2.setScene(scene2);
            
        stage2.getIcons().add(new Image("/img/ficha-logo.png"));
        stage2.setTitle("APUESTA");
        stage2.setResizable(false);
        
        stage2.show();
    }
}