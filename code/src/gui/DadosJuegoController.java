package gui;

import casino.CASINO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import juegos.Dados;

public class DadosJuegoController implements Initializable {

    @FXML
    private Text resultado;
    
    @FXML
    private Text resultadoTexto;
    
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
        
        if (casino.isGanado()){
            resultadoTexto.setText("Has ganado, ahora tienes " + casino.getPuntos(user) + " puntos");
        } else {
            resultadoTexto.setText("Has perdido, ahora tienes " + casino.getPuntos(user) + " puntos");
        }
    }
}
