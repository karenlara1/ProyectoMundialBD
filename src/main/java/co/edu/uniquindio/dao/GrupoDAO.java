package co.edu.uniquindio.dao;

import co.edu.uniquindio.conexion.ConexionOracle;
import co.edu.uniquindio.modelo.Grupo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GrupoDAO {

    public boolean crearGrupo(Grupo grupo) {
        String sql = """
                INSERT INTO grupo_mundial (nombre)
                VALUES (?)
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, grupo.getNombre().toUpperCase());

            return ps.executeUpdate() > 0;

        } catch (SQLException exception) {
            System.out.println("Error al crear grupo: " + exception.getMessage());
            return false;
        }
    }

    public boolean actualizarGrupo(Grupo grupo) {
        String sql = """
                UPDATE grupo_mundial
                SET nombre = ?
                WHERE id_grupo = ?
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, grupo.getNombre().toUpperCase());
            ps.setInt(2, grupo.getIdGrupo());

            return ps.executeUpdate() > 0;

        } catch (SQLException exception) {
            System.out.println("Error al actualizar grupo: " + exception.getMessage());
            return false;
        }
    }

    public boolean eliminarGrupo(int idGrupo) {
        String sql = """
                DELETE FROM grupo_mundial
                WHERE id_grupo = ?
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idGrupo);

            return ps.executeUpdate() > 0;

        } catch (SQLException exception) {
            System.out.println("Error al eliminar grupo: " + exception.getMessage());
            return false;
        }
    }

    public Grupo buscarPorId(int idGrupo) {
        String sql = """
                SELECT id_grupo, nombre
                FROM grupo_mundial
                WHERE id_grupo = ?
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idGrupo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapearGrupo(rs);
            }

        } catch (SQLException exception) {
            System.out.println("Error al buscar grupo: " + exception.getMessage());
        }

        return null;
    }

    public List<Grupo> listarGrupos() {
        List<Grupo> grupos = new ArrayList<>();

        String sql = """
                SELECT id_grupo, nombre
                FROM grupo_mundial
                ORDER BY nombre
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                grupos.add(mapearGrupo(rs));
            }

        } catch (SQLException exception) {
            System.out.println("Error al listar grupos: " + exception.getMessage());
        }

        return grupos;
    }

    public boolean existeNombreGrupo(String nombre) {
        String sql = """
                SELECT COUNT(*) AS total
                FROM grupo_mundial
                WHERE UPPER(nombre) = UPPER(?)
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, nombre);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("total") > 0;
            }

        } catch (SQLException exception) {
            System.out.println("Error al validar nombre del grupo: " + exception.getMessage());
        }

        return false;
    }

    private Grupo mapearGrupo(ResultSet rs) throws SQLException {
        Grupo grupo = new Grupo();

        grupo.setIdGrupo(rs.getInt("id_grupo"));
        grupo.setNombre(rs.getString("nombre"));

        return grupo;
    }
}