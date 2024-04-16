package juegos;

import static casino.CASINO.borrarPantalla;
import static casino.CASINO.pantallaDefault;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Clase para el juego del Blackjack.
 * 
 * Contiene un constructor y los metodos en los que se apuesta, se juega, se crea
 * la baraja, se reparten las cartas, se calcula la puntuacion de cada mano y se
 * determina el ganador de la jugada
 * 
 * @author Pau Aldea Batista
 */
public class Blackjack {
    // se crea un arraylist que recibira los puntos de la clase principal CASINO
    private static ArrayList<Integer> puntos = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);
    
    // Constructor clase Blackjack que recibe el arraylist de puntos del jugador
    public Blackjack(ArrayList<Integer> puntos){
        this.puntos = puntos;
    }
    
    /**
     * Metodo para la apuesta del blackjack, se hace la jugada de blackjack y se
     * recibe la puntuacion total en relacion a la apuesta
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public static void apuestaBlackjack() throws IOException, InterruptedException {
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
        String jug_s;
        
        // si nos quedamos a 0 puntos, salir del juego
        if (puntos.get(0) == 0) {
            jug_s = "n";
        } else {
            jug_s = sc.next();
        }
        
        // si la respuesta es 's' o 'S' jugamos otra vez
        if (jug_s.equals("s") || jug_s.equals("S")) {
            apuestaBlackjack();
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

        // creamos los arraylists que almacenaran las jugadas del jugador y dealer
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

                // bucle for para mostrar toda la jugada del jugador
                for (int i = 0; i < jugadaPlayer.size(); i++) {
                    System.out.println(jugadaPlayer.get(i));
                }

                // mostramos las cartas del dealer
                System.out.println("\n\t\t\t\t       .:DEALER:.\n\n" + jugadaDealer.get(0) + "\t\t\t\t\t.-------.\n\t\t\t\t\t|/|/|/|/|\n\t\t\t\t\t|/|/|/|/|\n\t\t\t\t\t|/|/|/|/|\n\t\t\t\t\t'-------'\n");

                System.out.print("\n\tPEDIR CARTA (1) O PLANTARSE (2): ");
                
                // estructura de control de errores para asegurarnos que recibimos un int por el Scanner
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
                // llamamos a la funcion repartirCarta para el jugador
                repartirCarta(jugadaPlayer, baraja);
                borrarPantalla();
                System.out.println("\n\n\t\t\t\t       .:PLAYER:.\n");
                
                // si la puntuacion de la mano es superior a 21...
                if (puntuacionMano(jugadaPlayer) > 21) {
                    System.out.println("\t\t\t\t      TE HAS PASADO");
                } else {
                    // en caso de que no se pase, mostrar todas las cartas de la jugada actual
                    for (int i = 0; i < jugadaPlayer.size(); i++) {
                        System.out.println(jugadaPlayer.get(i));
                    }
                }
                
                // mostrar la jugada del dealer
                System.out.println("\n\t\t\t\t       .:DEALER:.\n\n" + jugadaDealer.get(0) + "\t\t\t\t\t.-------.\n\t\t\t\t\t|/|/|/|/|\n\t\t\t\t\t|/|/|/|/|\n\t\t\t\t\t|/|/|/|/|\n\t\t\t\t\t'-------'\n");
                Thread.sleep(1500);
            } 
            // si seleccionamos 2, nos plantamos
            else if (accion == 2) {
                break;
            }
        }

        // una vez finalizemos nuestra jugada, le toca al dealer sacar cartas hasta que llegue a una puntuacion minima de 17
        while (puntuacionMano(jugadaDealer) < 17) {
            // iremos llamando al metodo repartirCarta para el dealer hasta que se cumpla la condicion
            repartirCarta(jugadaDealer, baraja);
        }
        
        // mostramos toda la jugada del dealer
        borrarPantalla();
        System.out.println("\n\t\t\t\t       .:DEALER:.\n");
        for (int i = 0; i < jugadaDealer.size(); i++) {
            System.out.println(jugadaDealer.get(i));
        }
        Thread.sleep(1500);

        // creamos la variable que determina lo que ha ocurrido en la partida tras llamar al metodo determinarGanador()
        int resultado = determinarGanador(jugadaPlayer, jugadaDealer);

        // dependiendo del resultado devolvemos segun que valores
        switch (resultado) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
        }
        
        // de forma predeterminada devolvemos un 2 (empate)
        return 2;
    }

    /**
     * metodo que devuelve un arraylist con todas las cartas del mazo con sus
     * respectivos dibujos, palos y valores
     *
     * @return
     */
    public static ArrayList<String> crearBaraja() {
        // creamos todo lo necesario para crear nuestra baraja de cartas
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
     * Metodo para repartir una carta al que la pida (usuario o dealer). 
     * En este caso, se agrega a la mano la carta que se quita de la baraja
     *
     * Recibe como parametros la mano del que pide carta y la baraja de cartas
     * 
     * @param mano
     * @param baraja
     */
    public static void repartirCarta(ArrayList<String> mano, ArrayList<String> baraja) {
        // quita la primera carta de la baraja (previamente mezcladas)
        String carta = baraja.remove(0);
        // se agrega a la mano la carta que se ha repartido
        mano.add(carta);
    }

    /**
     * Metodo para determinar el valor de la mano que se ha jugado. 
     * Se usa para calcular el valor de las manos de ambos jugadores
     *
     * Recibe la mano del jugador que hace la peticion de calculo
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
        
        // devolvemos la puntuacion de la mano calculada
        return puntuacion;
    }

    /**
     * Metodo para determinar el ganador recibiendo como atributo la jugada del
     * jugador y la del dealer
     *
     * @param jugadaPlayer
     * @param jugadaDealer
     * @return
     * @throws InterruptedException
     */
    public static int determinarGanador(ArrayList<String> jugadaPlayer, ArrayList<String> jugadaDealer) throws InterruptedException {
        // calculamos las puntuaciones del jugador y dealer llamando al metodo puntuacionMano()
        int resultado = 0;
        int puntuacionJugador = puntuacionMano(jugadaPlayer);
        int puntuacionCasa = puntuacionMano(jugadaDealer);

        // si el jugador se pasa de 21 pierde
        if (puntuacionJugador > 21) {
            System.out.println("\t\t\t\t      TE HAS PASADO");
            resultado = 0;
            
        } 
        // si el dealer se pasa o el jugador supera al dealer sin pasarse de 21, se gana tambien
        else if (puntuacionCasa > 21 || puntuacionJugador > puntuacionCasa) {
            System.out.println("\t\t\t\t       HAS GANADO");
            resultado = 1;
        } 
        // si el dealer tiene mas puntos que el jugador sin pasarse de 21, gana el dealer
        else if (puntuacionJugador < puntuacionCasa) {
            System.out.println("\t\t\t\t     EL DEALER GANA");
            resultado = 0;
        }
        // en cualquier otro caso, hay empate
        else {
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
}
