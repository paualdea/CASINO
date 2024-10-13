package datos;

import casino.CASINO;
import gui.Main;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase que crea un sistema de datos usando una BD SQLite
 *
 * @author Pau Aldea Batista
 */
public class Sistema_ficheros {

    // Importamos la clase CASINO
    private CASINO casino = Main.getCasino();

    // Variables para trabajar con la base de datos
    private String url = null;
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet rSet = null;

    /**
     * Este metodo constructor sirve para crear e inicializar la base de datos usando el SGBD SQLite
     *
     * @throws java.sql.SQLException
     */
    public Sistema_ficheros() throws SQLException {
        // creamos una conexion y un statement
        Connection connection = casino.crearConexion();
        Statement statement = casino.crearStatement(connection);

        // ejecutamos el script de creacion de la base de datos
        try {
            // Creamos un array con las sentencias SQL para crear la base de datos 'casino'
            String[] sentencias = {
                "CREATE TABLE IF NOT EXISTS usuarios (id INT PRIMARY KEY, usuario VARCHAR(100), passwd VARCHAR(100));",
                "CREATE TABLE IF NOT EXISTS puntos (id INT PRIMARY KEY, puntos INT, FOREIGN KEY (id) REFERENCES usuarios(id));",
                "INSERT OR IGNORE INTO usuarios (id, usuario, passwd) VALUES (1, 'paualdea', 'aldea2');",
                "INSERT OR IGNORE INTO puntos (id, puntos) VALUES (1, 12000);"
            };

            // Bucle for para iterar sobre todas las sentencias y ejecutarlas
            for (int i = 0; i < sentencias.length; i++) {
                statement.execute(sentencias[i]);
            }
        } catch (Exception e) {
            System.out.println("ERROR EN LA EJECUCION DE LA BASE DE DATOS");
        }

        statement.close();
        connection.close();
    }
}
