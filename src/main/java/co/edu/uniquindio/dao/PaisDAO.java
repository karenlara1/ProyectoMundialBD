package co.edu.uniquindio.dao;

import co.edu.uniquindio.conexion.ConexionOracle;
import co.edu.uniquindio.modelo.Pais;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaisDAO {

    public List<Pais> listarPaises() {
        List<Pais> paises = new ArrayList<>();

        String sql = """
                SELECT id_pais, nombre, es_anfitrion, id_confederacion
                FROM pais
                ORDER BY nombre
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Pais pais = new Pais();

                pais.setIdPais(rs.getInt("id_pais"));
                pais.setNombre(rs.getString("nombre"));
                pais.setEsAnfitrion(rs.getString("es_anfitrion"));
                pais.setIdConfederacion(rs.getInt("id_confederacion"));

                paises.add(pais);
            }

        } catch (SQLException exception) {
            System.out.println("Error al listar países: " + exception.getMessage());
        }

        return paises;
    }

    public Pais buscarPorId(int idPais) {
        String sql = """
                SELECT id_pais, nombre, es_anfitrion, id_confederacion
                FROM pais
                WHERE id_pais = ?
                """;

        try (Connection conexion = ConexionOracle.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idPais);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Pais pais = new Pais();

                pais.setIdPais(rs.getInt("id_pais"));
                pais.setNombre(rs.getString("nombre"));
                pais.setEsAnfitrion(rs.getString("es_anfitrion"));
                pais.setIdConfederacion(rs.getInt("id_confederacion"));

                return pais;
            }

        } catch (SQLException exception) {
            System.out.println("Error al buscar país: " + exception.getMessage());
        }

        return null;
    }
}