package co.edu.uniquindio.modelo;

import java.time.LocalDate;

public class DirectorTecnico {

    private int idDirectorTecnico;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String nacionalidad;
    private int idEquipo;

    public DirectorTecnico() {
    }

    public DirectorTecnico(int idDirectorTecnico, String nombre,
                           LocalDate fechaNacimiento, String nacionalidad, int idEquipo) {
        this.idDirectorTecnico = idDirectorTecnico;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.idEquipo = idEquipo;
    }

    public DirectorTecnico(String nombre, LocalDate fechaNacimiento,
                           String nacionalidad, int idEquipo) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.idEquipo = idEquipo;
    }

    public int getIdDirectorTecnico() {
        return idDirectorTecnico;
    }

    public void setIdDirectorTecnico(int idDirectorTecnico) {
        this.idDirectorTecnico = idDirectorTecnico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    @Override
    public String toString() {
        return nombre;
    }
}