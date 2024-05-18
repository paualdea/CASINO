package gui;

import casino.CASINO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RegistroController implements Initializable {

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
    
    private static CASINO casino;
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private final String url = "jdbc:mysql://localhost:3306/casino";
    private final String userBD = "root";
    private final String passwdBD = "";

    @FXML
    void registrarse(ActionEvent event) throws InterruptedException, IOException, SQLException {
        
        try {
            connection = DriverManager.getConnection(url, userBD, passwdBD);
            statement = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String sentencia = "", usuarioBD = "", passwdBD = "";
        int numeroUsuarios = 0;
        errorPasswd.setVisible(false);
        username.setStyle("-fx-border-color: null;");
        password.setStyle("-fx-border-color: null;");
        password1.setStyle("-fx-border-color: null;");
        
        casino = Main.getCasino();
        error.setVisible(false);
        int ingreso = 0;
        boolean usuarioNuevo = false, puntosEstablecidos = false;

        String user = username.getText();
        String passwd = password.getText();
        String passwd_aux = password1.getText();
        
        try {
            statement = connection.createStatement();
            
            sentencia = "SELECT COUNT(*) as usuarios FROM usuarios;";
            resultSet = statement.executeQuery(sentencia);
            
            if (resultSet.next()) {
                numeroUsuarios = resultSet.getInt("usuarios");
            }
            
            System.out.println(numeroUsuarios);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // el programa se mantiene en este bucle while siempre que el usuario no este registrado
        // si el usuario ya esta registrado, te saca del bucle y retorna un false
        for (int i = 0; i < numeroUsuarios; i++) {
            
            sentencia = "SELECT username, passwd from usuarios where id = " + i;
            resultSet = statement.executeQuery(sentencia);
            
             if (resultSet.next()) {
                usuarioBD = resultSet.getString("username");
                passwdBD = resultSet.getString("passwd");
            }
            
            if (usuarioBD.equals(user)) {
                error.setVisible(true);
                username.setStyle("-fx-border-color: red;");
                usuarioNuevo = false;
                break;
            } else {
                usuarioNuevo = true;
            }
        }

        // si la password y la confirmacion de la password son iguales, se desvia al if para agregar dinero a la cuenta
        if (passwd.equals(passwd_aux) && usuarioNuevo) {
            
            statement = connection.createStatement();
            
            casino.setUser(username.getText().toString());
            
            ingreso = 3000;

            sentencia = "INSERT INTO usuarios (username, passwd) VALUES ('" + username + "', '" + passwd + "');";
            statement.executeUpdate(sentencia);
            
            sentencia = "INSERT INTO puntos VALUES ('" + numeroUsuarios + "', '" + ingreso + "');";
            statement.executeUpdate(sentencia);
            
            casino.setPuntos(ingreso, user);

            // se vacia la variable user para evitar errores posteriores
            user = "";
            
            Stage stage;
            Parent root;

            stage = (Stage) register.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("MenuJuegos.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else if (!passwd.equals(passwd_aux)) {
            password.setStyle("-fx-border-color: red;");
            password1.setStyle("-fx-border-color: red;");
            errorPasswd.setVisible(true);
        }
    }

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        password.setStyle("-fx-border-color: null;");
        password1.setStyle("-fx-border-color: null;");
        username.setStyle("-fx-border-color: null;");
        
        register.setDisable(true);
        errorPasswd.setVisible(false);
        error.setVisible(false);

        password.textProperty().addListener((observable, oldValue, newValue) -> {
            if (username.getText().isEmpty() || password.getText().isEmpty() || password1.getText().isEmpty()) {
                register.setDisable(true);
            } else {
                register.setDisable(false);
            }
        });

        username.textProperty().addListener((observable, oldValue, newValue) -> {
            if (username.getText().isEmpty() || password.getText().isEmpty() || password1.getText().isEmpty()) {
                register.setDisable(true);
            } else {
                register.setDisable(false);
            }
        });

        password1.textProperty().addListener((observable, oldValue, newValue) -> {
            if (username.getText().isEmpty() || password.getText().isEmpty() || password1.getText().isEmpty()) {
                register.setDisable(true);
            } else {
                register.setDisable(false);
            }
        });

    }

}
