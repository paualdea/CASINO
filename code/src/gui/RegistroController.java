package gui;

import casino.CASINO;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
    private static String[][] usuariosList;
    private static ArrayList<Integer> puntosUsuario;

    @FXML
    void registrarse(ActionEvent event) throws InterruptedException, IOException {
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

        // el programa se mantiene en este bucle while siempre que el usuario no este registrado
        // si el usuario ya esta registrado, te saca del bucle y retorna un false
        for (int i = 0; i < usuariosList.length; i++) {
            if (usuariosList[i][0].equals(user)) {
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
            
            casino.setUser(username.getText().toString());
            
            ingreso = 3000;

            // se redimensiona el array de usuarios para agregar un registro nuevo
            usuariosList = Arrays.copyOf(usuariosList, usuariosList.length + 1);
            usuariosList[usuariosList.length - 1] = new String[3];

            // Se añaden a la ultima fila del array el usuario y contrasena que hemos especificado en el registro
            usuariosList[usuariosList.length - 1][0] = user;
            usuariosList[usuariosList.length - 1][1] = passwd;
            usuariosList[usuariosList.length - 1][2] = Integer.toString(ingreso);

            casino.setPuntos(ingreso, user);

            // se vacia la variable user para evitar errores posteriores
            user = "";
            
            casino.setUsuariosList(usuariosList);
            
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
        usuariosList = casino.getUsuariosList();
        
        puntosUsuario = casino.getPuntosUsuario();
        
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
