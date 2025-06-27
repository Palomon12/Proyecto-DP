package ClasesDAO;

import CampoCodigo.CConexion;
import CampoCodigo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO implements CrudDAO<Producto> {

    private final CConexion conexion;

    public ProductoDAO() {
        this.conexion = new CConexion();
    }

    @Override
public boolean crear(Producto producto) throws SQLException {
    String getMaxIdSql = "SELECT MAX(id_producto) FROM Producto";
    String insertSql = "INSERT INTO Producto (id_producto, nombre, precio, descripcion, cantidad) VALUES (?, ?, ?, ?, ?)";

    try (
        Connection conn = conexion.getConexion();
        PreparedStatement maxIdStmt = conn.prepareStatement(getMaxIdSql);
        PreparedStatement insertStmt = conn.prepareStatement(insertSql)
    ) {
        // Obtener el siguiente ID disponible
        ResultSet rs = maxIdStmt.executeQuery();
        int nextId = 1;
        if (rs.next()) {
            nextId = rs.getInt(1) + 1;
        }

        producto.setId_producto(nextId); // Establecer el nuevo ID

        // Insertar producto
        insertStmt.setInt(1, producto.getId_producto());
        insertStmt.setString(2, producto.getNombre());
        insertStmt.setDouble(3, producto.getPrecio());
        insertStmt.setString(4, producto.getDescripcion());
        insertStmt.setInt(5, producto.getCantidad());

        insertStmt.executeUpdate();
        System.out.println("Producto " + producto.getNombre() + " (ID: " + nextId + ") guardado correctamente.");
        return true;
    } catch (SQLException e) {
        System.out.println("Error al guardar el producto: " + e.getMessage());
        throw e;
    }
}


    @Override
    public Producto leer(int id) throws SQLException {
        String sql = "SELECT * FROM Producto WHERE id_producto = ?";
        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Producto(
                        rs.getInt("id_producto"),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getString("descripcion"),
                        rs.getInt("cantidad")
                );
            }
        }
        return null;
    }

    @Override
    public List<Producto> leerTodos() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM Producto";
        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Producto producto = new Producto(
                        rs.getInt("id_producto"),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getString("descripcion"),
                        rs.getInt("cantidad")
                );
                productos.add(producto);
            }
        }
        return productos;
    }

    @Override
    public boolean actualizar(Producto producto) throws SQLException {
        String sql = "UPDATE Producto SET nombre = ?, precio = ?, descripcion = ?, cantidad = ? WHERE id_producto = ?";
        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setDouble(2, producto.getPrecio());
            stmt.setString(3, producto.getDescripcion());
            stmt.setInt(4, producto.getCantidad());
            stmt.setInt(5, producto.getId_producto());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Producto WHERE id_producto = ?";
        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // Método adicional para verificar si un producto existe en la base de datos por su nombre
    public boolean productoExiste(String nombre) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Producto WHERE nombre = ?";
        try (Connection conn = conexion.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;  // Devuelve true si ya existe
            }
        }
        return false;
    }
}
