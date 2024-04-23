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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    @FXML
    private Button atras;

    @FXML
    private Button login;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    private ArrayList<Integer> puntos = new ArrayList<>();

    @FXML
    void comprobacionLogin(ActionEvent event) throws InterruptedException, IOException {
        CASINO casino = Main.getCasino();
        String[][] usuariosList = casino.getUsuariosList();
        ArrayList<Integer> puntosUsuario = casino.getPuntosUsuario();
        String user = username.getText();
        String passwd = password.getText();

        // Se recorre todo el array de usuarios para ver si algun registro coincide
        for (int i = 0; i < usuariosList.length; i++) {
            // Si el usuario y contrasenas introducidos mediante escaner coinciden en la fila de la iteracion actual...
            if (user.equals(usuariosList[i][0]) && passwd.equals(usuariosList[i][1])) {

                puntos.clear();

                casino.setUser(casino.user);
                puntos.add(puntosUsuario.get(i));
                casino.setPuntos(puntos);

                Stage stage;
                Parent root;

                stage = (Stage) login.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("MenuJuegos.fxml"));

                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

                // si el usuario no tiene puntos se le da la posibilidad de anadir mas (max 3000)
//                if (puntosUsuario.get(i) == 0) {
//                    // HACER ESTO
//
//                    // si la opcion es 's' o 'S', entonces...
////                    if (opcion.equals("s") || opcion.equals("S")) {
////                        // bucle infinito que se rompe cuando introducimos un valor de puntos correcto
////                        while (true) {
////                            pantallaDefault();
////                            System.out.print("\n\tCUANTOS PUNTOS QUIERES AGREGAR (MAX. 3000): ");
////
////                            // estructura de control de error para asegurarnos de que la entrada es un Int
////                            try {
////                                nuevosPuntos = sc.nextInt();
////
////                                if (nuevosPuntos > 0 && nuevosPuntos <= 3000) {
////                                    // si se cumplen todas las condiciones requeridas, se le anade la puntuacion que haya indicado el usuario a su cuenta
////                                    puntos.add(nuevosPuntos);
////                                    break;
////                                } else {
////                                    pantallaDefault();
////                                    System.out.println("\n\tINTRODUCE UN VALOR CORRECTO");
////                                    Thread.sleep(1000);
////                                }
////
////                            } catch (Exception e) {
////                                sc.next();
////                                pantallaDefault();
////                                System.out.println("\n\tINTRODUCE UN VALOR CORRECTO");
////                                Thread.sleep(1000);
////                            }
////                        }
////                    } else {
////                        // si no anade mas, se le asignan 0 puntos, lo que cierra el programa definitivamente
////                        puntos.add(puntosUsuario.get(i));
////                    }
//                } else {
//                    // si el usuario tiene puntos se le asignan los que estan ya en su pertenencia
//                  
//                }  
            }
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
        username.setPromptText("Ex: user01");
        password.setPromptText("Ex: 12345678");
    }

    public ArrayList<Integer> getPuntos() {
        return puntos;
    }
}