package co.edu.uniquindio.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionOracle {

    private static final String URL ="jdbc:oracle:thin:@localhost:1521/xe";
    private static final String USUARIO = ""; //Usuario con el que se crea la BD
    private static final String CONTRASENIA = ""; //Contraseña utilizada para crear BD

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENIA);
    }
}
