package gui;

import casino.CASINO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    
    // Variable posicion
    private int pos = 0;

    // Elementos graficos con ID
    @FXML
    private Button atras;
    @FXML
    private Button borrar;
    @FXML
    private Button boton;
    @FXML
    private Button derecha;
    @FXML
    private ImageView foto;
    @FXML
    private Button izquierda;
    
    private int puntos = 0;
    
    // Funcion inicializadora
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Reiniciamos el contador de posicion
        pos = 1;
        
        //Desactivamos el boton posicion izquierda
        izquierda.setDisable(true);

        // Creamos la conexion y statement
        connection = casino.crearConexion();
        statement = casino.crearStatement(connection);
 
        // Obtenemos los puntos del usuario de la sesion actual
        puntos = casino.getPuntos(user);
        
        // Establecer foto inicial
        Image juegoFoto = new Image(getClass().getResourceAsStream("../img/dado.png"));
        foto.setImage(juegoFoto);
        // Establecer boton inicial
        boton.setText("DADOS");
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
    void borrarCuenta(ActionEvent event) throws IOException, SQLException, InterruptedException {
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
        
        // Ponemos una pausa
        Thread.sleep(1250);
        
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
    
    // Funcion que cambian el juego
    @FXML
    void derechaJuego(ActionEvent event) {
        // Al pulsar boton derecha sumar 1 a posicion
        pos += 1;
        
        // Si la posicion es superior a 1 activar el boton izquierda
        if (pos > 1) {
            izquierda.setDisable(false);
        }
        
        // Si la posicion es 4 desactivar el boton derecha
        if (pos == 4) {
            derecha.setDisable(true);
        }
        
        // Llamamos a la funcion que cambia el juego
        cambioJuego(pos);
    }
    @FXML
    void izquierdaJuego(ActionEvent event) {
        // Al pulsar el boton izquierda, restar 1 a la posicion
        pos -= 1;
        
        // Si la posicion es 1 desactivar el boton izquierda
        if (pos == 1){
            izquierda.setDisable(true);
        }
        
        // Si la posicion es mas pequeña de 4, activar el boton derecha
        if (pos < 4) {
            derecha.setDisable(false);
        }
        
        // Llamamos a la funcion que cambia el juego
        cambioJuego(pos);
    }
    
    /**
     * Funcion para cambiar el juego actual del menu de juegos
     * 
     * @param pos 
     */
    private void cambioJuego(int pos) {
        // Creamos la imagen
        Image juegoFoto = null;
        
        switch (pos) {
            case 1:
                // Establecer foto
                juegoFoto = new Image(getClass().getResourceAsStream("../img/dado.png"));
                foto.setImage(juegoFoto);
                // Cambiamos texto boton
                boton.setText("DADOS");
                break;
            case 2:
                // Establecer foto
                juegoFoto = new Image(getClass().getResourceAsStream("../img/carta.png"));
                foto.setImage(juegoFoto);
                // Cambiamos texto boton
                boton.setText("BLACKJACK");
                break;
            case 3:
                // Establecer foto
                juegoFoto = new Image(getClass().getResourceAsStream("../img/ruleta.png"));
                foto.setImage(juegoFoto);
                // Cambiamos texto boton
                boton.setText("ROULETTE");
                break;
            case 4:
                // Establecer foto
                juegoFoto = new Image(getClass().getResourceAsStream("../img/bingo.png"));
                foto.setImage(juegoFoto);
                // Cambiamos texto boton
                boton.setText("BINGO");
                break;
        }


        
        
    }
    
    /**
     * Funcion que se ejecuta el juego seleccionado
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    void juego(ActionEvent event) throws IOException {
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
        
        // Si hay puntos entrar al juego...
        else {
            boolean fullscreen;
            Stage stage;
            Parent root;
            Scene scene;
            
            switch (pos) {
                case 1:
                    // Obtenemos variable fullscreen
                    fullscreen = casino.getFullscreen();

                    // Cambiamos a la pantalla de DADOS
                    stage = (Stage) atras.getScene().getWindow();
                    root = FXMLLoader.load(getClass().getResource("../juegos/Dados.fxml"));
                    scene = new Scene(root);
                    stage.setScene(scene);

                    // Si fullscreen esta en true redimensionar todo
                    stage.setFullScreen(fullscreen);
                    casino.proporcionFullscreen(stage, root, fullscreen);

                    stage.show();
                    break;
                case 2:
                    // Obtenemos variable fullscreen
                    fullscreen = casino.getFullscreen();

                    // Cambiamos a la pantalla de DADOS
                    stage = (Stage) atras.getScene().getWindow();
                    root = FXMLLoader.load(getClass().getResource("../juegos/BlackJack.fxml"));
                    scene = new Scene(root);
                    stage.setScene(scene);

                    // Si fullscreen esta en true redimensionar todo
                    stage.setFullScreen(fullscreen);
                    casino.proporcionFullscreen(stage, root, fullscreen);

                    stage.show();
                    break;
                case 3:

                    break;
                case 4:

                    break;
            }
        }
    }
}