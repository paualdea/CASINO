package gui;

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
    
    // Variables size base de la ventana
    private final double baseWidth = 1066;
    private final double baseHeight = 600;
    
    
    /**
     * Clase que inicializa
     * 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    /**
     * Funcion para volver a la pagina de inicio
     * 
     * @param event 
     */
    @FXML
    void volverAtras(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        stage = (Stage) atras.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("PaginaInicio.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
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
        } else {
            stage.setFullScreen(false);
        }
        
        if (stage.isFullScreen()) {
            System.out.println("ha funcionado !!!");
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