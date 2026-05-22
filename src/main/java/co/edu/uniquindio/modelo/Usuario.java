package co.edu.uniquindio.modelo;

public class Usuario {

    private int idUsuario;
    private String username;
    private String password;
    private String rol;
    private String estado;

    public Usuario(){

    }

    public Usuario(int idUsuario, String username, String password, String rol, String estado) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.estado = estado;
    }

    public int getIdUsuario(){
        return idUsuario;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getRol(){
        return rol;
    }

    public String getEstado(){
        return estado;
    }

    public void setIdUsuario(int idUsuario){
        this.idUsuario = idUsuario;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setRol (String rol){
        this.rol = rol;
    }

    public void setEstado (String estado){
        this.estado = estado;
    }

}
