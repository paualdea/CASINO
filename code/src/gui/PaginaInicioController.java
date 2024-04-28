package gui;

import datos.Sistema_ficheros;
import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class PaginaInicioController {

    @FXML
    private Button login;

    @FXML
    private Button menu;

    @FXML
    private Button signup;

    @FXML
    private Button x;

    @FXML
    void close(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void entrarMenu(ActionEvent event) {

    }

    @FXML
    public void initialize() throws InterruptedException, IOException {
    }

    @FXML
    private void iniciarSesion() throws IOException {
        Stage stage;
        Parent root;

        stage = (Stage) login.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void registrarse(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        stage = (Stage) signup.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("Registro.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}