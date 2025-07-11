
package ClasesDAO;

import CampoCodigo.CConexion;
import CampoCodigo.Pago;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PagoDAO {

    private final CConexion conexion;

     public PagoDAO() throws SQLException {
        this.conexion = CConexion.getInstancia();   // ← Singleton
    }
    // Método para crear un nuevo pago en la base de datos
    public boolean crear(Pago pago) throws SQLException {
        String sql = "INSERT INTO Pago (id_Pago, monto, fechaPago) VALUES (?, ?, ?)";

        try (Connection conn = conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pago.getId_Pago());
            stmt.setDouble(2, pago.getMonto());
            stmt.setDate(3, new Date(pago.getFechaPago().getTime()));

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Pago registrado correctamente.");
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error al crear el pago: " + e.getMessage());
            throw e;
        }
    }

    // Método para leer un pago específico por su id
    public Pago leer(int id) throws SQLException {
        String sql = "SELECT * FROM Pago WHERE id_Pago = ?";
        Pago pago = null;

        try (Connection conn = conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                pago = new Pago(
                        rs.getInt("id_Pago"),
                        rs.getDouble("monto")
                );
                pago.setFechaPago(rs.getDate("fechaPago"));
            }
        } catch (SQLException e) {
            System.out.println("Error al leer el pago: " + e.getMessage());
            throw e;
        }
        return pago;
    }

    // Método para actualizar un pago existente
    public boolean actualizar(Pago pago) throws SQLException {
        String sql = "UPDATE Pago SET monto = ?, fechaPago = ? WHERE id_Pago = ?";

        try (Connection conn = conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, pago.getMonto());
            stmt.setDate(2, new Date(pago.getFechaPago().getTime()));
            stmt.setInt(3, pago.getId_Pago());

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Pago actualizado correctamente.");
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar el pago: " + e.getMessage());
            throw e;
        }
    }

    // Método para eliminar un pago por su id
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Pago WHERE id_Pago = ?";

        try (Connection conn = conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Pago eliminado correctamente.");
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar el pago: " + e.getMessage());
            throw e;
        }
    }

    // Método para generar un recibo de pago
    public String generarRecibo(int idPago) throws SQLException {
        Pago pago = leer(idPago);  // Se obtiene el pago desde la base de datos
        if (pago == null) {
            System.out.println("Pago no encontrado.");
            return "No se encontró el pago con ID: " + idPago;
        }
        
        // Genera un recibo en formato de texto
        String recibo = "----- Recibo de Pago -----\n" +
                        "ID de Pago: " + pago.getId_Pago() + "\n" +
                        "Monto: $" + pago.getMonto() + "\n" +
                        "Fecha de Pago: " + pago.getFechaPago() + "\n" +
                        "--------------------------";
        
        System.out.println(recibo);
        return recibo;
    }
}
