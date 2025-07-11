package ClasesDAO;

import CampoCodigo.CConexion;
import CampoCodigo.Informe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InformeDAO {

    private final CConexion conexion;

    public InformeDAO() throws SQLException {
        this.conexion = CConexion.getInstancia();   // ← Singleton
    }

    // Método para calcular el total de ganancias de todos los pedidos
    public double calcularTotalGanancias() throws SQLException {
        String sql = "SELECT SUM(total) AS totalGanancias FROM Pedido";
        double totalGanancias = 0.0;

        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                totalGanancias = rs.getDouble("totalGanancias");
            }
        } catch (SQLException e) {
            System.out.println("Error al calcular el total de ganancias: " + e.getMessage());
            throw e;
        }
        return totalGanancias;
    }

    // Método para calcular el IGV de todas las ventas (por ejemplo, 18% del total)
    public double calcularIGV() throws SQLException {
        double totalGanancias = calcularTotalGanancias();
        return totalGanancias * 0.18; // 18% como ejemplo de IGV
    }

    // Método para contar el número total de pedidos realizados
    public int contarTotalPedidos() throws SQLException {
        String sql = "SELECT COUNT(*) AS totalPedidos FROM Pedido";
        int totalPedidos = 0;

        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                totalPedidos = rs.getInt("totalPedidos");
            }
        } catch (SQLException e) {
            System.out.println("Error al contar el total de pedidos: " + e.getMessage());
            throw e;
        }
        return totalPedidos;
    }

    // Método para generar un informe general en formato de texto
    public String generarInforme() throws SQLException {
        // Calcular los datos necesarios
        double totalGanancias = calcularTotalGanancias();
        double igv = calcularIGV();
        int totalPedidos = contarTotalPedidos();
        java.sql.Timestamp fechaGeneracion = new java.sql.Timestamp(System.currentTimeMillis());

        // Crear el formato del informe
        String informe = "----- Informe de Ventas -----\n"
                + "Total de Ganancias: $" + String.format("%.2f", totalGanancias) + "\n"
                + "IGV (18%): $" + String.format("%.2f", igv) + "\n"
                + "Total de Pedidos: " + totalPedidos + "\n"
                + "Fecha de Generación: " + java.time.LocalDateTime.now() + "\n"
                + "-----------------------------";

        // Mostrar el informe en consola
        System.out.println(informe);

        // Guardar el informe en la base de datos
        String sql = "INSERT INTO Informe (total_ganancias, igv, total_pedidos, fecha_generacion) VALUES (?, ?, ?, ?)";
        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, totalGanancias);
            stmt.setDouble(2, igv);
            stmt.setInt(3, totalPedidos);
            stmt.setTimestamp(4, fechaGeneracion);
            stmt.executeUpdate();
            System.out.println("El informe ha sido registrado en la base de datos.");
        } catch (SQLException e) {
            System.out.println("Error al guardar el informe: " + e.getMessage());
            throw e;
        }

        return informe;
    }

    public List<Informe> leerTodosLosInformes() throws SQLException {
        String sql = "SELECT * FROM Informe";
        List<Informe> informes = new ArrayList<>();

        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Informe informe = new Informe(
                        rs.getInt("id_informe"),
                        rs.getDouble("total_ganancias"),
                        rs.getDouble("igv"),
                        rs.getInt("total_pedidos"),
                        rs.getTimestamp("fecha_generacion")
                );
                informes.add(informe);
            }
        } catch (SQLException e) {
            System.out.println("Error al leer los informes: " + e.getMessage());
            throw e;
        }

        return informes;
    }

}
