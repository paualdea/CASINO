package gui;

import casino.CASINO;
import java.io.IOException;
import java.net.URL;
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

    @FXML
    void comprobacionLogin(ActionEvent event) throws InterruptedException, IOException {
        errorPasswd.setVisible(false);
        password.setStyle("-fx-border-color: null;");

        for (int i = 0; i < usuariosList.length; i++) {
            System.out.println(usuariosList[i][0]);
            System.out.println(usuariosList[i][1]);
        }

        ArrayList<Integer> puntosUsuario = casino.getPuntosUsuario();
        String user = username.getText();
        String passwd = password.getText();

        // Se recorre todo el array de usuarios para ver si algun registro coincide
        for (int i = 0; i < usuariosList.length; i++) {
            // Si el usuario y contrasenas introducidos mediante escaner coinciden en la fila de la iteracion actual...
            if (user.equals(usuariosList[i][0]) && passwd.equals(usuariosList[i][1])) {
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
            } else if (user.equals(usuariosList[i][0]) && !(passwd.equals(usuariosList[i][1]))) {
                errorPasswd.setVisible(true);
                password.setStyle("-fx-border-color: red;");
                break;
            }
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
        usuariosList = casino.getUsuariosList();
        puntosUsuario = casino.getPuntosUsuario();

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
