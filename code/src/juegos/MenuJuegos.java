package juegos;

import static casino.CASINO.pantallaDefault;
import java.io.IOException;
import java.util.Scanner;

public class MenuJuegos {

    public MenuJuegos() throws IOException, InterruptedException {
        int opcionMenu = 0;
        Scanner sc = new Scanner(System.in);
//        
//
//        // Mientras que la opcion del meno de juegos no sea 5 (salir del programa), se repite infinitamente el codigo contenido dentro
//        while (opcionMenu != 5) {
//            pantallaDefault();
//           
//            System.out.println("\n\t PUNTOS: " + puntosPendientes.get(0));
//            System.out.println("\n\t\t\t\t           1. DADOS");
//            System.out.println("\n\t\t\t\t           2. BINGO");
//            System.out.println("\n\t\t\t\t           3. RULETA");
//            System.out.println("\n\t\t\t\t           4. BLACKJACK");
//            System.out.println("\n\t\t\t\t           5. SALIR");
//            System.out.print("\n\n\tQUE QUIERE HACER: ");
//
//            sc.next();
//
//            // switch para desviar a los metodos de los juegos
//            switch (opcionMenu) {
//                // DADOS
//                case 1:
//                    // creamos una instancia de la clase Dados para poder jugar a los dados
//                    // mandamos el arraylist de los puntos para poder llevar un conteo jugando al juego
//                    Dados dados = new Dados(puntosPendientes);
//
//                    // llamamos a la funcion dados() que ejecuta el juego
//                    dados.dados();
//                    break;
//
//                // BINGO
//                case 2:
//                    // creamos una instancia de la clase Bingo (mandamos los puntos a la instancia)
//                    Bingo bingo = new Bingo(puntosPendientes);
//
//                    // llamamos a la clase que ejecuta el juego del bingo
//                    bingo.bingo();
//                    break;
//
//                // RULETA
//                case 3:
//                    // creamos una instancia de la clase Ruleta
//                    // mandamos el arraylist de puntosPendientes como argumento para el constructor de la clase
//                    Ruleta ruleta = new Ruleta(puntosPendientes);
//
//                    // llamamos a la funcion que ejecuta el juego de la ruleta
//                    ruleta.ruleta();
//                    break;
//
//                // BLACKJACK
//                case 4:
//                    // creamos una instancia de la clase Blackjack mandando el array de puntos
//                    Blackjack bj = new Blackjack(puntosPendientes);
//
//                    // llamamos al metodo de la clase que ejecuta todo el juego
//                    bj.apuestaBlackjack();
//                    break;
//
//                // SALIR
//                case 5:
//                    // enviamos un mensaje final por pantalla
//                    pantallaDefault();
//                    System.out.println("\n\t\t\t\t   HASTA PRONTO, " + user + "\n");
//                    Thread.sleep(3000);
//
//                    // mandamos un codigo de salida del programa
//                    System.exit(0);
//                    break;
//            }
//        }
    }
}
