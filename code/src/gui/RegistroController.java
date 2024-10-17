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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RegistroController implements Initializable {

    // Elementos graficos con ID
    @FXML
    private Button atras;
    @FXML
    private Text error;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField password1;
    @FXML
    private Button register;
    @FXML
    private TextField username;
    @FXML
    private Text errorPasswd;
    
    // Creacion instancia casino clase CASINO
    private CASINO casino = Main.getCasino();
    
    // Variables BD
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet rSet = null;
    
    /**
     * Funcion inicializacion pagina
     * 
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Borramos los estilos aplicados anteriormente
        password.setStyle("-fx-border-color: null;");
        password1.setStyle("-fx-border-color: null;");
        username.setStyle("-fx-border-color: null;");
        
        // Deshabilitamos el boton de registro y escondemos los mensajes de error
        register.setDisable(true);
        errorPasswd.setVisible(false);
        error.setVisible(false);
        
        // Si la passwd esta vacia no habilitar boton registro
        password.textProperty().addListener((observable, oldValue, newValue) -> {
            if (username.getText().isEmpty() || password.getText().isEmpty() || password1.getText().isEmpty()) {
                register.setDisable(true);
            } else {
                register.setDisable(false);
            }
        });
        
        // Si el username esta vacio no habilitar boton registro
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            if (username.getText().isEmpty() || password.getText().isEmpty() || password1.getText().isEmpty()) {
                register.setDisable(true);
            } else {
                register.setDisable(false);
            }
        });
        
        // Si la confirmacion de passwd esta vacia no habilitar el boton registro
        password1.textProperty().addListener((observable, oldValue, newValue) -> {
            if (username.getText().isEmpty() || password.getText().isEmpty() || password1.getText().isEmpty()) {
                register.setDisable(true);
            } else {
                register.setDisable(false);
            }
        });
        
        // Detectar si se pulso enter para registar el usuario
        password.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (!username.isDisabled() && !password.isDisabled() && !password1.isDisabled()) {
                    register.fire();
                }
            }
        });
        username.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (!username.isDisabled() && !password.isDisabled() && !password1.isDisabled()) {
                    register.fire();
                }
            }
        });
        password1.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (!username.isDisabled() && !password.isDisabled() && !password1.isDisabled()) {
                    register.fire();
                }
            }
        });
    }
    
    /**
     * Funcion para volver a la pagina de inicio cuando se pulse el boton de menu principal
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
     * Funcion para registrar los datos introducidos en la BD
     * 
     * @param event
     * @throws InterruptedException
     * @throws IOException
     * @throws SQLException 
     */
    @FXML
    void registrarse(ActionEvent event) throws InterruptedException, IOException, SQLException { 
        // Creamos la conexion y statement
        connection = casino.crearConexion();
        statement = casino.crearStatement(connection);
        
        // Variables 
        String sentencia = "", usuarioBD = "", passwdBD = "";
        int ingreso = 0;
        boolean usuarioNuevo = false, puntosEstablecidos = false;
        
        // Variable que almacena el numero actual de usuarios en la BD
        int numeroUsuarios = casino.numeroUsuarios();
        
        // Esconder los errores y estilos aplicados
        errorPasswd.setVisible(false);
        username.setStyle("-fx-border-color: null;");
        password.setStyle("-fx-border-color: null;");
        password1.setStyle("-fx-border-color: null;");
        error.setVisible(false);
        
        // Obtenemos los valores introducidos en la pagina
        String user = username.getText();
        String passwd = password.getText();
        String passwd_aux = password1.getText();
        
        // Bucle for que itera tantas veces como usuarios hay registrados
        for (int i = 1; i <= numeroUsuarios; i++) {
            // Establecemos la sentencia SQL para seleccionar el usuario y passwd
            sentencia = "SELECT usuario, passwd FROM usuarios where id = " + i;
            
            // Ejecutamos la sentencia
            rSet = statement.executeQuery(sentencia);
            
            // Si el resultado no es nulo...
            if (rSet.next()) {
                // Guardar los valores resultantes de la consulta en dos variables
                usuarioBD = rSet.getString("usuario");
                passwdBD = rSet.getString("passwd");
            }
            
            // Si el usuario ya existe...
            if (usuarioBD.equals(user)) {
                // Mostrar el error y cambiar el estilo del campo de usuario
                error.setVisible(true);
                username.setStyle("-fx-border-color: red;");
                usuarioNuevo = false;
                break;
            } 
            // En caso de que el usuario no exista...
            else {
                usuarioNuevo = true;
            }
        }

        // Si la passwd y passwd son correctas y el usuario es nuevo, creamos el usuario
        if (passwd.equals(passwd_aux) && usuarioNuevo) {
            // Guardamos el usuario introducido
            user = username.getText().toString();
            
            // Establecemos el usuario de la sesion
            casino.setUser(user);
            
            // Establecemos la cantidad de puntos predeterminada
            ingreso = 3000;

            // Creamos y ejecutamos la sentencia para introducir un nuevo usuario en la BD
            sentencia = "INSERT INTO usuarios VALUES (" + (numeroUsuarios + 1) + ", '" + user + "', '" + passwd + "');";
            statement.executeUpdate(sentencia);
            
            // Creamos y ejecutamos la sentencia para introducir los puntos del nuevo usuario a la BD
            sentencia = "INSERT INTO puntos VALUES (" + (numeroUsuarios + 1) + ", '" + ingreso + "');";
            statement.executeUpdate(sentencia);
            
            // se vacia la variable user para evitar errores posteriores
            user = "";
            
            // Obtenemos variable fullscreen
            boolean fullscreen = casino.getFullscreen();

            // Hacemos el cambio de pagina a MenuJuegos
            Stage stage;
            Parent root;

            stage = (Stage) register.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("MenuJuegos.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            
            // Si fullscreen esta en true redimensionar todo
            stage.setFullScreen(fullscreen);
            casino.proporcionFullscreen(stage, root, fullscreen);
            
            stage.show();
        } 
        // Si la passwd es diferente a la passwd confirmacion, mostrar errores
        else if (!passwd.equals(passwd_aux)) {
            password.setStyle("-fx-border-color: red;");
            password1.setStyle("-fx-border-color: red;");
            errorPasswd.setVisible(true);
        }
        
        // Cerrar conexion y statment
        statement.close();
        connection.close();
    }
}