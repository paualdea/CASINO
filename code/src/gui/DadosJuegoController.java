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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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
    private ImageView d1;

    @FXML
    private ImageView d2;

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
        int dado1 = dados.getDado1();
        int dado2 = dados.getDado2();
        puntos += dados.getApuesta();
        casino.setPuntos(puntos, user);
        casino.setApuesta(0);
        resultado.setText(Integer.toString(resultadoDados));

        Image cara1 = new Image("/img/1.png");
        Image cara2 = new Image("/img/2.png");
        Image cara3 = new Image("/img/3.png");
        Image cara4 = new Image("/img/4.png");
        Image cara5 = new Image("/img/5.png");
        Image cara6 = new Image("/img/6.png");

        switch (dado1) {
            case 1:
                d1.setImage(cara1);
                break;
            case 2:
                d1.setImage(cara2);
                break;
            case 3:
                d1.setImage(cara3);
                break;
            case 4:
                d1.setImage(cara4);
                break;
            case 5:
                d1.setImage(cara5);
                break;
            case 6:
                d1.setImage(cara6);
                break;
        }

        switch (dado2) {
            case 1:
                d2.setImage(cara1);
                break;
            case 2:
                d2.setImage(cara2);
                break;
            case 3:
                d2.setImage(cara3);
                break;
            case 4:
                d2.setImage(cara4);
                break;
            case 5:
                d2.setImage(cara5);
                break;
            case 6:
                d2.setImage(cara6);
                break;
        }

        resultadoTexto.setTextAlignment(TextAlignment.CENTER);

        if (casino.isGanado()) {
            resultadoTexto.setText("Has ganado\nTienes " + casino.getPuntos(user) + " puntos");
        } else {
            resultadoTexto.setText("Has perdido\nTienes " + casino.getPuntos(user) + " puntos");
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
        if (puntos > 0) {
            Stage stage;
            Parent root;

            stage = (Stage) menu.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Dados.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            Stage stage;
            Parent root;

            stage = (Stage) menu.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("MenuJuegos.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
}
