package ClasesDAO;

import CampoCodigo.CConexion;
import CampoCodigo.Envio;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnvioDAO {

    private final CConexion conexion;

    public EnvioDAO() {
        this.conexion = new CConexion();
    }

    // Método para crear un nuevo envío en la base de datos
    public void crear(Envio envio) throws SQLException {
        String sql = "INSERT INTO Envio (estado, fechaEnvio, direccionEnvio) VALUES (?, ?, ?)";

        try (Connection conn = conexion.getConexion();
     PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

    stmt.setString(1, envio.getEstado());
    stmt.setDate(2, java.sql.Date.valueOf(envio.getFechaEnvio()));
    stmt.setString(3, envio.getDireccionEnvio());
    stmt.executeUpdate();

    // Obtener las claves generadas automáticamente
    try (ResultSet rs = stmt.getGeneratedKeys()) {
        if (rs.next()) {
            envio.setId_envio(rs.getInt(1));
        }
    }
    System.out.println("Envío creado correctamente con ID: " + envio.getId_envio());
} catch (SQLException e) {
    System.err.println("Error al crear el envío: " + e.getMessage());
    throw e;  // Relanzar la excepción para depuración
}
    }

    // Método para leer un envío específico por su id
    public Envio leer(int id) throws SQLException {
        String sql = "SELECT * FROM Envio WHERE id_envio = ?";
        Envio envio = null;

        try (Connection conn = conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                envio = new Envio(
                        rs.getInt("id_envio"),
                        rs.getString("estado"),
                        rs.getDate("fechaEnvio").toLocalDate(),
                        rs.getString("direccionEnvio")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al leer el envío: " + e.getMessage());
            throw e;
        }
        return envio;
    }

    // Método para actualizar un envío existente
    public boolean actualizar(Envio envio) throws SQLException {
        String sql = "UPDATE Envio SET estado = ?, fechaEnvio = ?, direccionEnvio = ? WHERE id_envio = ?";

        try (Connection conn = conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, envio.getEstado());
            stmt.setDate(2, Date.valueOf(envio.getFechaEnvio()));
            stmt.setString(3, envio.getDireccionEnvio());
            stmt.setInt(4, envio.getId_envio());

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Envío actualizado correctamente.");
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar el envío: " + e.getMessage());
            throw e;
        }
    }

    // Método para eliminar un envío por su id
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Envio WHERE id_envio = ?";

        try (Connection conn = conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Envío eliminado correctamente.");
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar el envío: " + e.getMessage());
            throw e;
        }
    }

    // Método para generar un comprobante de envío
    public String generarComprobanteEnvio(int idEnvio) throws SQLException {
        Envio envio = leer(idEnvio);  // Se obtiene el envío desde la base de datos
        if (envio == null) {
            System.out.println("Envío no encontrado.");
            return "No se encontró el envío con ID: " + idEnvio;
        }
        
        // Genera un comprobante en formato de texto
        String comprobante = """
                             ----- Comprobante de Envio -----
                             ID de Envio: """ + envio.getId_envio() + "\n" +
                             "Estado: " + envio.getEstado() + "\n" +
                             "Fecha de Envío: " + envio.getFechaEnvio() + "\n" +
                             "Dirección de Envío: " + envio.getDireccionEnvio() + "\n" +
                             "------------------------------";
        
        System.out.println(comprobante);
        return comprobante;
    }
}
