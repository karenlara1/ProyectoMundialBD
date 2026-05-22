package co.edu.uniquindio.modelo;

public class Estadio {

    private int idEstadio;
    private String nombre;
    private int capacidad;
    private String direccion;
    private int idCiudad;

    public Estadio() {
    }

    public Estadio(int idEstadio, String nombre, int capacidad, String direccion, int idCiudad) {
        this.idEstadio = idEstadio;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.direccion = direccion;
        this.idCiudad = idCiudad;
    }

    public Estadio(String nombre, int capacidad, String direccion, int idCiudad) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.direccion = direccion;
        this.idCiudad = idCiudad;
    }

    public int getIdEstadio() {
        return idEstadio;
    }

    public void setIdEstadio(int idEstadio) {
        this.idEstadio = idEstadio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    @Override
    public String toString() {
        return nombre;
    }
}