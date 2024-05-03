package gui;

import casino.CASINO;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ApuestaController implements Initializable {

    @FXML
    private Button chip1;

    @FXML
    private Button chip5;

    @FXML
    private Button chip10;

    @FXML
    private Button chip50;

    @FXML
    private Button chip100;

    @FXML
    private Button chip500;

    @FXML
    private Button confirmar;

    @FXML
    private Text pantallaPuntos;

    @FXML
    private Button reset;

    @FXML
    private Button atras;

    private int apuesta = 0;
    private int puntos;
    private CASINO casino = Main.getCasino();
    private String user = casino.getUser();

    @FXML
    void volverAtras(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        stage = (Stage) atras.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("Menujuegos.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void confirmarApuesta(ActionEvent event) throws IOException {
        puntos -= apuesta;
        casino.setPuntos(puntos, user);
        casino.setApuesta(apuesta);

        System.out.println(casino.getPuntos(user));

        Stage stage;
        Parent root;

        stage = (Stage) confirmar.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("DadosJuego.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void resetPuntos(ActionEvent event) {
        apuesta = 0;
        actualizarPuntos();
    }

    @FXML
    void sumar1(ActionEvent event) {
        apuesta += 1;
        actualizarPuntos();
    }

    @FXML
    void sumar10(ActionEvent event) {
        apuesta += 10;
        actualizarPuntos();
    }

    @FXML
    void sumar100(ActionEvent event) {
        apuesta += 100;
        actualizarPuntos();
    }

    @FXML
    void sumar1000(ActionEvent event) {
        apuesta += 1000;
        actualizarPuntos();
    }

    @FXML
    void sumar5(ActionEvent event) {
        apuesta += 5;
        actualizarPuntos();
    }

    @FXML
    void sumar50(ActionEvent event) {
        apuesta += 50;
        actualizarPuntos();
    }

    @FXML
    void sumar500(ActionEvent event) {
        apuesta += 500;
        actualizarPuntos();
    }

    public void actualizarPuntos() {
        pantallaPuntos.setText(Integer.toString(apuesta));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pantallaPuntos.setText("0");
        puntos = casino.getPuntos(user);
        actualizarPuntos();
    }
}
