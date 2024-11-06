package juegos;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class BlackJackGameController implements Initializable {

    // Elementos graficos con ID
    @FXML
    private AnchorPane dealer;
    @FXML
    private Button parar;
    @FXML
    private Button pedir;
    @FXML
    private AnchorPane player;
    
    // Funcion inicializadora
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    @FXML
    void pedirCarta(ActionEvent event) {

    }
    
    @FXML
    void pararCarta(ActionEvent event) {

    }
}