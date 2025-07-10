package CampoCodigo.builders;

import CampoCodigo.Producto;

public class ProductoBuilder {
    private int id_producto;
    private String nombre;
    private double precio;
    private String descripcion;
    private int cantidad;

    public ProductoBuilder setId(int id) {
        this.id_producto = id;
        return this;
    }

    public ProductoBuilder setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public ProductoBuilder setPrecio(double precio) {
        this.precio = precio;
        return this;
    }

    public ProductoBuilder setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public ProductoBuilder setCantidad(int cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public Producto build() {
        return new Producto(id_producto, nombre, precio, descripcion, cantidad);
    }
}
