package co.edu.uniquindio.modelo;

public class Ciudad {

    private int idCiudad;
    private String nombre;
    private int idPais;

    public Ciudad() {
    }

    public Ciudad(int idCiudad, String nombre, int idPais) {
        this.idCiudad = idCiudad;
        this.nombre = nombre;
        this.idPais = idPais;
    }

    public Ciudad(String nombre, int idPais) {
        this.nombre = nombre;
        this.idPais = idPais;
    }

    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    @Override
    public String toString() {
        return nombre;
    }
}