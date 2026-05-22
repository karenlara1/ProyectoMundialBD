package co.edu.uniquindio.modelo;

public class Posicion {

    private int idPosicion;
    private String nombre;

    public Posicion() {
    }

    public Posicion(int idPosicion, String nombre) {
        this.idPosicion = idPosicion;
        this.nombre = nombre;
    }

    public Posicion(String nombre) {
        this.nombre = nombre;
    }

    public int getIdPosicion() {
        return idPosicion;
    }

    public void setIdPosicion(int idPosicion) {
        this.idPosicion = idPosicion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}