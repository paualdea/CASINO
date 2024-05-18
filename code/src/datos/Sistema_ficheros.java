package datos;

import casino.CASINO;
import gui.Main;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.sql.ResultSet;
import java.util.logging.Logger;
import java.sql.Connection;

/**
 * Clase que contiene todo el sistema de ficheros que implementamos a partir de
 * la version 3.0.0 en nuestro programa.
 *
 * Contiene un constructor que se encarga de crear todo el sistema de ficheros
 * una vez instanciamos la clase. Tambien tiene un metodo que sirve para
 * actualizar los valores de todos los usuarios del programa en el fichero que
 * tenemos creado.
 *
 * @author Pau Aldea Batista
 */
public class Sistema_ficheros {

    // Importamos variables de la clase CASINO
    private CASINO casino = Main.getCasino();
    private Connection conexion;

    // Valores conexion a la base de datos
    static String url = "jdbc:mysql://localhost:3306/";
    static final String user_db = "root";
    static Connection connection = null;
    static Statement statement = null;

    /**
     * Este metodo constructor sirve para crear e inicializar todo el sistema de
     * ficheros que hemos implementado en la v3.0.0 de este CASINO para hacer
     * los datos persistentes y recuperables en la siguiente ejecucion. Una vez
     * creamos una instancia de esta clase, se crea todo el sistema de ficheros
     * automaticamente
     *
     * @throws java.io.IOException
     */
    public Sistema_ficheros() throws IOException {
        ResultSet resultSet = null;
        BufferedReader reader = null;
        String script = "../db/db.sql";

        // Creacion conexion a la base de datos. 
        // Comprobacion existencia base de datos implementada
        try {
            connection = DriverManager.getConnection(url, user_db, "");
            statement = connection.createStatement();

            resultSet = statement.executeQuery("SHOW DATABASES LIKE 'casino'");

            if (!resultSet.next()) {
                try {
                    // Creamos un array con las sentencias SQL para crear la base de datos 'casino'
                    String[] sentencias = {
                        "CREATE DATABASE CASINO;",
                        "USE CASINO;",
                        "CREATE TABLE IF NOT EXISTS usuarios (id INT auto_increment PRIMARY KEY, username varchar(50), passwd varchar(50));",
                        "CREATE TABLE IF NOT EXISTS puntos (id_usuario int, puntos int, PRIMARY KEY (id_usuario), FOREIGN KEY (id_usuario) REFERENCES usuarios(id));",
                        "CREATE TABLE IF NOT EXISTS log (id int auto_increment, accion varchar(500), marca_tiempo datetime, PRIMARY KEY (id));",
                        "CREATE TRIGGER trigger_añadir_log_usuario AFTER INSERT ON usuarios FOR EACH ROW BEGIN INSERT INTO log (accion, marca_tiempo) VALUES (CONCAT('Usuario ',NEW.username,' añadido'), NOW()); END;",
                        "CREATE TRIGGER trigger_añadir_log_puntos AFTER INSERT ON puntos FOR EACH ROW BEGIN DECLARE usuario varchar(50); SELECT username INTO usuario FROM usuarios WHERE id = new.id_usuario ; INSERT INTO log (accion, marca_tiempo) VALUES (CONCAT(NEW.puntos, ' puntos añadidos al usuario ', usuario), NOW()); END;",
                        "INSERT INTO usuarios (username, passwd) VALUES ('paualdea', 'aldea2');",
                        "INSERT INTO usuarios (username, passwd) VALUES ('mohammadtufail', 'tufail2');",
                        "INSERT INTO puntos VALUES (1, 12000);",
                        "INSERT INTO puntos VALUES (2, 12000);"
                    };

                    for (int i = 0; i < sentencias.length; i++) {
                        statement.execute(sentencias[i]);
                    }
                    System.out.println("BASE DE DATOS CREADA CORRECTAMENTE");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            url = "jdbc:mysql://localhost:3306/casino";
            connection = DriverManager.getConnection(url, user_db, "");
            
            // Mandamos la conexion y el statement a la instancia casino
            casino.setConnection(connection);
            casino.setStatement(statement);

        } catch (SQLException ex) {
            Logger.getLogger(Sistema_ficheros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
