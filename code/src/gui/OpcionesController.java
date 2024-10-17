package gui;

import casino.CASINO;
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
import javafx.scene.control.CheckBox;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;


public class OpcionesController implements Initializable {
    
    // Objetos con ID
    @FXML
    private Button atras;
    @FXML
    private CheckBox fcheck;
    
    // Importamos clase CASINO
    private CASINO casino = Main.getCasino();
    
    // Variables size base de la ventana
    private final double baseWidth = 1066;
    private final double baseHeight = 600;
    
    
    /**
     * Clase que inicializa
     * 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Obtenemos variable fullscreen
        boolean fullscreen = casino.getFullscreen();
        
        // Cambiamos el valor del checkbox en funcion de la variable fullscreen
        fcheck.setSelected(fullscreen);
    }
    
    /**
     * Funcion para volver a la pagina de inicio
     * 
     * @param event 
     */
    @FXML
    void volverAtras(ActionEvent event) throws IOException {
        // Obtenemos variable fullscreen
        boolean fullscreen = casino.getFullscreen();
        
        Stage stage;
        Parent root;
        
        stage = (Stage) atras.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("PaginaInicio.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        
        // Si fullscreen esta en true redimensionar todo
        stage.setFullScreen(fullscreen);
        casino.proporcionFullscreen(stage, root, fullscreen);
        
        stage.show();
    }
    
    /**
     * Funcion para poner la ventana del juego en pantalla completa
     * 
     * @param event 
     */
    @FXML
    void fullscreen(ActionEvent event) {
        // Obtenemos el Stage desde algún nodo existente
        Stage stage = (Stage) atras.getScene().getWindow();
        Parent root = atras.getScene().getRoot(); // Obtenemos el nodo raíz de la escena actual
        
        // Activa o desactiva la pantalla completa
        if (!stage.isFullScreen()) {
            stage.setFullScreen(true);
            casino.setFullscreen(true);
            
            // Desactiva el mensaje "Pulse ESC para salir del modo pantalla completa"
            stage.setFullScreenExitHint("");
        } else {
            stage.setFullScreen(false);
            casino.setFullscreen(false);
        }
        
        if (stage.isFullScreen()) {
            // Dimensiones actuales de la pantalla en fullscreen
            double screenWidth = stage.getWidth();
            double screenHeight = stage.getHeight();

            // Calcula la escala basándose en la proporción original
            double scaleX = screenWidth / baseWidth;
            double scaleY = screenHeight / baseHeight;

            // Selecciona el menor de los dos para mantener la proporción
            double scale = Math.min(scaleX, scaleY);

            // Aplicamos la escala a todo el contenido
            Scale scaling = new Scale(scale, scale);
            root.getTransforms().setAll(scaling);
        } else {
            // Restablecemos la escala cuando salimos de fullscreen
            root.getTransforms().clear();
        }
    }
}