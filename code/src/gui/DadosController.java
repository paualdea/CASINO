package gui;

import casino.Apuesta;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;

public class DadosController implements Initializable {
    
    @FXML
    private Text numeroTexto;

    @FXML
    private Button ok;

    @FXML
    private Slider sliderNumero;

    @FXML
    void confirmar(ActionEvent event) throws IOException {
        int valorFinal = (int) sliderNumero.getValue();
        
        Apuesta apuesta = new Apuesta();
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