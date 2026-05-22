package co.edu.uniquindio.dao;

import co.edu.uniquindio.conexion.ConexionOracle;
import co.edu.uniquindio.modelo.Usuario;

import java.sql.*;


public class UsuarioDAO {

    public Usuario login(String username, String password) {
        String sql = """
                SELECT id_usuario, username, password, rol, estado
                FROM usuario
                WHERE username = ? AND password = ? AND estado = 'ACTIVO'
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setUsername(rs.getString("username"));
                usuario.setPassword(rs.getString("password"));
                usuario.setRol(rs.getString("rol"));
                usuario.setEstado(rs.getString("estado"));
            }
        } catch (SQLException exception){
            System.out.println("Error en el inicio de sesión: " + exception.getMessage());
        }
        return null;
    }

    public boolean crearUsuario (Usuario usuario){
        String sql = """
                INSERT INTO usuario (username, password, rol, estado)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)){

            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getPassword());
            ps.setString(3, usuario.getRol());
            ps.setString(4, usuario.getEstado());

            return ps.executeUpdate() > 0;
        } catch (SQLException exception){
            System.out.println("Error en la creación de usuario: " + exception.getMessage());
            return false;
        }
    }
}
