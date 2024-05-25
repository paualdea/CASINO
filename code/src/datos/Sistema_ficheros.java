package datos;

import static casino.CASINO.pantallaDefault;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
    Connection connection = null;
    Statement statement = null;
    ResultSet rSet = null;

    /**
     * Metodo constructor de este clase. Sirve para crear o/y inicializar el
     * sistema de base de datos que se ha implementado a partir de la version
     * 5.0.0 del programa.
     *
     * @throws java.sql.SQLException
     */
    public Sistema_ficheros() throws SQLException {
        File db = new File("../db/casino.db");
        ResultSet rSet1 = null;
        
        if (db.exists()) {
            try {
                connection = DriverManager.getConnection(url);
                statement = connection.createStatement();
                rSet = statement.executeQuery("SELECT * FROM usuarios");
                rSet1 = statement.executeQuery("SELECT * FROM puntos");
                
            } catch (Exception e) {
                System.out.println("ERROR EN LA CONEXION A LA BASE DE DATOS");
            }

            if (!rSet.next() && !rSet1.next()) {
                statement.executeUpdate("INSERT INTO usuarios VALUES (1, 'paualdea', 'aldea2')");
                statement.executeUpdate("INSERT INTO puntos VALUES (1, 12000)");
            }
        } else {
            
        }

    }

    /**
     * Este metodo sirve para actualizar el contenido del fichero para agregar
     * nuevos usuarios, puntos que se pierden o ganan, etc.
     *
     * Recibe como parametro el arraylist que tiene los puntos de la sesion
     * actual de juego
     *
     * @param puntosPendientes
     * @throws FileNotFoundException
     * @throws IOException
     * @throws InterruptedException
     */
    public static void actualizarFicheros(ArrayList<Integer> puntos, String[][] usuariosList) throws FileNotFoundException, IOException, InterruptedException {
        // establecemos un writer para hacer los cambios en el fichero que indicamos
        PrintWriter writer = new PrintWriter("./data/usuarios.txt", "UTF-8");
        String resultadoUsuarios = "";
        String linea = "";
        // llamamos al getter de la clase CASINO que nos da el usuario de la sesion actual de juego
        String user = casino.CASINO.getUser();

        // se repite este bucle for por las filas que tenga el array de usuarios de nuestro casino
        for (int i = 0; i < usuariosList.length; i++) {
            // buscamos la fila en la que se encuentra el usuario que estamos usando para poner la nueva puntuacion que tiene en el fichero
            if (usuariosList[i][0].equals(user)) {
                puntosUsuario.set(i, puntos.get(0));
            }
            // establecemos la linea que anadiremos al fichero (usuario, contrasena, puntos y salto de linea)
            linea = usuariosList[i][0] + "," + usuariosList[i][1] + "," + puntosUsuario.get(i) + "\n";
            // agregamos a la variable resultadoUsuarios el contenido de la nueva linea
            resultadoUsuarios += linea;
        }

        // anadimos en el fichero todo lo que hemos generado en el bucle for
        writer.print(resultadoUsuarios);
        // cerramos el writer
        writer.close();

        // animacion de salida del programa
        String punto = ".";
        for (int i = 0; i < 3; i++) {
            pantallaDefault();
            System.out.println("\n\n\t\t\t\t\tSALIENDO" + punto);
            punto += ".";
            Thread.sleep(450);
        }

        System.out.println("\n\n");
        // paramos la ejecucion del programa
        System.exit(0);
    }
}
