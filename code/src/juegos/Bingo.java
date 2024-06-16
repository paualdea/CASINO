package juegos;

import static casino.CASINO.borrarPantalla;
import static casino.CASINO.pantallaDefault;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Clase que contiene los metodos para jugar al juego del bingo
 * 
 * Tiene un constructor, el metodo principal del juego, el metodo que crea los cartones, el metodo
 * para hacer la jugada y el metodo que calcula el resultado.
 * 
 * @author Pau Aldea Batista
 */
public class Bingo {
    // creamos el ArrayList que almacenara los puntos del jugador de la sesion actual
    static private int puntos = 0;
    // creamos lo necesario para que el juego pueda funcionar correctamente (ArrayList y variable booleana)
    static private ArrayList<Integer> numerosBorrar = new ArrayList<>(), indexCol = new ArrayList<>(), bombo = new ArrayList<>(), numerosBingo = new ArrayList<>(), numerosBingoCpu = new ArrayList<>(), numerosBingoUsados = new ArrayList<>();
    static boolean finBingo = false;
    
    /*
        Constructor bingo
    
        En este, asignamos al arraylist puntosel valor del arraylist que recibimos
        como parametro a la hora de crear una instancia de este objeto.
    */
    public Bingo(int puntos) {
        this.puntos = puntos;
    }
    
    /**
     * Metodo principal para el juego del bingo.
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public static void bingo() throws IOException, InterruptedException {   
        finBingo = false;

        // vaciamos todos los arraylist estaticos para poder jugar al bingo mas de una vez
        numerosBorrar.clear();
        indexCol.clear();
        bombo.clear();
        numerosBingo.clear();
        numerosBingoCpu.clear();
        numerosBingoUsados.clear();
        
        Random rd = new Random();
        Scanner sc = new Scanner(System.in);

        // creamos y definimos dos arrays bidimensionales que corresponden con el carton del usuario y del jugador CPU
        int[][] bingo = new int[9][3];
        int[][] bingocpu = new int[9][3];

        int apuesta = 0;
        boolean puntos_c, salir = false;

        // bucle while para repetir el juego hasta que perdamos o ganemos
        while (!salir) {

            // if para salir del bingo cuando acabe la partida
            if (finBingo) {
                finBingo = false;
                break;
            }

            pantallaDefault();
            System.out.println("\n\t\t\t\t\t.:BINGO:.");
            System.out.println("\t\t\t\t\t_________");

            System.out.print("\n\tQuieres jugar un carton? (s/n) ");
            String jugar = sc.next();
            
            // si no recibimos una 's' o 'S' por el escaner salimos del bingo
            if (!(jugar.equals("s") || jugar.equals("S"))) {
                salir = true;
                break;
            }

            puntos_c = false;

            // bucle while para obligar al usuario a hacer una apuesta por debajo o igual de su saldo maximo disponible
            while (!puntos_c) {
                pantallaDefault();
                System.out.println("\n\tPuntos: " + puntos);
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

                if (apuesta <= puntos && apuesta > 0) {
                    puntos -= apuesta;
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

            // ejecutamos el metodo para jugar al bingo, en este array se almacenaran todas las respuestas que recibamos
            String[] resultado = jugarBingo(bingo, bingocpu);

            // creamos las variables booleanas para determinar el resultado de la partida
            boolean ganar = false, ganar_cpu = false;

            // analizamos el contenido del array resultado obtenido en el metodo jugarBingo() y definimos que ha ocurrido en la jugada en funcion del mismo
            for (int i = 0; i < resultado.length; i++) {
                // si en el array de resultados esta ganar, ponemos ganar en true
                if (resultado[i].equals("ganar")) {
                    ganar = true;
                } 
                // si en el array de resultados esta ganarcpu, ponemos ganar_cpu en true
                else if (resultado[i].equals("ganarcpu")) {
                    ganar_cpu = true;
                }
            }

            // llamamos a la funcion definitiva para hacer el calculo de nuestro resultado
            resultadoBingo(ganar, ganar_cpu, apuesta, puntos);
        }
    }

    /**
     * Funcion para devolver un carton de bingo que se genera con un algoritmo
     *
     * @return
     */
    public static int[][] creacionCarton() {
        int[][] bingo = new int[9][3];
        Random rd = new Random();
        // constante entera para determinar el numero de casillas del carton
        final int CASILLAS = 15;
        // variables enteras para crear los numeros del carton
        int fila1, fila2, fila3, numeroFila, casillasCreadas = 0;

        // establecemos a 0 la variable contador de numeros de casillas creadas
        casillasCreadas = 0;

        // mientras que no se usen todas las casillas
        while (casillasCreadas != CASILLAS) {

            // llenamos todas las posiciones del array bidimensional con ceros
            for (int i = 0; i < bingo.length; i++) {
                Arrays.fill(bingo[i], 0);
            }

            // vaciar arraylist que almacena los numeros que se usan
            numerosBingo.clear();
            
            casillasCreadas = 0;
            fila1 = 0;
            fila2 = 4;
            fila3 = 7;

            // bucle for para recorrer las filas del carton de bingo
            for (int i = 0; i < bingo.length; i++) {
                
                // vaciar el indice de columnas y le agregamos el valor 0, 1 y 2
                indexCol.clear();
                indexCol.add(0);
                indexCol.add(1);
                indexCol.add(2);
                
                // hacemos una variable aleatorio entre el 0 y el 2
                numeroFila = rd.nextInt(3);

                // un bucle for para borrar aleatoriamente entre 0 y 2 valores de la columna
                for (int k = 0; k <= (1 - numeroFila); k++) {
                    indexCol.remove(rd.nextInt(indexCol.size()));
                }

                // un bucle for que se repite las mismas veces que el tamaño del indice
                for (int j = 0; j < indexCol.size(); j++) {

                    // variable para determinar la columna del carton en la que estamos (iteracion for)
                    int posicion = indexCol.get(j);

                    // switch para generar un numero en las filas de la columna que se hayan elegido por el programa
                    switch (posicion) {
                        // fila 1 (numero del i0 al i3)
                        case 0:
                            // si sale un 0 en la primera linea, cambiar el 0 por 1
                            if (rd.nextInt(4) + fila1 == 0) {
                                bingo[i][0] = 1;
                                numerosBingo.add(bingo[i][0]);
                            } 
                            // crear un numero del 0 al 3 y sumarle el valor de fila1 (0, 10, 20, etc)
                            else {
                                bingo[i][0] = rd.nextInt(4) + fila1;
                                numerosBingo.add(bingo[i][0]);
                            }
                            break;
                        // fila 2 (numero del i4 al i6)
                        case 1:
                            // crear un numero del 0 al 2 y sumarle el valor de fila2 (4, 14, 24, etc)
                            bingo[i][1] = rd.nextInt(3) + fila2;
                            numerosBingo.add(bingo[i][1]);
                            break;
                        // fila 3 (numero del i7 al i9)
                        case 2:
                            // crear un numero del 0 al 2 y sumarle el valor de fila3 (7, 17, 27, etc)
                            bingo[i][2] = rd.nextInt(3) + fila3;
                            numerosBingo.add(bingo[i][2]);
                            break;
                    }
                }
                
                // Sumar 10 a todas las filas (cambio de columna en el carton)
                fila1 += 10;
                fila2 += 10;
                fila3 += 10;
                // sumar al contador de casillas creadas las que hemos creado en esta columna
                casillasCreadas += indexCol.size();
            }
        }

        // devolver array bingo al metodo principal del bingo
        return bingo;
    }

    /**
     * Juega con los dos cartones verificando sus valores.
     * Gana el primero que vacie el carton antes de que se termine el bombo
     * 
     * En caso de que los dos ganen al mismo tiempo o que se vacie el bombo se queda en empate
     * 
     * Recibe como argumentos los dos arrays (carton bingo y carton bingocpu)
     * @param bingo
     * @param bingocpu
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static String[] jugarBingo(int[][] bingo, int[][] bingocpu) throws IOException, InterruptedException {
        // variables y objetos que usaremos durante el transcurso del juego
        Random rd = new Random();
        int numero = 0;
        boolean ganar_cpu = false, perdido = false, ganar = false, finalBingo = false, finalBingocpu = false;
        ArrayList<String> resultado = new ArrayList<>();
        
        // bucle infinito que se rompe cuando acaba el juego actual
        while (true) {
            finalBingo = false;
            borrarPantalla();
            System.out.println(casino.CASINO.tituloCorto);

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
            } catch (Exception e) {}

            // imprimimos la cantidad de bolas restantes en el bingo
            System.out.println("\n\n\tBOMBO --> " + bombo.size());

            // espera entre bolas (ajustar al gusto)
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
                        finalBingo = true;
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
            if (finalBingocpu) {
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
                        finalBingocpu = true;
                    }
                    for (int k = 0; k < numerosBorrar.size(); k++) {
                        if (numerosBorrar.get(k) == bingocpu[j][i]) {
                            bingocpu[j][i] = 0;
                        }
                    }
                }
            }

            // la condicion se da si el jugador ha hecho bingo y la CPU ha tenido un turno mas
            if (finalBingo) {
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
        // devolvemos el array que contiene los resultados de la jugada
        return resultadoArray;
    }

    /**
     * Aqui se calcula el beneficio obtenido por el resultado de la jugada del
     * bingo
     * 
     * Recibe como parametros los puntos del usuario, la apuesta que ha realizado y si ha ganado alguien
     * @param ganar
     * @param ganar_cpu
     * @param apuesta
     * @param puntos
     * @throws IOException
     * @throws InterruptedException
     */
    public static void resultadoBingo(boolean ganar, boolean ganar_cpu, int apuesta, int puntos) throws IOException, InterruptedException {
        // si ganan el ordenador y el jugador hay un empate
        if (ganar_cpu && ganar) {
            // se devuelve al usuario el 80 por ciento del valor apostado
            apuesta *= 0.8;
            // actualizamos los puntos
            puntos += apuesta;

            pantallaDefault();
            System.out.println("\n\n\t\t\t    EMPATE, AHORA TIENES " + puntos + " PUNTOS");
            Thread.sleep(2500);
        } 
        // si gana el jugador...
        else if (ganar && !ganar_cpu) {
            // multiplicamos lo apostado por 2
            apuesta *= 2;
            // actualizamos los puntos
            puntos += apuesta;

            pantallaDefault();
            System.out.println("\n\n\t\t\t    HAS GANADO, AHORA TIENES " + puntos + " PUNTOS");
            Thread.sleep(2500);
        } 
        // si gana el ordenador...
        else {
            pantallaDefault();
            System.out.println("\n\n\t\t\t    HAS PERDIDO, TE QUEDAN " + puntos + " PUNTOS");
            Thread.sleep(2500);
        }
        // establecemos la variable para salir del juego en true
        finBingo = true;
    }
}
