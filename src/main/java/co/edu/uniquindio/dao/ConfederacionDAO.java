package co.edu.uniquindio.dao;

import co.edu.uniquindio.conexion.ConexionOracle;
import co.edu.uniquindio.modelo.Confederacion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConfederacionDAO {

    public boolean crearConfederacion(Confederacion confederacion) {
        String sql = """
                INSERT INTO confederacion (nombre, continente)
                VALUES (?, ?)
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, confederacion.getNombre());
            ps.setString(2, confederacion.getContinente());

            return ps.executeUpdate() > 0;

        } catch (SQLException exception) {
            System.out.println("Error al crear confederación: " + exception.getMessage());
            return false;
        }
    }

    public boolean actualizarConfederacion(Confederacion confederacion) {
        String sql = """
                UPDATE confederacion
                SET nombre = ?, continente = ?
                WHERE id_confederacion = ?
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, confederacion.getNombre());
            ps.setString(2, confederacion.getContinente());
            ps.setInt(3, confederacion.getIdConfederacion());

            return ps.executeUpdate() > 0;

        } catch (SQLException exception) {
            System.out.println("Error al actualizar confederación: " + exception.getMessage());
            return false;
        }
    }

    public boolean eliminarConfederacion(int idConfederacion) {
        String sql = """
                DELETE FROM confederacion
                WHERE id_confederacion = ?
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idConfederacion);

            return ps.executeUpdate() > 0;

        } catch (SQLException exception) {
            System.out.println("Error al eliminar confederación: " + exception.getMessage());
            return false;
        }
    }

    public Confederacion buscarPorId(int idConfederacion) {
        String sql = """
                SELECT id_confederacion, nombre, continente
                FROM confederacion
                WHERE id_confederacion = ?
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idConfederacion);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearConfederacion(rs);
            }

        } catch (SQLException exception) {
            System.out.println("Error al buscar confederación: " + exception.getMessage());
        }

        return null;
    }

    public List<Confederacion> listarConfederaciones() {
        List<Confederacion> confederaciones = new ArrayList<>();

        String sql = """
                SELECT id_confederacion, nombre, continente
                FROM confederacion
                ORDER BY nombre
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Confederacion confederacion = mapearConfederacion(rs);
                confederaciones.add(confederacion);
            }

        } catch (SQLException exception) {
            System.out.println("Error al listar confederaciones: " + exception.getMessage());
        }

        return confederaciones;
    }

    public boolean existeNombreConfederacion(String nombre) {
        String sql = """
                SELECT COUNT(*) AS total
                FROM confederacion
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
            System.out.println("Error al validar nombre de confederación: " + exception.getMessage());
        }

        return false;
    }

    private Confederacion mapearConfederacion(ResultSet rs) throws SQLException {
        Confederacion confederacion = new Confederacion();

        confederacion.setIdConfederacion(rs.getInt("id_confederacion"));
        confederacion.setNombre(rs.getString("nombre"));
        confederacion.setContinente(rs.getString("continente"));

        return confederacion;
    }
}