package datos;

import static casino.CASINO.pantallaDefault;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import juegos.MenuJuegos;

/**
 * Clase Login. Contiene todo el codigo para manejar el inicio y registro de
 * usuarios en nuestro programa. Solo contiene un metoto (usuarios)
 *
 * @author Pau Aldea Batista
 */
public class Login {

    private static String user = "", passwd = "", passwd_aux = "", sentencia = "";
    private static Scanner sc = new Scanner(System.in);

    /**
     * Metodo constructor de la clase Login
     *
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public Login() throws IOException, InterruptedException, SQLException {
        String uBD = "root", pBD = "", opcion = "";

        // metodo para mostrar la caberecera prederminada del CASINO
        pantallaDefault();

        System.out.println("\n\t\t\t\t     1. INICIAR SESION");
        System.out.println("\n\t\t\t\t     2. REGISTRARSE");
        System.out.println("\n\t\t\t\t     3. RESTABLECER CASINO");
        System.out.println("\n\t\t\t\t     4. SALIR");
        System.out.print("\n\n\tQUE QUIERE HACER: ");

        opcion = sc.next();

        // funcion para realizar la conexion con la base de datos SQLite
        Connection connection = casino.CASINO.crearConexion();
        Statement statement = casino.CASINO.crearStatement(connection);

        switch (opcion) {
            case "1":
                // funcion para iniciar sesion
                iniciarSesion();
                break;
            case "2":
                registro();
                break;
            case "3":
                reset();
                break;
            case "4":
                // enviamos un mensaje final por pantalla
                pantallaDefault();
                System.out.println("\n\t\t\t\t        HASTA PRONTO\n");
                Thread.sleep(1750);

                // mandamos un codigo de salida del programa
                System.exit(0);
                break;
            default:
                pantallaDefault();
                System.out.println("\n\t\t\t\tINTRODUCE UN OPCION CORRECTA\n");
                Thread.sleep(2500);
                break;
        }
    }

    /**
     * Funcion para iniciar sesion en el juego del casino. Se comunica con la
     * base de datos para comprobar que el usuario y contrasenas introducidas
     * sean validas.
     *
     * @throws IOException
     * @throws InterruptedException
     * @throws SQLException
     */
    public static void iniciarSesion() throws IOException, InterruptedException, SQLException {
        Connection connection = casino.CASINO.crearConexion();
        Statement statement = casino.CASINO.crearStatement(connection);
        ResultSet rs = null;
        boolean usuarioCorrecto = false;
        int numeroUsuarios = 0;
        String usuarioSQL = "",
                passwdSQL = "";

        numeroUsuarios = casino.CASINO.numeroUsuarios();
        
        // pedimos usuario y password
        pantallaDefault();
        System.out.print("\n\n\t\t\t\t   USUARIO: ");
        user = sc.next();
        System.out.print("\n\t\t\t\t   CONTRASENA: ");
        passwd = sc.next();

        // bucle for que comprueba si el usuario y contrasena introducida son validos
        for (int j = 1; j <= numeroUsuarios; j++) {
            sentencia = "SELECT usuario, passwd from usuarios where id = " + j;
            rs = statement.executeQuery(sentencia);

            if (rs.next()) {
                usuarioSQL = rs.getString("usuario");
                passwdSQL = rs.getString("passwd");
            }

            // Si el usuario y contrasenas introducidos mediante escaner coinciden en la fila de la iteracion actual...
            if (user.equals(usuarioSQL) && passwd.equals(passwdSQL)) {
                casino.CASINO.setUser(user);

                statement.close();
                connection.close();
                usuarioCorrecto = true;
                // acceso al metodo menujuegos() para seleccionar el juego al que queremos jugar
                MenuJuegos menujuegos = new MenuJuegos();
            }
            // Si el usuario es correcto pero la contrasena no, enseña un error
            else if (user.equals(usuarioSQL) && !passwd.equals(passwdSQL)) {
                System.out.println("\n\t\t\t\t   PASSWORD INCORRECTO");
                usuarioCorrecto = true;
                statement.close();
                connection.close();
                Thread.sleep(1750);
                break;
            }
            // Si no existe ni el usuario ni la contrasena, notificar que el usuario no existe
            else {
                usuarioCorrecto = false;
            }
        }
        
        // despues de acabar el bucle for cerrar la conexion con la BD
        statement.close();
        connection.close();
        
        // si despues del for no ha habido ninguna coincidencia enseñar que el usuario no existe
        if (!usuarioCorrecto) {
            System.out.println("\n\t\t\t\t   EL USUARIO NO EXISTE");
            Thread.sleep(1750);
        }
        
    }

    /**
     * Funcion para registrar a un usuario en la base de datos
     *
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public static void registro() throws IOException, InterruptedException, SQLException {
        boolean usuarioCorrecto = false;
        int numeroUsuarios = casino.CASINO.numeroUsuarios();
        Connection connection = casino.CASINO.crearConexion();
        Statement statement = casino.CASINO.crearStatement(connection);
        ResultSet rs = null;
        
        // pedimos usuario y password
        pantallaDefault();
        System.out.print("\n\n\t\t\t\t   USUARIO: ");
        user = sc.next();
        System.out.print("\n\t\t\t\t   CONTRASENA: ");
        passwd = sc.next();

        sentencia = "SELECT usuario from usuarios where usuario = '" + user + "';";
        rs = statement.executeQuery(sentencia);

        if (!rs.next()) {
            sentencia = "INSERT INTO usuarios VALUES (" + (numeroUsuarios + 1) + ",'" + user + "','" + passwd + "');";
            statement.executeUpdate(sentencia);
            sentencia = "INSERT INTO puntos VALUES (" + (numeroUsuarios + 1) + ", 3000);";
            statement.executeUpdate(sentencia);
            casino.CASINO.setUser(user);

            statement.close();
            connection.close();

            System.out.println("\n\t\t\t\t USUARIO REGISTRADO\n");
            Thread.sleep(1500);
            usuarioCorrecto = true;
        } else {
            System.out.println("\n\t\t\t\tEL USUARIO YA EXISTE\n");
            statement.close();
            connection.close();
            Thread.sleep(1500);
        }
    }
    
    /**
     * Funcion para restablecer los datos predeterminados de la base de datos
     * 
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     * @throws java.sql.SQLException
     */
    public static void reset() throws IOException, InterruptedException, SQLException {
        String inputReset = "";
        
        pantallaDefault();
        System.out.print("\n\t\t\t\tSE VAN A BORRAR TODOS LOS DATOS\n\n\t¿ESTAS SEGURO? (s/n): ");
        inputReset = sc.next();
        
        if (inputReset.equals("s") || inputReset.equals("S")) {
            Connection connection = casino.CASINO.crearConexion();
            Statement statement = casino.CASINO.crearStatement(connection);
            ResultSet rs = null;
            
            String db_route = "../db/casino.db";
            File db = new File(db_route);
            String url = "jdbc:sqlite:" + db_route;

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
                casino.CASINO.pantallaDefault();
                System.out.println("ERROR EN LA CONEXION A LA BASE DE DATOS");
                Thread.sleep(2500);
            }
            
            // ejecutamos el script de creacion de la base de datos
            try {
                // Creamos un array con las sentencias SQL para crear la base de datos 'casino'
                String[] sentencias = {
                    "DELETE FROM puntos;",
                    "DELETE FROM usuarios;",
                    "CREATE TABLE IF NOT EXISTS puntos (id INT PRIMARY KEY, puntos INT, FOREIGN KEY (id) REFERENCES usuarios(id));",
                    "INSERT OR IGNORE INTO usuarios (id, usuario, passwd) VALUES (1, 'paualdea', 'aldea2');",
                    "INSERT OR IGNORE INTO puntos (id, puntos) VALUES (1, 12000);"
                };

                // Bucle for para iterar sobre todas las sentencias y ejecutarlas
                for (int i = 0; i < sentencias.length; i++) {
                    statement.execute(sentencias[i]);
                }
            } catch (Exception e) {
                casino.CASINO.pantallaDefault();
                System.out.println("ERROR EN LA EJECUCION DE LA BASE DE DATOS");
                Thread.sleep(2500);
            }
            
            String puntos = ".";
            for (int i = 0; i < 3; i++) {
                casino.CASINO.pantallaDefault();
                System.out.println("\n\t\t\t\t     BORRANDO DATOS" + puntos);
                Thread.sleep(500);
                
                puntos += ".";
            }
            
            // cerramos el statement y la conexion con la BD
            statement.close();
            connection.close();
        }
    }
}
