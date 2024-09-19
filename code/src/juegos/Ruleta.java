package juegos;

import static casino.CASINO.borrarPantalla;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase que contiene los atributos y metodos para ejecutar el juego de la ruleta
 * 
 * @author Pau Aldea Batista
 */
public class Ruleta {
    // Creamos el arraylist que guardara los puntos del usuario
    private static int puntos = 0;
    private static Scanner sc = new Scanner(System.in);
	
    // importamos el nombre del usuario de la sesion actual
    static String user = casino.CASINO.getUser();
    
    // Constructor que recibe los puntos del jugador
    public Ruleta(int puntosPendientes){
        this.puntos = puntosPendientes;
    }
    
    /**
     * Metodo principal del juego de la ruleta
     *
     * @throws InterruptedException
     * @throws IOException
     */
    public static void ruleta() throws InterruptedException, IOException {
        // Todos los objetos y variables que usamos para la correcta ejecucion del juego de la ruleta
        ArrayList<Integer> numeros = new ArrayList<>(), apuestas_a = new ArrayList<>(), apuestas_b = new ArrayList<>();
        ArrayList<String> listaApuestas = new ArrayList<>();
        boolean apuesta_c = false, tipo = false, ganar = false;
        // variables para seleccionar las opciones de apuestas en el juego
        int opcion = 0, num = 0, ron = 0, poi = 0, mit = 0, doc = 0, fila = 0, apuesta_n = 0, opcion_r = 0;
        int apuesta_ron = 0, apuesta_poi = 0, apuesta_mit = 0, apuesta_doc = 0, apuesta_fila = 0, rr_ron = 0, rr_fila = 0, lastnum = 0, puntos_aux = puntos;
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
            if (puntos == 0) {
                pantallaDefault();
                System.out.println("\n\n\tTe has quedado sin puntos, adios.\n");
                Thread.sleep(2500);
                ganar = true;
            }

            // si la variable de puntos_aux es mejor a nuestros puntos actuales, significa que hemos ganado, por lo que queremos mostrar cuanto hemos ganado
            if (puntos_aux < puntos) {
                pantallaDefault();
                System.out.println("\n\t\t\t\t_________________________________");
                System.out.println("\n\t\t\t\t     HAS GANADO " + (puntos - puntos_aux) + " PUNTOS");
                Thread.sleep(2500);
            }
            
            // si seleccionamos 8 salimos del juego
            if (opcion == 8) {
                ganar = true;
            }

            opcion = 0;

            // mientras que en el menu no seleccionemos la opcion 7 (confirmar apuesta), repetir el bucle infinitamente
            while (opcion != 7) {
                // llamamos al metodo pantallaRuleta() para mostrar por pantalla todo lo necesario para jugar
                pantallaRuleta(tabla, lastnum, numeros, listaApuestas, puntos);

                // si se tienen 0 puntos confirmar la apuesta y salir, en caso contrario, seguir con el programa
                if (puntos == 0) {
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
                    
                    // NUMERO EQUIVOCADO DE OPCION
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

            // una vez confirmada la apuesta, restamos los puntos apostados de la base de datos del usuario
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

            // si algun numero que esta en el array rojo[] coindide con el numero que hemos escogido para la apuesta
            for (int i = 0; i < rojo.length; i++) {
                if (rojo[i] == numero) {
                    // si coincide, se pone rr_ron a 1, lo que significa rojo, en caso contrario, se queda a 0 (negro)
                    rr_ron = 1;
                    break;
                }
            }

            // si acertamos el color...
            if ((rr_ron == 0 && ron == 2) || (rr_ron == 1 && ron == 1)) {
                apuesta_ron *= 2;
            } else {
                apuesta_ron *= 0;
            }

            // si acertamos par o impar...
            if ((numero % 2 == 0 && poi == 1) || (numero % 2 == 1 && poi == 2)) {
                apuesta_poi *= 2;
            } else {
                apuesta_poi *= 0;
            }

            // si acertamos la mitad...
            if (((numero >= 1 && numero <= 18) && mit == 1) || ((numero >= 19 && numero <= 36) && mit == 2)) {
                apuesta_mit *= 2;
            } else {
                apuesta_mit *= 0;
            }

            // si acertamos la docena...
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

            // si acertamos la fila...
            if ((rr_fila == 1 && fila == 1) || (rr_fila == 2 && fila == 2) || (rr_fila == 3 && fila == 3)) {
                apuesta_fila *= 3;
            } else {
                apuesta_fila *= 0;
            }

            // asignar a puntos_aux los puntos actuales del usuario antes de sumarle las apuestas y sus resultados
            puntos_aux = puntos;

            // anadir los puntos resultantes al arraylist de puntos
            puntos += apuesta_n + apuesta_ron + apuesta_poi + apuesta_mit + apuesta_doc + apuesta_fila;
			
			// actualizamos de nuevo los puntos en la BD
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
			
            // establecer a 0 las apuestas
            apuesta_n = 0; apuesta_ron = 0; apuesta_poi = 0; apuesta_mit = 0; apuesta_doc = 0; apuesta_fila = 0;
        }
    }

    /**
     * Metodo para confirmar las apuestas sobre las diferentes opciones de la ruleta
     * 
     * Recibe como atributos los puntos, el tipo de apuesta y las opciones que hemos seleccionado
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
    public static boolean apuestaRuleta(String tipoApuesta, int puntos, ArrayList<String> listaApuestas, int num, int ron, int poi, int mit, int doc, int fila, ArrayList<Integer> apuestas_a, ArrayList<Integer> apuestas_b) throws IOException, InterruptedException {
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
                System.out.println("\n\tPUNTOS: " + puntos);
            }
        }

        // dependiendo del origen de la apuesta, se verifica un tipo de apuesta u otro. Posteriormente, se aÃ±ade la apuesta confirmada a la lista de apuestas
        switch (tipoApuesta) {
            // APUESTA NUMERO
            case "numero":
                // si puntos cumple la condicion del metodo comprobacionpuntos, entonces anadir el valor a los arraylist que guardan las apuestas
                if (comprobacionPuntos(puntos, apuesta)) {
                    listaApuestas.add("Numero " + num + " --> Apuesta de " + apuesta + " puntos");
                    apuestas_a.add(apuesta);
                    apuestas_b.add(1);
                    return true;
                }

                return false;
                
            // APUESTA ROJO O NEGRO
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
            
            // APUESTA PARES O IMPARES
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
             
            // APUESTA MITADES
            case "mitad":
                if (comprobacionPuntos(puntos, apuesta)) {
                    listaApuestas.add("Mitad " + mit + " --> Apuesta de " + apuesta + " puntos");
                    apuestas_a.add(apuesta);
                    apuestas_b.add(4);
                    return true;
                }
                return false;
             
            // APUESTA DOCENAS
            case "docenas":
                if (comprobacionPuntos(puntos, apuesta)) {
                    listaApuestas.add("Docena " + doc + " --> Apuesta de " + apuesta + " puntos");
                    apuestas_a.add(apuesta);
                    apuestas_b.add(5);
                    return true;
                }
                return false;
            
            // APUESTA FILAS
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
     * Metodo de comprobacion de puntos para verificar que la apuesta es menor o
     * igual al saldo total que tenemos
     *
     * @param puntos
     * @param apuesta
     * @return
     */
    public static boolean comprobacionPuntos(int puntos, int apuesta) {
        // si la acpuesta es menor o igual a nuestro saldo maximo y es mayor a 0...
        if (apuesta <= puntos && apuesta > 0) {
            puntos -= apuesta;			
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
    public static void pantallaRuleta(String tabla, int lastnum, ArrayList<Integer> numeros, ArrayList<String> listaApuestas, int puntos) throws IOException, InterruptedException {
        borrarPantalla();
        System.out.println(tabla);
        System.out.println("\n\tNumeros anteriores: " + numeros);
        System.out.println("\n\tUltimo numero: " + lastnum);
        System.out.println("\n\tLista de apuestas:\n\t" + listaApuestas);
        System.out.println("\n\tPuntos: " + puntos);
        System.out.println("\t__________________________________");
    }
    
    /**
     * Metodo para borrar pantala y mostrar el titulo del CASINO principal
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public static void pantallaDefault() throws IOException, InterruptedException {
        borrarPantalla();
        System.out.println(casino.CASINO.titulo);
    }
}
