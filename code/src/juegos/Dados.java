package juegos;

import casino.CASINO;
import gui.Main;
import java.io.IOException;
import java.util.Random;

/**
 * Clase que contiene todos los metodos para ejecutar el juego de los dados
 *
 * @author Pau Aldea Batista
 */
public class Dados {

    private static int n, apuesta, resultado;
    private static boolean ganado = false;
    private int dado1, dado2;
    
    private static CASINO casino = Main.getCasino();

    public Dados(int n, int apuesta) {
        this.n = n;
        this.apuesta = apuesta;
    }

    /**
     * Metodo principal del juego de los Dados
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public void dados() {
        // objetos y variables necesarias para la ejecucion del juego
        ganado = false;
        casino.setGanado(ganado);
        int n = 0;

        // se llama al metodo para obtener una puntuacion en relacion a la apuesta realizada
        apuesta = tirarDados(n, apuesta);
        casino.setGanado(ganado);
        
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
    public int tirarDados(int n, int apuesta) {
        // generamos el valor de los 2 dados y lo sumamos
        Random rd = new Random();
        dado1 = rd.nextInt(6) + 1;
        dado2 = rd.nextInt(6) + 1;
        resultado = dado1 + dado2;
        System.out.println();

        // estructura de condicionales para comprobar el resultado de la tirada y su respectiva recompensa
        if (resultado == n) {
            ganado = true;
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

    public static int getApuesta() {
        return apuesta;
    }

    public static int getResultado() {
        return resultado;
    }

    public int getDado1() {
        return dado1;
    }

    public int getDado2() {
        return dado2;
    }
}
