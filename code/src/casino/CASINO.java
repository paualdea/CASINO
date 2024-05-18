package casino;

import datos.Sistema_ficheros;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * CASINO - v6.0.0
 * _________________________________________________________________________________________________________________________________________
 *
 * PENDIENTE
 *
 * @author Pau Aldea Batista
 * @author Mohammad Tufail Imran
 */
public class CASINO {
    // Variables de datos de usuario
    public static String user;
    
    private int puntos;
    private int apuesta;
    private int valorDados = 0;
    private boolean ganado = false;
    private Sistema_ficheros datos;
    static Connection connection = null;
    static Statement statement = null;

    // Metodo constructor
    public CASINO() throws IOException, SQLException {
        datos = new Sistema_ficheros();
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        CASINO.user = user;
    }

    public void setPuntos(int puntos, String user) {
        // COMPLETAR ESTO
    }
    
    public int getPuntos(String user){
        // COMPLETAR ESTO
        return 0;
    }

    public int getApuesta() {
        return apuesta;
    }

    public void setApuesta(int apuesta) {
        this.apuesta = apuesta;
    }   

    public int getValorDados() {
        return valorDados;
    }

    public void setValorDados(int valorDados) {
        this.valorDados = valorDados;
    }

    public boolean isGanado() {
        return ganado;
    }

    public void setGanado(boolean ganado) {
        this.ganado = ganado;
    }  
}