package datos;

import static casino.CASINO.pantallaDefault;
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
    private static ResultSet rSet = null;
    private static Connection connection = null;
    private static Statement statement = null;

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
        conexion();

        switch (opcion) {
            case "1":
                // funcion para iniciar sesion
                iniciarSesion();
                break;
            case "2":
                registro();
                break;
            case "3":

                break;
            case "4":
                // enviamos un mensaje final por pantalla
                pantallaDefault();
                System.out.println("\n\t\t\t\t\tHASTA PRONTO\n");
                Thread.sleep(3000);

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

    public static void conexion() throws InterruptedException, IOException {
        String url = "jdbc:sqlite:../db/casino.db";

        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
        } catch (Exception e) {
            casino.CASINO.pantallaDefault();
            System.out.println("ERROR EN LA CONEXION A LA BASE DE DATOS");
            Thread.sleep(2500);
            System.exit(0);
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
        boolean usuarioCorrecto = false;
        int numeroUsuarios = 0;
        String usuarioSQL = "",
                passwdSQL = "";

        numeroUsuarios = casino.CASINO.numeroUsuarios();

        for (int i = 0; i < 3; i++) {
            if (usuarioCorrecto) {
                break;
            }

            usuarioCorrecto = false;

            // pedimos usuario y password
            pantallaDefault();
            System.out.print("\n\n\t\t\t\t   USUARIO: ");
            user = sc.next();
            System.out.print("\n\t\t\t\t   CONTRASENA: ");
            passwd = sc.next();

            // bucle for que comprueba si el usuario y contrasena introducida son validos
            for (int j = 1; j <= numeroUsuarios; j++) {
                sentencia = "SELECT usuario, passwd from usuarios where id = " + j;
                rSet = statement.executeQuery(sentencia);

                if (rSet.next()) {
                    usuarioSQL = rSet.getString("usuario");
                    passwdSQL = rSet.getString("passwd");
                }

                // Si el usuario y contrasenas introducidos mediante escaner coinciden en la fila de la iteracion actual...
                if (user.equals(usuarioSQL) && passwd.equals(passwdSQL)) {
                    casino.CASINO.setUser(user);

                    // acceso al metodo menujuegos() para seleccionar el juego al que queremos jugar
                    MenuJuegos menujuegos = new MenuJuegos();
                    usuarioCorrecto = true;
                }
            }
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
        
        conexion();
        
        for (int i = 0; i < 3; i++) {
            if (usuarioCorrecto) {
                break;
            }
            
            usuarioCorrecto = false;
            
            // pedimos usuario, password y confirmacion de password
            pantallaDefault();
            System.out.print("\n\n\t\t\t\t   USUARIO: ");
            user = sc.next();
            System.out.print("\n\t\t\t\t   CONTRASENA: ");
            passwd = sc.next();
            System.out.print("\n\t\t\t\t   CONTRASENA: ");
            passwd_aux = sc.next();

            sentencia = "SELECT usuario from usuarios where usuario = '" + user + "';";
            rSet = statement.executeQuery(sentencia);
            
            if(!rSet.next() && (passwd.equals(passwd_aux))) {
                sentencia = "INSERT INTO usuarios VALUES (" + (numeroUsuarios + 1) + ",'" + user + "','" + passwd + "');";
                statement.executeUpdate(sentencia);
                System.out.println("\n\t\t\tUSUARIO REGISTRADO\n");
                Thread.sleep(1500);
                usuarioCorrecto = true;
            } else if (rSet.next()) {
                System.out.println("\n\t\t\tEL USUARIO YA EXISTE\n");
                Thread.sleep(1500);
            } else {
                System.out.println("\n\t\t\tLA PASSWD ES INCORRECTA\n");
                Thread.sleep(1500);
            }
        }
    }
}
