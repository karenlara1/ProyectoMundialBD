package co.edu.uniquindio.dao;

import co.edu.uniquindio.conexion.ConexionOracle;
import co.edu.uniquindio.modelo.Bitacora;

import javax.swing.*;
import java.sql.*;
import java.util.*;

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

    public List<Bitacora> listarBitacora() {
        List<Bitacora> lista = new ArrayList<>();

        String sql = """
            SELECT b.id_bitacora,
                   b.id_usuario,
                   u.username,
                   b.fecha_hora_ingreso,
                   b.fecha_hora_salida
            FROM bitacora b
            INNER JOIN usuario u ON b.id_usuario = u.id_usuario
            ORDER BY b.fecha_hora_ingreso DESC
            """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Bitacora bitacora = new Bitacora();

                bitacora.setIdBitacora(rs.getInt("id_bitacora"));
                bitacora.setIdUsuario(rs.getInt("id_usuario"));
                bitacora.setUsername(rs.getString("username"));

                Timestamp ingreso = rs.getTimestamp("fecha_hora_ingreso");
                Timestamp salida = rs.getTimestamp("fecha_hora_salida");

                if (ingreso != null) {
                    bitacora.setFechaHoraIngreso(ingreso.toLocalDateTime());
                }

                if (salida != null) {
                    bitacora.setFechaHoraSalida(salida.toLocalDateTime());
                }

                lista.add(bitacora);
            }

        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al listar la bitácora: " + exception.getMessage()
            );
        }

        return lista;
    }
}