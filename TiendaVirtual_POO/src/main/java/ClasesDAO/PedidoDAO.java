package ClasesDAO;

import CampoCodigo.CConexion;
import CampoCodigo.Pedido;
import CampoCodigo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO implements CrudDAO<Pedido> {

    private final CConexion conexion;

    public PedidoDAO() throws SQLException {
        this.conexion = CConexion.getInstancia();   // ← Singleton
    }

    @Override
    public boolean crear(Pedido pedido) throws SQLException {
        String sql = "INSERT INTO Pedido (id_usuario, estado, fecha_pedido, total) OUTPUT INSERTED.id_pedido VALUES (?, ?, ?, ?)";
        try (Connection conn = conexion.getConexion(); 
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Validar que el usuario no sea nulo y que tenga un ID válido
            if (pedido.getUsuario() == null || pedido.getUsuario().getId_usuario() <= 0) {
                throw new SQLException("El usuario asociado al pedido no es válido.");
            }

            // Asignar parámetros
            stmt.setInt(1, pedido.getUsuario().getId_usuario());
            stmt.setString(2, pedido.getEstado());
            stmt.setDate(3, new java.sql.Date(pedido.getFecha_Pedido().getTime()));
            stmt.setDouble(4, pedido.getTotal());

            // Ejecutar la consulta y obtener el ID generado
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    pedido.setId_Pedido(rs.getInt(1)); // Asignar el ID al objeto
                }
            }
            System.out.println("Pedido guardado correctamente.");
            return true;
        } catch (SQLException e) {
            System.out.println("Error al crear el pedido: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public Pedido leer(int id) throws SQLException {
        String sql = "SELECT * FROM Pedido WHERE id_pedido = ?";
        Pedido pedido = null;

        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("contraseña"),
                        rs.getString("direccion"), rs.getString("direccion"),
                        rs.getInt("telefono"));
                pedido = new Pedido(
                        rs.getInt("id_pedido"),
                        usuario,
                        rs.getDate("fecha_pedido"));
                pedido.setEstado(rs.getString("estado"));
                pedido.setTotal(rs.getDouble("total"));
            }
        }
        return pedido;
    }

    @Override
    public List<Pedido> leerTodos() throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM Pedido";
        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Usuario usuario = new Usuario(rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("contraseña"),
                        rs.getString("direccion"), rs.getString("direccion"),
                        rs.getInt("telefono"));
                Pedido pedido = new Pedido(
                        rs.getInt("id_pedido"),
                        usuario,
                        rs.getDate("fecha_pedido")
                );
                pedido.setEstado(rs.getString("estado"));
                pedido.setTotal(rs.getDouble("total"));
                pedidos.add(pedido);
            }
        }
        return pedidos;
    }

    @Override
    public boolean actualizar(Pedido pedido) throws SQLException {
        String sql = "UPDATE Pedido SET estado = ?, total = ? WHERE id_pedido = ?";
        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pedido.getEstado());
            stmt.setDouble(2, pedido.getTotal());
            stmt.setInt(3, pedido.getId_Pedido());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Pedido WHERE id_pedido = ?";
        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}
