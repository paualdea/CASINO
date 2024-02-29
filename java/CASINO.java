
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
 * .:CASINO:. VERSION: 3.2.0
 * _________________________________________________________________________________________________________________________________________
 *
 * Este proyecto incorpora un sistema de login para posteriormente acceder a un
 * menu en donde se pueden seleccionar varios juegos de casino. El primero de
 * estos es el juego de los dados, luego tenemos tambien el bingo contra el
 * ordenador, una ruleta y un black jack.
 *
 * Esperemos que disfrutes del juego!
 *
 * @author Pau Aldea Batista
 * @authos Mohammad Tufail Imran
 */
public class CASINO {

    // Aqui definimos varias variables y objetos estaticos
    // Escaner que vamos a ir usando a lo largo del programa
    static Scanner sc = new Scanner(System.in);
    // String para almacenar el titulo de CASINO que iremos mostrando
    static String titulo = "\n\t                          _____\n" + "\t        .-------.        |A .  | _____\n" + "\t       /   o   /|        | /.\\ ||A ^  | _____ \n" + "\t      /_______/o|        |(_._)|| / \\ ||A _  | _____           \n" + "\t      | o     | |        |  |  || \\ / || ( ) ||A_ _ |         .-\"\"\"-.\n" + "\t      |   o   |o/        |____V||  .  ||(_'_)||( v )|        /   _   \\\n" + "\t      |     o |/                |____V||  |  || \\ / |        |  (8)  |\n" + "\t      '-------'                        |____V||  .  |        \\   ^   /\n" + "\t                                              |____V|         '-...-'\n\n" + "\t  __________________________________________________________________________\n" + "\t |                                                                          |\n" + "\t|     ::::::::      :::      ::::::::  ::::::::::: ::::    :::  ::::::::     |\n" + "\t|    :+:    :+:   :+: :+:   :+:    :+:     :+:     :+:+:   :+: :+:    :+:    |\n" + "\t|    +:+         +:+   +:+  +:+            +:+     :+:+:+  +:+ +:+    +:+    |\n" + "\t|    +#+        +#++:++#++: +#++:++#++     +#+     +#+ +:+ +#+ +#+    +:+    |\n" + "\t|    +#+        +#+     +#+        +#+     +#+     +#+  +#+#+# +#+    +#+    |\n" + "\t|    #+#    #+# #+#     #+# #+#    #+#     #+#     #+#   #+#+# #+#    #+#    |\n" + "\t|     ########  ###     ###  ########  ########### ###    ####  ########     |\n" + "\t|                                                                            |\n" + "\t |__________________________________________________________________________|\n";
    static String tituloCorto = "\n\n\t  __________________________________________________________________________\n" + "\t |                                                                          |\n" + "\t|     ::::::::      :::      ::::::::  ::::::::::: ::::    :::  ::::::::     |\n" + "\t|    :+:    :+:   :+: :+:   :+:    :+:     :+:     :+:+:   :+: :+:    :+:    |\n" + "\t|    +:+         +:+   +:+  +:+            +:+     :+:+:+  +:+ +:+    +:+    |\n" + "\t|    +#+        +#++:++#++: +#++:++#++     +#+     +#+ +:+ +#+ +#+    +:+    |\n" + "\t|    +#+        +#+     +#+        +#+     +#+     +#+  +#+#+# +#+    +#+    |\n" + "\t|    #+#    #+# #+#     #+# #+#    #+#     #+#     #+#   #+#+# #+#    #+#    |\n" + "\t|     ########  ###     ###  ########  ########### ###    ####  ########     |\n" + "\t|                                                                            |\n" + "\t |__________________________________________________________________________|\n\n";
    static String user = "", passwd = "", passwd_aux = "";
    // Array bidimensional para almacenar los usuarios del sistema de juego del casino
    static String[][] usuariosList = new String[0][2];
    // ArrayList para almacenar todos los puntos de los usuarios creados en el fichero de juego
    static ArrayList<Integer> puntosUsuario = new ArrayList<>();
    // ArrayLists para el bingo y sus respectivos metodos
    static ArrayList<Integer> numerosBorrar = new ArrayList<>(), indexCol = new ArrayList<>(), bombo = new ArrayList<>(), numerosBingo = new ArrayList<>(), numerosBingoCpu = new ArrayList<>(), numerosBingoUsados = new ArrayList<>();
    // variable booleana para detectar si el fichero que usamos para los usuarios y puntos es nuevo o no
    static boolean ficheroNuevo = false, finBingo = false;

    /**
     * Metodo principal, aqui esta el sistema usuarios, inicio de sesion,
     * registro, etc. Una vez el usuario inicia sesion, se pasa al metodo
     * menujuegos()
     *
     * @param args
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        // llamamos a la funcion que crea todo el sistema de ficheros y configura su uso en el programa
        sistemaArchivos();

        // si el fichero se crea de nuevo ejecutamos de nuevo la funcion  de creacion del sistema de ficheros para solucionar errores
        if (ficheroNuevo) {
            sistemaArchivos();
        }

        // llamamos a la funcion sistemaPantalla para que el usuario adapte la pantalla y pueda ver todo los elementos del programa correctamente
        sistemaPantalla();

        // creacion variables para el registro y login
        int puntos = 0;
        boolean iniciado = false, ini_juego = false, registrado = false;
        int opcion_r = 0, opcion_ij = 0, numero = 0;
        ArrayList<Integer> puntosPendientes = new ArrayList<>();

        // mientras que no se inicie de sesion con un usuario (administrador o normal) no se va a salir de este bucle while
        while (!ini_juego) {

            // si el arraylist puntosPendientes esta vacio, le asignaremos en la primera posicion el valor de puntos
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

            // switch para elegir entre inicio de sesion, registro o salir del programa
            switch (opcion_r) {
                // INICIAR SESION
                case 1:
                    // se repetira el inicio de sesion hasta tres veces por si te equivocas de usuario o contraseña
                    for (int i = 0; i < 3; i++) {
                        // metodo booleano para pedir usuario y password si sale true se pasa el menu de juegos, si sale false, se vuelve al menu de inicio
                        if (!usuarios("iniciarSesion", puntosPendientes)) {
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
                        if (usuarios("registro", puntosPendientes)) {
                            registrado = true;
                            break;
                        }
                    }

                    if (!registrado) {
                        System.out.print("\n\t\t\t\t     DATOS INCORRECTOS, VUELVE A INTENTARLO");
                    }

                    // Delay para poder ver correctamente lo que sale por pantalla
                    Thread.sleep(2000);
                    break;
                // RESTABLECER CASINO
                case 3:
                    // creamos un objeto con la clase file que haga referencia al fichero de usuarios que vamos usando a lo largo de todo el programa
                    File usuarios = new File("./casino_files", "usuarios.txt");
                    String opcion;

                    pantallaDefault();
                    System.out.print("\n\tESTAS SEGURO DE QUE QUIERES RESTABLECER EL CASINO?\n\tESTO BORRARA TODOS LOS USUARIOS Y PUNTOS\n\n\tBORRAR? (s/n): ");
                    opcion = sc.next();

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
                        Se llama al metodo actualizarFicheros para actualizar todos los valores en el fichero antes de finalizar la ejecuci�n del programa.
                        Se manda como argumento el arrayList que usamos para almacenar los puntos actuales del usuario.
                     */
                    actualizarFicheros(puntosPendientes);
                    break;
            }
        }

        // acceso al metodo menujuegos() para seleccionar el juego al que queremos jugar
        menujuegos(puntos);

    }

    /**
     * Metodo para administrar usuarios (registro, inicio de sesion, etc.)
     *
     * @param accion
     * @param puntos
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static boolean usuarios(String accion, ArrayList<Integer> puntos) throws IOException, InterruptedException {
        switch (accion) {
            // si recibimos "iniciarSesion" del metodo main se hara lo siguiente
            case "iniciarSesion":
                pantallaDefault();
                System.out.print("\n\n\t\t\t\t   USUARIO: ");
                user = sc.next();
                System.out.print("\n\t\t\t\t   CONTRASENA: ");
                passwd = sc.next();
                int nuevosPuntos = 0;

                // Se recorre todo el array de usuarios para ver si algun registro coincide con el usuario y contraseña que hemos introducido
                for (int i = 0; i < usuariosList.length; i++) {
                    if (user.equals(usuariosList[i][0]) && passwd.equals(usuariosList[i][1])) {
                        pantallaDefault();
                        System.out.println("\n\t\t\t\tHAS INICIADO SESION COMO " + user);

                        // se vacia al arraylist para llevar lo puntos actualizados
                        puntos.clear();

                        // si el usuario no tiene puntos se le da la posibilidad de anadir mas (max 3000)
                        if (puntosUsuario.get(i) == 0) {
                            System.out.print("\n\tTE HAS QUEDADO SIN PUNTOS, QUIERES AGREGAR MAS (S/N): ");
                            String opcion = sc.next();

                            if (opcion.equals("s") || opcion.equals("S")) {
                                while (true) {
                                    pantallaDefault();
                                    System.out.print("\n\tCUANTOS PUNTOS QUIERES AGREGAR (MAX. 3000): ");

                                    // estructura de control de error para asegurarnos de que la entrada es un Int
                                    try {
                                        nuevosPuntos = sc.nextInt();

                                        if (nuevosPuntos > 0 && nuevosPuntos <= 3000) {
                                            // si se cumplen todas las condiciones requeridas, se le anade la puntuacion que haya indicado el usuario a su cuenta
                                            puntos.add(nuevosPuntos);
                                            break;
                                        } else {
                                            pantallaDefault();
                                            System.out.println("\n\tINTRODUCE UN VALOR CORRECTO");
                                            Thread.sleep(1000);
                                        }

                                    } catch (Exception e) {
                                        sc.next();
                                        pantallaDefault();
                                        System.out.println("\n\tINTRODUCE UN VALOR CORRECTO");
                                        Thread.sleep(1000);
                                    }

                                }
                            } else {
                                // si no anade mas, se le asignan 0, lo que cierra el programa
                                puntos.add(puntosUsuario.get(i));
                            }
                        } else {
                            // si no anade mas, se le asignan 0, lo que cierra el programa
                            puntos.add(puntosUsuario.get(i));
                        }

                        Thread.sleep(1250);
                        return true;
                    }
                }

                // si ningun usuario coincide, se devuelte un false
                return false;

            case "registro":
                int ingreso = 0;
                boolean usuarioNuevo = false,
                 puntosEstablecidos = false;

                pantallaDefault();
                // se piden usuario y password
                System.out.print("\n\n\t\t\t\tNOMBRE USUARIO: ");
                user = sc.next();
                System.out.print("\n\t\t\t\tCONTRASENA: ");
                passwd = sc.next();
                System.out.print("\n\t\t\t\tCONFIRMAR CONTRASENA: ");
                passwd_aux = sc.next();

                // el programa se mantiene en este bucle while mientras el usuario no sea nuevo, si el usuario ya esta registrado, te saca del bucle y devuelve un false al metodo main
                while (!usuarioNuevo) {
                    for (int i = 0; i < usuariosList.length; i++) {
                        if (usuariosList[i][0].equals(user)) {
                            pantallaDefault();
                            System.out.println("\n\n\t\t\t\t   USUARIO YA EXISTENTE");
                            Thread.sleep(1500);
                            return false;
                        } else {
                            usuarioNuevo = true;
                        }
                    }
                }

                // si la password y la confirmacion de la password son iguales, se desvia el if para agregar dinero a la cuenta
                if (passwd.equals(passwd_aux)) {

                    /* 
                        bucle while para establecer puntos de usuario nuevo, si la cantidad pasa de 3000, se vuelve a pedir una cantidad hasta que esta
                        sea inferior de 3000 
                     */
                    while (!puntosEstablecidos) {
                        boolean entradaValida = false;

                        while (!entradaValida) {
                            // estructura de control para asegurar que no se rompe el codigo por una mala entrada de datos
                            try {
                                borrarPantalla();
                                pantallaDefault();
                                System.out.print("\n\t\t\t   INGRESO DINERO (MAXIMO 3000): ");
                                ingreso = sc.nextInt();
                                entradaValida = true;
                            } catch (Exception e) {
                                sc.next();
                                borrarPantalla();
                                System.out.println(titulo + "\n\n\tINTRODUCE UN VALOR VALIDO");
                                Thread.sleep(850);
                            }
                        }

                        if (ingreso <= 3000) {
                            puntos.clear();
                            puntos.add(ingreso);
                            puntosEstablecidos = true;
                            break;
                        } else {
                            pantallaDefault();
                            System.out.println("\n\t\t\t     DINERO POR ENCIMA DE LO PERMITIDO");
                            Thread.sleep(2000);
                        }
                    }

                    // se redimensiona el array de usuarios para agregar un registro nuevo
                    usuariosList = Arrays.copyOf(usuariosList, usuariosList.length + 1);
                    usuariosList[usuariosList.length - 1] = new String[2];

                    usuariosList[usuariosList.length - 1][0] = user;
                    usuariosList[usuariosList.length - 1][1] = passwd;
                    puntosUsuario.add(ingreso);

                    // se vacia la variable user para evitar errores posteriores
                    user = "";

                    return true;
                }
                break;
        }

        return false;
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
    public static void menujuegos(int puntos) throws IOException, InterruptedException {
        /*
            Este ArrayList es muy importante. Se trata de un arraylist que vamos usando en cada uno de los juegos que hacemos y para no tener
            problemas con la actualizacion de los puntos en el metodo menujuegos().
            Se usa un arraylist porque se puede actualizar sin usar return ni declararlo como estático en cualquier metodo. Por ello, se podra ver varias 
            veces el uso de sentencias como puntos.get(0) o puntos.set(0, puntos.get(0) - variable) porque jugaremos unicamente con la primera posicion
            de este array para aprovechar la propiedad que se ha mencionado anteriormente.
         */
        ArrayList<Integer> puntosPendientes = new ArrayList<>();

        puntosPendientes.add(puntos);
        int opcionMenu = 0;

        // Mientras que la opcion del meno de juegos no sea 5 (salir del programa), se repite infinitamente el codigo contenido dentro
        while (opcionMenu != 6) {

            pantallaDefault();

            if (puntos == 0) {

                System.out.println("\n\t\t\t\t   TE HAS QUEDADO SIN PUNTOS");
                Thread.sleep(3000);
                // se llama a la funcion actualizarFicheros para guardar que el usuario se ha quedado sin puntos
                actualizarFicheros(puntosPendientes);
            }

            System.out.println("\n\t PUNTOS: " + puntos);
            System.out.println("\n\t\t\t\t           1. DADOS");
            System.out.println("\n\t\t\t\t           2. BINGO");
            System.out.println("\n\t\t\t\t           3. RULETA");
            System.out.println("\n\t\t\t\t           4. BLACKJACK");
            System.out.println("\n\t\t\t\t           5. OTROS");
            System.out.println("\n\t\t\t\t           6. SALIR");
            System.out.print("\n\n\tQUE QUIERE HACER: ");

            try {
                opcionMenu = sc.nextInt();
            } catch (Exception e) {
                sc.next();
                pantallaDefault();
                System.out.println("\n\t\t\t\t  INTRODUCE UNA OPCION DISPONIBLE");
                Thread.sleep(1750);
            }

            // switch para desviar a los métodos de los juegos
            switch (opcionMenu) {
                // DADOS
                case 1:
                    // primero establecemos la posicion 0 del arraylist con el valor de puntos y luego llamamos a la función de dados() adjuntando dicho arraylist
                    puntosPendientes.set(0, puntos);
                    dados(puntosPendientes);
                    // una vez cambia el valor de la posición 0 del arraylist, hacemos que la variable puntos valga lo mismo
                    puntos = puntosPendientes.get(0);
                    break;
                // BINGO
                case 2:
                    puntosPendientes.set(0, puntos);
                    bingo(puntosPendientes);
                    puntos = puntosPendientes.get(0);
                    break;
                // RULETA
                case 3:
                    puntosPendientes.set(0, puntos);
                    ruleta(puntosPendientes);
                    puntos = puntosPendientes.get(0);
                    break;
                // BLACKJACK
                case 4:
                    puntosPendientes.set(0, puntos);
                    apuestaBlackjack(puntosPendientes);
                    puntos = puntosPendientes.get(0);
                    break;
                // OTROS
                case 5:
                    String opcion1 = "";
                    File carpeta = new File("./");
                    // hacemos un while infinito
                    while (true) {
                        // mostramos un menu que permite impirmir todo el contenido de la carpeta de ficheros que usa este programa
                        pantallaDefault();
                        System.out.println("\n\t\t           1. VER FICHEROS CARPETA ./CASINO_FILES");
                        System.out.println("\n\t\t           2. ATRAS");
                        System.out.print("\n\n\tQUE QUIERE HACER: ");
                        opcion1 = sc.next();

                        // si el scanner recibe "1"...
                        if (opcion1.equals("1")) {
                            String tipo = "";
                            // Creamos un array de File que contenga todos los ficheros que contenga el directorio ./casino_files
                            File[] arrayFicheros = carpeta.listFiles();

                            pantallaDefault();
                            // bucle for que se repita tantas veces como ficheros haya en el directorio
                            for (int i = 0; i < arrayFicheros.length; i++) {
                                System.out.println("\n\t\t           " + (i + 1) + ". " + arrayFicheros[i]);

                                // determinamos el tipo de contenido que hay dentro del directorio
                                if (arrayFicheros[i].isFile()) {
                                    tipo = "FICHERO";
                                } else {
                                    tipo = "DIRECTORIO";
                                }

                                System.out.println("\n\t\t           ES UN " + tipo + " QUE PESA " + arrayFicheros[i].length() + " BYTES\n");
                            }
                            Thread.sleep(4250);
                            // en caso que no sea "1" si es "2"...
                        } else if (opcion1.equals("2")) {
                            break;
                            // si no...
                        } else {
                            pantallaDefault();
                            System.out.println("\n\t\t\t           INTRODUCE UN VALOR CORRECTO");
                            Thread.sleep(2000);
                        }
                    }
                    break;
                // SALIR
                case 6:
                    actualizarFicheros(puntosPendientes);
                    pantallaDefault();
                    System.out.println("\n\t\t\t\t   HASTA PRONTO, " + user + "\n");
                    Thread.sleep(3000);
                    System.exit(0);
                    break;
            }

        }
    }

    /**
     * metodo principal del juego de los dados
     *
     * @param puntos
     * @throws IOException
     * @throws InterruptedException
     */
    public static void dados(ArrayList<Integer> puntos) throws IOException, InterruptedException {
        boolean ganar = false;
        int n = 0, apuesta = 0;

        // array con los dibujos de todos los dados del 1 al 6
        String[] caraDado = {
            "\t\t\t\t\t _____________\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|      O      |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|_____________|",
            "\t\t\t\t\t _____________\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|  O          |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|          O  |\n" + "\t\t\t\t\t|_____________|",
            "\t\t\t\t\t _____________\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|  O          |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|      O      |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|          O  |\n" + "\t\t\t\t\t|_____________|",
            "\t\t\t\t\t _____________\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|  O       O  |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|  O       O  |\n" + "\t\t\t\t\t|_____________|",
            "\t\t\t\t\t _____________\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|  O       O  |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|      O      |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|  O       O  |\n" + "\t\t\t\t\t|_____________|",
            "\t\t\t\t\t _____________\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|  O       O  |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|  O       O  |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|  O       O  |\n" + "\t\t\t\t\t|_____________|",};

        // mientras que la variable booleana sea false, todo el código se repite infinitamente
        while (!ganar) {
            // mientras que el numero introducido no sea entre 2 y 12 se repite todo
            do {
                borrarPantalla();

                // si te quedas sin puntos sale del while
                if (puntos.get(0) == 0) {
                    ganar = true;
                }

                // ponemos el valor de n a cero por si volvemos a jugar que no nos de errores de lectura
                n = 0;

                System.out.println(titulo);
                System.out.print(caraDado[4]);
                System.out.println("\n\n\t\t\t\t\t   .:DADOS:.");
                System.out.println("\n\tPuntos: " + puntos.get(0));
                System.out.print("\n\tEscoge un numero (2 - 12) (14 para salir): ");

                try {
                    n = sc.nextInt();
                } catch (Exception e) {
                    sc.next();
                    pantallaDefault();
                    System.out.println("\n\tESCRIBE UN VALOR CORRECTO");
                    Thread.sleep(1750);
                }
                if (n == 14) {
                    pantallaDefault();

                    // animacion de salida del juego
                    String punto = ".";
                    for (int i = 0; i < 3; i++) {
                        pantallaDefault();
                        System.out.println("\n\n\t\t\t\t\tSALIENDO" + punto);
                        punto += ".";
                        Thread.sleep(450);
                    }

                    ganar = true;
                    break;

                }

            } while (!(n >= 2 && n <= 12));

            if (ganar == true) {
                break;
            }

            System.out.println("\n\t_______________________________________________");

            // bucle while() para obligar al usuario a hacer una apuesta inferior o igual al saldo total disponible
            do {
                pantallaDefault();
                System.out.println("\n\tPuntos: " + puntos.get(0));
                System.out.print("\n\tApuesta: ");
                apuesta = sc.nextInt();

            } while (!(apuesta <= puntos.get(0) && apuesta > 0));

            puntos.set(0, puntos.get(0) - apuesta);
            // se llama al metodo para obtener una puntuación en relación a la apuesta realizada
            int puntosAdicionales = tirarDados(caraDado, n, apuesta);

            puntos.set(0, puntos.get(0) + puntosAdicionales);

            apuesta = 0;

            if (puntos.get(0) == 0) {
                ganar = true;
            }
        }
    }

    /**
     * Este metodo sirve para generar la tirada de los dados que da un valor
     * resultante de entre 2 y 12 Recibe el array caraDado para mostrar el valor
     * de los dados gráficamente, la cantidad apostada
     *
     * @param caraDado
     * @param n
     * @param apuesta
     * @return
     * @throws InterruptedException
     */
    public static int tirarDados(String[] caraDado, int n, int apuesta) throws InterruptedException {
        Random rd = new Random();
        int dado1 = rd.nextInt(6) + 1;
        int dado2 = rd.nextInt(6) + 1;
        int resultado = dado1 + dado2;

        System.out.println("\n\t     DADO 1\n" + caraDado[dado1 - 1] + "\n\n\t     DADO 2\n" + caraDado[dado2 - 1]);
        Thread.sleep(3500);

        // ifs para comprobar el resultado de la tirada y su respectiva recompensa
        if (resultado == n) {
            if (n == 2 || n == 12) {
                apuesta *= 2.5;
            } else if (n == 3 || n == 11) {
                apuesta *= 2.3;
            } else if (n == 4 || n == 10) {
                apuesta *= 2.2;
            } else if (n == 5 || n == 9) {
                apuesta *= 2.1;
            } else if (n == 6 || n == 8) {
                apuesta *= 2.0;
            } else {
                apuesta *= 1.9;
            }
        } else {
            apuesta *= 0;
        }

        // devuelve el valor total de la apuesta tras la tirada al metodo principal de los dados
        return apuesta;
    }

    /**
     * metodo principal para el juego del bingo. Sólo recibe cómo atributo el
     * arraylist de puntos
     *
     * @param puntos
     * @throws IOException
     * @throws InterruptedException
     */
    public static void bingo(ArrayList<Integer> puntos) throws IOException, InterruptedException {
        // vaciamos todos los arraylist estaticos para que no den conflicto si ejecutamos el juego del bingo más de una vez
        numerosBorrar.clear();
        indexCol.clear();
        bombo.clear();
        numerosBingo.clear();
        numerosBingoCpu.clear();
        numerosBingoUsados.clear();

        Random rd = new Random();
        final int CASILLAS = 15;
        // creamos y definimos dos arrays bidimensionales que corresponden con el carton del usuario y del ordenador
        int[][] bingo = new int[9][3];
        int[][] bingocpu = new int[9][3];

        int apuesta = 0;
        String jug_s;

        boolean puntos_c, salir = false;

        // bucle while para repetir el juego hasta que perdamos o ganemos
        while (!salir) {

            if (finBingo) {
                finBingo = false;
                break;
            }

            pantallaDefault();
            System.out.println("\n\t\t\t\t\t.:BINGO:.");
            System.out.println("\t\t\t\t\t_________");

            System.out.print("\n\tQuieres jugar un carton? (s/n) ");
            jug_s = sc.next();

            if (!jug_s.equals("s")) {
                salir = true;
                break;
            }

            puntos_c = false;

            // bucle while para obligar al usuario a hacer una apuesta por debajo o igual de su saldo máximo disponible
            while (!puntos_c) {
                pantallaDefault();
                System.out.println("\n\tPuntos: " + puntos.get(0));
                System.out.println("\n\t\t\t\t\t.:APUESTA PUNTOS:.");
                System.out.println("\t\t\t\t\t__________________");

                System.out.print("\n\tCuantos puntos quieres apostar: ");

                // estructura de control de errores para evitar que el programa pare por un error en la entrada por scanner
                try {
                    apuesta = sc.nextInt();
                } catch (Exception e) {
                    sc.next();
                    pantallaDefault();
                    System.out.println("\n\tINTRODUCE UN VALOR CORRECTO");
                    Thread.sleep(1250);
                }

                if (apuesta <= puntos.get(0) && apuesta > 0) {
                    puntos.set(0, puntos.get(0) - apuesta);
                    puntos_c = true;
                }

            }

            // llamamos al metodo creacion carton
            bingo = creacionCarton();

            // copiamos todos estos valores en el array del bingo del ordenador (bingocpu)
            for (int i = 0; i < bingo[i].length; i++) {
                for (int j = 0; j < bingo.length; j++) {
                    bingocpu[j][i] = bingo[j][i];
                }
            }
            // lo mismo con el arraylist de numeros usados en el carton
            for (int i = 0; i < numerosBingo.size(); i++) {
                numerosBingoCpu.add(numerosBingo.get(i));
            }

            // volvemos a llamar al metodo para crear el carton definitivo para el usuario
            bingo = creacionCarton();

            // creamos el bombo con 89 numeros
            for (int i = 1; i <= 89; i++) {
                bombo.add(i);
            }

            // jugada bingo
            String[] resultado = jugarBingo(bingo, bingocpu);

            boolean ganar = false, ganar_cpu = false;

            // analizamos el contenido del array resultado obtenido en el metodo jugarBingo() y definimos que ha ocurrido en la jugada en funcion del mismo
            for (int i = 0; i < resultado.length; i++) {
                if (resultado[i].equals("ganar")) {
                    ganar = true;
                } else if (resultado[i].equals("ganarcpu")) {
                    ganar_cpu = true;
                }
            }

            // llamamos a la función definitiva para hacer el calculo de nuestro resultado
            resultadoBingo(ganar, ganar_cpu, apuesta, puntos);
        }
    }

    /**
     * Función para devolver un carton de bingo que se genera con un algoritmo
     *
     * @return
     */
    public static int[][] creacionCarton() {
        int[][] bingo = new int[9][3];
        Random rd = new Random();
        final int CASILLAS = 15;
        int s1, s2, s3, nN, nU = 0;

        // establecemos a 0 la variable contador de numeros de casillas usadas
        nU = 0;

        // mientras que no se usen todas las casillas
        while (nU != CASILLAS) {

            // llenamos todas las posiciones del array bidimensional con ceros
            for (int i = 0; i < bingo.length; i++) {
                Arrays.fill(bingo[i], 0);
            }

            // vaciar arraylist que almacena los numeros que se usan
            numerosBingo.clear();
            nU = 0;
            s1 = 0;
            s2 = 4;
            s3 = 7;

            // bucle for para recorrer las filas del carton de bingo
            for (int i = 0; i < bingo.length; i++) {
                // vaciar el indice de columnas y le añadimos el valor 0, 1 y 2
                indexCol.clear();
                indexCol.add(0);
                indexCol.add(1);
                indexCol.add(2);
                // hacemos una variable aleatorio entre el 0 y el 2
                nN = rd.nextInt(3);

                // un bucle for para borrar aleatoriamente entre 0 y 2 valores de la columna
                for (int k = 0; k <= (1 - nN); k++) {

                    indexCol.remove(rd.nextInt(indexCol.size()));

                }

                // un bucle for que se repite tantas veces cómo el index de columnas tamaño tenga
                for (int j = 0; j < indexCol.size(); j++) {

                    // variable para determinar el valor de la columna en referencia a la iteracion del for actual
                    int posicion = indexCol.get(j);

                    // dependiendo de la posicion se generará un valor en la fila en una de las tres casillas limitado en tres valores por casilla de la fila
                    switch (posicion) {
                        case 0:
                            if (rd.nextInt(4) + s1 == 0) {
                                bingo[i][0] = 1;
                                numerosBingo.add(bingo[i][0]);
                            } else {
                                bingo[i][0] = rd.nextInt(4) + s1;
                                numerosBingo.add(bingo[i][0]);
                            }
                            break;
                        case 1:
                            bingo[i][1] = rd.nextInt(3) + s2;
                            numerosBingo.add(bingo[i][1]);
                            break;
                        case 2:
                            bingo[i][2] = rd.nextInt(3) + s3;
                            numerosBingo.add(bingo[i][2]);
                            break;
                    }
                }

                s1 += 10;
                s2 += 10;
                s3 += 10;
                nU += indexCol.size();
            }

        }

        // devolver array bingo al metodo principal del bingo
        return bingo;
    }

    /**
     * Juega con los dos cartones verificando sus valores Gana el primero que
     * vacie el cartón antes de que se termine el bombo
     *
     * @param bingo
     * @param bingocpu
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static String[] jugarBingo(int[][] bingo, int[][] bingocpu) throws IOException, InterruptedException {
        Random rd = new Random();
        int numero = 0;
        boolean ganar_cpu = false, perdido = false, ganar = false, romperSiguiente = false, romperSiguiente1 = false;
        ArrayList<String> resultado = new ArrayList<>();

        while (true) {
            romperSiguiente = false;
            borrarPantalla();
            System.out.println(tituloCorto);

            // imprimir el carton del jugador usando bucles for para recorrer el array bidimensional
            System.out.println("\n\t\t\t\t    TU CARTON");
            for (int i = 0; i < bingo[i].length; i++) {
                System.out.println("\n");
                for (int j = 0; j < bingo.length; j++) {
                    System.out.print("\t" + bingo[j][i] + " ");
                }
            }

            // imprimir el carton del ordenador usando bucles for para recorrer el array bidimensional
            System.out.println("\n\n\n\t\t\t\t    CARTON CPU");
            for (int i = 0; i < bingocpu[i].length; i++) {
                System.out.println("\n");
                for (int j = 0; j < bingocpu.length; j++) {
                    System.out.print("\t" + bingocpu[j][i] + " ");
                }
            }

            System.out.println("\n");

            // variables contadores
            int numerosRestantes = 27;
            int numerosRestantesCpu = 27;

            // si el bombo tiene mas de 0 bolas que saque un numero de posicion para el bombo
            if (bombo.size() > 0) {
                numero = rd.nextInt(bombo.size());
                numerosBingoUsados.add(bombo.get(numero));
                bombo.remove(numero);
            } else {
                String[] resultadoArray = resultado.toArray(new String[resultado.size()]);
                return resultadoArray;
            }

            /* 
                Bucle for para mostrar los ultimos 10 numeros.
                Contiene una estructura de control de errores por si aun no se han generado 10 numeros del bombo
             */
            System.out.print("\n\n\tULTIMOS 10 NUMEROS --> ");
            try {
                for (int i = 1; i <= 10; i++) {
                    System.out.print(" " + numerosBingoUsados.get(numerosBingoUsados.size() - i) + " ");
                }
            } catch (Exception e) {
            }

            System.out.println("\n\n\tBOMBO --> " + bombo.size());

            // cambiar tiempos
            Thread.sleep(750);

            for (int i = 0; i < numerosBingo.size(); i++) {
                // si coincide un numero que ya ha salido, borrar del arraylist numerosBingo
                for (int j = 0; j < numerosBingoUsados.size(); j++) {
                    if (numerosBingo.get(i) == numerosBingoUsados.get(j)) {
                        numerosBorrar.add(numerosBingoUsados.get(j));
                        numerosBingo.remove(i);
                    }
                }
            }

            // bucles para comprobar cuantos casillas restantes quedan, si es igual a cero devolver al arraylist el valor "ganar"
            for (int i = 0; i < bingo[i].length; i++) {
                for (int j = 0; j < bingo.length; j++) {
                    if (bingo[j][i] == 0) {
                        numerosRestantes -= 1;
                    }
                    if (numerosRestantes == 0) {
                        resultado.add("ganar");
                        // si el jugador gana, la CPU tiene un intento mas para empatar, sino empata, solo queda como vencedor el usuario
                        romperSiguiente = true;
                    }
                    // si el numero ha salido borrarlo del carton
                    for (int k = 0; k < numerosBorrar.size(); k++) {
                        if (numerosBorrar.get(k) == bingo[j][i]) {
                            bingo[j][i] = 0;
                        }
                    }
                }
            }

            // si coincide un numero que ya ha salido, borrar del arraylist numerosBingoCpu
            for (int i = 0; i < numerosBingoCpu.size(); i++) {
                for (int j = 0; j < numerosBingoUsados.size(); j++) {
                    if (numerosBingoCpu.get(i) == numerosBingoUsados.get(j)) {
                        numerosBorrar.add(numerosBingoUsados.get(j));
                        numerosBingoCpu.remove(i);
                    }
                }

            }

            // esta condicion se cumple cuando la CPU ha ganado y se le ha dado un turno mas al jugador
            if (romperSiguiente1) {
                String[] resultadoArray = resultado.toArray(new String[resultado.size()]);
                return resultadoArray;
            }

            // bucles para comprobar cuantos casillas restantes quedan en el bingo del ordenador, si es igual a cero devolver al arraylist el valor "ganar"
            for (int i = 0; i < bingocpu[i].length; i++) {
                for (int j = 0; j < bingocpu.length; j++) {
                    if (bingocpu[j][i] == 0) {
                        numerosRestantesCpu -= 1;
                    }
                    if (numerosRestantesCpu == 0) {
                        resultado.add("ganarcpu");
                        // si la CPU gana, el jugador tiene una ronda mas para empatar, sino, solo gana la CPU
                        romperSiguiente1 = true;
                    }
                    for (int k = 0; k < numerosBorrar.size(); k++) {
                        if (numerosBorrar.get(k) == bingocpu[j][i]) {
                            bingocpu[j][i] = 0;
                        }
                    }
                }
            }

            // la condicion se da si el jugador ha hecho bingo y la CPU ha tenido un turno mas
            if (romperSiguiente) {
                String[] resultadoArray = resultado.toArray(new String[resultado.size()]);
                return resultadoArray;
            }

            numerosBorrar.clear();

            // si el bombo se vacia, salir del programa
            if (bombo.isEmpty()) {
                break;
            }

        }

        String[] resultadoArray = resultado.toArray(new String[resultado.size()]);
        return resultadoArray;
    }

    /**
     * Aqui se calcula el beneficio obtenido por el resultado de la jugada del
     * bingo
     *
     * @param ganar
     * @param ganar_cpu
     * @param apuesta
     * @param puntos
     * @throws IOException
     * @throws InterruptedException
     */
    public static void resultadoBingo(boolean ganar, boolean ganar_cpu, int apuesta, ArrayList<Integer> puntos) throws IOException, InterruptedException {
        // si ganan el ordenador y el jugador quedan empate
        if (ganar_cpu && ganar) {
            apuesta *= 0.8;
            puntos.set(0, puntos.get(0) + apuesta);

            pantallaDefault();
            System.out.println("\n\n\t\t\t    EMPATE, AHORA TIENES " + puntos + " PUNTOS");
            Thread.sleep(2500);
            // si gana solo el jugador
        } else if (ganar && !ganar_cpu) {
            apuesta *= 2;
            puntos.set(0, puntos.get(0) + apuesta);

            pantallaDefault();
            System.out.println("\n\n\t\t\t    HAS GANADO, AHORA TIENES " + puntos + " PUNTOS");
            Thread.sleep(2500);
            // en cualquier otro caso el jugador pierda
        } else {
            pantallaDefault();

            System.out.println("\n\n\t\t\t    HAS PERDIDO, TE QUEDAN " + puntos + " PUNTOS");
            Thread.sleep(2500);
        }
        finBingo = true;
    }

    /**
     * metodo principal del juego de la ruleta, recibe como único atributo el
     * arraylist de puntos
     *
     * @param puntos
     * @throws InterruptedException
     * @throws IOException
     */
    public static void ruleta(ArrayList<Integer> puntos) throws InterruptedException, IOException {
        ArrayList<Integer> numeros = new ArrayList<>(), apuestas_a = new ArrayList<>(), apuestas_b = new ArrayList<>();
        ArrayList<String> listaApuestas = new ArrayList<>();
        boolean apuesta_c = false, tipo = false, ganar = false;
        // variables para seleccionar las opciones de apuestas en el juego
        int opcion = 0, num = 0, ron = 0, poi = 0, mit = 0, doc = 0, fila = 0, apuesta_n = 0, opcion_r = 0;
        int apuesta_ron = 0, apuesta_poi = 0, apuesta_mit = 0, apuesta_doc = 0, apuesta_fila = 0, rr_ron = 0, rr_fila = 0, lastnum = 0, puntos_aux = puntos.get(0);
        // arrays para poder diferenciar cada numero y sus caracteristicas (par o impar, rojo o negro, fila1, fila2 o fila3, etc.)
        int[] rojo = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36}, fila1 = {3, 6, 9, 12, 15, 18, 21, 24, 27, 30, 33, 36}, fila2 = {2, 5, 8, 11, 14, 17, 20, 23, 26, 29, 32, 35}, fila3 = {1, 4, 7, 10, 13, 16, 19, 22, 25, 28, 31, 34};
        // string que contiene el dibujo de la tabla de la ruleta
        String tabla = "          _______________________________________________________________________________________\n" + "         |                              |                           |                           |\n" + "         |             1 - 12           |          13 - 24          |          25 - 36          |\n" + "     ____|______________________________|___________________________|___________________________|_______________\n" + "    /    |       |       |       |      |      |      |      |      |      |      |      |      |              |\n" + "   /     |   3   |   6   |   9   |  12  |  15  |  18  |  21  |  24  |  27  |  30  |  33  |  36  |    Fila 1    |\n" + "  /      |_______|_______|_______|______|______|______|______|______|______|______|______|______|______________|\n" + " |       |       |       |       |      |      |      |      |      |      |      |      |      |              |\n" + " |   0   |   2   |   5   |   8   |  11  |  14  |  17  |  20  |  23  |  26  |  29  |  32  |  35  |    Fila 2    |\n" + " |       |_______|_______|_______|______|______|______|______|______|______|______|______|______|______________|\n" + "  \\      |       |       |       |      |      |      |      |      |      |      |      |      |              |\n" + "   \\     |   1   |   4   |   7   |  10  |  13  |  16  |  19  |  22  |  25  |  28  |  31  |  34  |    Fila 3    |\n" + "    \\____|_______|_______|_______|______|______|______|______|______|______|______|______|______|______________|\n" + "         |               |              |             |             |             |             |\n" + "         |    1 - 18     |     Par      |    Rojo     |    Negro    |    Impar    |   19 - 36   |\n" + "         |_______________|______________|_____________|_____________|_____________|_____________|\n";
        int numero = 0;

        // mientras no se gane el bucle se repite infinitamente
        while (!ganar) {

            // vaciar los arraylist de lista de apuestas
            listaApuestas.clear();
            apuestas_a.clear();
            apuestas_b.clear();

            // si los puntos del jugador actual son iguales a 0 sale del programa poniendo ganar en true
            if (puntos.get(0) == 0) {
                pantallaDefault();
                System.out.println("\n\n\tTe has quedado sin puntos, adios.\n");
                Thread.sleep(2500);
                ganar = true;
            }

            // si la variable de puntos_aux es mejor a nuestros puntos actuales, significa que hemos ganado, por lo que queremos mostrar cuanto hemos ganado
            if (puntos_aux < puntos.get(0)) {
                pantallaDefault();
                System.out.println("\n\t\t\t\t_________________________________");
                System.out.println("\n\t\t\t\t     HAS GANADO " + (puntos.get(0) - puntos_aux) + " PUNTOS");
                Thread.sleep(2500);
            }

            if (opcion == 8) {
                ganar = true;
            }

            opcion = 0;

            // mientras que en el menu no seleccionemos la opcion 7 (confirmar apuesta), repetir el bucle infinitamente
            while (opcion != 7) {

                pantallaRuleta(tabla, lastnum, numeros, listaApuestas, puntos);

                // si se tienen 0 puntos confirmar la apuesta y salir, en caso contrario, seguir con el programa
                if (puntos.get(0) == 0) {
                    opcion = 7;
                } else {
                    System.out.println("\n\t1. Numero especifico\n\t2. Rojo o negro\n\t3. Par o impar\n\t4. Mitad de tablero\n\t5. Docenas\n\t6. Filas\n\t7. Confirmar\n\t8. Salir");
                    System.out.print("\n\tQue tipo de apuesta quieres hacer: ");
                    // estructura de control para prevenir errores
                    try {
                        opcion = sc.nextInt();
                    } catch (Exception e) {
                        sc.next();
                        pantallaDefault();
                        System.out.println("\n\n\tINTRODUCE UNA OPCION VALIDA");
                        // ponemos la opcion en -1 para que no se vuelva a abrir el menu de apuestas que hayamos seleccionado anteriormente
                        opcion = -1;
                        Thread.sleep(850);
                    }
                }

                apuesta_c = false;
                tipo = false;

                // switch para elegir a que vamos a apostar
                switch (opcion) {
                    // NUMERO
                    case 1:
                        // mostramos la pantalla de la ruleta con un metodo enviando todos los datos actualizados
                        pantallaRuleta(tabla, lastnum, numeros, listaApuestas, puntos);
                        while (!tipo) {
                            System.out.print("\n\tA que numero quieres apostar (40 para salir): ");

                            // estructura de control para evitar errores si no se introduce un numero
                            try {
                                num = sc.nextInt();
                            } catch (Exception e) {
                                sc.next();
                                System.out.println("\n\tINTRODUCE UN VALOR CORRECTO");
                                Thread.sleep(850);
                                apuesta_c = true;
                            }

                            if (num >= 0 && num <= 36) {
                                tipo = true;
                            } else {
                                tipo = true;
                                apuesta_c = true;
                            }
                        }

                        // mientras que la apuesta no se confirme repetir infinito este bucle
                        while (!apuesta_c) {
                            // llamamos al metodo booleano apuestaruleta que nos verifica, anade y actualiza todo lo referente a las apuestas del juego
                            apuesta_c = apuestaRuleta("numero", puntos, listaApuestas, num, ron, poi, mit, doc, fila, apuestas_a, apuestas_b);
                        }
                        break;
                    // ROJO O NEGRO
                    case 2:

                        pantallaRuleta(tabla, lastnum, numeros, listaApuestas, puntos);

                        while (!tipo) {
                            System.out.println("\n\t1. Rojo\n\t2. Negro\n\t3. Salir");
                            System.out.print("\n\tRojo o negro: ");

                            // llamamos al metodo entradaruleta que contiene una estructura de control para evitar errores en la ejecucion del codigo
                            ron = entradaRuleta();

                            if (ron == 1 || ron == 2) {
                                tipo = true;
                            } else {
                                tipo = true;
                                // si el numero que hemos recibido no es una de las dos opciones, no pedir por apuesta y salir de este switch
                                apuesta_c = true;
                            }
                        }

                        while (!apuesta_c) {
                            apuesta_c = apuestaRuleta("rojoNegro", puntos, listaApuestas, num, ron, poi, mit, doc, fila, apuestas_a, apuestas_b);
                        }
                        break;
                    // PAR O IMPAR
                    case 3:

                        pantallaRuleta(tabla, lastnum, numeros, listaApuestas, puntos);

                        while (!tipo) {
                            System.out.println("\n\t1. Par\n\t2. Impar\n\t3. Salir");
                            System.out.print("\n\tPar o impar: ");

                            poi = entradaRuleta();

                            if (poi == 1 || poi == 2) {
                                tipo = true;
                            } else {
                                tipo = true;
                                apuesta_c = true;
                            }
                        }

                        while (!apuesta_c) {
                            apuesta_c = apuestaRuleta("paresImpares", puntos, listaApuestas, num, ron, poi, mit, doc, fila, apuestas_a, apuestas_b);
                        }

                        break;
                    // MITADES
                    case 4:

                        pantallaRuleta(tabla, lastnum, numeros, listaApuestas, puntos);

                        while (!tipo) {
                            System.out.println("\n\t1. 1 - 18\n\t2. 19 - 36\n\t3. Salir");
                            System.out.print("\n\tA que mitad quieres apostar: ");

                            mit = entradaRuleta();

                            if (mit == 1 || mit == 2) {
                                tipo = true;
                            } else {
                                tipo = true;
                                apuesta_c = true;
                            }
                        }

                        while (!apuesta_c) {
                            apuesta_c = apuestaRuleta("mitad", puntos, listaApuestas, num, ron, poi, mit, doc, fila, apuestas_a, apuestas_b);
                        }
                        break;
                    // DOCENAS
                    case 5:

                        pantallaRuleta(tabla, lastnum, numeros, listaApuestas, puntos);

                        while (!tipo) {
                            System.out.println("\n\t1. 1 - 12\n\t2. 13 - 24\n\t3. 25 - 36\n\t4. Salir");
                            System.out.print("\n\tA que docena quieres apostar: ");

                            doc = entradaRuleta();

                            if (doc >= 1 && doc <= 3) {
                                tipo = true;
                            } else {
                                tipo = true;
                                apuesta_c = true;
                            }
                        }

                        while (!apuesta_c) {
                            apuesta_c = apuestaRuleta("docenas", puntos, listaApuestas, num, ron, poi, mit, doc, fila, apuestas_a, apuestas_b);
                        }
                        break;
                    // FILAS
                    case 6:

                        pantallaRuleta(tabla, lastnum, numeros, listaApuestas, puntos);

                        while (!tipo) {
                            System.out.println("\n\t1. Fila 1\n\t2. Fila 2\n\t3. Fila 3\n\t4. Salir");
                            System.out.print("\n\tA que fila quieres apostar: ");

                            fila = entradaRuleta();

                            if (fila >= 1 && fila <= 3) {
                                tipo = true;
                            } else {
                                tipo = true;
                                apuesta_c = true;
                            }
                        }

                        while (!apuesta_c) {
                            apuesta_c = apuestaRuleta("filas", puntos, listaApuestas, num, ron, poi, mit, doc, fila, apuestas_a, apuestas_b);
                        }
                        break;
                    // CONFIRMACION APUESTA
                    case 7:
                        pantallaDefault();
                        System.out.println("\n\tLista de apuestas:\n\t" + listaApuestas);
                        System.out.println("\n\tConfirmando apuesta...");
                        Thread.sleep(1000);
                        break;
                    // SALIR
                    case 8:
                        pantallaDefault();
                        System.out.println("\n\t\t\t\t\tSALIENDO...\n");
                        Thread.sleep(1000);
                        opcion_r = opcion;
                        opcion = 7;
                        break;
                    default:
                        pantallaDefault();
                        System.out.println("\n\n\tINTRODUCE UN VALOR DISPONIBLE");
                        break;
                }
            }

            // si la opcion es 8 salir
            if (opcion_r == 8) {
                break;
            }

            // actualizacion de variables de apuesta despues de anadir todos los valores en el metodo apuestaRuleta()
            // usamos un for para recorrer todos los espacios del arraylist apuestas a
            for (int i = 0; i < apuestas_a.size(); i++) {
                // por cada iteracion miramos el contenido de la posicion i en el arraylist apuestas_b
                switch (apuestas_b.get(i)) {
                    case 1:
                        // en caso de que se cumpla, anadir a apuesta_n el valor de apuestas_a en la iteracion del bucle for
                        apuesta_n = apuestas_a.get(i);
                        break;
                    case 2:
                        apuesta_ron = apuestas_a.get(i);
                        break;
                    case 3:
                        apuesta_poi = apuestas_a.get(i);
                        break;
                    case 4:
                        apuesta_mit = apuestas_a.get(i);
                        break;
                    case 5:
                        apuesta_doc = apuestas_a.get(i);
                        break;
                    case 6:
                        apuesta_fila = apuestas_a.get(i);
                        break;
                }
            }

            // a partir de aqui, se genera el numero de la ruleta, y se comprueban las apuesta, devolviendo los respectivos puntos por ellas
            numero = (int) (Math.random() * 37);
            numeros.add(numero);
            lastnum = numero;

            // si numero coindide en la apuesta individual por numeros, se multiplica la apuesta que hemos hecho por 36, sino por 0
            if (numero == num) {
                apuesta_n *= 36;
            } else {
                apuesta_n *= 0;
            }

            // ponemos la variable para decidir si un numero es rojo o negro en 0
            rr_ron = 0;

            // por todas las posiciones que tiene el array rojo[] miramos si en alguna posicion coindide con el numero que hemos escogido para la apuesta
            for (int i = 0; i < rojo.length; i++) {
                if (rojo[i] == numero) {
                    // si coincide, se pone rr_ron a 1, lo que significa rojo, en caso contrario, se queda a 0 (negro)
                    rr_ron = 1;
                    break;
                }
            }

            // si el numero que ha salido es negro y nosotros hemos escogido negro o ha salido rojo y hemos escogido rojo...
            if ((rr_ron == 0 && ron == 2) || (rr_ron == 1 && ron == 1)) {
                apuesta_ron *= 2;
            } else {
                apuesta_ron *= 0;
            }

            // si el numero es par y hemos escogido esa opcion o si el numero es impar y hemos escogido esa opcion...
            if ((numero % 2 == 0 && poi == 1) || (numero % 2 == 1 && poi == 2)) {
                apuesta_poi *= 2;
            } else {
                apuesta_poi *= 0;
            }

            // si hemos escogido la primera mitad y ha tocado o si hemos escogido la segunda mitad y ha tocado...
            if (((numero >= 1 && numero <= 18) && mit == 1) || ((numero >= 19 && numero <= 36) && mit == 2)) {
                apuesta_mit *= 2;
            } else {
                apuesta_mit *= 0;
            }

            // si hemos escogido la primera, segunda o tercera docena y ha salido...
            if (((numero >= 1 && numero <= 12) && doc == 1) || ((numero >= 13 && numero <= 24) && doc == 2) || ((numero >= 25 && numero <= 36) && doc == 3)) {
                apuesta_doc *= 3;
            } else {
                apuesta_doc *= 0;
            }

            // bucle for que se repite tantas veces como fila1 numeros tenga
            for (int i = 0; i < fila1.length; i++) {
                // si el numero coincide con uno de la fila 3, poner rr_fila a 3, asi con las otras dos filas
                if (numero == fila3[i]) {
                    rr_fila = 3;
                } else if (numero == fila2[i]) {
                    rr_fila = 2;
                } else if (numero == fila1[i]) {
                    rr_fila = 1;
                }

            }

            // si hemos escogido la fila 1, 2 o 3 y ha tocado...
            if ((rr_fila == 1 && fila == 1) || (rr_fila == 2 && fila == 2) || (rr_fila == 3 && fila == 3)) {
                apuesta_fila *= 3;
            } else {
                apuesta_fila *= 0;
            }

            // asignar a puntos_aux los puntos actuales del usuario antes de sumarle las apuestas y sus resultados
            puntos_aux = puntos.get(0);

            // añadir los puntos resultantes al arraylist de puntos
            puntos.set(0, puntos.get(0) + apuesta_n + apuesta_ron + apuesta_poi + apuesta_mit + apuesta_doc + apuesta_fila);
            // establecer a 0 las apuestas
            apuesta_n = 0;
            apuesta_ron = 0;
            apuesta_poi = 0;
            apuesta_mit = 0;
            apuesta_doc = 0;
            apuesta_fila = 0;
        }
    }

    /**
     * metodo para confirmar las apuestas sobre las diferentes opciones de la
     * ruleta
     *
     * @param tipoApuesta
     * @param puntos
     * @param listaApuestas
     * @param num
     * @param ron
     * @param poi
     * @param mit
     * @param doc
     * @param fila
     * @return
     */
    public static boolean apuestaRuleta(String tipoApuesta, ArrayList<Integer> puntos, ArrayList<String> listaApuestas, int num, int ron, int poi, int mit, int doc, int fila, ArrayList<Integer> apuestas_a, ArrayList<Integer> apuestas_b) throws IOException, InterruptedException {

        int apuesta = 0;
        String r_ron = "";
        String r_poi = "";

        // estructura de manejo de erorres para asegurar que el programa sigue funcionando corectamente aunque se den excepciones en la ejecucion
        while (true) {    
            System.out.print("\n\tCuantos puntos quieres apostar: ");
            try {
                apuesta = sc.nextInt();
                break;
            } catch (Exception e) {
                sc.next();
                pantallaDefault();
                System.out.println("\n\n\tINTRODUCE UN VALOR CORRECTO\n");
                Thread.sleep(850);
                pantallaDefault();
                System.out.println("\n\tPUNTOS: " + puntos.get(0));
            }
        }

        // dependiendo del origen de la apuesta, se verifica un tipo de apuesta u otro. Posteriormente, se añade la apuesta confirmada a la lista de apuestas
        switch (tipoApuesta) {
            case "numero":
                // si puntos cumple la condicion del metodo comprobacionpuntos, entonces anadir el valor a los arraylist que guardan las apuestas
                if (comprobacionPuntos(puntos, apuesta)) {
                    listaApuestas.add("Numero " + num + " --> Apuesta de " + apuesta + " puntos");
                    apuestas_a.add(apuesta);
                    apuestas_b.add(1);
                    return true;
                }

                return false;

            case "rojoNegro":

                if (ron == 1) {
                    r_ron = "rojo";
                } else {
                    r_ron = "negro";
                }

                if (comprobacionPuntos(puntos, apuesta)) {
                    listaApuestas.add("Color " + r_ron + " --> Apuesta de " + apuesta + " puntos");
                    apuestas_a.add(apuesta);
                    apuestas_b.add(2);
                    return true;
                }
                return false;

            case "paresImpares":
                if (poi == 1) {
                    r_poi = "pares";
                } else {
                    r_poi = "impares";
                }

                if (comprobacionPuntos(puntos, apuesta)) {
                    listaApuestas.add("Apuesta a " + r_poi + " --> Apuesta de " + apuesta + " puntos");
                    apuestas_a.add(apuesta);
                    apuestas_b.add(3);
                    return true;
                }
                return false;

            case "mitad":
                if (comprobacionPuntos(puntos, apuesta)) {
                    listaApuestas.add("Mitad " + mit + " --> Apuesta de " + apuesta + " puntos");
                    apuestas_a.add(apuesta);
                    apuestas_b.add(4);
                    return true;
                }
                return false;

            case "docenas":
                if (comprobacionPuntos(puntos, apuesta)) {
                    listaApuestas.add("Docena " + doc + " --> Apuesta de " + apuesta + " puntos");
                    apuestas_a.add(apuesta);
                    apuestas_b.add(5);
                    return true;
                }
                return false;

            case "filas":
                if (comprobacionPuntos(puntos, apuesta)) {
                    listaApuestas.add("Fila " + fila + " --> Apuesta de " + apuesta + " puntos");
                    apuestas_a.add(apuesta);
                    apuestas_b.add(6);
                    return true;
                }
                return false;

        }
        return false;
    }

    /**
     * metodo de comprobacion de puntos para verificar que la apuesta es menor o
     * igual al saldo total que tenemos
     *
     * @param puntos
     * @param apuesta
     * @return
     */
    public static boolean comprobacionPuntos(ArrayList<Integer> puntos, int apuesta) {
        // si la acpuesta es menor o igual a nuestro saldo maximo y es mayor a 0...
        if (apuesta <= puntos.get(0) && apuesta > 0) {
            puntos.set(0, puntos.get(0) - apuesta);
            return true;
        }
        return false;
    }

    /**
     * Metodo para comprobar que el dato introducido es un int y no otro valor,
     * para prevenir errores en la ejecucion del codigo
     *
     * Recibe como parametro la variable entera valor, que corresponde con cada
     * una de las opciones de las apuestas de la ruleta
     *
     * @param valor
     * @return
     */
    public static int entradaRuleta() throws InterruptedException {
        int valor = 0;

        // estrutura de control de error para evitar erorres criticos que interrumpan la ejecucion del programa
        try {
            valor = sc.nextInt();
        } catch (Exception e) {
            sc.next();
            System.out.println("\n\tINTRODUCE UN VALOR CORRECTO");
            Thread.sleep(850);
        }

        return valor;
    }

    /**
     * metodo para la apuesta del blackjack, se hace la jugada de blackjack y se
     * recibe la puntuacion total en relacion a la apuesta
     *
     * @param puntos
     * @throws IOException
     * @throws InterruptedException
     */
    public static void apuestaBlackjack(ArrayList<Integer> puntos) throws IOException, InterruptedException {
        boolean puntos_c = false;
        int apuesta = 0;
        pantallaDefault();

        // bucle while para verificar que la apuesta sea menor o igual al saldo total del que disponemos
        while (!puntos_c) {
            pantallaDefault();
            System.out.println("\n\tPuntos: " + puntos);
            System.out.println("\n\t\t\t\t\t.:APUESTA PUNTOS:.");
            System.out.println("\t\t\t\t\t__________________");

            // estructura de control de errores para evitar finalizaciones repentinas del programa
            try {
                System.out.print("\n\tCuantos puntos quieres apostar: ");
                apuesta = sc.nextInt();

                if (apuesta <= puntos.get(0) && apuesta > 0) {
                    puntos.set(0, puntos.get(0) - apuesta);
                    puntos_c = true;
                }
            } catch (Exception e) {
                sc.next();
                pantallaDefault();
                System.out.println("\n\n\tINTRODUCE UN VALOR VALIDO");
                Thread.sleep(850);
            }

        }

        // se juega la partida de blackjack esperando un numero por respuesta
        int resultado = blackjack();

        // dependiendo del numero podemos ver lo que ha ocurrido (ganar, perder o empatar)
        switch (resultado) {
            case 0:
                puntos.set(0, puntos.get(0) + (apuesta * 0));
                break;
            case 1:
                puntos.set(0, puntos.get(0) + (apuesta * 2));
                break;
            case 2:
                puntos.set(0, puntos.get(0) + (apuesta));
                break;
        }

        // preguntamos si queremos jugar otra partida o no
        pantallaDefault();
        System.out.println("\n\tPuntos: " + puntos);
        System.out.print("\n\tQuieres jugar una mano? (s/n) ");
        String jug_s = sc.next();

        if (jug_s.equals("s")) {
            apuestaBlackjack(puntos);
        }

    }

    /**
     * metodo partida blackjack
     *
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static int blackjack() throws IOException, InterruptedException {

        // llamamos al metodo para crear la baraja
        ArrayList<String> baraja = crearBaraja();
        // mezclamos todos los elementos del arraylist baraja()
        Collections.shuffle(baraja);

        ArrayList<String> jugadaPlayer = new ArrayList<>();
        ArrayList<String> jugadaDealer = new ArrayList<>();

        // repartimos las cartas para el jugador y el dealer
        repartirCarta(jugadaPlayer, baraja);
        repartirCarta(jugadaDealer, baraja);
        repartirCarta(jugadaPlayer, baraja);
        repartirCarta(jugadaDealer, baraja);

        // mientras la puntuacion del jugador sea menor de 21, podemos seguir jugando
        while (puntuacionMano(jugadaPlayer) < 21) {
            int accion = 0;

            // mientras no introduzcamos un valor valido no salir del bucle
            while (true) {
                borrarPantalla();

                // mostramos nuestras cartas por pantalla
                System.out.println("\n\n\t\t\t\t       .:PLAYER:.\n");

                for (int i = 0; i < jugadaPlayer.size(); i++) {
                    System.out.println(jugadaPlayer.get(i));
                }

                // mostramos las cartas del dealer
                System.out.println("\n\t\t\t\t       .:DEALER:.\n\n" + jugadaDealer.get(0) + "\t\t\t\t\t.-------.\n\t\t\t\t\t|/|/|/|/|\n\t\t\t\t\t|/|/|/|/|\n\t\t\t\t\t|/|/|/|/|\n\t\t\t\t\t'-------'\n");

                System.out.print("\n\tPEDIR CARTA (1) O PLANTARSE (2): ");

                try {
                    accion = sc.nextInt();
                    break;
                } catch (Exception e) {
                    sc.next();
                    pantallaDefault();
                    System.out.println("\n\n\tINTRODUCE UN VALOR VALIDO");
                    Thread.sleep(850);
                }
            }

            // si le damos al 1, pedimos otra carta
            if (accion == 1) {
                repartirCarta(jugadaPlayer, baraja);
                borrarPantalla();
                System.out.println("\n\n\t\t\t\t       .:PLAYER:.\n");

                if (puntuacionMano(jugadaPlayer) > 21) {
                    System.out.println("\t\t\t\t      TE HAS PASADO");
                } else {
                    for (int i = 0; i < jugadaPlayer.size(); i++) {
                        System.out.println(jugadaPlayer.get(i));
                    }
                }

                System.out.println("\n\t\t\t\t       .:DEALER:.\n\n" + jugadaDealer.get(0) + "\t\t\t\t\t.-------.\n\t\t\t\t\t|/|/|/|/|\n\t\t\t\t\t|/|/|/|/|\n\t\t\t\t\t|/|/|/|/|\n\t\t\t\t\t'-------'\n");
                Thread.sleep(1500);
            } else if (accion == 2) {
                break;
            }
        }

        // una vez finalizemos nuestra jugada, le toca al dealer sacar cartas hasta que llegue a una puntuación mínima de 17
        while (puntuacionMano(jugadaDealer) < 17) {
            repartirCarta(jugadaDealer, baraja);
        }

        borrarPantalla();
        System.out.println("\n\t\t\t\t       .:DEALER:.\n");

        for (int i = 0; i < jugadaDealer.size(); i++) {
            System.out.println(jugadaDealer.get(i));
        }
        Thread.sleep(1500);

        // creamos la variable que determina lo que ha ocurrido en la partida tras llamar al metodo determinarGanador()
        int resultado = determinarGanador(jugadaPlayer, jugadaDealer);

        switch (resultado) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
        }

        return 2;
    }

    /**
     * metodo que devuelve un arraylist con todas las cartas del mazo con sus
     * respectivos dibujos, palos y valores determinados
     *
     * @return
     */
    public static ArrayList<String> crearBaraja() {
        ArrayList<String> baraja = new ArrayList<>();
        String[] palos = {"\u2665", "\u2666", "\u2660", "\u2663"};
        String[] valores = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

        // por cada palo, hacer los 13 valores que hay para las cartas
        for (int i = 0; i < palos.length; i++) {
            for (int j = 0; j < valores.length; j++) {
                // si el valor es 10, cambiar el dibujo de la carta para adaptarlo
                if (j == 8) {
                    baraja.add("\t\t\t\t\t.-------.\n\t\t\t\t\t| " + valores[j] + "    |\n\t\t\t\t\t|   " + palos[i] + "   |\n\t\t\t\t\t|    " + valores[j] + " |\n\t\t\t\t\t'-------'\n");
                } else {
                    baraja.add("\t\t\t\t\t.-------.\n\t\t\t\t\t| " + valores[j] + "     |\n\t\t\t\t\t|   " + palos[i] + "   |\n\t\t\t\t\t|     " + valores[j] + " |\n\t\t\t\t\t'-------'\n");
                }
            }
        }
        return baraja;
    }

    /**
     * metodo para repartir una carta, que recibe el jugador que ha pedido dicha
     * accion. Em este caso se añade a la mano la carta que se quita de la
     * baraja
     *
     * @param mano
     * @param baraja
     */
    public static void repartirCarta(ArrayList<String> mano, ArrayList<String> baraja) {
        String carta = baraja.remove(0);
        mano.add(carta);
    }

    /**
     * metodo para determinar el valor de la mano que se ha jugado. Se usa para
     * determinar el valor de las manos de ambos jugadores
     *
     * @param mano
     * @return
     */
    public static int puntuacionMano(ArrayList<String> mano) {
        int puntuacion = 0;
        int ases = 0;

        // recorremos todo la mano y convertimos sus valores
        for (int i = 0; i < mano.size(); i++) {
            String valor = mano.get(i);

            // si hay un as, sumar 11 a la puntuacion y aumentar en uno el numero de ases por si nos pasamos de puntuacion en una jugada posterior
            if (valor.contains("A")) {
                ases++;
                puntuacion += 11;
                // si hay un rey, reina o principe o un 10, aumentar en 10 la puntuacion total de la mano
            } else if (valor.contains("K") || valor.contains("Q") || valor.contains("J") || valor.contains("10")) {
                puntuacion += 10;
            }

            // por otro lugar, si es un numero, convertir a entero y sumar a la variable puntuacion
            for (int j = 2; j < 10; j++) {
                String num = String.valueOf(j);
                if (valor.contains(num)) {
                    puntuacion += j;
                }
            }

        }

        // si la puntuacion es superior a 21 y hay algun as, restar 10 para dejar su valor en 1
        while (puntuacion > 21 && ases > 0) {
            puntuacion -= 10;
            ases--;
        }

        return puntuacion;
    }

    /**
     * metodo para determinar el ganador recibiendo como atributo la jugada del
     * jugador y la del dealer
     *
     * @param jugadaPlayer
     * @param jugadaDealer
     * @return
     * @throws InterruptedException
     */
    public static int determinarGanador(ArrayList<String> jugadaPlayer, ArrayList<String> jugadaDealer) throws InterruptedException {
        int resultado = 0;
        int puntuacionJugador = puntuacionMano(jugadaPlayer);
        int puntuacionCasa = puntuacionMano(jugadaDealer);

        // si el jugador se pasa de 21 pierde
        if (puntuacionJugador > 21) {
            System.out.println("\t\t\t\t      TE HAS PASADO");
            resultado = 0;
            // si la casa se pasa o el jugador supera al dealer sin pasarse de 21, se gana tambien
        } else if (puntuacionCasa > 21 || puntuacionJugador > puntuacionCasa) {
            System.out.println("\t\t\t\t       HAS GANADO");
            resultado = 1;
            // si el dealer tiene mas puntos que el jugador sin pasarse de 21, gana el dealer
        } else if (puntuacionJugador < puntuacionCasa) {
            System.out.println("\t\t\t\t     EL DEALER GANA");
            resultado = 0;
            // en cualquier otro caso, es un empate
        } else {
            System.out.println("\t\t\t\t         EMPATE");
            resultado = 2;
        }

        // mostrar puntuaciones de ambos jugadores
        System.out.println("\n\n\t\t\t\t  .:PUNTUACION PLAYER:.\n\t\t\t\t\t   " + puntuacionJugador);
        System.out.println("\n\n\t\t\t\t  .:PUNTUACION DEALER:.\n\t\t\t\t\t   " + puntuacionCasa);

        Thread.sleep(4500);

        // devolver el resultado de la jugada al metodo principal de apuestas del blackjack
        return resultado;
    }

    /**
     * metodo para borrar pantalla en el terminal de Windows (cmd)
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public static void borrarPantalla() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }

    /**
     * Es un metodo para optimizar y hacer mas legible el codigo. Se imprime
     * todo lo necesario para visualizar los elementos de la ruleta
     *
     * @param tabla
     * @param lastnum
     * @param numeros
     * @param listaApuestas
     * @param puntos
     * @throws IOException
     * @throws InterruptedException
     */
    public static void pantallaRuleta(String tabla, int lastnum, ArrayList<Integer> numeros, ArrayList<String> listaApuestas, ArrayList<Integer> puntos) throws IOException, InterruptedException {
        borrarPantalla();
        System.out.println(tabla);
        System.out.println("\n\tNumeros anteriores: " + numeros);
        System.out.println("\n\tUltimo numero: " + lastnum);
        System.out.println("\n\tLista de apuestas:\n\t" + listaApuestas);
        System.out.println("\n\tPuntos: " + puntos);
        System.out.println("\t__________________________________");
    }

    /**
     * Este metodo sirve para crear e inicializar todo el sistema de ficheros
     * que hemos implementado en la v3.0.0 de este CASINO para hacer los datos
     * persistentes y recuperables en la siguiente ejecucion.
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public static void sistemaArchivos() throws IOException, InterruptedException {
        // determinamos el directorio que contiene el fichero CASINO.java para asegurar que este programa se ejecuta desde CASINO.bat
        File java = new File("./java");
        // uso de la clase File para determinar el directorio que contiene los ficheros que va a usar el programa para manejar los usuarios y los puntos
        File casino_files = new File("./casino_files");
        // File para determinar el fichero que tiene usuarios y puntos
        File usuarios = new File(casino_files, "usuarios.txt");
        // lector de lineas para leer todo el fichero de usuarios
        BufferedReader lectorLineas = null;

        /*
            Si la carpeta de ficheros no existe y la carpeta java existe se crea el directorio.
        
            De esta forma nos aseguramos que el usuario ejecuta el programa desde CASINO.bat y no desde el propio fichero CASINO.java, lo que crearia el
            directorio de datos dentro de ./java/
         */
        if (!casino_files.exists() && java.exists()) {
            casino_files.mkdir();
        }
        // si el fichero no existe y el programa esta bien ejecutado, se crea el fichero dentro del directorio ./casino_files
        if (!usuarios.exists() && java.exists()) {
            usuarios.createNewFile();
        }

        /*
            Usamos una estructura de control de errores para comprobar que el programa se ejecuta desde CASINO.bat.
            En caso de que no sea asi, el programa manda un mensaje de error y se cierra directamente
         */
        try {
            lectorLineas = new BufferedReader(new FileReader(usuarios));
        } catch (Exception e) {
            pantallaDefault();
            System.out.println("\n\n\t\t\t\t\t.:ERROR:.\n\n\tRECUERDA ABRIR EL PROGRAMA USANDO EL EJECUTABLE CASINO.BAT QUE SE ENCUENTRA\n\tEN LA RAIZ DE LA CARPETA CASINO\n");
            Thread.sleep(500);
            System.exit(0);
        }

        // creamos todo lo necesario para leer las lineas, partes de las lineas y procesarlas posteriormente
        Scanner lector = new Scanner(usuarios);
        String[] arrayLinea;
        String linea;

        int i = 0;

        // si el fichero esta vacio crea dos usuarios predeterminados (paualdea y mohammadtufail)
        if (usuarios.length() == 0) {
            PrintWriter writer = new PrintWriter(usuarios, "UTF-8");
            writer.print("paualdea,aldea2,12000\r\nmohammadtufail,tufail2,12000");
            writer.close();
            // establecemos la variable estatica ficheroNuevo en true para que no haya errores luego a la hora de leer estos nuevos usuarios
            ficheroNuevo = true;
            // si el fichero de usuarios no esta vacio entonces...
        } else {
            // mientras el fichero tenga m�s lineas...
            while (lectorLineas.readLine() != null) {
                // establece la variable String linea con el contenido de la linea actual del fichero
                linea = lector.nextLine();
                // el arrayLinea almacena los elementos de la linea usando como criterio las comas
                arrayLinea = linea.split(",");

                // anadimos una fila al array (empieza en 0, por lo que daria error)
                usuariosList = Arrays.copyOf(usuariosList, usuariosList.length + 1);
                usuariosList[usuariosList.length - 1] = new String[2];

                // el usuario es lo primero que hay en la linea del documento, la contrase�a lo segundo y los puntos lo tercero
                usuariosList[i][0] = arrayLinea[0];
                usuariosList[i][1] = arrayLinea[1];
                // anadimos a este arraylist los puntos del usuario de la linea actual
                puntosUsuario.add(Integer.parseInt(arrayLinea[2]));

                i++;
            }
        }
    }

    /**
     * Este metodo sirve para actualizar el contenido del fichero para a�adir
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
    public static void actualizarFicheros(ArrayList<Integer> puntosPendientes) throws FileNotFoundException, IOException, InterruptedException {
        // establecemos un writer para hacer los cambios en el fichero que indicamos
        PrintWriter writer = new PrintWriter("./casino_files/usuarios.txt", "UTF-8");
        String resultadoUsuarios = "";
        String linea = "";

        // se repite este bucle for por las filas que tenga el array de usuarios de nuestro casino
        for (int i = 0; i < usuariosList.length; i++) {
            // buscamos la fila en la que se encuentra el usuario que estamos usando para poner la nueva puntuacion que tiene en el fichero
            if (usuariosList[i][0].equals(user)) {
                puntosUsuario.set(i, puntosPendientes.get(0));
            }
            // establecemos la linea que anadiremos al fichero (usuario, contrasena, puntos y salto de linea)
            linea = usuariosList[i][0] + "," + usuariosList[i][1] + "," + puntosUsuario.get(i) + "\r\n";
            // anadimos a la variable string resultadoUsuarios el contenido de la nueva linea
            resultadoUsuarios += linea;
        }

        // anadimos en el fichero todo lo que hemos generado en el bucle for
        writer.print(resultadoUsuarios);
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
        System.exit(0);
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

    /**
     * Este metodo lanza varios textos para hacer unas comprobaciones y que el
     * usuario adapte la ventana del terminal a las medidas correctas
     *
     * @throws InterruptedException
     * @throws IOException
     */
    public static void sistemaPantalla() throws InterruptedException, IOException {
        Thread.sleep(200);
        String opcion = "", punto = ".";

        // bucle for para mostrar una animacion de que carga un adaptador de pantalla (no tiene utilidad real, solo estetica)
        for (int i = 0; i < 3; i++) {
            borrarPantalla();
            System.out.println(titulo + "\n\n\t\t\t\tCARGANDO ADAPTADOR PANTALLA" + punto);
            punto += ".";
            Thread.sleep(550);
        }

        // mientras que la opcion no sea ni 's' o 'S' no se sale de este bucle while
        while (!(opcion.equals("s") || opcion.equals("S"))) {
            borrarPantalla();
            System.out.println(tituloCorto);
            // muestra dos barras con # para que sea facil de ver cuando la ventana esta correctamente redimensionada
            String horizontal = "\n\n##################################################################################################################\n##################################################################################################################";
            System.out.print(horizontal + "\n\n\n\n REDIMENSIONA EN HORIZONTAL. \n\n PULSA 'S' Y LUEGO ENTER CUANDO LAS DOS BARRAS SE VEAN BIEN \n\n");
            opcion = sc.next();
        }

        borrarPantalla();
        System.out.print(titulo + "\n\n\t\t    REDIMENSIONA VERTICALMENTE TODO LO QUE PUEDAS, GRACIAS");
        Thread.sleep(4500);

    }

}
