package CampoCodigo;

public class Producto  {

    //Atributos
    
    private int id_producto;
    private String nombre;
    private double precio;
    private String descripcion;
    private int cantidad;

    //Constructor
    
     public Producto() {
        // Inicialización opcional
    }
    public Producto(int id_producto, String nombre, double precio, String descripcion, int cantidad) {
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
    }

    //G y S
    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

 
    

    @Override
    public String toString() {
        return String.format("%-5d %-20s %-10.2f %-20s %-10d",
                id_producto,
                nombre,
                precio,
                descripcion,
                cantidad);
    }
}
