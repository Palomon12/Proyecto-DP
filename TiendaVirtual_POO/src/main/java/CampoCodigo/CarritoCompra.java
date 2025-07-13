package CampoCodigo;

import java.util.ArrayList;
import java.util.List;

public class CarritoCompra {

    private int idCarrito;
    private final List<CarritoItem> items; // Lista de items en el carrito
    private double total;
    private final List<ObservadorCarrito> observadores = new ArrayList<>();

    // Constructor
    public CarritoCompra(int idCarrito) {
        this.idCarrito = idCarrito;
        this.items = new ArrayList<>();
        this.total = 0.0;
    }

    // Getters y Setters
    public int getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(int idCarrito) {
        this.idCarrito = idCarrito;
    }

    public List<CarritoItem> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }

    public void agregarObservador(ObservadorCarrito obs) {
        observadores.add(obs);
    }

    public void eliminarObservador(ObservadorCarrito obs) {
        observadores.remove(obs);
    }

    private void notificarObservadores() {
        for (ObservadorCarrito obs : observadores) {
            obs.actualizar(this);
        }
    }

    // Agregar un producto al carrito
    public void agregarAlCarrito(Producto producto, int cantidad) {
        if (cantidad <= 0) {
            System.out.println("La cantidad debe ser mayor a cero.");
            return;
        }

        // Verificar si el producto ya existe en el carrito
        for (CarritoItem item : items) {
            if (item.getProducto().getId_producto() == producto.getId_producto()) {
                item.setCantidad(item.getCantidad() + cantidad); // Actualizar cantidad
                calcularTotal();
                System.out.println("Producto " + producto.getNombre() + " actualizado en el carrito con cantidad: " + item.getCantidad());
                return;
            }
        }

        // Agregar como nuevo item si no existe
        items.add(new CarritoItem(producto, cantidad));
        calcularTotal();
        System.out.println("Producto " + producto.getNombre() + " agregado al carrito con cantidad: " + cantidad);
        // Al final de agregarAlCarrito
        notificarObservadores();

    }

    // Vaciar el carrito
    public void vaciarCarrito() {
        items.clear();
        total = 0.0;
        System.out.println("El carrito ha sido vaciado.");
        // Al final de vaciarCarrito
        notificarObservadores();

    }

    // Calcular el total del carrito
    public double calcularTotal() {
        total = 0.0;
        for (CarritoItem item : items) {
            total += item.calcularSubtotal();
        }
        return total;
    }

    // Mostrar productos en el carrito
    public void mostrarProductosDelCarrito() {
        if (items.isEmpty()) {
            System.out.println("El carrito está vacío.");
        } else {
            System.out.println("Productos en el carrito:");
            for (CarritoItem item : items) {
                System.out.printf("ID: %d - %s - Precio: %.2f - Cantidad: %d - Subtotal: %.2f%n",
                        item.getProducto().getId_producto(),
                        item.getProducto().getNombre(),
                        item.getProducto().getPrecio(),
                        item.getCantidad(),
                        item.calcularSubtotal());
            }
            System.out.printf("Total: %.2f%n", total);
        }
    }

    @Override
    public String toString() {
        return "-- CarritoCompra --\nID: " + idCarrito + "\nTotal: " + total;
    }
}
