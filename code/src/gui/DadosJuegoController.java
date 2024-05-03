package gui;

import casino.CASINO;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import juegos.Dados;

public class DadosJuegoController implements Initializable {

    @FXML
    private Button menu;

    @FXML
    private Text resultado;

    @FXML
    private Text resultadoTexto;

    @FXML
    private Button retry;

    private CASINO casino = Main.getCasino();
    private int valorDados = casino.getValorDados();
    private int apuesta = casino.getApuesta();
    private String user = casino.getUser();
    private int puntos = casino.getPuntos(user);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Dados dados = new Dados(valorDados, apuesta);
        dados.dados();
        int resultadoDados = dados.getResultado();
        puntos += dados.getApuesta();
        casino.setPuntos(puntos, user);
        casino.setApuesta(0);
        resultado.setText(Integer.toString(resultadoDados));

        if (casino.isGanado()) {
            resultadoTexto.setText("Has ganado, ahora tienes " + casino.getPuntos(user) + " puntos");
        } else {
            resultadoTexto.setText("Has perdido, ahora tienes " + casino.getPuntos(user) + " puntos");
        }
    }

    @FXML
    void menuPrincipal(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        stage = (Stage) menu.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("MenuJuegos.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void volverJugar(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        stage = (Stage) menu.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("Dados.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
