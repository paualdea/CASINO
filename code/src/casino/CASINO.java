package casino;

import datos.Sistema_ficheros;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * CASINO - v6.0.0
 * _________________________________________________________________________________________________________________________________________
 *
 * Bienvenido a la version 6 de este CASINO.
 * 
 * En este caso, hemos remodelado todo el codigo para que incorpore un sistema de pantallas graficas usando JavaFX.
 * Sigue teniendo los mismos juegos e igual que en la version 5, incorpora un sistema de almacenamiento de datos
 * basado en una BD SQLite.
 * 
 * ¡Espero que disfrutes del juego!
 *
 * @author Pau Aldea Batista
 */
public class CASINO {
    // Variable publica que almacena el usuario que esta usando la sesion actual del CASINO
    public static String user;
    
    // Creamos las variables privadas de puntos y datos
    private int puntos;
    private Sistema_ficheros datos;

    /**
     * Metodo constructor que crea e inicializa el sistema de datos
     */
    public CASINO() throws SQLException {
        datos = new Sistema_ficheros();
    }
    
    /**
     * Metodo para crear una conexion con la BD
     * 
     * @return
     */
    public static synchronized Connection crearConexion() {
        Connection connection = null;
        String url = "jdbc:sqlite:../db/casino.db";

        try {
            connection = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.out.println("ERROR EN LA CONEXION A LA BASE DE DATOS");
            System.exit(0);
        }

        return connection;
    }
    
    /**
     * Metodo para crear un statement en base a una conexion
     * 
     * @param connection
     * @return 
     */
    public static synchronized Statement crearStatement(Connection connection) {
        Statement statement = null;

        try {
            statement = connection.createStatement();
        } catch (Exception e) {
            System.out.println("ERROR EN LA CONEXION A LA BASE DE DATOS");
            System.exit(0);
        }

        return statement;
    }
    
    // Getters y Setters
    
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        CASINO.user = user;
    }

    public void setPuntos(int puntos, String user) {
        // hacer esto
    }
    
    public int getPuntos(String user) {
        // hacer esto
        return 0;
    } 
}