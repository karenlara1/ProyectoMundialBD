package co.edu.uniquindio.dao;

import co.edu.uniquindio.conexion.ConexionOracle;
import co.edu.uniquindio.modelo.Jugador;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JugadorDAO {

    public boolean crearJugador(Jugador jugador) {
        String sql = """
                INSERT INTO jugador (nombre, fecha_nacimiento, estatura, peso, valor, id_equipo, id_posicion)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, jugador.getNombre());
            ps.setDate(2, Date.valueOf(jugador.getFechaNacimiento()));
            ps.setDouble(3, jugador.getEstatura());
            ps.setDouble(4, jugador.getPeso());
            ps.setDouble(5, jugador.getValor());
            ps.setInt(6, jugador.getIdEquipo());
            ps.setInt(7, jugador.getIdPosicion());

            return ps.executeUpdate() > 0;

        } catch (SQLException exception) {
            System.out.println("Error al crear jugador: " + exception.getMessage());
            return false;
        }
    }

    public boolean actualizarJugador(Jugador jugador) {
        String sql = """
                UPDATE jugador
                SET nombre = ?,
                    fecha_nacimiento = ?,
                    estatura = ?,
                    peso = ?,
                    valor = ?,
                    id_equipo = ?,
                    id_posicion = ?
                WHERE id_jugador = ?
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, jugador.getNombre());
            ps.setDate(2, Date.valueOf(jugador.getFechaNacimiento()));
            ps.setDouble(3, jugador.getEstatura());
            ps.setDouble(4, jugador.getPeso());
            ps.setDouble(5, jugador.getValor());
            ps.setInt(6, jugador.getIdEquipo());
            ps.setInt(7, jugador.getIdPosicion());
            ps.setInt(8, jugador.getIdJugador());

            return ps.executeUpdate() > 0;

        } catch (SQLException exception) {
            System.out.println("Error al actualizar jugador: " + exception.getMessage());
            return false;
        }
    }

    public boolean eliminarJugador(int idJugador) {
        String sql = """
                DELETE FROM jugador
                WHERE id_jugador = ?
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idJugador);

            return ps.executeUpdate() > 0;

        } catch (SQLException exception) {
            System.out.println("Error al eliminar jugador: " + exception.getMessage());
            return false;
        }
    }

    public Jugador buscarPorId(int idJugador) {
        String sql = """
                SELECT id_jugador, nombre, fecha_nacimiento, estatura, peso, valor, id_equipo, id_posicion
                FROM jugador
                WHERE id_jugador = ?
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idJugador);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearJugador(rs);
            }

        } catch (SQLException exception) {
            System.out.println("Error al buscar jugador: " + exception.getMessage());
        }

        return null;
    }

    public List<Jugador> listarJugadores() {
        List<Jugador> jugadores = new ArrayList<>();

        String sql = """
                SELECT id_jugador, nombre, fecha_nacimiento, estatura, peso, valor, id_equipo, id_posicion
                FROM jugador
                ORDER BY nombre
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                jugadores.add(mapearJugador(rs));
            }

        } catch (SQLException exception) {
            System.out.println("Error al listar jugadores: " + exception.getMessage());
        }

        return jugadores;
    }

    public List<Jugador> listarJugadoresPorEquipo(int idEquipo) {
        List<Jugador> jugadores = new ArrayList<>();

        String sql = """
                SELECT id_jugador, nombre, fecha_nacimiento, estatura, peso, valor, id_equipo, id_posicion
                FROM jugador
                WHERE id_equipo = ?
                ORDER BY nombre
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idEquipo);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    jugadores.add(mapearJugador(rs));
                }
            }

        } catch (SQLException exception) {
            System.out.println("Error al listar jugadores por equipo: " + exception.getMessage());
        }

        return jugadores;
    }

    public boolean existeJugadorEnEquipo(String nombre, int idEquipo) {
        String sql = """
                SELECT COUNT(*) AS total
                FROM jugador
                WHERE LOWER(nombre) = LOWER(?)
                AND id_equipo = ?
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setInt(2, idEquipo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("total") > 0;
            }

        } catch (SQLException exception) {
            System.out.println("Error al validar jugador en equipo: " + exception.getMessage());
        }

        return false;
    }

    public boolean existeJugadorEnEquipoEditando(String nombre, int idEquipo, int idJugador) {
        String sql = """
                SELECT COUNT(*) AS total
                FROM jugador
                WHERE LOWER(nombre) = LOWER(?)
                AND id_equipo = ?
                AND id_jugador <> ?
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setInt(2, idEquipo);
            ps.setInt(3, idJugador);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("total") > 0;
            }

        } catch (SQLException exception) {
            System.out.println("Error al validar jugador en equipo al editar: " + exception.getMessage());
        }

        return false;
    }

    private Jugador mapearJugador(ResultSet rs) throws SQLException {
        Jugador jugador = new Jugador();

        jugador.setIdJugador(rs.getInt("id_jugador"));
        jugador.setNombre(rs.getString("nombre"));

        Date fechaSql = rs.getDate("fecha_nacimiento");
        LocalDate fechaNacimiento = null;

        if (fechaSql != null) {
            fechaNacimiento = fechaSql.toLocalDate();
        }

        jugador.setFechaNacimiento(fechaNacimiento);
        jugador.setEstatura(rs.getDouble("estatura"));
        jugador.setPeso(rs.getDouble("peso"));
        jugador.setValor(rs.getDouble("valor"));
        jugador.setIdEquipo(rs.getInt("id_equipo"));
        jugador.setIdPosicion(rs.getInt("id_posicion"));

        return jugador;
    }
}