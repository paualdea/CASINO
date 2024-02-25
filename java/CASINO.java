// prueba git
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
 * .:CASINO:. VERSION: 3.0.0
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

    // Aqui definimos varias variables y objetos que queremos ir usando en todos los metodos que vayamos usando.
    // Escaner que vamos a ir usando a lo largo del programa
    static Scanner sc = new Scanner(System.in);
    // String para almacenar el titulo de CASINO que iremos mostrando
    static String titulo = "\n\t                          _____\n" + "\t        .-------.        |A .  | _____\n" + "\t       /   o   /|        | /.\\ ||A ^  | _____ \n" + "\t      /_______/o|        |(_._)|| / \\ ||A _  | _____           \n" + "\t      | o     | |        |  |  || \\ / || ( ) ||A_ _ |         .-\"\"\"-.\n" + "\t      |   o   |o/        |____V||  .  ||(_'_)||( v )|        /   _   \\\n" + "\t      |     o |/                |____V||  |  || \\ / |        |  (8)  |\n" + "\t      '-------'                        |____V||  .  |        \\   ^   /\n" + "\t                                              |____V|         '-...-'\n\n" + "\t  __________________________________________________________________________\n" + "\t |                                                                          |\n" + "\t|     ::::::::      :::      ::::::::  ::::::::::: ::::    :::  ::::::::     |\n" + "\t|    :+:    :+:   :+: :+:   :+:    :+:     :+:     :+:+:   :+: :+:    :+:    |\n" + "\t|    +:+         +:+   +:+  +:+            +:+     :+:+:+  +:+ +:+    +:+    |\n" + "\t|    +#+        +#++:++#++: +#++:++#++     +#+     +#+ +:+ +#+ +#+    +:+    |\n" + "\t|    +#+        +#+     +#+        +#+     +#+     +#+  +#+#+# +#+    +#+    |\n" + "\t|    #+#    #+# #+#     #+# #+#    #+#     #+#     #+#   #+#+# #+#    #+#    |\n" + "\t|     ########  ###     ###  ########  ########### ###    ####  ########     |\n" + "\t|                                                                            |\n" + "\t |__________________________________________________________________________|\n";
    static String user = "", passwd = "", passwd_aux = "";
    // Array bidimensional para almacenar los usuarios del sistema de juego del casino
    static String[][] usuariosList = new String[0][2];
    // ArrayList para almacenar todos los puntos de los usuarios creados en el fichero de juego
    static ArrayList<Integer> puntosUsuario = new ArrayList<>();
    // ArrayLists para el bingo y sus respectivos metodos
    static ArrayList<Integer> numerosBorrar = new ArrayList<>(), indexCol = new ArrayList<>(), bombo = new ArrayList<>(), numerosBingo = new ArrayList<>(), numerosBingoCpu = new ArrayList<>(), numerosBingoUsados = new ArrayList<>();
    // variable booleana para detectar si el fichero que usamos para los usuarios y puntos es nuevo o no
    static boolean ficheroNuevo = false;

    /**
     * Metodo principal, aquiÔøΩ esta el sistema usuarios, inicio de sesion,
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

        // si el fichero se crea de nuevo ejecutamos de nuevo la funcion  de creaciÛn del sistema de ficheros para solucionar errores
        if (ficheroNuevo) {
            sistemaArchivos();
        }

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

            // metodo para borrar la pantalla siempre que se ejecute en el cmd
            borrarPantalla();
            System.out.println(titulo);

            System.out.println("\n\t\t\t\t     1. INICIAR SESION");
            System.out.println("\n\t\t\t\t     2. REGISTRARSE");
            System.out.println("\n\t\t\t\t     3. RESTABLECER CASINO");
            System.out.println("\n\t\t\t\t     4. SALIR");
            System.out.print("\n\n\tQUE QUIERE HACER: ");
            opcion_r = sc.nextInt();

            // switch para elegir entre inicio de sesion, registro o salir del programa
            switch (opcion_r) {
                case 1:
                    // se repetira el inicio de sesion hasta tres veces por si te equivocas de usuario o contrase√±a
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
                case 3:
                    // creamos un objeto con la clase file que haga referencia al fichero de usuarios que vamos usando a lo largo de todo el programa
                    File usuarios = new File("./casino_files", "usuarios.txt");
                    String opcion;

                    borrarPantalla();
                    System.out.print(titulo + "\n\n\tESTAS SEGURO DE QUE QUIERES RESTABLECER EL CASINO?\n\tESTO BORRARA TODOS LOS USUARIOS Y PUNTOS\n\n\tBORRAR? (s/n): ");
                    opcion = sc.next();

                    // si la entrada de la variable String opcion es s, S, y o Y, se restablece el casino
                    if (opcion.equals("s") || opcion.equals("S") || opcion.equals("y") || opcion.equals("Y")) {
                        String punto = ".";
                        // bucle for para animar el movimiento de los tres puntos de progreso
                        for (int i = 0; i < 3; i++) {
                            borrarPantalla();
                            System.out.println(titulo + "\n\n\tRESTABLECIENDO CASINO" + punto);
                            punto += ".";
                            Thread.sleep(1000);
                        }

                        // creamos un printwriter y lo cerramos para dejar el fichero vacio
                        PrintWriter writer = new PrintWriter(usuarios, "UTF-8");
                        writer.close();

                        System.out.println("");
                        // se para la ejecuciÛn del programa
                        System.exit(0);
                    }
                    
                    break;
                case 4:
                    /* 
                        Se llama al metodo actualizarFicheros para actualizar todos los valores en el fichero antes de finalizar la ejecuciÛn del programa.
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
                borrarPantalla();
                System.out.println(titulo);
                System.out.print("\n\n\t\t\t\t   USUARIO: ");
                user = sc.next();
                System.out.print("\n\t\t\t\t   CONTRASENA: ");
                passwd = sc.next();

                // Se recorre todo el array de usuarios para ver si algun registro coincide con el usuario y contrase√±a que hemos introducido
                for (int i = 0; i < usuariosList.length; i++) {
                    if (user.equals(usuariosList[i][0]) && passwd.equals(usuariosList[i][1])) {
                        System.out.println("\n\t\t\t\t   HAS INICIADO SESION COMO " + user);

                        // se vacia al arraylist para llevar lo puntos actualizados
                        puntos.clear();
                        /*
                            Se le aÒade siempre en la primera posicion el valor de puntos usuarios en la iteraciÛn en la
                            que esta el usuario con el que iniciamos sesiÛn.
                        */
                        puntos.add(puntosUsuario.get(i));

                        Thread.sleep(1000);
                        return true;
                    }
                }

                // si ningun usuario coincide, se devuelte un false
                return false;

            case "registro":
                int ingreso = 0;
                boolean usuarioNuevo = false,
                 puntosEstablecidos = false;

                borrarPantalla();
                System.out.println(titulo);
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
                            System.out.println("\n\t\t\t\tUSUARIO YA EXISTENTE");
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
                        borrarPantalla();
                        System.out.println(titulo);
                        System.out.print("\n\t\t\t   INGRESO DINERO (MAXIMO 3000): ");
                        ingreso = sc.nextInt();

                        if (ingreso <= 3000) {
                            puntos.clear();
                            puntos.add(ingreso);
                            puntosEstablecidos = true;
                            break;
                        } else {
                            borrarPantalla();
                            System.out.println(titulo);
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
            Se usa un arraylist porque se puede actualizar sin usar return ni declararlo como est√°tico en cualquier metodo. Por ello, se podra ver varias 
            veces el uso de sentencias como puntos.get(0) o puntos.set(0, puntos.get(0) - variable) porque jugaremos unicamente con la primera posicion
            de este array para aprovechar la propiedad que se ha mencionado anteriormente.
         */
        ArrayList<Integer> puntosPendientes = new ArrayList<>();
        
        puntosPendientes.add(puntos);
        int opcionMenu = 0;

        // Mientras que la opcion del meno de juegos no sea 5 (salir del programa), se repite infinitamente el codigo contenido dentro
        while (opcionMenu != 5) {

            if (puntos == 0) {
                borrarPantalla();
                System.out.println(titulo);
                System.out.println("\n\t\t\t\t   TE HAS QUEDADO SIN PUNTOS");
                Thread.sleep(3000);
                System.exit(0);
            }

            borrarPantalla();
            System.out.println(titulo);
            System.out.println("\n\t PUNTOS: " + puntos);
            System.out.println("\n\t\t\t\t           1. DADOS");
            System.out.println("\n\t\t\t\t           2. BINGO");
            System.out.println("\n\t\t\t\t           3. RULETA");
            System.out.println("\n\t\t\t\t           4. BLACKJACK");
            System.out.println("\n\t\t\t\t           5. SALIR");
            System.out.print("\n\n\tQUE QUIERE HACER: ");
            opcionMenu = sc.nextInt();

            // switch para desviar a los m√©todos de los juegos
            switch (opcionMenu) {
                case 1:
                    // primero establecemos la posicion 0 del arraylist con el valor de puntos y luego llamamos a la funci√≥n de dados() adjuntando dicho arraylist
                    puntosPendientes.set(0, puntos);
                    dados(puntosPendientes);
                    // una vez cambia el valor de la posici√≥n 0 del arraylist, hacemos que la variable puntos valga lo mismo
                    puntos = puntosPendientes.get(0);
                    break;
                case 2:
                    puntosPendientes.set(0, puntos);
                    bingo(puntosPendientes);
                    puntos = puntosPendientes.get(0);
                    break;
                case 3:
                    puntosPendientes.set(0, puntos);
                    ruleta(puntosPendientes);
                    puntos = puntosPendientes.get(0);
                    break;
                case 4:
                    puntosPendientes.set(0, puntos);
                    apuestaBlackjack(puntosPendientes);
                    puntos = puntosPendientes.get(0);
                    break;
                case 5:
                    actualizarFicheros(puntosPendientes);
                    borrarPantalla();
                    System.out.println(titulo);
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
        boolean dadoAcertado = false;
        int n, apuesta = 0;

        // array con los dibujos de todos los dados del 1 al 6
        String[] caraDado = {
            "\t\t\t\t\t _____________\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|      O      |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|_____________|",
            "\t\t\t\t\t _____________\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|  O          |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|          O  |\n" + "\t\t\t\t\t|_____________|",
            "\t\t\t\t\t _____________\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|  O          |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|      O      |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|          O  |\n" + "\t\t\t\t\t|_____________|",
            "\t\t\t\t\t _____________\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|  O       O  |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|  O       O  |\n" + "\t\t\t\t\t|_____________|",
            "\t\t\t\t\t _____________\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|  O       O  |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|      O      |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|  O       O  |\n" + "\t\t\t\t\t|_____________|",
            "\t\t\t\t\t _____________\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|  O       O  |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|  O       O  |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|  O       O  |\n" + "\t\t\t\t\t|_____________|",};

        // mientras que la variable booleana sea false, todo el c√≥digo se repite infinitamente
        while (!ganar) {
            // mientras que el numero introducido no sea entre 2 y 12 se repite todo
            do {
                dadoAcertado = false;
                borrarPantalla();

                // si te quedas sin puntos sale del while
                if (puntos.get(0) == 0) {
                    ganar = true;
                }

                System.out.println(titulo);
                System.out.print(caraDado[4]);
                System.out.println("\n\n\t\t\t\t\t   .:DADOS:.");
                System.out.println("\n\tPuntos: " + puntos.get(0));
                System.out.print("\n\tEscoge un numero (2 - 12) (14 para salir): ");
                n = sc.nextInt();
                Thread.sleep(500);

                if (n == 14) {
                    System.out.println("\n\t_______________________________________________________");
                    System.out.println("\n\t\t\t\tSALIENDO...\n");
                    Thread.sleep(2500);
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
                borrarPantalla();
                System.out.println(titulo);
                System.out.println("\n\tPuntos: " + puntos.get(0));
                System.out.print("\n\tApuesta: ");
                apuesta = sc.nextInt();

            } while (!(apuesta <= puntos.get(0) && apuesta > 0));

            puntos.set(0, puntos.get(0) - apuesta);
            // se llama al metodo para obtener una puntuaci√≥n en relaci√≥n a la apuesta realizada
            puntos.set(0, puntos.set(0, tirarDados(caraDado, n, apuesta)));
            apuesta = 0;

            if (puntos.get(0) == 0) {
                ganar = true;
            }
        }
    }

    /**
     * Este metodo sirve para generar la tirada de los dados que da un valor
     * resultante de entre 2 y 12 Recibe el array caraDado para mostrar el valor
     * de los dados gr√°ficamente, la cantidad apostada
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

        boolean dadoAcertado = false;

        System.out.println("\n\t     DADO 1\n" + caraDado[dado1 - 1] + "\n\n\t     DADO 2\n" + caraDado[dado2 - 1]);
        Thread.sleep(3500);

        // switch para comprobar el resultado en puntos si acertamos la apuesta
        if (resultado == n) {
            dadoAcertado = true;
            switch (n) {
                case 2:
                case 12:
                    apuesta *= 2;
                    break;

                case 3:
                case 11:
                    apuesta *= 1.5;
                    break;

                case 4:
                case 10:
                    apuesta *= 1.4;
                    break;

                case 5:
                case 9:
                    apuesta *= 1.3;
                    break;

                case 6:
                case 8:
                    apuesta *= 1.2;
                    break;

                case 7:
                    apuesta *= 1.1;
                    break;
                default:
                    apuesta *= 0;
                    break;
            }
        }

        // devuelve el valor total de la apuesta tras la tirada al metodo principal de los dados
        return apuesta;
    }

    /**
     * metodo principal para el juego del bingo. S√≥lo recibe c√≥mo atributo el
     * arraylist de puntos
     *
     * @param puntos
     * @throws IOException
     * @throws InterruptedException
     */
    public static void bingo(ArrayList<Integer> puntos) throws IOException, InterruptedException {
        // vaciamos todos los arraylist estaticos para que no den conflicto si ejecutamos el juego del bingo m√°s de una vez
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
            borrarPantalla();
            System.out.println(titulo);
            System.out.println("\n\t\t\t\t\t.:BINGO:.");
            System.out.println("\t\t\t\t\t_________");

            System.out.print("\n\tQuieres jugar un carton? (s/n) ");
            jug_s = sc.next();

            if (!jug_s.equals("s")) {
                salir = true;
                break;
            }

            puntos_c = false;

            // bucle while para obligar al usuario a hacer una apuesta por debajo o igual de su saldo m√°ximo disponible
            while (!puntos_c) {
                borrarPantalla();
                System.out.println(titulo);
                System.out.println("\n\tPuntos: " + puntos.get(0));
                System.out.println("\n\t\t\t\t\t.:APUESTA PUNTOS:.");
                System.out.println("\t\t\t\t\t__________________");

                System.out.print("\n\tCuantos puntos quieres apostar: ");
                apuesta = sc.nextInt();

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

            // llamamos a la funci√≥n definitiva para hacer el calculo de nuestro resultado
            resultadoBingo(ganar, ganar_cpu, apuesta, puntos);
        }
    }

    /**
     * Funci√≥n para devolver un carton de bingo que se genera con un algoritmo
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
                // vaciar el indice de columnas y le a√±adimos el valor 0, 1 y 2
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

                // un bucle for que se repite tantas veces c√≥mo el index de columnas tama√±o tenga
                for (int j = 0; j < indexCol.size(); j++) {

                    // variable para determinar el valor de la columna en referencia a la iteracion del for actual
                    int posicion = indexCol.get(j);

                    // dependiendo de la posicion se generar√° un valor en la fila en una de las tres casillas limitado en tres valores por casilla de la fila
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
     * vacie el cart√≥n antes de que se termine el bombo
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
        boolean ganar_cpu = false, perdido = false, ganar = false;
        ArrayList<String> resultado = new ArrayList<>();

        while (true) {
            borrarPantalla();
            System.out.println(titulo);

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
                resultado.add("perdido");
                String[] resultadoArray = resultado.toArray(new String[resultado.size()]);
                return resultadoArray;
            }

            System.out.println("\n\tUltimo numero: " + numerosBingoUsados.get(numerosBingoUsados.size() - 1));
            System.out.println("\n\tNumeros restantes: " + bombo.size());

            Thread.sleep(1250);

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

            // bucles para comprobar cuantos casillas restantes quedan en el bingo del ordenador, si es igual a cero devolver al arraylist el valor "ganar"
            for (int i = 0; i < bingocpu[i].length; i++) {
                for (int j = 0; j < bingocpu.length; j++) {
                    if (bingocpu[j][i] == 0) {
                        numerosRestantesCpu -= 1;
                    }
                    if (numerosRestantesCpu == 0) {
                        resultado.add("ganarcpu");

                    }
                    for (int k = 0; k < numerosBorrar.size(); k++) {
                        if (numerosBorrar.get(k) == bingocpu[j][i]) {
                            bingocpu[j][i] = 0;
                        }
                    }
                }
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
            borrarPantalla();
            System.out.println(titulo);
            System.out.println("\n\n\t\t\t    EMPATE, AHORA TIENES " + puntos + " PUNTOS");
            Thread.sleep(2500);
            // si gana solo el jugador
        } else if (ganar && !ganar_cpu) {
            apuesta *= 2;
            puntos.set(0, puntos.get(0) + apuesta);
            borrarPantalla();
            System.out.println(titulo);
            System.out.println("\n\n\t\t\t    HAS GANADO, AHORA TIENES " + puntos + " PUNTOS");
            Thread.sleep(2500);
            // en cualquier otro caso el jugador pierda
        } else {
            borrarPantalla();
            System.out.println(titulo);
            System.out.println("\n\n\t\t\t    HAS PERDIDO, TE QUEDAN " + puntos + " PUNTOS");
            Thread.sleep(2500);
        }
    }

    /**
     * metodo principal del juego de la ruleta, recibe como √∫nico atributo el
     * arraylist de puntos
     *
     * @param puntos
     * @throws InterruptedException
     * @throws IOException
     */
    public static void ruleta(ArrayList<Integer> puntos) throws InterruptedException, IOException {
        ArrayList<Integer> numeros = new ArrayList<>();
        ArrayList<String> listaApuestas = new ArrayList<>();
        boolean apuesta_c = false, tipo = false, ganar = false;
        int opcion = 0, num = 0, ron = 0, poi = 0, mit = 0, doc = 0, fila = 0, apuesta_n = 0, opcion_r = 0;
        int apuesta_ron = 0, apuesta_poi = 0, apuesta_mit = 0, apuesta_doc = 0, apuesta_fila = 0, rr_ron = 0, rr_fila = 0, lastnum = 0, puntos_aux = puntos.get(0);
        int[] rojo = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36}, fila1 = {3, 6, 9, 12, 15, 18, 21, 24, 27, 30, 33, 36}, fila2 = {2, 5, 8, 11, 14, 17, 20, 23, 26, 29, 32, 35}, fila3 = {1, 4, 7, 10, 13, 16, 19, 22, 25, 28, 31, 34};
        String tabla = "          _______________________________________________________________________________________\n" + "         |                              |                           |                           |\n" + "         |             1 - 12           |          13 - 24          |          25 - 36          |\n" + "     ____|______________________________|___________________________|___________________________|_______________\n" + "    /    |       |       |       |      |      |      |      |      |      |      |      |      |              |\n" + "   /     |   3   |   6   |   9   |  12  |  15  |  18  |  21  |  24  |  27  |  30  |  33  |  36  |    Fila 1    |\n" + "  /      |_______|_______|_______|______|______|______|______|______|______|______|______|______|______________|\n" + " |       |       |       |       |      |      |      |      |      |      |      |      |      |              |\n" + " |   0   |   2   |   5   |   8   |  11  |  14  |  17  |  20  |  23  |  26  |  29  |  32  |  35  |    Fila 2    |\n" + " |       |_______|_______|_______|______|______|______|______|______|______|______|______|______|______________|\n" + "  \\      |       |       |       |      |      |      |      |      |      |      |      |      |              |\n" + "   \\     |   1   |   4   |   7   |  10  |  13  |  16  |  19  |  22  |  25  |  28  |  31  |  34  |    Fila 3    |\n" + "    \\____|_______|_______|_______|______|______|______|______|______|______|______|______|______|______________|\n" + "         |               |              |             |             |             |             |\n" + "         |    1 - 18     |     Par      |    Rojo     |    Negro    |    Impar    |   19 - 36   |\n" + "         |_______________|______________|_____________|_____________|_____________|_____________|\n";
        int numero = 0;

        // mientras no se gane el bucle se repite infinitamente
        while (!ganar) {

            listaApuestas.clear();

            if (puntos.get(0) == 0) {
                borrarPantalla();
                System.out.println(titulo);
                System.out.println("\n\n\tTe has quedado sin puntos, adios.\n");
                Thread.sleep(2500);
                ganar = true;
            }

            if (puntos_aux < puntos.get(0)) {
                borrarPantalla();
                System.out.println(titulo);
                System.out.println("\n\t\t\t\t_________________________________");
                System.out.println("\n\t\t\t\t     HAS GANADO " + (puntos.get(0) - puntos_aux) + " PUNTOS");
                Thread.sleep(2500);
            }

            if (opcion == 8) {
                ganar = true;
            }

            opcion = 0;

            // mientras que en el men√∫ no seleccionemos la opci√≥n 7 (confirmar apuesta), repetir el bucle infinitamente
            while (opcion != 7) {

                pantallaRuleta(tabla, lastnum, numeros, listaApuestas, puntos);

                // si se tienen 0 puntos confirmar la apuesta y salir, en caso contrario, seguir con el programa
                if (puntos.get(0) == 0) {
                    opcion = 7;
                } else {
                    System.out.println("\n\t1. Numero especifico\n\t2. Rojo o negro\n\t3. Par o impar\n\t4. Mitad de tablero\n\t5. Docenas\n\t6. Filas\n\t7. Confirmar\n\t8. Salir");
                    System.out.print("\n\tQue tipo de apuesta quieres hacer: ");
                    opcion = sc.nextInt();
                }

                apuesta_c = false;
                tipo = false;

                // switch para elegir a que vamos a apostar
                switch (opcion) {

                    case 1:
                        pantallaRuleta(tabla, lastnum, numeros, listaApuestas, puntos);
                        while (!tipo) {
                            System.out.print("\n\tA que numero quieres apostar (40 para salir): ");
                            num = sc.nextInt();

                            if (num >= 0 && num <= 36) {
                                tipo = true;
                            } else if (num == 40) {
                                tipo = true;
                                apuesta_c = true;
                            }
                        }
                        while (!apuesta_c) {
                            apuesta_c = apuestaRuleta("numero", puntos, listaApuestas, num, ron, poi, mit, doc, fila);
                        }
                        break;
                    case 2:

                        pantallaRuleta(tabla, lastnum, numeros, listaApuestas, puntos);

                        while (!tipo) {
                            System.out.println("\n\t1. Rojo\n\t2. Negro\n\t3. Salir");
                            System.out.print("\n\tRojo o negro: ");
                            ron = sc.nextInt();

                            if (ron == 1 || ron == 2) {
                                tipo = true;
                            } else if (ron == 3) {
                                tipo = true;
                                apuesta_c = true;
                            }
                        }

                        while (!apuesta_c) {
                            apuesta_c = apuestaRuleta("rojoNegro", puntos, listaApuestas, num, ron, poi, mit, doc, fila);
                        }
                        break;
                    case 3:

                        pantallaRuleta(tabla, lastnum, numeros, listaApuestas, puntos);

                        while (!tipo) {
                            System.out.println("\n\t1. Par\n\t2. Impar\n\t3. Salir");
                            System.out.print("\n\tPar o impar: ");
                            poi = sc.nextInt();

                            if (poi == 1 || poi == 2) {
                                tipo = true;
                            } else if (poi == 3) {
                                tipo = true;
                                apuesta_c = true;
                            }
                        }

                        while (!apuesta_c) {
                            apuesta_c = apuestaRuleta("paresImpares", puntos, listaApuestas, num, ron, poi, mit, doc, fila);
                        }

                        break;
                    case 4:

                        pantallaRuleta(tabla, lastnum, numeros, listaApuestas, puntos);

                        while (!tipo) {
                            System.out.println("\n\t1. 1 - 18\n\t2. 19 - 36\n\t3. Salir");
                            System.out.print("\n\tA que mitad quieres apostar: ");
                            mit = sc.nextInt();

                            if (mit == 1 || mit == 2) {
                                tipo = true;
                            } else if (mit == 3) {
                                tipo = true;
                                apuesta_c = true;
                            }
                        }

                        while (!apuesta_c) {
                            apuesta_c = apuestaRuleta("mitad", puntos, listaApuestas, num, ron, poi, mit, doc, fila);
                        }
                        break;
                    case 5:

                        pantallaRuleta(tabla, lastnum, numeros, listaApuestas, puntos);

                        while (!tipo) {
                            System.out.println("\n\t1. 1 - 12\n\t2. 13 - 24\n\t3. 25 - 36\n\t4. Salir");
                            System.out.print("\n\tA que docena quieres apostar: ");
                            doc = sc.nextInt();

                            if (doc >= 1 && doc <= 3) {
                                tipo = true;
                            } else if (doc == 4) {
                                tipo = true;
                                apuesta_c = true;
                            }
                        }

                        while (!apuesta_c) {
                            apuesta_c = apuestaRuleta("docenas", puntos, listaApuestas, num, ron, poi, mit, doc, fila);
                        }
                        break;
                    case 6:

                        pantallaRuleta(tabla, lastnum, numeros, listaApuestas, puntos);

                        while (!tipo) {
                            System.out.println("\n\t1. Fila 1\n\t2. Fila 2\n\t3. Fila 3\n\t4. Salir");
                            System.out.print("\n\tA que fila quieres apostar: ");
                            fila = sc.nextInt();

                            if (fila >= 1 && fila <= 3) {
                                tipo = true;
                            } else if (fila == 4) {
                                tipo = true;
                                apuesta_c = true;
                            }
                        }

                        while (!apuesta_c) {
                            apuesta_c = apuestaRuleta("filas", puntos, listaApuestas, num, ron, poi, mit, doc, fila);
                        }
                        break;
                    case 7:
                        borrarPantalla();
                        System.out.println(titulo);
                        System.out.println("\n\tLista de apuestas:\n\t" + listaApuestas);
                        System.out.println("\n\tConfirmando apuesta...");
                        Thread.sleep(1000);
                        break;
                    case 8:
                        borrarPantalla();
                        System.out.println(titulo);
                        System.out.println("\n\t\t\tSALIENDO...\n");
                        Thread.sleep(1000);
                        opcion_r = opcion;
                        opcion = 7;
                        break;
                }
            }

            if (opcion_r == 8) {
                break;
            }

            // a partir de aqui, se genera el numero de la ruleta, y se comprueban las apuesta, devolviendo los respectivos puntos por ellas
            numero = (int) (Math.random() * 37);
            numeros.add(numero);
            lastnum = numero;

            if (numero == num) {
                apuesta_n *= 36;
            } else {
                apuesta_n *= 0;
            }

            rr_ron = 0;

            for (int i = 0; i < rojo.length; i++) {
                if (rojo[i] == numero) {
                    rr_ron = 1;
                    break;
                }
            }

            if ((rr_ron == 0 && ron == 2) || (rr_ron == 1 && ron == 1)) {
                apuesta_ron *= 2;
            } else {
                apuesta_ron *= 0;
            }

            if ((numero % 2 == 0 && poi == 1) || (numero % 2 == 1 && poi == 2)) {
                apuesta_poi *= 2;
            } else {
                apuesta_poi *= 0;
            }

            if (((numero >= 1 && numero <= 18) && mit == 1) || ((numero >= 19 && numero <= 36) && mit == 2)) {
                apuesta_mit *= 2;
            } else {
                apuesta_mit *= 0;
            }

            if (((numero >= 1 && numero <= 12) && doc == 1) || ((numero >= 13 && numero <= 24) && doc == 2) || ((numero >= 25 && numero <= 36) && doc == 3)) {
                apuesta_doc *= 3;
            } else {
                apuesta_doc *= 0;
            }

            for (int i = 0; i < fila1.length; i++) {

                if (numero == fila3[i]) {
                    rr_fila = 3;
                } else if (numero == fila2[i]) {
                    rr_fila = 2;
                } else if (numero == fila1[i]) {
                    rr_fila = 1;
                }

            }

            if ((rr_fila == 1 && fila == 1) || (rr_fila == 2 && fila == 2) || (rr_fila == 3 && fila == 3)) {
                apuesta_fila *= 3;
            } else {
                apuesta_fila *= 0;
            }

            puntos_aux = puntos.get(0);

            // a√±adir los puntos resultantes al arraylist de puntos
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
    public static boolean apuestaRuleta(String tipoApuesta, ArrayList<Integer> puntos, ArrayList<String> listaApuestas, int num, int ron, int poi, int mit, int doc, int fila) {

        int apuesta = 0;
        String r_ron = "";
        String r_poi = "";

        System.out.print("\n\tCuantos puntos quieres apostar: ");
        apuesta = sc.nextInt();

        // dependiendo del origen de la apuesta, se verifica un tipo de apuesta u otro. Posteriormente, se a√±ade la apuesta confirmada a la lista de apuestas
        switch (tipoApuesta) {
            case "numero":
                if (comprobacionPuntos(puntos, apuesta)) {
                    listaApuestas.add("Numero " + num + " --> Apuesta de " + apuesta + " puntos");
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
                    return true;
                }
                return false;

            case "mitad":
                if (comprobacionPuntos(puntos, apuesta)) {
                    listaApuestas.add("Mitad " + mit + " --> Apuesta de " + apuesta + " puntos");
                    return true;
                }
                return false;

            case "docenas":
                if (comprobacionPuntos(puntos, apuesta)) {
                    listaApuestas.add("Docena " + doc + " --> Apuesta de " + apuesta + " puntos");
                    return true;
                }
                return false;

            case "filas":
                if (comprobacionPuntos(puntos, apuesta)) {
                    listaApuestas.add("Fila " + fila + " --> Apuesta de " + apuesta + " puntos");
                    return true;
                }
                return false;

        }
        return false;
    }

    /**
     * metodo de comprobaci√≥n de puntos para verificar que la apuesta es menor
     * o igual al saldo total que tenemos
     *
     * @param puntos
     * @param apuesta
     * @return
     */
    public static boolean comprobacionPuntos(ArrayList<Integer> puntos, int apuesta) {
        if (apuesta <= puntos.get(0) && apuesta > 0) {
            puntos.set(0, puntos.get(0) - apuesta);
            return true;
        }
        return false;
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
        borrarPantalla();
        System.out.println(titulo);

        // bucle while para verificar que la apuesta sea menor o igual al saldo total del que disponemos
        while (!puntos_c) {
            borrarPantalla();
            System.out.println(titulo);
            System.out.println("\n\tPuntos: " + puntos);
            System.out.println("\n\t\t\t\t\t.:APUESTA PUNTOS:.");
            System.out.println("\t\t\t\t\t__________________");

            System.out.print("\n\tCuantos puntos quieres apostar: ");
            apuesta = sc.nextInt();

            if (apuesta <= puntos.get(0) && apuesta > 0) {
                puntos.set(0, puntos.get(0) - apuesta);
                puntos_c = true;
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
        borrarPantalla();
        System.out.println(titulo);
        System.out.println("Puntos: " + puntos);
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
        Scanner sc = new Scanner(System.in);

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

        borrarPantalla();

        // mostramos nuestras cartas por pantalla
        System.out.println("\n\n\t\t\t\t       .:PLAYER:.\n");

        for (int i = 0; i < jugadaPlayer.size(); i++) {
            System.out.println(jugadaPlayer.get(i));
        }

        // mostramos las cartas del dealer
        System.out.println("\n\t\t\t\t       .:DEALER:.\n\n" + jugadaDealer.get(0) + "\t\t\t\t\t.-------.\n\t\t\t\t\t|/|/|/|/|\n\t\t\t\t\t|/|/|/|/|\n\t\t\t\t\t|/|/|/|/|\n\t\t\t\t\t'-------'\n");

        // mientras la puntuacion del jugador sea menor de 21, podemos seguir jugando
        while (puntuacionMano(jugadaPlayer) < 21) {
            System.out.print("\n\tPEDIR CARTA (1) O PLANTARSE (2): ");
            int accion = sc.nextInt();

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
            } else {
                break;
            }
        }

        // una vez finalizemos nuestra jugada, le toca al dealer sacar cartas hasta que llegue a una puntuaci√≥n m√≠nima de 17
        while (puntuacionMano(jugadaDealer) < 17) {
            repartirCarta(jugadaDealer, baraja);
        }

        borrarPantalla();
        System.out.println("\n\t\t\t\t       .:DEALER:.\n");

        for (int i = 0; i < jugadaDealer.size(); i++) {
            System.out.println(jugadaDealer.get(i));
        }

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
     * accion. Em este caso se a√±ade a la mano la carta que se quita de la
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
        System.out.println(titulo);
        System.out.println(tabla);
        System.out.println("\n\tUltimo numero: " + lastnum);
        System.out.println("\tNumeros anteriores: " + numeros);
        System.out.println("\n\tLista de apuestas:\n\t" + listaApuestas);
        System.out.println("\n\tPuntos: " + puntos);
        System.out.println("\t__________________________________");
    }
    
    /**
     * Este metodo sirve para crear e inicializar todo el sistema de ficheros que hemos implementado en la v3 de este CASINO
     * para hacer los datos persistentes y recuperables en la siguiente ejecuciÛn.
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
            borrarPantalla();
            System.out.println(titulo + "\n\n\t\t\t\t\t.:ERROR:.\n\n\tRECUERDA ABRIR EL PROGRAMA USANDO EL EJECUTABLE CASINO.BAT QUE SE ENCUENTRA\n\tEN LA RAÔøΩZ DE LA CARPETA CASINO\n");
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
            // mientras el fichero tenga m·s lineas...
            while (lectorLineas.readLine() != null) {
                // establece la variable String linea con el contenido de la linea actual del fichero
                linea = lector.nextLine();
                // el arrayLinea almacena los elementos de la linea usando como criterio las comas
                arrayLinea = linea.split(",");
                
                // aÒadimos una fila al array (empieza en 0, por lo que daria error)
                usuariosList = Arrays.copyOf(usuariosList, usuariosList.length + 1);
                usuariosList[usuariosList.length - 1] = new String[2];
                
                // el usuario es lo primero que hay en la linea del documento, la contraseÒa lo segundo y los puntos lo tercero
                usuariosList[i][0] = arrayLinea[0];
                usuariosList[i][1] = arrayLinea[1];
                // aÒadimos a este arraylist los puntos del usuario de la linea actual
                puntosUsuario.add(Integer.parseInt(arrayLinea[2]));
                
                i++;
            }
        }
    }
    
    /**
     * Este metodo sirve para actualizar el contenido del fichero para aÒadir nuevos usuarios, puntos que se pierden o ganan, etc.
     * 
     * Recibe como parametro el arraylist que tiene los puntos de la sesion actual de juego
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
            // establecemos la linea que aÒadiremos al fichero (usuario, contraseÒa, puntos y salto de linea)
            linea = usuariosList[i][0] + "," + usuariosList[i][1] + "," + puntosUsuario.get(i) + "\r\n";
            // aÒadimos a la variable string resultadoUsuarios el contenido de la nueva linea
            resultadoUsuarios += linea;
        }
        
        // aÒadimos en el fichero todo lo que hemos generado en el bucle for
        writer.print(resultadoUsuarios);
        writer.close();
        
        System.out.println("");
        System.exit(0);
    }

}
