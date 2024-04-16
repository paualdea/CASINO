package casino;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import datos.Sistema_ficheros;
import datos.Login;
import juegos.*;

/**
 * CASINO - v5.0.0
 * _________________________________________________________________________________________________________________________________________
 *
 * Este proyecto incorpora un sistema de login para posteriormente acceder a un
 * menu en donde se pueden seleccionar varios juegos de casino (en clases). El
 * primero de estos es el juego de los dados, luego tenemos tambien el bingo
 * contra el ordenador, una ruleta y un black jack.
 * 
 * En cuanto a los usuarios, tenemos una clase (datos.Sistema_ficheros) que trabaja
 * con un fichero de texto para almacenar los usuarios del sistema y sus puntos.
 *
 * ¡Esperemos que disfrutes del juego!
 *
 * @author Pau Aldea Batista
 * @author Mohammad Tufail Imran
 */
public class CASINO {
    // Aqui definimos variables e instancias estaticas
    static Scanner sc = new Scanner(System.in);
    
    // Strings para la cabecera del CASINO
    public static String titulo = "\n\t                          _____\n" + "\t        .-------.        |A .  | _____\n" + "\t       /   o   /|        | /.\\ ||A ^  | _____ \n" + "\t      /_______/o|        |(_._)|| / \\ ||A _  | _____           \n" + "\t      | o     | |        |  |  || \\ / || ( ) ||A_ _ |         .-\"\"\"-.\n" + "\t      |   o   |o/        |____V||  .  ||(_'_)||( v )|        /   _   \\\n" + "\t      |     o |/                |____V||  |  || \\ / |        |  (8)  |\n" + "\t      '-------'                        |____V||  .  |        \\   ^   /\n" + "\t                                              |____V|         '-...-'\n\n" + "\t  __________________________________________________________________________\n" + "\t |                                                                          |\n" + "\t|     ::::::::      :::      ::::::::  ::::::::::: ::::    :::  ::::::::     |\n" + "\t|    :+:    :+:   :+: :+:   :+:    :+:     :+:     :+:+:   :+: :+:    :+:    |\n" + "\t|    +:+         +:+   +:+  +:+            +:+     :+:+:+  +:+ +:+    +:+    |\n" + "\t|    +#+        +#++:++#++: +#++:++#++     +#+     +#+ +:+ +#+ +#+    +:+    |\n" + "\t|    +#+        +#+     +#+        +#+     +#+     +#+  +#+#+# +#+    +#+    |\n" + "\t|    #+#    #+# #+#     #+# #+#    #+#     #+#     #+#   #+#+# #+#    #+#    |\n" + "\t|     ########  ###     ###  ########  ########### ###    ####  ########     |\n" + "\t|                                                                            |\n" + "\t |__________________________________________________________________________|\n";
    public static String tituloCorto = "\n\n\t  __________________________________________________________________________\n" + "\t |                                                                          |\n" + "\t|     ::::::::      :::      ::::::::  ::::::::::: ::::    :::  ::::::::     |\n" + "\t|    :+:    :+:   :+: :+:   :+:    :+:     :+:     :+:+:   :+: :+:    :+:    |\n" + "\t|    +:+         +:+   +:+  +:+            +:+     :+:+:+  +:+ +:+    +:+    |\n" + "\t|    +#+        +#++:++#++: +#++:++#++     +#+     +#+ +:+ +#+ +#+    +:+    |\n" + "\t|    +#+        +#+     +#+        +#+     +#+     +#+  +#+#+# +#+    +#+    |\n" + "\t|    #+#    #+# #+#     #+# #+#    #+#     #+#     #+#   #+#+# #+#    #+#    |\n" + "\t|     ########  ###     ###  ########  ########### ###    ####  ########     |\n" + "\t|                                                                            |\n" + "\t |__________________________________________________________________________|\n\n";
    
    // variable booleana que evalua si el sistema de ficheros es nuevo o ya existia
    public static boolean ficheroNuevo = false;
    
    // Array bidimensional para almacenar los usuarios del sistema de juego del casino
    public static String[][] usuariosList = new String[0][2];
    
    // ArrayList para almacenar todos los puntos de los usuarios creados en el fichero de juego
    public static ArrayList<Integer> puntosUsuario = new ArrayList<>();
    
    // Variables con datos de usuarios
    public static String user = "", passwd = "", passwd_aux = "";
    
    // Variable para almacenar puntos para usar en el CASINO (actualmente solo sirve como intercambio para el arrayList de puntosPendientes)
    static int puntos = 0;

    /**
     * Metodo principal. En este, se crea el sistema de ficheros, esta el menu de inicio de sesion y se accede al menujuegos()
     *
     * @param args
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {

        // creamos una instancia de la clase sistema_ficheros
        Sistema_ficheros datos = new Sistema_ficheros();

        // si el sistema de ficheros es nuevo, recargamos la instancia de la clase Sistema_ficheros
        if (ficheroNuevo) {
            // borramos los datos de la instancia
            datos = null;
            // recargamos la instancia
            datos = new Sistema_ficheros();
        }
        
        // creacion variables para el registro y login
        boolean iniciado = false, ini_juego = false, registrado = false;
        int opcion_r = 0, opcion_ij = 0, numero = 0;
        
        // Array para almacenar los puntos a lo largo de todo el programa
        ArrayList<Integer> puntosPendientes = new ArrayList<>();

        // mientras que no se inicie de sesion con un usuario no se va a salir de este bucle while
        while (!ini_juego) {

            // si el arraylist puntosPendientes esta vacio, le asignaremos en la primera posicion el valor de la variable puntos
            if (puntosPendientes.size() > 0) {
                puntos = puntosPendientes.get(0);
            }

            // metodo para mostrar la caberecera prederminada del CASINO
            pantallaDefault();

            System.out.println("\n\t\t\t\t     1. INICIAR SESION");
            System.out.println("\n\t\t\t\t     2. REGISTRARSE");
            System.out.println("\n\t\t\t\t     3. RESTABLECER CASINO");
            System.out.println("\n\t\t\t\t     4. SALIR");
            System.out.print("\n\n\tQUE QUIERE HACER: ");

            // estructura de control de errores para evitar que se cierre el programa si se introduce un dato erroneo en el scanner
            try {
                opcion_r = sc.nextInt();
            } catch (Exception e) {
                pantallaDefault();
                sc.next();
                System.out.println("\n\t\t\t\t  INTRODUCE UNA OPCION DISPONIBLE");
                Thread.sleep(1750);
            }

            // Creamos una instancia de la clase Login
            Login login = new Login();

            // switch para elegir entre inicio de sesion, registro o salir del programa
            switch (opcion_r) {
                // INICIAR SESION
                case 1:
                    // se repetira el inicio de sesion hasta tres veces por si te equivocas de usuario o password
                    for (int i = 0; i < 3; i++) {
                        // metodo booleano para pedir usuario y password
                        // si sale true se pasa al metodo menujuegos()
                        // si sale false, se vuelve al menu de inicio
                        if (!login.usuarios("iniciarSesion", puntosPendientes)) {
                            System.out.print("\n\t\t\t\t   DATOS INCORRECTOS, VUELVE A INTENTARLO");
                            Thread.sleep(2000);
                        } else {
                            // variable booleana para salir del while y poder acceder al menu de juegos
                            ini_juego = true;
                            puntos = puntosPendientes.get(0);
                            break;
                        }
                    }
                    break;
                
                // REGISTRARSE
                case 2:
                    // se repetira el registro hasta tres veces por si te equivocas de usuario o password
                    for (int i = 0; i < 3; i++) {
                        /* si los datos del nuevo usuario no se repiten y son correctos, se establece la variable booleana registrado en true
                        lo que hace que al iniciar sesion con el usuario este sea identificado */
                        if (login.usuarios("registro", puntosPendientes)) {
                            registrado = true;
                            break;
                        }
                    }
                    
                    // si la variable booleana registrado es false, mandamos un mensaje de error por pantalla
                    if (!registrado) {
                        System.out.print("\n\t\t\t\t     DATOS INCORRECTOS, VUELVE A INTENTARLO");
                    }

                    // Delay para poder ver correctamente lo que sale por pantalla
                    Thread.sleep(2000);
                    break;
                
                // RESTABLECER CASINO
                case 3:
                    // File que hace referencia al fichero de usuarios que vamos usando a lo largo de todo el programa (usuarios.txt)
                    File usuarios = new File("./data", "usuarios.txt");

                    // sout por pantalla para preguntar si queremos restablecer los datos predeterminados
                    pantallaDefault();
                    System.out.print("\n\tESTAS SEGURO DE QUE QUIERES RESTABLECER EL CASINO?\n\tESTO BORRARA TODOS LOS USUARIOS Y PUNTOS\n\n\tBORRAR? (s/n): ");
                    String opcion = sc.next();

                    // si la entrada de la variable String opcion es s, S, y o Y, se restablece el casino
                    if (opcion.equals("s") || opcion.equals("S") || opcion.equals("y") || opcion.equals("Y")) {
                        String punto = ".";
                        // bucle for para animar el movimiento de los tres puntos de progreso
                        for (int i = 0; i < 3; i++) {
                            pantallaDefault();
                            System.out.println("\n\n\tRESTABLECIENDO CASINO" + punto);
                            punto += ".";
                            Thread.sleep(1000);
                        }

                        // creamos un printwriter y lo cerramos para dejar el fichero vacio
                        PrintWriter writer = new PrintWriter(usuarios, "UTF-8");
                        writer.close();

                        System.out.println("");
                        // se para la ejecucion del programa
                        System.exit(0);
                    }
                    break;
                
                // SALIR
                case 4:
                    /* 
                        Se llama al metodo actualizarFicheros para actualizar todos los valores en el fichero antes de finalizar la ejecuciï¿½n del programa.
                        Se manda como argumento el arrayList que usamos para almacenar los puntos actuales del usuario.
                     */
                    datos.actualizarFicheros(puntosPendientes, usuariosList);
                    break;
            }
        }

        // acceso al metodo menujuegos() para seleccionar el juego al que queremos jugar
        menujuegos(puntos, datos);
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
    public static void menujuegos(int puntos, Sistema_ficheros datos) throws IOException, InterruptedException {
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
                
                // se llama a la funcion actualizarFicheros para guardar que el usuario se ha quedado sin puntos
                datos.actualizarFicheros(puntosPendientes, usuariosList);
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
                    // llamamos al metodo actualizarFicheros() para asi poder guardar los cambios en el fichero
                    datos.actualizarFicheros(puntosPendientes, usuariosList);
                    
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
    
    public static void setFicheroNuevo(boolean ficheroNuevo) {
        CASINO.ficheroNuevo = ficheroNuevo;
    }

    public static void setUsuariosList(String[][] usuariosList) {
        CASINO.usuariosList = usuariosList;
    }

    public static void setPuntosUsuario(ArrayList<Integer> puntosUsuario) {
        CASINO.puntosUsuario = puntosUsuario;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        CASINO.user = user;
    }
}
