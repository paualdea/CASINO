package casino;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import datos.Sistema_ficheros;
import datos.Login;
import java.sql.SQLException;
import juegos.*;

/**
 * .:CASINO:. VERSION: 5.0.0
 * _________________________________________________________________________________________________________________________________________
 *
 * Este proyecto incorpora un sistema de login para posteriormente acceder a un
 * menu en donde se pueden seleccionar varios juegos de casino (en clases). El
 * primero de estos es el juego de los dados, luego tenemos tambien el bingo
 * contra el ordenador, una ruleta y un black jack.
 * 
 * En esta version 5 del CASINO, hemos implementado un nuevo sistema de ficheros que usa una base de datos.
 * Hemos cambiado el sistema de ficheros de texto por SQLite.
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
    private boolean logueado = false;
    private Scanner sc = new Scanner(System.in);

    /**
     * Metodo principal. En este, se crea el sistema de ficheros, esta el menu de inicio de sesion y se accede al menujuegos()
     *
     * @param args
     * @throws IOException
     * @throws InterruptedException
     */
    public void main(String[] args) throws IOException, InterruptedException, SQLException {

        // creamos una instancia de la clase sistema_ficheros
        Sistema_ficheros datos = new Sistema_ficheros();
        
        // bucle while que crea una instancia de la clase Login para iniciar sesion en nuestro programa
        while (!logueado){
            Login login = new Login();
        }
        
        // acceso al metodo menujuegos() para seleccionar el juego al que queremos jugar
        MenuJuegos menujuegos = new MenuJuegos();
    }

    /**
     * Metodo con un menu para seleccionar el juego al que queramos jugar Este,
     * recibe la variable de puntos para poder administrarlo en cada uno de los
     * juegos posteriormente
     *
     * @param puntos
     * @throws IOException
     * @throws InterruptedException
     */
    public void menujuegos(int puntos, Sistema_ficheros datos) throws IOException, InterruptedException {
        /*
            Este ArrayList es muy importante. Se trata de un arraylist que vamos usando en cada uno de los juegos que hacemos y para no tener
            problemas con la actualizacion de los puntos en el metodo menujuegos().
            Se usa un arraylist porque se puede actualizar sin usar return ni declararlo como estatico en cualquier metodo. Por ello, se podra ver varias 
            veces el uso de sentencias como puntos.get(0) o puntos.set(0, puntos.get(0) - variable) porque jugaremos unicamente con la primera posicion
            de este array para aprovechar la propiedad que se ha mencionado anteriormente.
        
            Esta propiedad es vigente incluso entre clases.
         */
        ArrayList<Integer> puntosPendientes = new ArrayList<>();

        // Añadimos en primera posicion el valor de puntos que hemos recibido del menu de inicio
        puntosPendientes.add(puntos);
        int opcionMenu = 0;

        // Mientras que la opcion del meno de juegos no sea 5 (salir del programa), se repite infinitamente el codigo contenido dentro
        while (opcionMenu != 5) {
            pantallaDefault();
            
            // si nos quedamos con 0 puntos, entonces...
            if (puntosPendientes.get(0) == 0) {
                System.out.println("\n\t\t\t\t   TE HAS QUEDADO SIN PUNTOS");
                Thread.sleep(3000);
                
                
            }

            System.out.println("\n\t PUNTOS: " + puntosPendientes.get(0));
            System.out.println("\n\t\t\t\t           1. DADOS");
            System.out.println("\n\t\t\t\t           2. BINGO");
            System.out.println("\n\t\t\t\t           3. RULETA");
            System.out.println("\n\t\t\t\t           4. BLACKJACK");
            System.out.println("\n\t\t\t\t           5. SALIR");
            System.out.print("\n\n\tQUE QUIERE HACER: ");

            // Estructura de control de errores para asegurar que el programa continua con la ejecucion
            try {
                opcionMenu = sc.nextInt();
            } catch (Exception e) {
                sc.next();
                pantallaDefault();
                System.out.println("\n\t\t\t\t  INTRODUCE UNA OPCION DISPONIBLE");
                Thread.sleep(1750);
            }

            // switch para desviar a los metodos de los juegos
            switch (opcionMenu) {
                // DADOS
                case 1:
                    // creamos una instancia de la clase Dados para poder jugar a los dados
                    // mandamos el arraylist de los puntos para poder llevar un conteo jugando al juego
                    Dados dados = new Dados(puntosPendientes);
                    
                    // llamamos a la funcion dados() que ejecuta el juego
                    dados.dados();
                    break;

                // BINGO
                case 2:
                    // creamos una instancia de la clase Bingo (mandamos los puntos a la instancia)
                    Bingo bingo = new Bingo(puntosPendientes);
                    
                    // llamamos a la clase que ejecuta el juego del bingo
                    bingo.bingo();
                    break;

                // RULETA
                case 3:
                    // creamos una instancia de la clase Ruleta
                    // mandamos el arraylist de puntosPendientes como argumento para el constructor de la clase
                    Ruleta ruleta = new Ruleta(puntosPendientes);
                    
                    // llamamos a la funcion que ejecuta el juego de la ruleta
                    ruleta.ruleta();
                    break;
                
                // BLACKJACK
                case 4:
                    // creamos una instancia de la clase Blackjack mandando el array de puntos
                    Blackjack bj = new Blackjack(puntosPendientes);
                    
                    // llamamos al metodo de la clase que ejecuta todo el juego
                    bj.apuestaBlackjack();
                    break;
                
                // SALIR
                case 5: 
                    // enviamos un mensaje final por pantalla
                    pantallaDefault();
                    System.out.println("\n\t\t\t\t   HASTA PRONTO, " + user + "\n");
                    Thread.sleep(3000);
                    
                    // mandamos un codigo de salida del programa
                    System.exit(0);
                    break;
            }
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
            } 
            // si el string contiene 'nix', 'nux' o 'mac', entonces el metodo borrarPantalla usara sentencias para el terminal de linux y mac
            else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
                new ProcessBuilder("bash", "-c", "clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {}
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

    // Getters y Setters
    
    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        CASINO.user = user;
    }

    public int getPuntos() {
        // ACABAR ESTO
        return puntos;
    }

    public void setPuntos(int puntos) {
        // ACABAR ESTO
        this.puntos = puntos;
    }

    public void setLogueado(boolean logueado) {
        this.logueado = logueado;
    }
}
