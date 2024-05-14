package casino;

import datos.Sistema_ficheros;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * CASINO - v5.0.0
 * _________________________________________________________________________________________________________________________________________
 *
 * PENDIENTE
 *
 * @author Pau Aldea Batista
 * @author Mohammad Tufail Imran
 */
public class CASINO {

    // Array bidimensional para almacenar los usuarios del sistema de juego del casino
    public static String[][] usuariosList = new String[0][3];

    // ArrayList para almacenar todos los puntos de los usuarios creados en el fichero de juego
    public static ArrayList<Integer> puntosUsuario = new ArrayList<>();

    // Variable booleana para saber si el sistema de ficheros es nuevo o no
    public static boolean ficheroNuevo = false;
    
    // Variables de datos de usuario
    public static String user;
    
    private int puntos;
    private int apuesta;
    private int valorDados = 0;
    private boolean ganado = false;
    private Sistema_ficheros datos;

    // Metodo constructor
    public CASINO() throws IOException, SQLException {
        datos = new Sistema_ficheros();

        if (ficheroNuevo) {
            datos = null;
            datos = new Sistema_ficheros();
        }
    }

    // Getters y Setters
    public static void setFicheroNuevo(boolean ficheroNuevo) {
        CASINO.ficheroNuevo = ficheroNuevo;
    }

    public static void setUsuariosList(String[][] usuariosList) {
        CASINO.usuariosList = usuariosList;
    }

    public static void setPuntosUsuario(ArrayList<Integer> puntosUsuario) {
        CASINO.puntosUsuario = puntosUsuario;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        CASINO.user = user;
    }

    public static String[][] getUsuariosList() {
        return usuariosList;
    }

    public static ArrayList<Integer> getPuntosUsuario() {
        return puntosUsuario;
    }

    public static boolean isFicheroNuevo() {
        return ficheroNuevo;
    }

    public void setPuntos(int puntos, String user) {
        for (int i = 0; i < usuariosList.length; i++) {
            if (user.equals(usuariosList[i][0])){
                usuariosList[i][2] = Integer.toString(puntos);
            }
        }
    }
    
    public int getPuntos(String user){
        for (int i = 0; i < usuariosList.length; i++) {
            if (user.equals(usuariosList[i][0])) {
                int puntos = Integer.parseInt(usuariosList[i][2]);
                return puntos;
            }
        }
        return 0;
    }

    public int getApuesta() {
        return apuesta;
    }

    public void setApuesta(int apuesta) {
        this.apuesta = apuesta;
    }   

    public int getValorDados() {
        return valorDados;
    }

    public void setValorDados(int valorDados) {
        this.valorDados = valorDados;
    }

    public boolean isGanado() {
        return ganado;
    }

    public void setGanado(boolean ganado) {
        this.ganado = ganado;
    }
    
    public void actualizarFicheros() throws IOException, FileNotFoundException, InterruptedException {
        datos.actualizarFicheros();
    }
}