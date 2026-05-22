package co.edu.uniquindio.dao;

import co.edu.uniquindio.conexion.ConexionOracle;
import co.edu.uniquindio.modelo.Estadio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstadioDAO {

    public boolean crearEstadio(Estadio estadio) {
        String sql = """
                INSERT INTO estadio (nombre, capacidad, direccion, id_ciudad)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, estadio.getNombre());
            ps.setInt(2, estadio.getCapacidad());
            ps.setString(3, estadio.getDireccion());
            ps.setInt(4, estadio.getIdCiudad());

            return ps.executeUpdate() > 0;

        } catch (SQLException exception) {
            System.out.println("Error al crear estadio: " + exception.getMessage());
            return false;
        }
    }

    public boolean actualizarEstadio(Estadio estadio) {
        String sql = """
                UPDATE estadio
                SET nombre = ?,
                    capacidad = ?,
                    direccion = ?,
                    id_ciudad = ?
                WHERE id_estadio = ?
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, estadio.getNombre());
            ps.setInt(2, estadio.getCapacidad());
            ps.setString(3, estadio.getDireccion());
            ps.setInt(4, estadio.getIdCiudad());
            ps.setInt(5, estadio.getIdEstadio());

            return ps.executeUpdate() > 0;

        } catch (SQLException exception) {
            System.out.println("Error al actualizar estadio: " + exception.getMessage());
            return false;
        }
    }

    public boolean eliminarEstadio(int idEstadio) {
        String sql = """
                DELETE FROM estadio
                WHERE id_estadio = ?
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idEstadio);

            return ps.executeUpdate() > 0;

        } catch (SQLException exception) {
            System.out.println("Error al eliminar estadio: " + exception.getMessage());
            return false;
        }
    }

    public Estadio buscarPorId(int idEstadio) {
        String sql = """
                SELECT id_estadio, nombre, capacidad, direccion, id_ciudad
                FROM estadio
                WHERE id_estadio = ?
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idEstadio);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearEstadio(rs);
            }

        } catch (SQLException exception) {
            System.out.println("Error al buscar estadio: " + exception.getMessage());
        }

        return null;
    }

    public List<Estadio> listarEstadios() {
        List<Estadio> estadios = new ArrayList<>();

        String sql = """
                SELECT id_estadio, nombre, capacidad, direccion, id_ciudad
                FROM estadio
                ORDER BY nombre
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                estadios.add(mapearEstadio(rs));
            }

        } catch (SQLException exception) {
            System.out.println("Error al listar estadios: " + exception.getMessage());
        }

        return estadios;
    }

    public List<Estadio> listarEstadiosPorCiudad(int idCiudad) {
        List<Estadio> estadios = new ArrayList<>();

        String sql = """
                SELECT id_estadio, nombre, capacidad, direccion, id_ciudad
                FROM estadio
                WHERE id_ciudad = ?
                ORDER BY nombre
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idCiudad);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    estadios.add(mapearEstadio(rs));
                }
            }

        } catch (SQLException exception) {
            System.out.println("Error al listar estadios por ciudad: " + exception.getMessage());
        }

        return estadios;
    }

    public boolean existeNombreEstadio(String nombre) {
        String sql = """
                SELECT COUNT(*) AS total
                FROM estadio
                WHERE LOWER(nombre) = LOWER(?)
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, nombre);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("total") > 0;
            }

        } catch (SQLException exception) {
            System.out.println("Error al validar nombre del estadio: " + exception.getMessage());
        }

        return false;
    }

    public boolean existeNombreEstadioEditando(String nombre, int idEstadio) {
        String sql = """
                SELECT COUNT(*) AS total
                FROM estadio
                WHERE LOWER(nombre) = LOWER(?)
                AND id_estadio <> ?
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setInt(2, idEstadio);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("total") > 0;
            }

        } catch (SQLException exception) {
            System.out.println("Error al validar nombre del estadio al editar: " + exception.getMessage());
        }

        return false;
    }

    private Estadio mapearEstadio(ResultSet rs) throws SQLException {
        Estadio estadio = new Estadio();

        estadio.setIdEstadio(rs.getInt("id_estadio"));
        estadio.setNombre(rs.getString("nombre"));
        estadio.setCapacidad(rs.getInt("capacidad"));
        estadio.setDireccion(rs.getString("direccion"));
        estadio.setIdCiudad(rs.getInt("id_ciudad"));

        return estadio;
    }
}