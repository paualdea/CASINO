package gui;

import casino.CASINO;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuJuegosController implements Initializable {

    private CASINO casino = Main.getCasino();
    private String user = casino.getUser();
    private String[][] usuariosList = casino.getUsuariosList();
    private int puntos;
        
    @FXML
    private Button atras;

    @FXML
    private Button dados;

    @FXML
    void juegoDados(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        stage = (Stage) atras.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("Dados.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void volverAtras(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        stage = (Stage) atras.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("PaginaInicio.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        for (int i = 0; i < usuariosList.length; i++) {
            if (usuariosList[i][0].equals(user)){
                puntos = Integer.parseInt(usuariosList[i][2]);
            }
        }
          
    }

}
