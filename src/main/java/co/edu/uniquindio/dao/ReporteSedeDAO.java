package co.edu.uniquindio.dao;

import co.edu.uniquindio.conexion.ConexionOracle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReporteSedeDAO {

    public List<Object[]> obtenerPartidosPorEstadio(int idEstadio) {
        List<Object[]> resultados = new ArrayList<>();

        String sql = """
                SELECT es.nombre AS estadio,
                       p.fecha_partido AS fecha,
                       p.hora_partido AS hora,
                       el.nombre AS equipo_local,
                       ev.nombre AS equipo_visitante,
                       g.nombre AS grupo,
                       p.estado AS estado
                FROM partido p
                INNER JOIN estadio es ON p.id_estadio = es.id_estadio
                INNER JOIN equipo el ON p.id_equipo_local = el.id_equipo
                INNER JOIN equipo ev ON p.id_equipo_visitante = ev.id_equipo
                INNER JOIN grupo_mundial g ON p.id_grupo = g.id_grupo
                WHERE es.id_estadio = ?
                ORDER BY p.fecha_partido, p.hora_partido
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idEstadio);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = {
                            rs.getString("estadio"),
                            rs.getDate("fecha"),
                            rs.getString("hora"),
                            rs.getString("equipo_local"),
                            rs.getString("equipo_visitante"),
                            rs.getString("grupo"),
                            rs.getString("estado")
                    };

                    resultados.add(fila);
                }
            }

        } catch (SQLException exception) {
            System.out.println("Error al consultar partidos por estadio: " + exception.getMessage());
        }

        return resultados;
    }

    public List<Object[]> obtenerEquipoMasCostosoPorPaisAnfitrion() {
        List<Object[]> resultados = new ArrayList<>();

        String sql = """
                SELECT pais_anfitrion,
                       equipo,
                       valor_total
                FROM (
                    SELECT pais_anfitrion,
                           equipo,
                           valor_total,
                           ROW_NUMBER() OVER (
                               PARTITION BY pais_anfitrion
                               ORDER BY valor_total DESC
                           ) AS posicion
                    FROM (
                        SELECT pa.nombre AS pais_anfitrion,
                               eq.nombre AS equipo,
                               SUM(j.valor) AS valor_total
                        FROM (
                            SELECT p.id_partido,
                                   p.id_equipo_local AS id_equipo,
                                   p.id_estadio
                            FROM partido p
                            
                            UNION
                            
                            SELECT p.id_partido,
                                   p.id_equipo_visitante AS id_equipo,
                                   p.id_estadio
                            FROM partido p
                        ) equipos_partido
                        INNER JOIN equipo eq ON equipos_partido.id_equipo = eq.id_equipo
                        INNER JOIN jugador j ON eq.id_equipo = j.id_equipo
                        INNER JOIN estadio es ON equipos_partido.id_estadio = es.id_estadio
                        INNER JOIN ciudad ci ON es.id_ciudad = ci.id_ciudad
                        INNER JOIN pais pa ON ci.id_pais = pa.id_pais
                        WHERE pa.es_anfitrion = 'S'
                        GROUP BY pa.nombre, eq.nombre
                    )
                )
                WHERE posicion = 1
                ORDER BY pais_anfitrion
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] fila = {
                        rs.getString("pais_anfitrion"),
                        rs.getString("equipo"),
                        rs.getDouble("valor_total")
                };

                resultados.add(fila);
            }

        } catch (SQLException exception) {
            System.out.println("Error al consultar equipo más costoso por país anfitrión: " + exception.getMessage());
        }

        return resultados;
    }

    public List<Object[]> obtenerPartidosPorPaisAnfitrion() {
        List<Object[]> resultados = new ArrayList<>();

        String sql = """
                SELECT pa.nombre AS pais_anfitrion,
                       ci.nombre AS ciudad,
                       es.nombre AS estadio,
                       p.fecha_partido AS fecha,
                       p.hora_partido AS hora,
                       el.nombre AS equipo_local,
                       ev.nombre AS equipo_visitante,
                       g.nombre AS grupo,
                       p.estado AS estado
                FROM partido p
                INNER JOIN estadio es ON p.id_estadio = es.id_estadio
                INNER JOIN ciudad ci ON es.id_ciudad = ci.id_ciudad
                INNER JOIN pais pa ON ci.id_pais = pa.id_pais
                INNER JOIN equipo el ON p.id_equipo_local = el.id_equipo
                INNER JOIN equipo ev ON p.id_equipo_visitante = ev.id_equipo
                INNER JOIN grupo_mundial g ON p.id_grupo = g.id_grupo
                WHERE pa.es_anfitrion = 'S'
                ORDER BY pa.nombre, ci.nombre, es.nombre, p.fecha_partido, p.hora_partido
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] fila = {
                        rs.getString("pais_anfitrion"),
                        rs.getString("ciudad"),
                        rs.getString("estadio"),
                        rs.getDate("fecha"),
                        rs.getString("hora"),
                        rs.getString("equipo_local"),
                        rs.getString("equipo_visitante"),
                        rs.getString("grupo"),
                        rs.getString("estado")
                };

                resultados.add(fila);
            }

        } catch (SQLException exception) {
            System.out.println("Error al consultar partidos por país anfitrión: " + exception.getMessage());
        }

        return resultados;
    }

    public List<Object[]> obtenerCantidadPartidosPorPaisAnfitrion() {
        List<Object[]> resultados = new ArrayList<>();

        String sql = """
                SELECT pa.nombre AS pais_anfitrion,
                       COUNT(p.id_partido) AS cantidad_partidos
                FROM pais pa
                INNER JOIN ciudad ci ON pa.id_pais = ci.id_pais
                INNER JOIN estadio es ON ci.id_ciudad = es.id_ciudad
                LEFT JOIN partido p ON es.id_estadio = p.id_estadio
                WHERE pa.es_anfitrion = 'S'
                GROUP BY pa.nombre
                ORDER BY pa.nombre
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] fila = {
                        rs.getString("pais_anfitrion"),
                        rs.getInt("cantidad_partidos")
                };

                resultados.add(fila);
            }

        } catch (SQLException exception) {
            System.out.println("Error al consultar cantidad de partidos por país anfitrión: " + exception.getMessage());
        }

        return resultados;
    }

    public List<Object[]> obtenerPaisesQueJueganPorPaisAnfitrion() {
        List<Object[]> resultados = new ArrayList<>();

        String sql = """
                SELECT DISTINCT pais_sede.nombre AS pais_anfitrion,
                                pais_equipo.nombre AS pais_que_juega
                FROM partido p
                INNER JOIN estadio es ON p.id_estadio = es.id_estadio
                INNER JOIN ciudad ci ON es.id_ciudad = ci.id_ciudad
                INNER JOIN pais pais_sede ON ci.id_pais = pais_sede.id_pais
                INNER JOIN equipo eq_local ON p.id_equipo_local = eq_local.id_equipo
                INNER JOIN pais pais_local ON eq_local.id_pais = pais_local.id_pais
                INNER JOIN equipo eq_visitante ON p.id_equipo_visitante = eq_visitante.id_equipo
                INNER JOIN pais pais_visitante ON eq_visitante.id_pais = pais_visitante.id_pais
                INNER JOIN (
                    SELECT p1.id_partido,
                           eq1.id_pais
                    FROM partido p1
                    INNER JOIN equipo eq1 ON p1.id_equipo_local = eq1.id_equipo
                    
                    UNION
                    
                    SELECT p2.id_partido,
                           eq2.id_pais
                    FROM partido p2
                    INNER JOIN equipo eq2 ON p2.id_equipo_visitante = eq2.id_equipo
                ) paises_partido ON p.id_partido = paises_partido.id_partido
                INNER JOIN pais pais_equipo ON paises_partido.id_pais = pais_equipo.id_pais
                WHERE pais_sede.es_anfitrion = 'S'
                ORDER BY pais_sede.nombre, pais_equipo.nombre
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] fila = {
                        rs.getString("pais_anfitrion"),
                        rs.getString("pais_que_juega")
                };

                resultados.add(fila);
            }

        } catch (SQLException exception) {
            System.out.println("Error al consultar países que juegan por país anfitrión: " + exception.getMessage());
        }

        return resultados;
    }
}