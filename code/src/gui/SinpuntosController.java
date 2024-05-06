package gui;

import casino.CASINO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SinpuntosController implements Initializable {

    @FXML
    private Button anadir;

    @FXML
    private TextField nuevosPuntos;

    @FXML
    private Text error;

    @FXML
    private Button salir;

    private CASINO casino = Main.getCasino();

    @FXML
    void anadirPuntos(ActionEvent event) throws IOException {
        error.setVisible(false);

        int puntos = Integer.parseInt(nuevosPuntos.getText());

        if (puntos > 0 && puntos < 10000) {
            String user = casino.getUser();
            casino.setPuntos(puntos, user);

            Stage stage;
            Parent root;

            stage = (Stage) anadir.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("MenuJuegos.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            error.setVisible(true);
        }
    }

    @FXML
    void exit(ActionEvent event) throws IOException, FileNotFoundException, InterruptedException {
        casino.actualizarFicheros();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        error.setVisible(false);
    }

}
