package casino;

import datos.Sistema_ficheros;
import java.util.ArrayList;

/**
 * CASINO - v5.0.0
 * _________________________________________________________________________________________________________________________________________
 *
 * PENDIENTE
 *
 * ¡Esperemos que disfrutes del juego!
 *
 * @author Pau Aldea Batista
 * @author Mohammad Tufail Imran
 */
public class CASINO {

    // Array bidimensional para almacenar los usuarios del sistema de juego del casino
    public static String[][] usuariosList = new String[0][2];

    // ArrayList para almacenar todos los puntos de los usuarios creados en el fichero de juego
    public static ArrayList<Integer> puntosUsuario = new ArrayList<>();

    // Variable booleana para saber si el sistema de ficheros es nuevo o no
    public static boolean ficheroNuevo = false;
    
    // Variables de datos de usuario
    public static String user;

    // Metodo constructor
    public CASINO() {
        Sistema_ficheros datos = new Sistema_ficheros();

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
}