package juegos;

import casino.CASINO;
import gui.Main;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.KeyFrame;

public class DadosController implements Initializable {
    
    // Elementos graficos con ID
    @FXML
    private Button atras;
    @FXML
    private TextField cuadroPuntos;
    @FXML
    private Text errorPuntos;
    @FXML
    private Button botonApuesta;
    @FXML
    private TextField cuadroApuesta;
    @FXML
    private Button fifty;
    @FXML
    private Button five;
    @FXML
    private Button fivehundred;
    @FXML
    private Button hundred;
    @FXML
    private Button one;
    @FXML
    private Button ten;
    @FXML
    private Button thousand;
    @FXML
    private ImageView dado1;
    @FXML
    private ImageView dado2;
    @FXML
    private Button tirar;
    @FXML
    private TextField apuestaNumero;
    @FXML
    private Text errorNumero;
    @FXML
    private Text mensajeNumero;
    
    // Importamos una instancia de la clase CASINO
    private CASINO casino = Main.getCasino();
    
    // Importamos los puntos
    private String user = casino.getUser();
    private int puntos = casino.getPuntos(user);
    private int puntos_dif = 0;
    
    // Creamos la variable de apuesta
    private int apuesta = 0;
    
    // Creamos un array para las fotos de los dados
    private final Image[] fotosDado = new Image[6];
    private final Random random = new Random();
    
    // Clase inicializadora
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Establecemos los puntos en los marcadores
        cuadroPuntos.setText(puntos + " puntos");   
        cuadroApuesta.setText(apuesta + "");
        // Escondemos el error de puntos
        errorPuntos.setVisible(false);
        // Desactivamos el boton de TIRAR DADOS
        tirar.setDisable(true);
        // Quitamos el error de numero dado
        mensajeNumero.setVisible(true);
        errorNumero.setVisible(false);
        
        // Cargamos las fotos de las caras del dado
        for (int i = 0; i < 6; i++) {
            fotosDado[i] = new Image(getClass().getResourceAsStream("../img/" + (i + 1) + ".png"));
        }
        
        // Establecemos ambos dados en 6
        dado1.setImage(fotosDado[5]);
        dado2.setImage(fotosDado[5]);
        
        // Si la apuesta es 0 deshabilitar el boton de tirar dados
        cuadroApuesta.textProperty().addListener((observable, oldValue, newValue) -> {
            if (Integer.valueOf(cuadroApuesta.getText()).equals(0)) {
                tirar.setDisable(true);
            } else {
                tirar.setDisable(false);
            }
        });
    }

    /**
     * Funcion que se ejecuta al pulsat boton ATRAS
     * @param event 
     */
    @FXML
    void volverAtras(ActionEvent event) throws IOException {
        // Obtenemos variable fullscreen
        boolean fullscreen = casino.getFullscreen();
        
        Stage stage;
        Parent root;

        stage = (Stage) atras.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("../gui/MenuJuegos.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        
        // Si fullscreen esta en true redimensionar todo
        stage.setFullScreen(fullscreen);
        casino.proporcionFullscreen(stage, root, fullscreen);
        
        stage.show();
    }
    
    // Funciones para apostar puntos con los botones de las fichas
    @FXML
    void oneFicha(ActionEvent event) {
        actualizarPuntos(1);
    }
    @FXML
    void fiveFicha(ActionEvent event) {
        actualizarPuntos(5);
    }
    @FXML
    void tenFicha(ActionEvent event) {
        actualizarPuntos(10);
    }
    @FXML
    void fiftyFicha(ActionEvent event) {
        actualizarPuntos(50);
    }
    @FXML
    void hundredFicha(ActionEvent event) {
        actualizarPuntos(100);
    }
    @FXML
    void fivehundredFicha(ActionEvent event) {
        actualizarPuntos(500);
    }
    @FXML
    void thousandFicha(ActionEvent event) {
        actualizarPuntos(1000);
    }
    
    /**
     * Funcion para actualizar los puntos del marcador de la pantalla
     */
    private void actualizarPuntos (int restarPuntos) {
        // Escondemos los posibles errores
        errorPuntos.setVisible(false);
        mensajeNumero.setVisible(true);
        errorNumero.setVisible(false);
        
        // Si tenemos al menos los puntos que apostamos...
        if (puntos >= restarPuntos) {
            puntos -= restarPuntos;
            apuesta += restarPuntos;
        }
        // Si no tenemos suficientes puntos...
        else {
            // Mostramos error puntos
            errorPuntos.setText("No tienes suficientes puntos");
            errorPuntos.setVisible(true);
        }

        // Establecemos los puntos en los marcadores
        cuadroPuntos.setText(puntos + " puntos");
        cuadroApuesta.setText(apuesta + "");
    }
        
    /**
     * Funcion para borrar la apuesta actual
     * 
     * @param event 
     */
    @FXML
    void eliminarApuesta(ActionEvent event) {
        // Escondemos el error
        errorPuntos.setVisible(false);
        // Quitamos el error de numero dado
        mensajeNumero.setVisible(true);
        errorNumero.setVisible(false);

        // Devolvemos los puntos de la apuesta
        puntos += apuesta;
        apuesta = 0;
        
        // Cambiamos el valor de los marcadores
        cuadroPuntos.setText(puntos + " puntos");
        cuadroApuesta.setText(apuesta + "");
    }
    
    /**
     * Funcion que se ejecuta al pulsar TIRAR DADOS
     * 
     * @param event 
     */
    @FXML
    void tirarDados(ActionEvent event) {
        // Escondemos el error
        errorPuntos.setVisible(false);
        // Quitamos el error de numero dado
        mensajeNumero.setVisible(true);
        errorNumero.setVisible(false);
        
        // Si la puntuacion en el campo dados no es entre 2 y 12...
        try {
            int n = Integer.valueOf(apuestaNumero.getText());
            
            if (n >= 2 && n <= 12) {
                // Creamos una variable que tiene los puntos antiguos
                puntos_dif = casino.getPuntos(user);
                
                // Actualizamos los puntos en la BD
                casino.setPuntos(puntos, user);
                
                // Creamos los dos valores random de los dados y calculamos el resultado
                int r1 = random.nextInt(6) + 1;
                int r2 = random.nextInt(6) + 1;
                int resultado = r1 + r2;
                
                // Si el resultado es igual a nuestra apuesta...
                if (resultado == n) {
                    if (n == 2 || n == 12) {
                        apuesta *= 2.5;
                    } else if (n == 3 || n == 11) {
                        apuesta *= 2.3;
                    } else if (n == 4 || n == 10) {
                        apuesta *= 2.2;
                    } else if (n == 5 || n == 9) {
                        apuesta *= 2.1;
                    } else if (n == 6 || n == 8) {
                        apuesta *= 2.0;
                    } else {
                        apuesta *= 1.9;
                    }
                } else {
                    apuesta *= 0;
                }
                
                // Ejecutamos las animaciones
                animacionDado(dado1, dado2, r1, r2);
            }
            
            // Si la puntuacion esta fuera de rango...
            else {
                mensajeNumero.setVisible(false);
                errorNumero.setVisible(true);
            }
        } catch (Exception e) {
            mensajeNumero.setVisible(false);
            errorNumero.setVisible(true);
        }
        
        
    }
    
    /**
     * Funcion para ejecutar la animacion sobre el dado que se pase como parametro
     * 
     * @param dado 
     */
    private void animacionDado (ImageView dado1, ImageView dado2, int r1, int r2) {
        // Variable random del dado
        int ran1 = r1 - 1;
        int ran2 = r2 - 1;
        
        // Definimos dos timelines para los dados
        Timeline timeline = new Timeline();
        // Definir un KeyFrame para cambiar la cara del dado
        KeyFrame keyFrame = new KeyFrame(Duration.millis(100), event -> {
            int index = random.nextInt(6);  // Selecciona una cara al azar
            dado1.setImage(fotosDado[index]);
        });  
        Timeline timeline2 = new Timeline();
        // Definir un KeyFrame para cambiar la cara del dado
        KeyFrame keyFrame2 = new KeyFrame(Duration.millis(100), event -> {
            int index = random.nextInt(6);  // Selecciona una cara al azar
            dado2.setImage(fotosDado[index]);
        });
        
        // Programamos la animacion
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(10);  // Número de veces que "rueda" el dado
        timeline.setOnFinished(event -> {
            // Establecer el resultado final cuando termina la animación
            dado1.setImage(fotosDado[ran1]);
            // Cuando acabe de rodar el dado1, que ruede el 2
            timeline2.play();
        });
        timeline2.getKeyFrames().add(keyFrame2);
        timeline2.setCycleCount(10);  // Número de veces que "rueda" el dado
        timeline2.setOnFinished(event -> {
            // Establecer el resultado final cuando termina la animación
            dado2.setImage(fotosDado[ran2]);
            
            // Sumamos el resultado de la apuesta a los puntos
            puntos += apuesta;
            
            // Si teniamos mas puntos antes, entonces...
            if (puntos_dif > puntos) {
                // Mostramos error puntos
                errorPuntos.setStyle("-fx-alignment: center-right;");
                errorPuntos.setText("Has perdido " + (puntos_dif - puntos) + " puntos");
                errorPuntos.setVisible(true);
            }
            // Si ahora tenemos mas, entonces...
            else if (puntos_dif < puntos) {
                // Mostramos error puntos
                errorPuntos.setStyle("-fx-alignment: center-right;");
                errorPuntos.setText("¡Has ganado " + (puntos - puntos_dif) + " puntos!");
                errorPuntos.setVisible(true);
            }
            
            // actualizamos los puntos en la BD
            casino.setPuntos(puntos, user);
            // Reiniciamos el contador de apuesta a 0
            apuesta = 0;

            // Establecemos los puntos en los marcadores
            cuadroPuntos.setText(puntos + " puntos");
            cuadroApuesta.setText(apuesta + "");
            apuestaNumero.setText("");
        });

        timeline.play();
    }
}
