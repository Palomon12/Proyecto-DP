package ClasesDAO;

import CampoCodigo.CConexion;
import CampoCodigo.CarritoCompra;
import CampoCodigo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CarritoCompraDAO {

    private final CConexion conexion;

    public CarritoCompraDAO() throws SQLException {
        this.conexion = CConexion.getInstancia();   // ← Singleton
    }

    // Método para agregar un producto al carrito de un cliente
    public boolean agregarProducto(int idCliente, Producto producto, int cantidad) {
        String sql = "INSERT INTO Carrito (id_cliente, id_producto, cantidad) VALUES (?, ?, ?)";

        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            stmt.setInt(2, producto.getId_producto());
            stmt.setInt(3, cantidad);
            stmt.executeUpdate();
            System.out.println("Producto " + producto.getNombre() + " agregado al carrito.");
            return true;

        } catch (SQLException e) {
            System.out.println("Error al agregar producto al carrito: " + e.getMessage());
            return false;
        }
    }

    public CarritoCompra leerCarrito(int idCliente) {
        String sql = "SELECT Producto.*, Carrito.cantidad FROM Carrito "
                + "JOIN Producto ON Carrito.id_producto = Producto.id_producto "
                + "WHERE Carrito.id_cliente = ?";
        CarritoCompra carrito = new CarritoCompra(idCliente); // inicializar con id_cliente o ID_carrito, según tu implementación.

        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId_producto(rs.getInt("id_producto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setDescripcion(rs.getString("descripcion"));
                int cantidad = rs.getInt("cantidad");

                // Método modificado para agregar el producto con cantidad
                carrito.agregarAlCarrito(producto, cantidad);
            }
            System.out.println("Carrito leído exitosamente.");

        } catch (SQLException e) {
            System.out.println("Error al leer carrito: " + e.getMessage());
        }
        return carrito;
    }

    // Método para actualizar la cantidad de un producto en el carrito
    public boolean actualizarCantidad(int idCliente, Producto producto, int nuevaCantidad) {
        String sql = "UPDATE Carrito SET cantidad = ? WHERE id_cliente = ? AND id_producto = ?";

        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, nuevaCantidad);
            stmt.setInt(2, idCliente);
            stmt.setInt(3, producto.getId_producto());
            stmt.executeUpdate();
            System.out.println("Cantidad de " + producto.getNombre() + " actualizada en el carrito.");
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar cantidad en el carrito: " + e.getMessage());
            return false;
        }
    }

    // Método para eliminar un producto del carrito
    public boolean eliminarProducto(int idCliente, int idProducto) {
        String sql = "DELETE FROM Carrito WHERE id_cliente = ? AND id_producto = ?";

        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            stmt.setInt(2, idProducto);
            stmt.executeUpdate();
            System.out.println("Producto eliminado del carrito.");
            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar producto del carrito: " + e.getMessage());
            return false;
        }
    }

    // Método para vaciar el carrito de un cliente
    public boolean vaciarCarrito(int idCliente) {
        String sql = "DELETE FROM Carrito WHERE id_cliente = ?";

        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            stmt.executeUpdate();
            System.out.println("Carrito vaciado correctamente.");
            return true;

        } catch (SQLException e) {
            System.out.println("Error al vaciar el carrito: " + e.getMessage());
            return false;
        }
    }
}
