package CampoCodigo;

public class CarritoItem {
    private Producto producto;
    private int cantidad;

    // Constructor
    public CarritoItem(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    // Getters y Setters
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    // Calcular el subtotal de este item
    public double calcularSubtotal() {
        return producto.getPrecio() * cantidad;
    }

    @Override
    public String toString() {
        return "Producto: " + producto.getNombre() + ", Cantidad: " + cantidad + ", Subtotal: " + calcularSubtotal();
    }
}
