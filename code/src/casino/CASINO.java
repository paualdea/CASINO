package casino;

import java.io.IOException;
import java.util.Scanner;
import datos.Sistema_ficheros;
import datos.Login;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 * .:CASINO:. VERSION: 5.0.0
 * _________________________________________________________________________________________________________________________________________
 *
 * Este proyecto incorpora un sistema de login para posteriormente acceder a un
 * menu en donde se pueden seleccionar varios juegos de casino (en clases). El
 * primero de estos es el juego de los dados, luego tenemos tambien el bingo
 * contra el ordenador, una ruleta y un black jack.
 *
 * En esta version 5 del CASINO, hemos implementado un nuevo sistema de ficheros
 * que usa una base de datos. Hemos cambiado el sistema de ficheros de texto por
 * SQLite.
 *
 * ¡Espero que disfrutes del juego!
 *
 * @author Pau Aldea Batista
 */
public class CASINO {

    // Strings para la cabecera del CASINO
    public static String titulo = "\n\t                          _____\n" + "\t        .-------.        |A .  | _____\n" + "\t       /   o   /|        | /.\\ ||A ^  | _____ \n" + "\t      /_______/o|        |(_._)|| / \\ ||A _  | _____           \n" + "\t      | o     | |        |  |  || \\ / || ( ) ||A_ _ |         .-\"\"\"-.\n" + "\t      |   o   |o/        |____V||  .  ||(_'_)||( v )|        /   _   \\\n" + "\t      |     o |/                |____V||  |  || \\ / |        |  (8)  |\n" + "\t      '-------'                        |____V||  .  |        \\   ^   /\n" + "\t                                              |____V|         '-...-'\n\n" + "\t  __________________________________________________________________________\n" + "\t |                                                                          |\n" + "\t|     ::::::::      :::      ::::::::  ::::::::::: ::::    :::  ::::::::     |\n" + "\t|    :+:    :+:   :+: :+:   :+:    :+:     :+:     :+:+:   :+: :+:    :+:    |\n" + "\t|    +:+         +:+   +:+  +:+            +:+     :+:+:+  +:+ +:+    +:+    |\n" + "\t|    +#+        +#++:++#++: +#++:++#++     +#+     +#+ +:+ +#+ +#+    +:+    |\n" + "\t|    +#+        +#+     +#+        +#+     +#+     +#+  +#+#+# +#+    +#+    |\n" + "\t|    #+#    #+# #+#     #+# #+#    #+#     #+#     #+#   #+#+# #+#    #+#    |\n" + "\t|     ########  ###     ###  ########  ########### ###    ####  ########     |\n" + "\t|                                                                            |\n" + "\t |__________________________________________________________________________|\n";
    public static String tituloCorto = "\n\n\t  __________________________________________________________________________\n" + "\t |                                                                          |\n" + "\t|     ::::::::      :::      ::::::::  ::::::::::: ::::    :::  ::::::::     |\n" + "\t|    :+:    :+:   :+: :+:   :+:    :+:     :+:     :+:+:   :+: :+:    :+:    |\n" + "\t|    +:+         +:+   +:+  +:+            +:+     :+:+:+  +:+ +:+    +:+    |\n" + "\t|    +#+        +#++:++#++: +#++:++#++     +#+     +#+ +:+ +#+ +#+    +:+    |\n" + "\t|    +#+        +#+     +#+        +#+     +#+     +#+  +#+#+# +#+    +#+    |\n" + "\t|    #+#    #+# #+#     #+# #+#    #+#     #+#     #+#   #+#+# #+#    #+#    |\n" + "\t|     ########  ###     ###  ########  ########### ###    ####  ########     |\n" + "\t|                                                                            |\n" + "\t |__________________________________________________________________________|\n\n";

    private static String user = "";
    private int puntos = 0;
    private static boolean logueado = false;
    private Scanner sc = new Scanner(System.in);

    /**
     * Metodo principal. En este, se crea el sistema de ficheros, esta el menu
     * de inicio de sesion y se accede al menujuegos()
     *
     * @param args
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException, SQLException {

        // creamos una instancia de la clase sistema_ficheros
        Sistema_ficheros datos = new Sistema_ficheros();

        // bucle while que crea una instancia de la clase Login para iniciar sesion en nuestro programa
        while (true) {
            Login login = new Login();
        }
    }

    /**
     * metodo para borrar pantalla tanto en Windows como en Linux y MacOS
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public static void borrarPantalla() throws IOException, InterruptedException {
        try {
            // este string almacena el nombre del sistema operativo
            String os = System.getProperty("os.name").toLowerCase();

            // si el string contiene 'win', encontes el borrar pantalla sera una sentencia del cmd
            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } // si el string contiene 'nix', 'nux' o 'mac', entonces el metodo borrarPantalla usara sentencias para el terminal de linux y mac
            else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
                new ProcessBuilder("bash", "-c", "clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
        }
    }

    /**
     * Metodo para borrar pantala y mostrar el titulo del CASINO principal
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public static void pantallaDefault() throws IOException, InterruptedException {
        borrarPantalla();
        System.out.println(titulo);
    }

    public static int numeroUsuarios() {
        String url = "jdbc:sqlite:../db/casino.db";
        int numeroUsuarios = 0;

        try {
            Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();

            String sentencia = "SELECT COUNT(*) FROM usuarios;";
            ResultSet rSet = statement.executeQuery(sentencia);

            if (rSet.next()) {
                numeroUsuarios = rSet.getInt(1);
            }

            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return numeroUsuarios;
    }

    public static synchronized Connection crearConexion() throws InterruptedException, IOException {
        Connection connection = null;
        String url = "jdbc:sqlite:../db/casino.db";

        try {
            connection = DriverManager.getConnection(url);
        } catch (Exception e) {
            casino.CASINO.pantallaDefault();
            System.out.println("ERROR EN LA CONEXION A LA BASE DE DATOS");
            Thread.sleep(2500);
            System.exit(0);
        }

        return connection;
    }

    public static synchronized Statement crearStatement(Connection connection) throws InterruptedException, IOException {
        Statement statement = null;
        String url = "jdbc:sqlite:../db/casino.db";

        try {
            statement = connection.createStatement();
        } catch (Exception e) {
            casino.CASINO.pantallaDefault();
            System.out.println("ERROR EN LA CONEXION A LA BASE DE DATOS");
            Thread.sleep(2500);
            System.exit(0);
        }

        return statement;
    }

    // Getters y Setters
    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        CASINO.user = user;
    }

    public int getPuntos(String user) {

        return puntos;
    }

    public void setPuntos(int puntos) {
        // ACABAR ESTO
        this.puntos = puntos;
    }
}
