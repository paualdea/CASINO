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

    /**
     * Metodo constructor de la clase Login
     *
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public Login() throws IOException, InterruptedException, SQLException {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        String user = "", passwd = "", passwd_aux = "", sentencia = "", uBD = "root", pBD = "";

        // bucle while para seleccionar opciones de inicio en el programa
        while (true) {
            // metodo para mostrar la caberecera prederminada del CASINO
            pantallaDefault();

            System.out.println("\n\t\t\t\t     1. INICIAR SESION");
            System.out.println("\n\t\t\t\t     2. REGISTRARSE");
            System.out.println("\n\t\t\t\t     3. RESTABLECER CASINO");
            System.out.println("\n\t\t\t\t     4. SALIR");
            System.out.print("\n\n\tQUE QUIERE HACER: ");

            // estructura de control de errores para evitar que se cierre el programa si se introduce un dato erroneo en el scanner
            try {
                opcion = sc.nextInt();

                if (!(opcion >= 1 && opcion <= 4)) {
                    throw new Exception();
                } else {
                    break;
                }
            } catch (Exception e) {
                pantallaDefault();
                sc.next();
                System.out.println("\n\t\t\t\t  INTRODUCE UNA OPCION DISPONIBLE");
                Thread.sleep(1750);
            }
        }

        String url = "jdbc:sqlite:../db/casino.db";
        Connection connection = null;
        Statement statement = null;
        ResultSet rSet = null;

        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
        } catch (Exception e) {
            casino.CASINO.pantallaDefault();
            System.out.println("ERROR EN LA CONEXION A LA BASE DE DATOS");
            Thread.sleep(2500);
            System.exit(0);
        }

        switch (opcion) {
            case 1:
                int numeroUsuarios = 0;
                String usuarioSQL = "",
                 passwdSQL = "";

                // creamos una estructura de control que intenta seleccionar el numero de usuarios que tenemos
                try {
                    connection = DriverManager.getConnection(url, uBD, pBD);
                    statement = connection.createStatement();

                    sentencia = "SELECT COUNT(*) FROM usuarios;";
                    rSet = statement.executeQuery(sentencia);

                    if (rSet.next()) {
                        numeroUsuarios = rSet.getInt(1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < 2; i++) {
                    // pedimos usuario y password
                    pantallaDefault();
                    System.out.print("\n\n\t\t\t\t   USUARIO: ");
                    user = sc.next();
                    System.out.print("\n\t\t\t\t   CONTRASENA: ");
                    passwd = sc.next();

                    // bucle for que empieza desde el 1 y comprueba si el usuario y contrasena introducida son validos
                    for (int j = 1; j <= numeroUsuarios; j++) {
                        sentencia = "SELECT usuario, passwd from usuarios where id = " + i;
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
                        }
                    }
                }
                break;

            case 2:

                break;
            case 3:

                break;
            case 4:
                // enviamos un mensaje final por pantalla
                pantallaDefault();
                System.out.println("\n\t\t\t\t\tHASTA PRONTO\n");
                Thread.sleep(3000);

                // mandamos un codigo de salida del programa
                System.exit(0);
                break;
        }
    }
}
