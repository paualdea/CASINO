package juegos;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    
    // Arrays para el deck de cartas y sus imagenes
    private final List<ImageView> deck_img = new ArrayList<>();
    private final String[][] deck_valor = {
        {"Corazones", "Corazones", "Corazones", "Corazones", "Corazones", "Corazones", "Corazones", "Corazones", "Corazones", "Corazones", "Corazones", "Corazones", "Corazones", "Picas", "Picas", "Picas", "Picas", "Picas", "Picas", "Picas", "Picas", "Picas", "Picas", "Picas", "Picas", "Picas", "Rombos", "Rombos", "Rombos", "Rombos", "Rombos", "Rombos", "Rombos", "Rombos", "Rombos", "Rombos", "Rombos", "Rombos", "Rombos","Tréboles", "Tréboles", "Tréboles", "Tréboles", "Tréboles", "Tréboles", "Tréboles", "Tréboles", "Tréboles", "Tréboles", "Tréboles", "Tréboles", "Tréboles",},
        {"2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "10", "10", "10", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "10", "10", "10", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "10", "10", "10", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "10", "10", "10"}
    };
    private final ArrayList<String> cartas_usadas = new ArrayList<>();
    
    private final double ESPACIO_ENTRE_CARTAS = 10;
    private final double ANCHO_CARTA = 100;
    private final double ALTO_CARTA = 150;     
    
    // Funcion inicializadora
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Variables para leer la carpeta que tiene el deck
        String carpetaRuta = "../img/cartas/deck"; 
        File carpeta = new File(carpetaRuta);
        File[] archivos = carpeta.listFiles();
        
        // Recorrer cada archivo en la carpeta
        for (File archivo : archivos) {
            // Crear un objeto Image a partir del archivo
            Image imagen = new Image(archivo.toURI().toString());

            // Crear un ImageView con la imagen y agregarlo a la lista
            ImageView imageView = new ImageView(imagen);
            deck_img.add(imageView);
        }
        
        
    }    
    
    @FXML
    void pedirCarta(ActionEvent event) {

    }
    
    @FXML
    void pararCarta(ActionEvent event) {

    }
}