package gui;

import datos.Sistema_ficheros;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class PaginaInicioController {

    @FXML
    private Button iniciarSesion;

    @FXML
    private Button registrarse;

    @FXML
    private Button completa;

    @FXML
    private ImageView cartel;

    @FXML
    public void initialize() throws InterruptedException, IOException {
        // creamos una instancia de la clase sistema_ficheros
        Sistema_ficheros datos = new Sistema_ficheros();
    }

    @FXML
    private void iniciarSesion() throws IOException {
        Stage stage;
        Parent root;

        stage = (Stage) iniciarSesion.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void registrarse() {
        // Aquí puedes manejar la acción del botón "Registrarse"
    }

    @FXML
    private void pantallaCompleta() {
        Stage stage = (Stage) completa.getScene().getWindow();

        // Cambia el estado de la ventana entre pantalla completa y modo normal
        stage.setFullScreen(!stage.isFullScreen());

        if (stage.isFullScreen()) {
            completa.setText("Modo ventana");
        } else {
            completa.setText("Pantalla completa");

        }
    }
}
