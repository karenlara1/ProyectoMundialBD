package co.edu.uniquindio.dao;

import co.edu.uniquindio.conexion.ConexionOracle;
import co.edu.uniquindio.modelo.DirectorTecnico;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DirectorTecnicoDAO {

    public boolean crearDirectorTecnico(DirectorTecnico directorTecnico) {
        String sql = """
                INSERT INTO director_tecnico (nombre, fecha_nacimiento, nacionalidad, id_equipo)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, directorTecnico.getNombre());
            ps.setDate(2, Date.valueOf(directorTecnico.getFechaNacimiento()));
            ps.setString(3, directorTecnico.getNacionalidad());
            ps.setInt(4, directorTecnico.getIdEquipo());

            return ps.executeUpdate() > 0;

        } catch (SQLException exception) {
            System.out.println("Error al crear director técnico: " + exception.getMessage());
            return false;
        }
    }

    public boolean actualizarDirectorTecnico(DirectorTecnico directorTecnico) {
        String sql = """
                UPDATE director_tecnico
                SET nombre = ?,
                    fecha_nacimiento = ?,
                    nacionalidad = ?,
                    id_equipo = ?
                WHERE id_director_tecnico = ?
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, directorTecnico.getNombre());
            ps.setDate(2, Date.valueOf(directorTecnico.getFechaNacimiento()));
            ps.setString(3, directorTecnico.getNacionalidad());
            ps.setInt(4, directorTecnico.getIdEquipo());
            ps.setInt(5, directorTecnico.getIdDirectorTecnico());

            return ps.executeUpdate() > 0;

        } catch (SQLException exception) {
            System.out.println("Error al actualizar director técnico: " + exception.getMessage());
            return false;
        }
    }

    public boolean eliminarDirectorTecnico(int idDirectorTecnico) {
        String sql = """
                DELETE FROM director_tecnico
                WHERE id_director_tecnico = ?
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idDirectorTecnico);

            return ps.executeUpdate() > 0;

        } catch (SQLException exception) {
            System.out.println("Error al eliminar director técnico: " + exception.getMessage());
            return false;
        }
    }

    public DirectorTecnico buscarPorId(int idDirectorTecnico) {
        String sql = """
                SELECT id_director_tecnico, nombre, fecha_nacimiento, nacionalidad, id_equipo
                FROM director_tecnico
                WHERE id_director_tecnico = ?
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idDirectorTecnico);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearDirectorTecnico(rs);
            }

        } catch (SQLException exception) {
            System.out.println("Error al buscar director técnico: " + exception.getMessage());
        }

        return null;
    }

    public List<DirectorTecnico> listarDirectoresTecnicos() {
        List<DirectorTecnico> directores = new ArrayList<>();

        String sql = """
                SELECT id_director_tecnico, nombre, fecha_nacimiento, nacionalidad, id_equipo
                FROM director_tecnico
                ORDER BY nombre
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                directores.add(mapearDirectorTecnico(rs));
            }

        } catch (SQLException exception) {
            System.out.println("Error al listar directores técnicos: " + exception.getMessage());
        }

        return directores;
    }

    public boolean existeDirectorParaEquipo(int idEquipo) {
        String sql = """
                SELECT COUNT(*) AS total
                FROM director_tecnico
                WHERE id_equipo = ?
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idEquipo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("total") > 0;
            }

        } catch (SQLException exception) {
            System.out.println("Error al validar director por equipo: " + exception.getMessage());
        }

        return false;
    }

    public boolean existeDirectorParaEquipoEditando(int idEquipo, int idDirectorTecnico) {
        String sql = """
                SELECT COUNT(*) AS total
                FROM director_tecnico
                WHERE id_equipo = ?
                AND id_director_tecnico <> ?
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idEquipo);
            ps.setInt(2, idDirectorTecnico);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("total") > 0;
            }

        } catch (SQLException exception) {
            System.out.println("Error al validar director por equipo al editar: " + exception.getMessage());
        }

        return false;
    }

    private DirectorTecnico mapearDirectorTecnico(ResultSet rs) throws SQLException {
        DirectorTecnico directorTecnico = new DirectorTecnico();

        directorTecnico.setIdDirectorTecnico(rs.getInt("id_director_tecnico"));
        directorTecnico.setNombre(rs.getString("nombre"));

        Date fechaSql = rs.getDate("fecha_nacimiento");
        LocalDate fechaNacimiento = null;

        if (fechaSql != null) {
            fechaNacimiento = fechaSql.toLocalDate();
        }

        directorTecnico.setFechaNacimiento(fechaNacimiento);
        directorTecnico.setNacionalidad(rs.getString("nacionalidad"));
        directorTecnico.setIdEquipo(rs.getInt("id_equipo"));

        return directorTecnico;
    }
}