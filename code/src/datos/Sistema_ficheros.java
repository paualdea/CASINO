package datos;

import casino.CASINO;
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
    // Creacion de las variables para interactuar con la BD
    private String url = null;
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet rSet = null;
    
    private CASINO casino = new CASINO();

    /**
     * Metodo constructor de este clase. Sirve para crear o/y inicializar el
     * sistema de base de datos que se ha implementado a partir de la version
     * 5.0.0 del programa.
     *
     * @throws java.sql.SQLException
     */
    public Sistema_ficheros() throws SQLException, IOException, InterruptedException {
        String db_route = "../db/casino.db";
        File db = new File(db_route);
        url = "jdbc:sqlite:" + db_route;

        // cargamos el driver SQLite JDBC
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // condificional que nos asegura que el fichero casino.db esta creado
        if (!db.exists()) {
            db.createNewFile();
        }

        // iniciamos la conexion con la base de datos
        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
        } catch (SQLException e) {
            casino.pantallaDefault();
            System.out.println("ERROR EN LA CONEXION A LA BASE DE DATOS");
            Thread.sleep(2500);
        }

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
            casino.pantallaDefault();
            System.out.println("ERROR EN LA EJECUCION DE LA BASE DE DATOS");
            Thread.sleep(2500);
        }

        statement.close();
        connection.close();
    }

    // Setter y getter
    public CASINO getCasino() {
        return casino;
    }

    public void setCasino(CASINO casino) {
        this.casino = casino;
    }

}
