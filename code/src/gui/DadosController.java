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
import javafx.scene.control.Slider;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DadosController implements Initializable {

    @FXML
    private Button atras;
    
    @FXML
    private Text numeroTexto;

    @FXML
    private Button ok;

    @FXML
    private Slider sliderNumero;
    
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
    void confirmar(ActionEvent event) throws IOException {
        int valorFinal = (int) sliderNumero.getValue();
        
        CASINO casino = Main.getCasino();
        
        casino.setValorDados(valorFinal);
        
        Stage stage;
        Parent root;

        stage = (Stage) ok.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("Apuesta.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sliderNumero.setMajorTickUnit(1);
        numeroTexto.setText("2");

        sliderNumero.valueProperty().addListener((observable, oldValue, newValue) -> {
            int valorEntero = newValue.intValue();
            numeroTexto.setText(Integer.toString(valorEntero));
        });
    }
}
