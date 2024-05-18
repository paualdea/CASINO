package gui;

import casino.CASINO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
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

    private int puntos;

    private static CASINO casino;
    private static String[][] usuariosList;
    private static ArrayList<Integer> puntosUsuario;
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    @FXML
    void comprobacionLogin(ActionEvent event) throws InterruptedException, IOException, SQLException {
        boolean usuarioCorrecto = false, contrasenaCorrecto = false;
        int numeroUsuarios = 0;
        String sentencia = "";
        errorPasswd.setVisible(false);
        register.setVisible(false);
        mensaje.setVisible(false);
        password.setStyle("-fx-border-color: null;");

        String user = username.getText();
        String passwd = password.getText();
        
        usuarioCorrecto = false;
        contrasenaCorrecto = false;

        try {
            statement = connection.createStatement();
            
            sentencia = "SELECT COUNT(*) FROM usuarios;";
            resultSet = statement.executeQuery(sentencia);
            
            numeroUsuarios = resultSet.getInt(1);
            
            System.out.println(numeroUsuarios);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        String usuarioBD = "", passwdBD = "";

        // Se recorre todo el array de usuarios para ver si algun registro coincide
        for (int i = 0; i < numeroUsuarios; i++) {
            
            sentencia = "SELECT username, passwd from usuarios where id = " + i;
            resultSet = statement.executeQuery(sentencia);
            
             if (resultSet.next()) {
                usuarioBD = resultSet.getString("username");
                passwdBD = resultSet.getString("passwd");
            }
             
            // Si el usuario y contrasenas introducidos mediante escaner coinciden en la fila de la iteracion actual...
            if (user.equals(usuarioBD) && passwd.equals(passwdBD)) {
                usuarioCorrecto = true;
                contrasenaCorrecto = true;
                puntos = casino.getPuntos(user);

                casino.setUser(user);

                casino.setPuntos(puntos, user);

                Stage stage;
                Parent root;

                stage = (Stage) login.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("MenuJuegos.fxml"));

                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                break;
            } else if (user.equals(usuarioBD) && !(passwd.equals(passwdBD))) {
                contrasenaCorrecto = false;
                usuarioCorrecto = true;
                break;
            } else if (!user.equals(usuarioBD) && !passwd.equals(passwdBD)) {
                usuarioCorrecto = false;
                contrasenaCorrecto = false;
            }
        }

        if (usuarioCorrecto && !contrasenaCorrecto) {
            errorPasswd.setVisible(true);
            password.setStyle("-fx-border-color: red;");
        } else if (!usuarioCorrecto && !contrasenaCorrecto) {
            register.setVisible(true);
            mensaje.setVisible(true);
        }

    }

    @FXML
    void registrarse(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) register.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("Registro.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
        errorPasswd.setVisible(false);

        casino = Main.getCasino();

        username.setPromptText("Ex: user01");
        password.setPromptText("Ex: 12345678");

        login.setDisable(true);
        register.setVisible(false);
        mensaje.setVisible(false);

        password.textProperty().addListener((observable, oldValue, newValue) -> {
            if (username.getText().isEmpty() || password.getText().isEmpty()) {
                login.setDisable(true);
            } else {
                login.setDisable(false);
            }
        });

        username.textProperty().addListener((observable, oldValue, newValue) -> {
            if (username.getText().isEmpty() || password.getText().isEmpty()) {
                login.setDisable(true);
            } else {
                login.setDisable(false);
            }
        });
    }

    public int getPuntos() {
        return puntos;
    }
}
