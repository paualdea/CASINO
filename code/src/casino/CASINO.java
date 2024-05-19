package casino;

import datos.Sistema_ficheros;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    private ResultSet resultSet = null;
    private Connection connection = null;
    private Statement statement = null;
    private final String url = "jdbc:mysql://localhost:3306/casino";
    private final String userBD = "root";
    private final String passwdBD = "";

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

    public void setPuntos(int puntos, String user) throws SQLException {
        try {
            connection = DriverManager.getConnection(url, userBD, passwdBD);
            statement = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(CASINO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String sentencia = "UPDATE puntos SET puntos = " + puntos + " WHERE id_usuario = (SELECT id FROM usuarios WHERE username = '" + user + "')";
        statement.executeUpdate(sentencia);
    }
    
    public int getPuntos(String user) throws SQLException{
        int puntos = 0;
        String sentencia = "";
        
        try {
            connection = DriverManager.getConnection(url, userBD, passwdBD);
            statement = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(CASINO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        sentencia = "SELECT puntos FROM puntos WHERE id_usuario = (SELECT id FROM usuarios WHERE username = '" + user + "')";
        resultSet = statement.executeQuery(sentencia);
        
        if (resultSet.next()) {
            puntos = resultSet.getInt("puntos");
            System.out.println(puntos);
        }
        
        return puntos;
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