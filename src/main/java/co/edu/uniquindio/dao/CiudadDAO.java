package co.edu.uniquindio.dao;

import co.edu.uniquindio.conexion.ConexionOracle;
import co.edu.uniquindio.modelo.Ciudad;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CiudadDAO {

    public boolean crearCiudad(Ciudad ciudad) {
        String sql = """
                INSERT INTO ciudad (nombre, id_pais)
                VALUES (?, ?)
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, ciudad.getNombre());
            ps.setInt(2, ciudad.getIdPais());

            return ps.executeUpdate() > 0;

        } catch (SQLException exception) {
            System.out.println("Error al crear ciudad: " + exception.getMessage());
            return false;
        }
    }

    public boolean actualizarCiudad(Ciudad ciudad) {
        String sql = """
                UPDATE ciudad
                SET nombre = ?,
                    id_pais = ?
                WHERE id_ciudad = ?
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, ciudad.getNombre());
            ps.setInt(2, ciudad.getIdPais());
            ps.setInt(3, ciudad.getIdCiudad());

            return ps.executeUpdate() > 0;

        } catch (SQLException exception) {
            System.out.println("Error al actualizar ciudad: " + exception.getMessage());
            return false;
        }
    }

    public boolean eliminarCiudad(int idCiudad) {
        String sql = """
                DELETE FROM ciudad
                WHERE id_ciudad = ?
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idCiudad);

            return ps.executeUpdate() > 0;

        } catch (SQLException exception) {
            System.out.println("Error al eliminar ciudad: " + exception.getMessage());
            return false;
        }
    }

    public Ciudad buscarPorId(int idCiudad) {
        String sql = """
                SELECT id_ciudad, nombre, id_pais
                FROM ciudad
                WHERE id_ciudad = ?
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idCiudad);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearCiudad(rs);
            }

        } catch (SQLException exception) {
            System.out.println("Error al buscar ciudad: " + exception.getMessage());
        }

        return null;
    }

    public List<Ciudad> listarCiudades() {
        List<Ciudad> ciudades = new ArrayList<>();

        String sql = """
                SELECT id_ciudad, nombre, id_pais
                FROM ciudad
                ORDER BY nombre
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ciudades.add(mapearCiudad(rs));
            }

        } catch (SQLException exception) {
            System.out.println("Error al listar ciudades: " + exception.getMessage());
        }

        return ciudades;
    }

    public List<Ciudad> listarCiudadesPorPais(int idPais) {
        List<Ciudad> ciudades = new ArrayList<>();

        String sql = """
                SELECT id_ciudad, nombre, id_pais
                FROM ciudad
                WHERE id_pais = ?
                ORDER BY nombre
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idPais);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ciudades.add(mapearCiudad(rs));
                }
            }

        } catch (SQLException exception) {
            System.out.println("Error al listar ciudades por país: " + exception.getMessage());
        }

        return ciudades;
    }

    public boolean existeCiudadEnPais(String nombre, int idPais) {
        String sql = """
                SELECT COUNT(*) AS total
                FROM ciudad
                WHERE LOWER(nombre) = LOWER(?)
                AND id_pais = ?
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setInt(2, idPais);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("total") > 0;
            }

        } catch (SQLException exception) {
            System.out.println("Error al validar ciudad en país: " + exception.getMessage());
        }

        return false;
    }

    public boolean existeCiudadEnPaisEditando(String nombre, int idPais, int idCiudad) {
        String sql = """
                SELECT COUNT(*) AS total
                FROM ciudad
                WHERE LOWER(nombre) = LOWER(?)
                AND id_pais = ?
                AND id_ciudad <> ?
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setInt(2, idPais);
            ps.setInt(3, idCiudad);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("total") > 0;
            }

        } catch (SQLException exception) {
            System.out.println("Error al validar ciudad en país al editar: " + exception.getMessage());
        }

        return false;
    }

    private Ciudad mapearCiudad(ResultSet rs) throws SQLException {
        Ciudad ciudad = new Ciudad();

        ciudad.setIdCiudad(rs.getInt("id_ciudad"));
        ciudad.setNombre(rs.getString("nombre"));
        ciudad.setIdPais(rs.getInt("id_pais"));

        return ciudad;
    }
}