package datos;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    private String url = "jdbc:sqlite:../db/casino.db";
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet rSet = null;

    /**
     * Metodo constructor de este clase. Sirve para crear o/y inicializar el
     * sistema de base de datos que se ha implementado a partir de la version
     * 5.0.0 del programa.
     *
     * @throws java.sql.SQLException
     */
    public Sistema_ficheros() throws SQLException, IOException, InterruptedException {
        File db = new File("../db/casino.db");
        ResultSet rSet1 = null;

        if (db.exists()) {
            try {
                connection = DriverManager.getConnection(url);
                statement = connection.createStatement();
                rSet = statement.executeQuery("SELECT * FROM usuarios");
            } catch (Exception e) {
                casino.CASINO.pantallaDefault();
                System.out.println("ERROR EN LA CONEXION A LA BASE DE DATOS");
                Thread.sleep(2500);
            }

            if (!rSet.next()) {
                statement.executeUpdate("INSERT INTO usuarios VALUES (1, 'paualdea', 'aldea2')");
                statement.executeUpdate("INSERT INTO puntos VALUES (1, 12000)");
            }
        } else {
            try {
                // Creamos un array con las sentencias SQL para crear la base de datos 'casino'
                String[] sentencias = {
                    "CREATE DATABASE CASINO;",
                    "USE CASINO;",
                    "CREATE TABLE IF NOT EXISTS usuarios (id INT PRIMARY KEY, username varchar(50), passwd varchar(50));",
                    "CREATE TABLE IF NOT EXISTS puntos (id_usuario int, puntos int, PRIMARY KEY (id_usuario), FOREIGN KEY (id_usuario) REFERENCES usuarios(id));",
                    "INSERT INTO usuarios (username, passwd) VALUES ('paualdea', 'aldea2');",
                    "INSERT INTO puntos VALUES (1, 12000);"
                };

                // Bucle for para iterar sobre todas las sentencias y ejecutarlas
                for (int i = 0; i < sentencias.length; i++) {
                    statement.execute(sentencias[i]);
                }
            } catch (Exception e) {
                casino.CASINO.pantallaDefault();
                System.out.println("ERROR EN LA CREACIÓN DE LA BASE DE DATOS");
                Thread.sleep(2500);
            }
        }
    }
}