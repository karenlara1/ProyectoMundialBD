package co.edu.uniquindio.dao;

import co.edu.uniquindio.conexion.ConexionOracle;
import co.edu.uniquindio.modelo.Equipo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAO {

    public boolean crearEquipo(Equipo equipo) {
        String sql = """
                INSERT INTO equipo (nombre, fecha_fundacion, id_pais, id_grupo)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, equipo.getNombre());

            if (equipo.getFechaFundacion() != null) {
                ps.setDate(2, Date.valueOf(equipo.getFechaFundacion()));
            } else {
                ps.setNull(2, Types.DATE);
            }

            ps.setInt(3, equipo.getIdPais());
            ps.setInt(4, equipo.getIdGrupo());

            return ps.executeUpdate() > 0;

        } catch (SQLException exception) {
            System.out.println("Error al crear equipo: " + exception.getMessage());
            return false;
        }
    }

    public boolean actualizarEquipo(Equipo equipo) {
        String sql = """
                UPDATE equipo
                SET nombre = ?,
                    fecha_fundacion = ?,
                    id_pais = ?,
                    id_grupo = ?
                WHERE id_equipo = ?
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, equipo.getNombre());

            if (equipo.getFechaFundacion() != null) {
                ps.setDate(2, Date.valueOf(equipo.getFechaFundacion()));
            } else {
                ps.setNull(2, Types.DATE);
            }

            ps.setInt(3, equipo.getIdPais());
            ps.setInt(4, equipo.getIdGrupo());
            ps.setInt(5, equipo.getIdEquipo());

            return ps.executeUpdate() > 0;

        } catch (SQLException exception) {
            System.out.println("Error al actualizar equipo: " + exception.getMessage());
            return false;
        }
    }

    public boolean eliminarEquipo(int idEquipo) {
        String sql = """
                DELETE FROM equipo
                WHERE id_equipo = ?
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idEquipo);

            return ps.executeUpdate() > 0;

        } catch (SQLException exception) {
            System.out.println("Error al eliminar equipo: " + exception.getMessage());
            return false;
        }
    }

    public Equipo buscarPorId(int idEquipo) {
        String sql = """
                SELECT id_equipo, nombre, fecha_fundacion, id_pais, id_grupo
                FROM equipo
                WHERE id_equipo = ?
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idEquipo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearEquipo(rs);
            }

        } catch (SQLException exception) {
            System.out.println("Error al buscar equipo: " + exception.getMessage());
        }

        return null;
    }

    public List<Equipo> listarEquipos() {
        List<Equipo> equipos = new ArrayList<>();

        String sql = """
                SELECT id_equipo, nombre, fecha_fundacion, id_pais, id_grupo
                FROM equipo
                ORDER BY nombre
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                equipos.add(mapearEquipo(rs));
            }

        } catch (SQLException exception) {
            System.out.println("Error al listar equipos: " + exception.getMessage());
        }

        return equipos;
    }

    public boolean existeNombreEquipo(String nombre) {
        String sql = """
                SELECT COUNT(*) AS total
                FROM equipo
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
            System.out.println("Error al validar nombre del equipo: " + exception.getMessage());
        }

        return false;
    }

    private Equipo mapearEquipo(ResultSet rs) throws SQLException {
        Equipo equipo = new Equipo();

        equipo.setIdEquipo(rs.getInt("id_equipo"));
        equipo.setNombre(rs.getString("nombre"));

        Date fechaSql = rs.getDate("fecha_fundacion");
        LocalDate fechaFundacion = null;

        if (fechaSql != null) {
            fechaFundacion = fechaSql.toLocalDate();
        }

        equipo.setFechaFundacion(fechaFundacion);
        equipo.setIdPais(rs.getInt("id_pais"));
        equipo.setIdGrupo(rs.getInt("id_grupo"));

        return equipo;
    }
}