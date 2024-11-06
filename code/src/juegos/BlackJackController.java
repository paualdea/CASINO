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
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BlackJackController implements Initializable {
    
    // Elementos graficos con ID
    @FXML
    private Button atras;
    @FXML
    private TextField cuadroPuntos;
    @FXML
    private Text errorPuntos;
    @FXML
    private TextField cuadroApuesta;
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
    @FXML
    private Button apostar;
    
    // Importamos una instancia de la clase CASINO
    private CASINO casino = Main.getCasino();
    
    // Importamos los puntos
    private String user = casino.getUser();
    private int puntos = casino.getPuntos(user);
    private int puntos_dif = 0;
    
    // Creamos la variable de apuesta
    private int apuesta = 0;
    
    // Clase inicializadora
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Escondemos el error de puntos
        errorPuntos.setVisible(false);

        // Establecemos los puntos en los marcadores
        cuadroPuntos.setText(puntos + " puntos");   
        cuadroApuesta.setText(apuesta + "");
        
        
        // Si la apuesta es 0 deshabilitar el boton de tirar dados
        cuadroApuesta.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Integer.valueOf(cuadroApuesta.getText()).equals(0)) {
                //tirar.setDisable(true);
            } else {
                //tirar.setDisable(false);
            }
        });
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
    
    // Funciones para apostar puntos con los botones de las fichas
    @FXML
    void oneFicha(ActionEvent event) throws IOException {
        actualizarPuntos(1);
    }
    @FXML
    void fiveFicha(ActionEvent event) throws IOException {
        actualizarPuntos(5);
    }
    @FXML
    void tenFicha(ActionEvent event) throws IOException {
        actualizarPuntos(10);
    }
    @FXML
    void fiftyFicha(ActionEvent event) throws IOException {
        actualizarPuntos(50);
    }
    @FXML
    void hundredFicha(ActionEvent event) throws IOException {
        actualizarPuntos(100);
    }
    @FXML
    void fivehundredFicha(ActionEvent event) throws IOException {
        actualizarPuntos(500);
    }
    @FXML
    void thousandFicha(ActionEvent event) throws IOException {
        actualizarPuntos(1000);
    }
    
    /**
     * Funcion para actualizar los puntos del marcador de la pantalla
     */
    private void actualizarPuntos (int restarPuntos) throws IOException {
        // Escondemos los posibles errores
        errorPuntos.setVisible(false);

        // Si tenemos al menos los puntos que apostamos...
        if (puntos >= restarPuntos) {
            puntos -= restarPuntos;
            apuesta += restarPuntos;
        }
        // Si no tenemos suficientes puntos...
        else {
            // Mostramos error puntos
            errorPuntos.setText("No tienes suficientes puntos");
            errorPuntos.setVisible(true);
        }

        // Establecemos los puntos en los marcadores
        cuadroPuntos.setText(puntos + " puntos");
        cuadroApuesta.setText(apuesta + "");
    }
        
    /**
     * Funcion para borrar la apuesta actual
     * 
     * @param event 
     */
    @FXML
    void eliminarApuesta(ActionEvent event) {
        // Escondemos el error
        errorPuntos.setVisible(false);

        // Devolvemos los puntos de la apuesta
        puntos += apuesta;
        apuesta = 0;
        
        // Cambiamos el valor de los marcadores
        cuadroPuntos.setText(puntos + " puntos");
        cuadroApuesta.setText(apuesta + "");
    }
    
    /**
     * Apuesta para restar los puntos y pasar a la pagina de juego
     * 
     * @param event 
     */
    @FXML
    void apostarPuntos(ActionEvent event) {
        // Actualizamos la variable de puntos
        puntos -= apuesta;
        
        // Actualizamos los puntos en la BD
        casino.setPuntos(puntos, user);
        
        // Cambiamos a la pantalla de juego
        
    }
}
