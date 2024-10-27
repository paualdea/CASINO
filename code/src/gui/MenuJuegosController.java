package gui;

import casino.CASINO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuJuegosController implements Initializable {

    // Importar clase CASINO
    private CASINO casino = Main.getCasino();
    // Obtenemos el usuario actual de la sesion
    private String user = casino.getUser();
    
    // Variables BD
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet rSet = null;

    // Elementos graficos con ID
    @FXML
    private Button atras;
    @FXML
    private Button dados;
    @FXML
    private Button borrar;
    
    private int puntos = 0;
    
    // Funcion inicializadora
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Creamos la conexion y statement
        connection = casino.crearConexion();
        statement = casino.crearStatement(connection);
 
        // Obtenemos los puntos del usuario de la sesion actual
        puntos = casino.getPuntos(user);
    }
    
    /**
     * Funcion para volver a la pagina de inicio
     * 
     * @param event
     * @throws IOException 
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
     * Funcion para borrar la cuenta del usuario actual de la sesion
     * 
     * @param event
     * @throws IOException
     * @throws SQLException 
     */
    @FXML
    void borrarCuenta(ActionEvent event) throws IOException, SQLException {
        // Creamos la conexion y statement
        connection = casino.crearConexion();
        statement = casino.crearStatement(connection);
        
        // Borramos primero el registro de puntos, luego el registro de usuario de la BD
        String sentencia = "DELETE FROM puntos WHERE id = (SELECT id FROM usuarios WHERE usuario = '" + user + "')";
        statement.executeUpdate(sentencia);
        sentencia = "DELETE FROM usuarios WHERE usuario = '" + user + "'";
        statement.executeUpdate(sentencia);
        
        // Cerrar conexion y statment
        statement.close();
        connection.close();
        
        // Obtenemos variable fullscreen
        boolean fullscreen = casino.getFullscreen();

        // Hacemos el cambio de pagina a PaginaInicio
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
     * Funcion que se ejecuta al entrar al juego DADOS
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    void juegoDados(ActionEvent event) throws IOException {
        // Si no tenemos puntos, mandar a la pagina Sinpuntos
        if (puntos == 0) {
            // Obtenemos variable fullscreen
            boolean fullscreen = casino.getFullscreen();

            // Cambiamos a la pantalla de sinPuntos
            Stage stage;
            Parent root;
            stage = (Stage) atras.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Sinpuntos.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Si fullscreen esta en true redimensionar todo
            stage.setFullScreen(fullscreen);
            casino.proporcionFullscreen(stage, root, fullscreen);

            stage.show();
        } 
        // Si tenemos puntos entrar a la pantalla DADOS
        else {
            // Obtenemos variable fullscreen
            boolean fullscreen = casino.getFullscreen();

            // Cambiamos a la pantalla de DADOS
            Stage stage;
            Parent root;
            stage = (Stage) atras.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Dados.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Si fullscreen esta en true redimensionar todo
            stage.setFullScreen(fullscreen);
            casino.proporcionFullscreen(stage, root, fullscreen);

            stage.show();
        }
    }
}