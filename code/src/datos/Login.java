package datos;

import static casino.CASINO.borrarPantalla;
import static casino.CASINO.pantallaDefault;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

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
    public Login() throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        String user = "", passwd = "", passwd_aux = "";

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
                for (int i = 0; i < 2; i++) {
                    // pedimos usuario y password
                    pantallaDefault();
                    System.out.print("\n\n\t\t\t\t   USUARIO: ");
                    user = sc.next();
                    System.out.print("\n\t\t\t\t   CONTRASENA: ");
                    passwd = sc.next();
                    
                                        
                }
                break;

            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
        }
    }
}
