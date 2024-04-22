package gui;

import javafx.fxml.FXML;
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
    public void initialize() {
        // Aquí puedes inicializar tus componentes si es necesario
    }

    @FXML
    private void iniciarSesion() {
        // Aquí puedes manejar la acción del botón "Iniciar sesión"
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
    }
}
