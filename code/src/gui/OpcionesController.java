package gui;

import casino.CASINO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

public class OpcionesController implements Initializable {
    
    // Objetos con ID
    @FXML
    private Button atras;
    @FXML
    private CheckBox fcheck;
    @FXML
    private Button reset;
    @FXML
    private Text warning;
    
    // Importamos clase CASINO
    private CASINO casino = Main.getCasino();
    
    // Variables size base de la ventana
    private final double baseWidth = 1066;
    private final double baseHeight = 600;
    
    private int borrarBoton = 0;
    
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
        
        // Establecemos la variable borrarBoton a 0
        borrarBoton = 0;
        // Establecemos el texto del warning
        warning.setText("Click another time to reset database");
        // Ocultamos warning borrado datos
        warning.setVisible(false);
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
    
    /**
     * Funcion para resetear la base de datos
     * 
     * @param event 
     */
    @FXML
    void resetData(ActionEvent event) throws SQLException, IOException, InterruptedException {
        // Cuando hacemos clic sobre el boton se suma uno a borrarBoton
        borrarBoton += 1;
        
        if (borrarBoton == 1) {
            warning.setVisible(true);
        } 
        
        else if (borrarBoton == 2) {
            // Ejecutamos la funcion borrarDatos
            borrarDatos();
        }
    }
    
    void borrarDatos() throws SQLException, InterruptedException, IOException {
        // creamos una conexion y un statement
        Connection connection = casino.crearConexion();
        Statement statement = casino.crearStatement(connection);

        // ejecutamos el script de creacion de la base de datos
        try {
            // Creamos un array con las sentencias SQL para crear la base de datos 'casino'
            String[] sentencias = {
                "DROP TABLE puntos;",
                "DROP TABLE usuarios;",
                "CREATE TABLE usuarios (id INT PRIMARY KEY, usuario VARCHAR(100), passwd VARCHAR(100));",
                "CREATE TABLE puntos (id INT PRIMARY KEY, puntos INT, FOREIGN KEY (id) REFERENCES usuarios(id));",
                "INSERT INTO usuarios (id, usuario, passwd) VALUES (1, 'paualdea', 'aldea2');",
                "INSERT INTO puntos (id, puntos) VALUES (1, 12000);"
            };

            // Bucle for para iterar sobre todas las sentencias y ejecutarlas
            for (int i = 0; i < sentencias.length; i++) {
                statement.execute(sentencias[i]);
            }
        } catch (Exception e) {
            System.out.println("ERROR EN LA EJECUCION DE LA BASE DE DATOS");
        }
        
        Thread.sleep(1750);
        
        statement.close();
        connection.close();
        
        // Volvemos al menu inicio
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
}