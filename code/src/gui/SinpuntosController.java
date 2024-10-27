package gui;

import casino.CASINO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SinpuntosController implements Initializable {
    // Elementos graficos con ID
    @FXML
    private Button anadir;
    @FXML
    private TextField nuevosPuntos;
    @FXML
    private Text error;
    @FXML
    private Button salir;
    
    // Importamos una instancia de la clase CASINO
    private CASINO casino = Main.getCasino();
    
    // Funcion inicializadora
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Escondemos el error
        error.setVisible(false);
        
        // Deshabilitamos el boton anadir
        anadir.setDisable(true);
        
        // Si el campo de puntos esta vacio no habilitar boton anadir
        nuevosPuntos.textProperty().addListener((observable, oldValue, newValue) -> {
            if (nuevosPuntos.getText().isEmpty()) {
                anadir.setDisable(true);
            } else {
                anadir.setDisable(false);
            }
        });
        
        // Detectar si se pulso enter para anadir los puntos
        nuevosPuntos.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (!anadir.isDisabled()) {
                    anadir.fire();
                }
            }
        });
    }
    
    /**
     * Funcion para volver a la pagina inicial
     * 
     * @param event
     * @throws IOException
     * @throws FileNotFoundException
     * @throws InterruptedException 
     */
    @FXML
    void exit(ActionEvent event) throws IOException, FileNotFoundException, InterruptedException {
        // Obtenemos variable fullscreen
        boolean fullscreen = casino.getFullscreen();
        
        Stage stage;
        Parent root;

        stage = (Stage) salir.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("PaginaInicio.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        
        // Si fullscreen esta en true redimensionar todo
        stage.setFullScreen(fullscreen);
        casino.proporcionFullscreen(stage, root, fullscreen);
        
        stage.show();
    }
    
    /**
     * Funcion para anadir mas puntos a el usuario de la sesion actual
     * 
     * @param event
     * @throws IOException
     * @throws SQLException 
     */
    @FXML
    void anadirPuntos(ActionEvent event) throws IOException, SQLException, InterruptedException {
        // Escondemos el error
        error.setVisible(false);
        
        int puntos = 0;
        
        // Estructura de control para controlar errores de introducccion de valores
        try {
            puntos = Integer.parseInt(nuevosPuntos.getText());
        } catch (Exception e) {}
        
        // Si la cantidad de puntos es mas grande a 0 y mas pequena o igual a 10000...
        if (puntos > 0 && puntos <= 10000) {
            // Cambiamos los puntos en la BD
            String user = casino.getUser();
            casino.setPuntos(puntos, user);
            
            // Ponemos una pausa
            Thread.sleep(500);
            
            // Volvemos a la pantalla MenuJuegos
            boolean fullscreen = casino.getFullscreen();

            Stage stage;
            Parent root;

            stage = (Stage) error.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("MenuJuegos.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Si fullscreen esta en true redimensionar todo
            stage.setFullScreen(fullscreen);
            casino.proporcionFullscreen(stage, root, fullscreen);

            stage.show();
        } else {
            error.setVisible(true);
        }
    }
}
