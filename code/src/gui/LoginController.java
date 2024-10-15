package gui;

import casino.CASINO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController implements Initializable {

    // Elementos graficos con ID
    @FXML
    private Text mensaje;
    @FXML
    private Button atras;
    @FXML
    private Button login;
    @FXML
    private PasswordField password;
    @FXML
    private TextField username;
    @FXML
    private Button register;
    @FXML
    private Text errorPasswd;
    
    // Importamos la clase CASINO
    private CASINO casino = Main.getCasino();
    
    // Creamos la variable de puntos
    private int puntos;
    
    // Variables DB
    private String url = null;
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet rSet = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Borramos el error (en caso que haya aparecido anteriormente)
        password.setStyle("-fx-border-color: null;");
        errorPasswd.setVisible(false);
        
        // Ponemos unos placeholders dentro de los campos de usuario y passwd
        username.setPromptText("Ex: user01");
        password.setPromptText("Ex: 12345678");
        
        // Deshabilitamos el boton de Log in y escondemos el boton y mensaje de regitrso
        login.setDisable(true);
        register.setVisible(false);
        mensaje.setVisible(false);

        // Si la password esta vacia no activar el boton de Log in
        password.textProperty().addListener((observable, oldValue, newValue) -> {
            if (username.getText().isEmpty() || password.getText().isEmpty()) {
                login.setDisable(true);
            } else {
                login.setDisable(false);
            }
        });
        
        // Si el usuario esta vacio no activar el boton de Log in
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            if (username.getText().isEmpty() || password.getText().isEmpty()) {
                login.setDisable(true);
            } else {
                login.setDisable(false);
            }
        });
    }
    
    // Funcion que se ejecuta al pulsar boton Main menu
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
    
    // Funcion que se ejecuta al pulsar boton Log in
    @FXML
    void comprobacionLogin(ActionEvent event) throws InterruptedException, IOException, SQLException {
        // Variables funcion
        boolean usuarioCorrecto = false, contrasenaCorrecto = false;
        int numUsuarios = 0;
        String sentencia = "", userBD = "", passwdBD = "";
        
        // Escondemos los errores
        errorPasswd.setVisible(false);
        register.setVisible(false);
        mensaje.setVisible(false);
        password.setStyle("-fx-border-color: null;");

        // Almacenamos los valores que hemos introducido
        String user = username.getText();
        String passwd = password.getText();
        
        // Obtenemos el numero de usuarios de la BD
        numUsuarios = casino.numeroUsuarios();
        
        // Creamos la conexion y statement
        connection = casino.crearConexion();
        statement = casino.crearStatement(connection);

        // Bucle for que itera tantas veces como usuarios haya
        for (int i = 1; i <= numUsuarios; i++) {

            // Sentencia SQL que selecciona el usuario y la contraseña en la iteracion del FOR
            sentencia = "SELECT usuario, passwd from usuarios where id = " + i;
            // Ejecutamos la sentencia
            rSet = statement.executeQuery(sentencia);

            // Si la sentencia devuelve valores, almacenarlos
            if (rSet.next()) {
                userBD = rSet.getString("usuario");
                passwdBD = rSet.getString("passwd");
            }

            // Si el usuario y contrasenas coinciden en la fila de la iteracion actual...
            if (user.equals(userBD) && passwd.equals(passwdBD)) {
                // Establecemos las variables de usuario y passwd en true
                usuarioCorrecto = true;
                contrasenaCorrecto = true;
                
                // Obtenemos los puntos del usuario
                puntos = casino.getPuntos(user);

                // Establecemos el usuario recibido como el actual de la sesion
                casino.setUser(user);
                
                // Establecemos los puntos en la instancia casino
                casino.setPuntos(puntos, user);

                // Cambiamos la pantalla al MenuJuegos
                Stage stage;
                Parent root;

                stage = (Stage) login.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("MenuJuegos.fxml"));

                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                break;
            // Si el usuario es correcto pero la passw no...
            } else if (user.equals(userBD) && !(passwd.equals(passwdBD))) {
                // Establecemos el usuario en true pero la passwd en false
                contrasenaCorrecto = false;
                usuarioCorrecto = true;
                break;
            // Si el usuario y la passwd son incorrectas...
            } else if (!user.equals(userBD) && !passwd.equals(passwdBD)) {
                // Establecemos el usuario y passwd en false
                usuarioCorrecto = false;
                contrasenaCorrecto = false;
            }
        }
        
        // Si el usuario es correcto pero la contrasena no...
        if (usuarioCorrecto && !contrasenaCorrecto) {
            // Mostramos el error de passwd en la pantalla
            errorPasswd.setVisible(true);
            password.setStyle("-fx-border-color: red;");
        // Si el usuario y la passwd son incorrectos...
        } else if (!usuarioCorrecto && !contrasenaCorrecto) {
            // Mostrar opciones de registro
            register.setVisible(true);
            mensaje.setVisible(true);
        }

    }
    
    // Esta funcion se ejecuta al pulsar boton Sign up
    @FXML
    void registrarse(ActionEvent event) throws IOException {
        // Cambiamos a la pantalla de registro
        Stage stage;
        Parent root;
        stage = (Stage) register.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("Registro.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
