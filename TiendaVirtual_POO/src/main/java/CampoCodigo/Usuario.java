
package CampoCodigo;

import java.util.List;
import java.util.ArrayList;

public class Usuario {
    private int id_usuario;
    private String nombre;
    private String email;
    private String contraseña;
    private String tipo;
    private String direccion;
    private int telefono;
    private final List<Producto> carritoCompra;

    public Usuario(int id_usuario, String nombre, String email, String contraseña, String tipo, String direccion, int telefono) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.email = email;
        this.contraseña = contraseña;
        this.tipo = tipo;
        this.direccion = direccion;
        this.telefono = telefono;
        this.carritoCompra = new ArrayList<>();
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
    
    public List<Producto> getCarritoCompra() {
        return carritoCompra;
    }

    public void agregarAlCarrito(Producto producto) {
        carritoCompra.add(producto);
        System.out.println("Producto agregado al carrito: " + producto.getNombre());
    }

    public void eliminarDelCarrito(Producto producto) {
        if (carritoCompra.remove(producto)) {
            System.out.println("Producto eliminado del carrito: " + producto.getNombre());
        } else {
            System.out.println("Producto no encontrado en el carrito.");
        }
    }
    
    // Método para iniciar sesión
    public boolean iniciarSesion(String email, String contraseña) {
        return this.email.equals(email) && this.contraseña.equals(contraseña);
    }
}

    