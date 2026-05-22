package co.edu.uniquindio.dao;

import co.edu.uniquindio.conexion.ConexionOracle;

import java.sql.*;

public class BitacoraDAO {

    public int registrarIngreso(int idUsuario){
        String sql = """
                INSERT INTO bitacora (id_usuario, fecha_hora_ingreso)
                VALUES (?, SYSTIMESTAMP)
                """;

        try (Connection con = ConexionOracle.conectar();
             PreparedStatement ps = con.prepareStatement(sql, new String[]{"id_bitacora"})) {

            ps.setInt(1, idUsuario);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("Error al registrar el ingreso: " + e.getMessage());
        }

        return -1;
    }

    public boolean registrarSalida(int idBitacora) {
        String sql = """
                UPDATE bitacora
                SET fecha_hora_salida = SYSTIMESTAMP
                WHERE id_bitacora = ?
                """;

        try (Connection con = ConexionOracle.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idBitacora);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al registrar la salida: " + e.getMessage());
            return false;
        }
    }
}