package co.edu.uniquindio.dao;

import co.edu.uniquindio.conexion.ConexionOracle;
import co.edu.uniquindio.modelo.Posicion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PosicionDAO {

    public List<Posicion> listarPosiciones() {
        List<Posicion> posiciones = new ArrayList<>();

        String sql = """
                SELECT id_posicion, nombre
                FROM posicion
                ORDER BY nombre
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                posiciones.add(mapearPosicion(rs));
            }

        } catch (SQLException exception) {
            System.out.println("Error al listar posiciones: " + exception.getMessage());
        }

        return posiciones;
    }

    public Posicion buscarPorId(int idPosicion) {
        String sql = """
                SELECT id_posicion, nombre
                FROM posicion
                WHERE id_posicion = ?
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idPosicion);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearPosicion(rs);
            }

        } catch (SQLException exception) {
            System.out.println("Error al buscar posición: " + exception.getMessage());
        }

        return null;
    }

    private Posicion mapearPosicion(ResultSet rs) throws SQLException {
        Posicion posicion = new Posicion();

        posicion.setIdPosicion(rs.getInt("id_posicion"));
        posicion.setNombre(rs.getString("nombre"));

        return posicion;
    }
}