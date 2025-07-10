package CampoCodigo;

import java.util.Date;

public class Pedido {
    private int id_Pedido;
    private Usuario usuario; 
    private String estado; 
    private Date fecha_Pedido; 
    private double total; 

    // Constructor principal
    public Pedido(int id_Pedido, Usuario usuario, String estado, Date fecha_Pedido, double total) {
        this.id_Pedido = id_Pedido;
        this.usuario = usuario;
        this.estado = estado;
        this.fecha_Pedido = fecha_Pedido;
        this.total = total;
    }
    // Constructor sin ID (para creación)
    public Pedido(Usuario usuario, String estado, Date fecha_Pedido, double total) {
        this.usuario = usuario;
        this.estado = estado;
        this.fecha_Pedido = fecha_Pedido;
        this.total = total;
    }


    
    // Constructor con id_Pedido, usuario y fecha_Pedido

    public Pedido(int id_Pedido, Usuario usuario, Date fecha_Pedido) {
        this.id_Pedido = id_Pedido;
        this.usuario = usuario;
        this.fecha_Pedido = fecha_Pedido;
    }

// Getters y Setters
    public int getId_Pedido() {
        return id_Pedido;
    }

    public void setId_Pedido(int id_Pedido) {
        this.id_Pedido = id_Pedido;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFecha_Pedido() {
        return fecha_Pedido;
    }

    public void setFecha_Pedido(Date fecha_Pedido) {
        this.fecha_Pedido = fecha_Pedido;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }


    @Override
    public String toString() {
        return "-- Pedido --\n" +
                "ID Pedido: " + id_Pedido + "\n" +
                "Usuario: " + usuario.getNombre() + "\n" +
                "Estado: " + estado + "\n" +
                "Fecha del Pedido: " + fecha_Pedido + "\n" +
                "Total: $" + total;
    }
}
