package CampoCodigo.builders;

import CampoCodigo.Usuario;

public class UsuarioBuilder {
    private int id_usuario;
    private String nombre;
    private String email;
    private String contraseña;
    private String tipo;
    private String direccion;
    private int telefono;

    public UsuarioBuilder setId(int id) {
        this.id_usuario = id;
        return this;
    }

    public UsuarioBuilder setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public UsuarioBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UsuarioBuilder setContraseña(String contraseña) {
        this.contraseña = contraseña;
        return this;
    }

    public UsuarioBuilder setTipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public UsuarioBuilder setDireccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public UsuarioBuilder setTelefono(int telefono) {
        this.telefono = telefono;
        return this;
    }

    public Usuario build() {
        return new Usuario(id_usuario, nombre, email, contraseña, tipo, direccion, telefono);
    }
}
