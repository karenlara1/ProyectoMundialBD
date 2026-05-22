package co.edu.uniquindio.modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class Partido {

    private int idPartido;
    private LocalDate fechaPartido;
    private LocalTime horaPartido;
    private int idEstadio;
    private int idGrupo;
    private int idEquipoLocal;
    private int idEquipoVisitante;
    private int golesLocal;
    private int golesVisitante;
    private String estado;

    public Partido() {
    }

    public Partido(int idPartido, LocalDate fechaPartido, LocalTime horaPartido,
                   int idEstadio, int idGrupo, int idEquipoLocal, int idEquipoVisitante,
                   int golesLocal, int golesVisitante, String estado) {
        this.idPartido = idPartido;
        this.fechaPartido = fechaPartido;
        this.horaPartido = horaPartido;
        this.idEstadio = idEstadio;
        this.idGrupo = idGrupo;
        this.idEquipoLocal = idEquipoLocal;
        this.idEquipoVisitante = idEquipoVisitante;
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
        this.estado = estado;
    }

    public Partido(LocalDate fechaPartido, LocalTime horaPartido,
                   int idEstadio, int idGrupo, int idEquipoLocal, int idEquipoVisitante,
                   int golesLocal, int golesVisitante, String estado) {
        this.fechaPartido = fechaPartido;
        this.horaPartido = horaPartido;
        this.idEstadio = idEstadio;
        this.idGrupo = idGrupo;
        this.idEquipoLocal = idEquipoLocal;
        this.idEquipoVisitante = idEquipoVisitante;
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
        this.estado = estado;
    }

    public int getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(int idPartido) {
        this.idPartido = idPartido;
    }

    public LocalDate getFechaPartido() {
        return fechaPartido;
    }

    public void setFechaPartido(LocalDate fechaPartido) {
        this.fechaPartido = fechaPartido;
    }

    public LocalTime getHoraPartido() {
        return horaPartido;
    }

    public void setHoraPartido(LocalTime horaPartido) {
        this.horaPartido = horaPartido;
    }

    public int getIdEstadio() {
        return idEstadio;
    }

    public void setIdEstadio(int idEstadio) {
        this.idEstadio = idEstadio;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public int getIdEquipoLocal() {
        return idEquipoLocal;
    }

    public void setIdEquipoLocal(int idEquipoLocal) {
        this.idEquipoLocal = idEquipoLocal;
    }

    public int getIdEquipoVisitante() {
        return idEquipoVisitante;
    }

    public void setIdEquipoVisitante(int idEquipoVisitante) {
        this.idEquipoVisitante = idEquipoVisitante;
    }

    public int getGolesLocal() {
        return golesLocal;
    }

    public void setGolesLocal(int golesLocal) {
        this.golesLocal = golesLocal;
    }

    public int getGolesVisitante() {
        return golesVisitante;
    }

    public void setGolesVisitante(int golesVisitante) {
        this.golesVisitante = golesVisitante;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}