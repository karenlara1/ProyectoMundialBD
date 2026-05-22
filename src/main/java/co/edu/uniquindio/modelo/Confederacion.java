package co.edu.uniquindio.modelo;

public class Confederacion {

    private int idConfederacion;
    private String nombre;
    private String continente;

    public Confederacion() {
    }

    public Confederacion(int idConfederacion, String nombre, String continente) {
        this.idConfederacion = idConfederacion;
        this.nombre = nombre;
        this.continente = continente;
    }

    public Confederacion(String nombre, String continente) {
        this.nombre = nombre;
        this.continente = continente;
    }

    public int getIdConfederacion() {
        return idConfederacion;
    }

    public void setIdConfederacion(int idConfederacion) {
        this.idConfederacion = idConfederacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContinente() {
        return continente;
    }

    public void setContinente(String continente) {
        this.continente = continente;
    }

    @Override
    public String toString() {
        return nombre;
    }
}