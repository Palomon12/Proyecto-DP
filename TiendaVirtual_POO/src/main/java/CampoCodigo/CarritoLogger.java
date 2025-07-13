package CampoCodigo;

public class CarritoLogger implements ObservadorCarrito {
    @Override
    public void actualizar(CarritoCompra carrito) {
        System.out.println("🛒 [Logger] El carrito fue actualizado. Total actual: " + carrito.getTotal());
    }
}
