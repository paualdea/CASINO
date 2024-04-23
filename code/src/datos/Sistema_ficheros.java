package datos;

import casino.CASINO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Clase que contiene todo el sistema de ficheros que implementamos a partir de la version 3.0.0
 * en nuestro programa.
 * 
 * Contiene un constructor que se encarga de crear todo el sistema de ficheros una vez instanciamos
 * la clase. 
 * Tambien tiene un metodo que sirve para actualizar los valores de todos los usuarios del
 * programa en el fichero que tenemos creado.
 * 
 * @author Pau Aldea Batista
 */
public class Sistema_ficheros {
    // Importamos variables de la clase CASINO
    static boolean ficheroNuevo = casino.CASINO.ficheroNuevo;
    static ArrayList<Integer> puntosUsuario = casino.CASINO.puntosUsuario;
    
    /**
     * Este metodo constructor sirve para crear e inicializar todo el sistema de ficheros
     * que hemos implementado en la v3.0.0 de este CASINO para hacer los datos
     * persistentes y recuperables en la siguiente ejecucion.
     * Una vez creamos una instancia de esta clase, se crea todo el sistema de ficheros automaticamente
     *
     */
    public Sistema_ficheros() throws IOException {
        // importamos el array bidimensional arrayList
        String[][] usuariosList = casino.CASINO.usuariosList;
        
        // uso de la clase File para determinar el directorio que contiene los ficheros que va a usar el programa para manejar usuarios y puntos
        File casino_files = new File("./data");
        // File para determinar el fichero que contiene los datos
        File usuarios = new File(casino_files, "usuarios.txt");
        // creamos una instancia de la clase BufferedReader para tener un lector que lee el fichero usuarios
        BufferedReader lectorLineas = null;

        // si la carpeta de ficheros no existe se crea el directorio.
        if (!casino_files.exists()) {
            casino_files.mkdir();
        }
        // si el fichero no existe, se crea el fichero dentro del directorio ./data
        if (!usuarios.exists()) {
            usuarios.createNewFile();
        }

        // creamos todo lo necesario para leer las lineas, partes de las lineas y procesarlas posteriormente
        Scanner lector = new Scanner(usuarios);
        String[] arrayLinea;
        String linea;

        // variable contador
        int i = 0;

        // si el fichero esta vacio crea dos usuarios predeterminados (paualdea y mohammadtufail)
        if (usuarios.length() == 0) {
            // creamos una instancia del objeto que escribe en ficheros
            PrintWriter writer = new PrintWriter(usuarios, "UTF-8");
            // metemos los usuarios default en el fichero de usuarios
            writer.print("paualdea,aldea2,12000\nmohammadtufail,tufail2,12000");
            // cerramos la herramienta
            writer.close();
            // establecemos la variable estatica ficheroNuevo en true para que no haya errores luego a la hora de leer estos nuevos usuarios
            ficheroNuevo = true;
        } 
        // si el fichero de usuarios no esta vacio entonces...
        else {
            // mientras el fichero tenga mas lineas...
            while (lectorLineas.readLine() != null) {
                // establece la variable String linea con el contenido de la linea actual del fichero
                linea = lector.nextLine();
                // el arrayLinea almacena los elementos de la linea usando como criterio las comas
                arrayLinea = linea.split(",");

                // anadimos una fila al array
                usuariosList = Arrays.copyOf(usuariosList, usuariosList.length + 1);
                usuariosList[usuariosList.length - 1] = new String[2];

                // el usuario es lo primero que hay en la linea del documento, la password lo segundo y los puntos lo tercero
                usuariosList[i][0] = arrayLinea[0];
                usuariosList[i][1] = arrayLinea[1];
                // anadimos a este arraylist los puntos del usuario de la linea actual
                puntosUsuario.add(Integer.parseInt(arrayLinea[2]));

                i++;
            }
        }
        // actualizamos los valores de estas variables en la clase principal CASINO
        CASINO.setUsuariosList(usuariosList);
        CASINO.setFicheroNuevo(ficheroNuevo);
        CASINO.setPuntosUsuario(puntosUsuario);
    }

    /**
     * Este metodo sirve para actualizar el contenido del fichero para agregar
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
    public static void actualizarFicheros(ArrayList<Integer> puntos, String[][] usuariosList) throws FileNotFoundException, IOException, InterruptedException {
        // establecemos un writer para hacer los cambios en el fichero que indicamos
        PrintWriter writer = new PrintWriter("./data/usuarios.txt", "UTF-8");
        String resultadoUsuarios = "";
        String linea = "";
        // llamamos al getter de la clase CASINO que nos da el usuario de la sesion actual de juego
        String user = casino.CASINO.getUser();
        
        // se repite este bucle for por las filas que tenga el array de usuarios de nuestro casino
        for (int i = 0; i < usuariosList.length; i++) {
            // buscamos la fila en la que se encuentra el usuario que estamos usando para poner la nueva puntuacion que tiene en el fichero
            if (usuariosList[i][0].equals(user)) {
                puntosUsuario.set(i, puntos.get(0));
            }
            // establecemos la linea que anadiremos al fichero (usuario, contrasena, puntos y salto de linea)
            linea = usuariosList[i][0] + "," + usuariosList[i][1] + "," + puntosUsuario.get(i) + "\n";
            // agregamos a la variable resultadoUsuarios el contenido de la nueva linea
            resultadoUsuarios += linea;
        }

        // anadimos en el fichero todo lo que hemos generado en el bucle for
        writer.print(resultadoUsuarios);
        // cerramos el writer
        writer.close();
        
        System.exit(0);
    }
}
