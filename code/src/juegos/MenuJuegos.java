package juegos;

import static casino.CASINO.pantallaDefault;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class MenuJuegos {

    static String user = "";

    public MenuJuegos() throws IOException, InterruptedException, SQLException {
        user = casino.CASINO.getUser();
        int opcionMenu = 0;
        Scanner sc = new Scanner(System.in);
        int puntos = actualizarPuntos(user);
        boolean puntosCorrectos = false;

        if (puntos == 0) {
            String masPuntos = "";
            pantallaDefault();
            System.out.print("\n\n\tTE HAS QUEDADO SIN PUNTOS\n\tQUIERES ANADIR MAS PUNTOS (s/n): ");

            masPuntos = sc.next();

            if (masPuntos.equals("s") || masPuntos.equals("n") || masPuntos.equals("S") || masPuntos.equals("N")) {
                while (true) {
                    if (puntosCorrectos) {
                        break;
                    }

                    puntosCorrectos = false;

                    pantallaDefault();
                    System.out.print("\n\n\tCUANTOS PUNTOS QUIERES (max. 3000): ");

                    puntos = sc.nextInt();

                    if (puntos <= 3000 && puntos > 0) {
                        Connection connection = casino.CASINO.crearConexion();
                        Statement statement = casino.CASINO.crearStatement(connection);

                        String sentencia = "UPDATE puntos SET puntos = " + puntos + " WHERE id = (SELECT id FROM usuarios WHERE usuario = '" + user + "');";
                        statement.executeQuery(sentencia);

                        statement.close();
                        connection.close();

                        puntosCorrectos = true;
                    }
                }
            }
        }

        // Mientras que la opcion del meno de juegos no sea 5 (salir del programa), se repite infinitamente el codigo contenido dentro
        while (opcionMenu != 5) {
            puntos = actualizarPuntos(user);

            if (puntos == 0) {
                break;
            }

            pantallaDefault();

            System.out.println("\n\t PUNTOS: " + puntos);
            System.out.println("\n\t\t\t\t           1. DADOS");
            System.out.println("\n\t\t\t\t           2. BINGO");
            System.out.println("\n\t\t\t\t           3. RULETA");
            System.out.println("\n\t\t\t\t           4. BLACKJACK");
            System.out.println("\n\t\t\t\t           5. SALIR");
            System.out.print("\n\n\tQUE QUIERE HACER: ");

            try {
                opcionMenu = sc.nextInt();
            } catch (Exception e) {
                System.out.println("\n\n\tINTRODUCE UN VALOR CORRECTO");
            }

            // switch para desviar a los metodos de los juegos
            switch (opcionMenu) {
                // DADOS
                case 1:
                    // creamos una instancia de la clase Dados para poder jugar a los dados
                    // mandamos el arraylist de los puntos para poder llevar un conteo jugando al juego
                    Dados dados = new Dados(puntos);

                    // llamamos a la funcion dados() que ejecuta el juego
                    dados.dados();
                    break;

                // BINGO
                case 2:
                    // creamos una instancia de la clase Bingo (mandamos los puntos a la instancia)
                    Bingo bingo = new Bingo(puntos);

                    // llamamos a la clase que ejecuta el juego del bingo
                    bingo.bingo();
                    break;

                // RULETA
                case 3:
                    // creamos una instancia de la clase Ruleta
                    // mandamos el arraylist de puntosPendientes como argumento para el constructor de la clase
                    Ruleta ruleta = new Ruleta(puntos);

                    // llamamos a la funcion que ejecuta el juego de la ruleta
                    ruleta.ruleta();
                    break;

                // BLACKJACK
                case 4:
                    // creamos una instancia de la clase Blackjack mandando el array de puntos
                    Blackjack bj = new Blackjack(puntos);

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
                default:
                    pantallaDefault();
                    System.out.println("\n\t\t\t\t   INTRODUCE UNA OPCION DISPONIBLE\n");
                    Thread.sleep(3000);
                    break;
            }
        }
    }

    public static int actualizarPuntos(String usuario) throws InterruptedException, IOException {
        Connection connection = casino.CASINO.crearConexion();
        Statement statement = casino.CASINO.crearStatement(connection);
        ResultSet rs = null;
        int puntos = 0;

        try {
            String sentencia = "SELECT puntos FROM puntos WHERE id = (SELECT id from USUARIOS WHERE '" + usuario + "' = usuario);";
            rs = statement.executeQuery(sentencia);

            if (rs.next()) {
                puntos = rs.getInt("puntos");
            }

            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("\n\t\tERROR EN LA BASE DE DATOS\n");
        }

        return puntos;
    }
}
