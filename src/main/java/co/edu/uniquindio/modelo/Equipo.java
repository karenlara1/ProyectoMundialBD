package co.edu.uniquindio.modelo;

import java.time.LocalDate;

public class Equipo {

    private int idEquipo;
    private String nombre;
    private LocalDate fechaFundacion;
    private int idPais;
    private int idGrupo;

    public Equipo() {
    }

    public Equipo(int idEquipo, String nombre, LocalDate fechaFundacion, int idPais, int idGrupo) {
        this.idEquipo = idEquipo;
        this.nombre = nombre;
        this.fechaFundacion = fechaFundacion;
        this.idPais = idPais;
        this.idGrupo = idGrupo;
    }

    public Equipo(String nombre, LocalDate fechaFundacion, int idPais, int idGrupo) {
        this.nombre = nombre;
        this.fechaFundacion = fechaFundacion;
        this.idPais = idPais;
        this.idGrupo = idGrupo;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaFundacion() {
        return fechaFundacion;
    }

    public void setFechaFundacion(LocalDate fechaFundacion) {
        this.fechaFundacion = fechaFundacion;
    }

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    @Override
    public String toString() {
        return nombre;
    }
}