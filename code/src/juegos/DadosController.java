package juegos;

import casino.CASINO;
import gui.Main;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class DadosController implements Initializable {
    
    // Elementos graficos con ID
    @FXML
    private Button atras;
    @FXML
    private TextField cuadroPuntos;
    @FXML
    private Button fifty;
    @FXML
    private Button five;
    @FXML
    private Button fivehundred;
    @FXML
    private Button hundred;
    @FXML
    private Button one;
    @FXML
    private Button ten;
    @FXML
    private Button thousand;
    
    // Importamos una instancia de la clase CASINO
    private CASINO casino = Main.getCasino();
    
    // Importamos los puntos
    private String user = casino.getUser();
    private int puntos = casino.getPuntos(user);
    
    // Clase inicializadora
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Establecemos los puntos en el marcador
        cuadroPuntos.setText(puntos + " puntos");    
    }

    /**
     * Funcion que se ejecuta al pulsat boton ATRAS
     * @param event 
     */
    @FXML
    void volverAtras(ActionEvent event) throws IOException {
        // Obtenemos variable fullscreen
        boolean fullscreen = casino.getFullscreen();
        
        Stage stage;
        Parent root;

        stage = (Stage) atras.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("../gui/MenuJuegos.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        
        // Si fullscreen esta en true redimensionar todo
        stage.setFullScreen(fullscreen);
        casino.proporcionFullscreen(stage, root, fullscreen);
        
        stage.show();
    }
    
    @FXML
    void oneFicha(ActionEvent event) {

    }
    
    @FXML
    void fiveFicha(ActionEvent event) {

    }
    
    @FXML
    void tenFicha(ActionEvent event) {

    }
    
    @FXML
    void fiftyFicha(ActionEvent event) {

    }
    
    @FXML
    void hundredFicha(ActionEvent event) {

    }
    
    @FXML
    void fivehundredFicha(ActionEvent event) {

    }
    
    @FXML
    void thousandFicha(ActionEvent event) {

    }
}
