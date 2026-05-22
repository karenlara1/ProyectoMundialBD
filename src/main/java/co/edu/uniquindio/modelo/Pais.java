package co.edu.uniquindio.modelo;

public class Pais {

    private int idPais;
    private String nombre;
    private String esAnfitrion;
    private int idConfederacion;

    public Pais() {
    }

    public Pais(int idPais, String nombre, String esAnfitrion, int idConfederacion) {
        this.idPais = idPais;
        this.nombre = nombre;
        this.esAnfitrion = esAnfitrion;
        this.idConfederacion = idConfederacion;
    }

    public Pais(String nombre, String esAnfitrion, int idConfederacion) {
        this.nombre = nombre;
        this.esAnfitrion = esAnfitrion;
        this.idConfederacion = idConfederacion;
    }

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEsAnfitrion() {
        return esAnfitrion;
    }

    public void setEsAnfitrion(String esAnfitrion) {
        this.esAnfitrion = esAnfitrion;
    }

    public int getIdConfederacion() {
        return idConfederacion;
    }

    public void setIdConfederacion(int idConfederacion) {
        this.idConfederacion = idConfederacion;
    }

    @Override
    public String toString() {
        return nombre;
    }
}