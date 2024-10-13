package gui;

import casino.CASINO;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private int puntos;
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private String sentencia = "";
    private final String urlBD = "jdbc:mysql://localhost:3306/casino";
    private final String userBD = "root";
    private final String passwdBD = "";

    @FXML
    private Button atras;

    @FXML
    private Button dados;
    
    @FXML
    private Button borrar;
    
    @FXML
    void borrarCuenta(ActionEvent event) throws IOException, SQLException {
        try {
            connection = DriverManager.getConnection(urlBD, userBD, passwdBD);
            statement = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String sentencia = "DELETE FROM puntos WHERE id_usuario = (SELECT id FROM usuarios WHERE username = '" + user + "')";
        statement.executeUpdate(sentencia);
        
        sentencia = "DELETE FROM usuarios WHERE username = '" + user + "'";
        statement.executeUpdate(sentencia);
        
        Stage stage;
        Parent root;

        stage = (Stage) atras.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("PaginaInicio.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void juegoDados(ActionEvent event) throws IOException {

        if (puntos == 0) {
            Stage stage;
            Parent root;

            stage = (Stage) atras.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Sinpuntos.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            Stage stage;
            Parent root;

            stage = (Stage) atras.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Dados.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
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
        
        try {
            connection = DriverManager.getConnection(urlBD, userBD, passwdBD);
            statement = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(RegistroController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int numeroUsuarios = 0;
        String usuarioBD = "";
        int puntosBD = 0;

        try {
            statement = connection.createStatement();

            sentencia = "SELECT COUNT(*) FROM usuarios;";
            resultSet = statement.executeQuery(sentencia);

            if (resultSet.next()) {
                numeroUsuarios = resultSet.getInt(1);
            }
            
            System.out.println(numeroUsuarios);

        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 1; i <= numeroUsuarios; i++) {

            try {
                sentencia = "SELECT username, puntos from usuarios u inner join puntos p on p.id_usuario = u.id where id = " + i;
                resultSet = statement.executeQuery(sentencia);

                if (resultSet.next()) {
                    usuarioBD = resultSet.getString("username");
                    puntosBD = resultSet.getInt("puntos");
                }

                if (usuarioBD.equals(user)) {
                    puntos = puntosBD;
                    casino.setPuntos(puntos, user);
                }
            } catch (Exception e) {}
        }
        
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(MenuJuegosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
