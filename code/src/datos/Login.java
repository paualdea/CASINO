package datos;

import static casino.CASINO.borrarPantalla;
import static casino.CASINO.pantallaDefault;
import static casino.CASINO.puntosUsuario;
import static casino.CASINO.usuariosList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Clase Login. Contiene todo el codigo para manejar el inicio y registro de usuarios en nuestro programa.
 * Solo contiene un metoto (usuarios)
 * 
 * @author Pau Aldea Batista
 */
public class Login {
    /**
     * Metodo para administrar usuarios (registro, inicio de sesion, etc.).
     * Recibe como parametros un string que contiene la accion que queremos hacer y
     * el ArrayList que contiene los puntos de juego.
     * 
     * @param accion
     * @param puntos
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static boolean usuarios(String accion, ArrayList<Integer> puntos) throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        String user, passwd, passwd_aux;
        
        // estructura de seleccion switch que evalua el contenido del string accion
        switch (accion) {
            // si recibimos "iniciarSesion" de la clase CASINO...
            case "iniciarSesion":
                // pedimos usuario y password
                pantallaDefault();
                System.out.print("\n\n\t\t\t\t   USUARIO: ");
                user = sc.next();
                System.out.print("\n\t\t\t\t   CONTRASENA: ");
                passwd = sc.next();
                int nuevosPuntos = 0;

                // Se recorre todo el array de usuarios para ver si algun registro coincide
                for (int i = 0; i < usuariosList.length; i++) {
                    // Si el usuario y contrasenas introducidos mediante escaner coinciden en la fila de la iteracion actual...
                    if (user.equals(usuariosList[i][0]) && passwd.equals(usuariosList[i][1])) {
                        pantallaDefault();
                        System.out.println("\n\t\t\t\tHAS INICIADO SESION COMO " + user);
                        casino.CASINO.setUser(user);
                        
                        // se vacia al arraylist para llevar los puntos actualizados
                        puntos.clear();

                        // si el usuario no tiene puntos se le da la posibilidad de anadir mas (max 3000)
                        if (puntosUsuario.get(i) == 0) {
                            System.out.print("\n\tTE HAS QUEDADO SIN PUNTOS, QUIERES AGREGAR MAS (S/N): ");
                            String opcion = sc.next();
                            
                            // si la opcion es 's' o 'S', entonces...
                            if (opcion.equals("s") || opcion.equals("S")) {
                                // bucle infinito que se rompe cuando introducimos un valor de puntos correcto
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
                                // si no anade mas, se le asignan 0 puntos, lo que cierra el programa definitivamente
                                puntos.add(puntosUsuario.get(i));
                            }
                        } else {
                            // si el usuario tiene puntos se le asignan los que estan ya en su pertenencia
                            puntos.add(puntosUsuario.get(i));
                        }
                        
                        // hacemos una pausa de 1,25 segundos
                        Thread.sleep(1250);
                        return true;
                    }
                }

                // si ningun usuario coincide, se devuelte un false
                return false;
                
            // en el caso de elegir REGISTRO...
            case "registro":
                int ingreso = 0;
                boolean usuarioNuevo = false, puntosEstablecidos = false;

                pantallaDefault();
                // se piden usuario y password
                System.out.print("\n\n\t\t\t\tNOMBRE USUARIO: ");
                user = sc.next();
                System.out.print("\n\t\t\t\tCONTRASENA: ");
                passwd = sc.next();
                System.out.print("\n\t\t\t\tCONFIRMAR CONTRASENA: ");
                passwd_aux = sc.next();
                
                // el programa se mantiene en este bucle while siempre que el usuario no este registrado
                // si el usuario ya esta registrado, te saca del bucle y retorna un false
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

                // si la password y la confirmacion de la password son iguales, se desvia al if para agregar dinero a la cuenta
                if (passwd.equals(passwd_aux)) {
                    /* 
                        bucle while para establecer puntos de usuario nuevo, si la cantidad pasa de 3000, se vuelve a pedir una cantidad hasta que esta
                        sea inferior de 3000 y mayor a 0
                     */
                    while (!puntosEstablecidos) {
                        while (true) {
                            // estructura de control para asegurar que no se rompe el codigo por una mala entrada de datos
                            try {
                                borrarPantalla();
                                pantallaDefault();
                                System.out.print("\n\t\t\t   INGRESO DINERO (MAXIMO 3000): ");
                                ingreso = sc.nextInt();
                                break;
                            } catch (Exception e) {
                                sc.next();
                                borrarPantalla();
                                System.out.println(casino.CASINO.titulo + "\n\n\tINTRODUCE UN VALOR VALIDO");
                                Thread.sleep(850);
                            }
                        }

                        // Si el ingreso es menor a 3000 y superior a 0 entonces proceder
                        if (ingreso <= 3000 && ingreso > 0) {
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

                    // Se añaden a la ultima fila del array el usuario y contrasena que hemos especificado en el registro
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
}