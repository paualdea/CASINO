package gui;

import casino.CASINO;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PaginaInicioController {

    // Elementos graficos con ID
    @FXML
    private Button login;
    @FXML
    private Button menu;
    @FXML
    private Button signup;
    @FXML
    private Button x;
    
    // Importamos la clase CASINO
    private CASINO casino = Main.getCasino();

    // Funcion que se ejecuta al pulsar sobre boton 'x'
    @FXML
    void close(ActionEvent event) throws IOException, FileNotFoundException, InterruptedException {
        System.exit(0);
    }
    
    // Funcion que se ejecuta al pulsar sobre boton LOGIN
    @FXML
    private void iniciarSesion() throws IOException {
        Stage stage;
        Parent root;

        stage = (Stage) login.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    // Funcion que se ejecuta al pulsar sobre boton SIGN UP
    @FXML
    private void registrarse(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        stage = (Stage) signup.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("Registro.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    // Funcion que se ejecuta al pulsar boton OPTIONS
    @FXML
    void entrarMenu(ActionEvent event) {
        // aqui se hace la pantalla de opciones PENDIENTE
    }
}