package co.edu.uniquindio.modelo;

import java.time.LocalDate;

public class Jugador {

    private int idJugador;
    private String nombre;
    private LocalDate fechaNacimiento;
    private double estatura;
    private double peso;
    private double valor;
    private int idEquipo;
    private int idPosicion;

    public Jugador() {
    }

    public Jugador(int idJugador, String nombre, LocalDate fechaNacimiento,
                   double estatura, double peso, double valor, int idEquipo, int idPosicion) {
        this.idJugador = idJugador;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.estatura = estatura;
        this.peso = peso;
        this.valor = valor;
        this.idEquipo = idEquipo;
        this.idPosicion = idPosicion;
    }

    public Jugador(String nombre, LocalDate fechaNacimiento,
                   double estatura, double peso, double valor, int idEquipo, int idPosicion) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.estatura = estatura;
        this.peso = peso;
        this.valor = valor;
        this.idEquipo = idEquipo;
        this.idPosicion = idPosicion;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
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

    public double getEstatura() {
        return estatura;
    }

    public void setEstatura(double estatura) {
        this.estatura = estatura;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public int getIdPosicion() {
        return idPosicion;
    }

    public void setIdPosicion(int idPosicion) {
        this.idPosicion = idPosicion;
    }

    @Override
    public String toString() {
        return nombre;
    }
}