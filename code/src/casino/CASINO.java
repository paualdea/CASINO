package casino;

import datos.Sistema_ficheros;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
    
    // Variables DB
    private String url = null;
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet rSet = null;

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
            e.printStackTrace();
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
    
    /**
     * Metodo para obetener el numero de usuarios de la BD
     * 
     * @return 
     */
    public int numeroUsuarios () {
        int numeroUsuarios = 0;

        // Estructura de control para obtener el numero de usuarios de la base de datos
        try {
            connection = crearConexion();
            statement = crearStatement(connection);
            
            // Sentencia SQL para contar el numero de registros de la tabla usuarios
            String sentencia = "SELECT COUNT(*) FROM usuarios;";
            
            // Ejecutamos la sentencia
            rSet = statement.executeQuery(sentencia);

            // Si la sentencia ha dado resultados, almacenarlos en la variable numeroUsuarios
            if (rSet.next()) {
                numeroUsuarios = rSet.getInt(1);
            }
            
            // Cerramos el statement y la conexion
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return numeroUsuarios;
    }
    
    // Getters y Setters
    
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        CASINO.user = user;
    }

    public void setPuntos(int puntos, String user) {
        // Estructura de control para actualizar los puntos del user en la BD
        try {
            // Creamos la conexion y el statement
            connection = crearConexion();
            statement = crearStatement(connection);
            
            // Sentencia SQL para actualizar puntos en el registro del usuario indicado
            String sentencia = "UPDATE puntos SET puntos = " + puntos + " WHERE id = (SELECT id FROM usuarios WHERE usuario = '" + user + "');";
            
            // Ejecutamos la sentencia
            statement.execute(sentencia);
            
            // Cerrar conexion y statment
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public int getPuntos(String user) {
        // Estructura de control para obtener los puntos del usuario logueado
        try {
            // Creamos la conexion y el statement
            connection = crearConexion();
            statement = crearStatement(connection);

            // Sentencia SQL para obtener el numero de puntos del usuario logueado
            String sentencia = "SELECT puntos FROM puntos WHERE id = (SELECT id FROM usuarios WHERE usuario = '" + user + "');";

            // Ejecutamos la sentencia
            rSet = statement.executeQuery(sentencia);
            
            // Si la sentencia tiene resultados, guardarlos en la variable puntos
            if (rSet.next()) {
                puntos = rSet.getInt(1);
            }
            
            // Cerrar conexion y statment
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return puntos;
    } 
}