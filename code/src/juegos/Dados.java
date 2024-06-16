package juegos;

import static casino.CASINO.borrarPantalla;
import static casino.CASINO.pantallaDefault;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Scanner;

/**
 * Clase que contiene todos los metodos para ejecutar el juego de los dados
 *
 * @author Pau Aldea Batista
 */
public class Dados {

    // arraylist que contendra los puntos del jugador
    private static int puntos = 0;
    static String user = casino.CASINO.getUser();

    /*
        Constructor de la clase Dados que recibe los puntos 
        del jugador como parametro a la hora de crear una instancia
     */
    public Dados(int puntosPendientes) {
        this.puntos = puntosPendientes;
    }

    /**
     * Metodo principal del juego de los Dados
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public static void dados() throws IOException, InterruptedException, SQLException {
        // objetos y variables necesarias para la ejecucion del juego
        Scanner sc = new Scanner(System.in);
        boolean ganar = false;
        int n = 0, apuesta = 0;

        // array con los dibujos de todos los Dados del 1 al 6
        String[] caraDado = {
            "\t\t\t\t\t _____________\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|      O      |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|_____________|",
            "\t\t\t\t\t _____________\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|  O          |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|          O  |\n" + "\t\t\t\t\t|_____________|",
            "\t\t\t\t\t _____________\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|  O          |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|      O      |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|          O  |\n" + "\t\t\t\t\t|_____________|",
            "\t\t\t\t\t _____________\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|  O       O  |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|  O       O  |\n" + "\t\t\t\t\t|_____________|",
            "\t\t\t\t\t _____________\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|  O       O  |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|      O      |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|  O       O  |\n" + "\t\t\t\t\t|_____________|",
            "\t\t\t\t\t _____________\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|  O       O  |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|  O       O  |\n" + "\t\t\t\t\t|             |\n" + "\t\t\t\t\t|  O       O  |\n" + "\t\t\t\t\t|_____________|",};

        // mientras que la variable booleana sea false, todo el codigo se repite infinitamente
        while (!ganar) {
            // mientras que el numero introducido no sea entre 2 y 12 se repite todo
            do {
                borrarPantalla();

                // si te quedas sin puntos se sale del while
                if (puntos == 0) {
                    ganar = true;
                }

                // ponemos el valor de n a cero por si volvemos a jugar, asi evitando errores de lectura de la variable
                n = 0;

                System.out.println(casino.CASINO.titulo);
                System.out.print(caraDado[4]);
                System.out.println("\n\n\t\t\t\t\t   .:DADOS:.");
                System.out.println("\n\tPuntos: " + puntos);
                System.out.print("\n\tEscoge un numero (2 - 12) (14 para salir): ");

                // estructura de control de errores para prevenir finalizaciones en la ejecucion del codigo
                try {
                    n = sc.nextInt();
                } catch (Exception e) {
                    sc.next();
                    pantallaDefault();
                    System.out.println("\n\tESCRIBE UN VALOR CORRECTO");
                    Thread.sleep(1750);
                }

                // si la entrada del escaner es 14 salimos del programa
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

            // si ganamos salimos del bucle while
            if (ganar == true) {
                break;
            }

            System.out.println("\n\t_______________________________________________");

            // bucle do while para obligar al usuario a hacer una apuesta inferior o igual al saldo total disponible
            do {
                pantallaDefault();
                System.out.println("\n\tPuntos: " + puntos);
                System.out.print("\n\tApuesta: ");

                // estructura de control de errores
                try {
                    apuesta = sc.nextInt();
                } catch (Exception e) {
                    sc.nextLine();
                    pantallaDefault();
                    System.out.println("\n\n\tINTRODUCE UN VALOR CORRECTO\n");
                    Thread.sleep(850);
                }

            } while (!(apuesta <= puntos && apuesta > 0));

            // restamos a los puntos del usuario la cantidad apostada
            puntos -= apuesta;

            try {
                Connection connection = casino.CASINO.crearConexion();
                Statement statement = casino.CASINO.crearStatement(connection);
                
                String sentencia = "UPDATE puntos SET puntos = " + puntos + " WHERE id = (SELECT id FROM usuarios WHERE usuario = '" + user + "');;";
                statement.executeUpdate(sentencia);
                
                connection.close();
                statement.close();
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("\n\t ERROR CON LA BASE DE DATOS");
            }

            // se llama al metodo para obtener una puntuacion en relacion a la apuesta realizada
            int puntosAdicionales = tirarDados(caraDado, n, apuesta);

            // sumamos los puntos ganados
            puntos += puntosAdicionales;

            apuesta = 0;

            // si nos quedamos sin puntos salimos del bucle while
            if (puntos == 0) {
                ganar = true;
            }
        }
    }

    /**
     * Este metodo sirve para generar la tirada de los Dados que da un valor
     * resultante de entre 2 y 12. Recibe el array caraDado para mostrar el
     * valor de los Dados graficamente
     *
     * @param caraDado
     * @param n
     * @param apuesta
     * @return
     * @throws InterruptedException
     */
    public static int tirarDados(String[] caraDado, int n, int apuesta) throws InterruptedException {
        // generamos el valor de los 2 dados y lo sumamos
        Random rd = new Random();
        int dado1 = rd.nextInt(6) + 1;
        int dado2 = rd.nextInt(6) + 1;
        int resultado = dado1 + dado2;

        // imprimimos la imagen del array de caraDado en una posicion menos del valor del dado
        System.out.println("\n\t     DADO 1\n" + caraDado[dado1 - 1] + "\n\n\t     DADO 2\n" + caraDado[dado2 - 1]);
        Thread.sleep(3500);

        // estructura de condicionales para comprobar el resultado de la tirada y su respectiva recompensa
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

        // devuelve el valor total de la apuesta tras la tirada al metodo principal de los Dados
        return apuesta;
    }
}
