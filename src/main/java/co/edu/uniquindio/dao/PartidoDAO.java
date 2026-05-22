package co.edu.uniquindio.dao;

import co.edu.uniquindio.conexion.ConexionOracle;
import co.edu.uniquindio.modelo.Partido;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PartidoDAO {

    public boolean crearPartido(Partido partido) {
        String sql = """
                INSERT INTO partido (
                    fecha_partido,
                    hora_partido,
                    id_estadio,
                    id_grupo,
                    id_equipo_local,
                    id_equipo_visitante,
                    goles_local,
                    goles_visitante,
                    estado
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(partido.getFechaPartido()));
            ps.setString(2, partido.getHoraPartido().toString());
            ps.setInt(3, partido.getIdEstadio());
            ps.setInt(4, partido.getIdGrupo());
            ps.setInt(5, partido.getIdEquipoLocal());
            ps.setInt(6, partido.getIdEquipoVisitante());
            ps.setInt(7, partido.getGolesLocal());
            ps.setInt(8, partido.getGolesVisitante());
            ps.setString(9, partido.getEstado());

            return ps.executeUpdate() > 0;

        } catch (SQLException exception) {
            System.out.println("Error al crear partido: " + exception.getMessage());
            return false;
        }
    }

    public boolean actualizarPartido(Partido partido) {
        String sql = """
                UPDATE partido
                SET fecha_partido = ?,
                    hora_partido = ?,
                    id_estadio = ?,
                    id_grupo = ?,
                    id_equipo_local = ?,
                    id_equipo_visitante = ?,
                    goles_local = ?,
                    goles_visitante = ?,
                    estado = ?
                WHERE id_partido = ?
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(partido.getFechaPartido()));
            ps.setString(2, partido.getHoraPartido().toString());
            ps.setInt(3, partido.getIdEstadio());
            ps.setInt(4, partido.getIdGrupo());
            ps.setInt(5, partido.getIdEquipoLocal());
            ps.setInt(6, partido.getIdEquipoVisitante());
            ps.setInt(7, partido.getGolesLocal());
            ps.setInt(8, partido.getGolesVisitante());
            ps.setString(9, partido.getEstado());
            ps.setInt(10, partido.getIdPartido());

            return ps.executeUpdate() > 0;

        } catch (SQLException exception) {
            System.out.println("Error al actualizar partido: " + exception.getMessage());
            return false;
        }
    }

    public boolean eliminarPartido(int idPartido) {
        String sql = """
                DELETE FROM partido
                WHERE id_partido = ?
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idPartido);

            return ps.executeUpdate() > 0;

        } catch (SQLException exception) {
            System.out.println("Error al eliminar partido: " + exception.getMessage());
            return false;
        }
    }

    public Partido buscarPorId(int idPartido) {
        String sql = """
                SELECT id_partido,
                       fecha_partido,
                       hora_partido,
                       id_estadio,
                       id_grupo,
                       id_equipo_local,
                       id_equipo_visitante,
                       goles_local,
                       goles_visitante,
                       estado
                FROM partido
                WHERE id_partido = ?
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idPartido);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearPartido(rs);
            }

        } catch (SQLException exception) {
            System.out.println("Error al buscar partido: " + exception.getMessage());
        }

        return null;
    }

    public List<Partido> listarPartidos() {
        List<Partido> partidos = new ArrayList<>();

        String sql = """
                SELECT id_partido,
                       fecha_partido,
                       hora_partido,
                       id_estadio,
                       id_grupo,
                       id_equipo_local,
                       id_equipo_visitante,
                       goles_local,
                       goles_visitante,
                       estado
                FROM partido
                ORDER BY fecha_partido, hora_partido
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                partidos.add(mapearPartido(rs));
            }

        } catch (SQLException exception) {
            System.out.println("Error al listar partidos: " + exception.getMessage());
        }

        return partidos;
    }

    public List<Partido> listarPartidosPorEstadio(int idEstadio) {
        List<Partido> partidos = new ArrayList<>();

        String sql = """
                SELECT id_partido,
                       fecha_partido,
                       hora_partido,
                       id_estadio,
                       id_grupo,
                       id_equipo_local,
                       id_equipo_visitante,
                       goles_local,
                       goles_visitante,
                       estado
                FROM partido
                WHERE id_estadio = ?
                ORDER BY fecha_partido, hora_partido
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idEstadio);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    partidos.add(mapearPartido(rs));
                }
            }

        } catch (SQLException exception) {
            System.out.println("Error al listar partidos por estadio: " + exception.getMessage());
        }

        return partidos;
    }

    public List<Partido> listarPartidosPorGrupo(int idGrupo) {
        List<Partido> partidos = new ArrayList<>();

        String sql = """
                SELECT id_partido,
                       fecha_partido,
                       hora_partido,
                       id_estadio,
                       id_grupo,
                       id_equipo_local,
                       id_equipo_visitante,
                       goles_local,
                       goles_visitante,
                       estado
                FROM partido
                WHERE id_grupo = ?
                ORDER BY fecha_partido, hora_partido
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idGrupo);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    partidos.add(mapearPartido(rs));
                }
            }

        } catch (SQLException exception) {
            System.out.println("Error al listar partidos por grupo: " + exception.getMessage());
        }

        return partidos;
    }

    public boolean existePartidoMismoHorarioYEstadio(LocalDate fechaPartido,
                                                     LocalTime horaPartido,
                                                     int idEstadio) {
        String sql = """
                SELECT COUNT(*) AS total
                FROM partido
                WHERE fecha_partido = ?
                AND hora_partido = ?
                AND id_estadio = ?
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(fechaPartido));
            ps.setString(2, horaPartido.toString());
            ps.setInt(3, idEstadio);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("total") > 0;
            }

        } catch (SQLException exception) {
            System.out.println("Error al validar partido en mismo horario y estadio: " + exception.getMessage());
        }

        return false;
    }

    public boolean existePartidoMismoHorarioYEstadioEditando(LocalDate fechaPartido,
                                                             LocalTime horaPartido,
                                                             int idEstadio,
                                                             int idPartido) {
        String sql = """
                SELECT COUNT(*) AS total
                FROM partido
                WHERE fecha_partido = ?
                AND hora_partido = ?
                AND id_estadio = ?
                AND id_partido <> ?
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(fechaPartido));
            ps.setString(2, horaPartido.toString());
            ps.setInt(3, idEstadio);
            ps.setInt(4, idPartido);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("total") > 0;
            }

        } catch (SQLException exception) {
            System.out.println("Error al validar partido en mismo horario y estadio al editar: " + exception.getMessage());
        }

        return false;
    }

    private Partido mapearPartido(ResultSet rs) throws SQLException {
        Partido partido = new Partido();

        partido.setIdPartido(rs.getInt("id_partido"));

        Date fechaSql = rs.getDate("fecha_partido");
        LocalDate fechaPartido = null;

        if (fechaSql != null) {
            fechaPartido = fechaSql.toLocalDate();
        }

        partido.setFechaPartido(fechaPartido);

        String horaTexto = rs.getString("hora_partido");
        LocalTime horaPartido = null;

        if (horaTexto != null && !horaTexto.trim().isEmpty()) {
            horaPartido = LocalTime.parse(horaTexto);
        }

        partido.setHoraPartido(horaPartido);
        partido.setIdEstadio(rs.getInt("id_estadio"));
        partido.setIdGrupo(rs.getInt("id_grupo"));
        partido.setIdEquipoLocal(rs.getInt("id_equipo_local"));
        partido.setIdEquipoVisitante(rs.getInt("id_equipo_visitante"));
        partido.setGolesLocal(rs.getInt("goles_local"));
        partido.setGolesVisitante(rs.getInt("goles_visitante"));
        partido.setEstado(rs.getString("estado"));

        return partido;
    }
}