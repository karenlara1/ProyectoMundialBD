package co.edu.uniquindio.dao;

import co.edu.uniquindio.conexion.ConexionOracle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReporteDAO {

    public List<Object[]> obtenerJugadorMasCostosoPorConfederacion() {
        List<Object[]> resultados = new ArrayList<>();

        String sql = """
                SELECT c.nombre AS confederacion,
                       j.nombre AS jugador,
                       e.nombre AS equipo,
                       j.valor AS valor
                FROM jugador j
                INNER JOIN equipo e ON j.id_equipo = e.id_equipo
                INNER JOIN pais p ON e.id_pais = p.id_pais
                INNER JOIN confederacion c ON p.id_confederacion = c.id_confederacion
                WHERE j.valor = (
                    SELECT MAX(j2.valor)
                    FROM jugador j2
                    INNER JOIN equipo e2 ON j2.id_equipo = e2.id_equipo
                    INNER JOIN pais p2 ON e2.id_pais = p2.id_pais
                    WHERE p2.id_confederacion = c.id_confederacion
                )
                ORDER BY c.nombre
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] fila = {
                        rs.getString("confederacion"),
                        rs.getString("jugador"),
                        rs.getString("equipo"),
                        rs.getDouble("valor")
                };

                resultados.add(fila);
            }

        } catch (SQLException exception) {
            System.out.println("Error al consultar jugador más costoso por confederación: " + exception.getMessage());
        }

        return resultados;
    }

    public List<Object[]> obtenerCantidadJugadoresMenores21PorEquipo() {
        List<Object[]> resultados = new ArrayList<>();

        String sql = """
                SELECT e.nombre AS equipo,
                       COUNT(j.id_jugador) AS cantidad_menores
                FROM equipo e
                LEFT JOIN jugador j ON e.id_equipo = j.id_equipo
                    AND MONTHS_BETWEEN(SYSDATE, j.fecha_nacimiento) / 12 < 21
                GROUP BY e.nombre
                ORDER BY e.nombre
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] fila = {
                        rs.getString("equipo"),
                        rs.getInt("cantidad_menores")
                };

                resultados.add(fila);
            }

        } catch (SQLException exception) {
            System.out.println("Error al consultar menores de 21 años por equipo: " + exception.getMessage());
        }

        return resultados;
    }

    public List<Object[]> obtenerJugadoresFiltrados(double pesoMinimo,
                                                    double pesoMaximo,
                                                    double estaturaMinima,
                                                    double estaturaMaxima,
                                                    int idEquipo) {
        List<Object[]> resultados = new ArrayList<>();

        String sql = """
                SELECT j.nombre AS jugador,
                       e.nombre AS equipo,
                       j.peso AS peso,
                       j.estatura AS estatura,
                       j.valor AS valor
                FROM jugador j
                INNER JOIN equipo e ON j.id_equipo = e.id_equipo
                WHERE j.peso BETWEEN ? AND ?
                AND j.estatura BETWEEN ? AND ?
                AND j.id_equipo = ?
                ORDER BY j.nombre
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setDouble(1, pesoMinimo);
            ps.setDouble(2, pesoMaximo);
            ps.setDouble(3, estaturaMinima);
            ps.setDouble(4, estaturaMaxima);
            ps.setInt(5, idEquipo);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = {
                            rs.getString("jugador"),
                            rs.getString("equipo"),
                            rs.getDouble("peso"),
                            rs.getDouble("estatura"),
                            rs.getDouble("valor")
                    };

                    resultados.add(fila);
                }
            }

        } catch (SQLException exception) {
            System.out.println("Error al consultar jugadores filtrados: " + exception.getMessage());
        }

        return resultados;
    }

    public List<Object[]> obtenerValorTotalJugadoresPorEquipoYConfederacion(int idConfederacion) {
        List<Object[]> resultados = new ArrayList<>();

        String sql = """
                SELECT c.nombre AS confederacion,
                       e.nombre AS equipo,
                       SUM(j.valor) AS valor_total
                FROM jugador j
                INNER JOIN equipo e ON j.id_equipo = e.id_equipo
                INNER JOIN pais p ON e.id_pais = p.id_pais
                INNER JOIN confederacion c ON p.id_confederacion = c.id_confederacion
                WHERE c.id_confederacion = ?
                GROUP BY c.nombre, e.nombre
                ORDER BY e.nombre
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idConfederacion);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = {
                            rs.getString("confederacion"),
                            rs.getString("equipo"),
                            rs.getDouble("valor_total")
                    };

                    resultados.add(fila);
                }
            }

        } catch (SQLException exception) {
            System.out.println("Error al consultar valor total por equipo y confederación: " + exception.getMessage());
        }

        return resultados;
    }
}