/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClasesDAO;

import CampoCodigo.CConexion;
import CampoCodigo.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO implements CrudDAO<Categoria> {

    private final CConexion conexion;

    public CategoriaDAO() {
        this.conexion = new CConexion();
    }

    @Override
    public boolean crear(Categoria categoria) throws SQLException {
        String getMaxIdSql = "SELECT MAX(id_categoria) FROM Categoria";
        String insertSql = "INSERT INTO Categoria (id_categoria, nombre) VALUES (?, ?)";

        try (
                Connection conn = conexion.getConexion(); PreparedStatement maxIdStmt = conn.prepareStatement(getMaxIdSql); PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
            // Obtener el siguiente ID disponible
            ResultSet rs = maxIdStmt.executeQuery();
            int nextId = 1;
            if (rs.next()) {
                nextId = rs.getInt(1) + 1;
            }

            categoria.setIdCategoria(nextId); // Establecer el nuevo ID

            // Insertar categoría
            insertStmt.setInt(1, categoria.getIdCategoria());
            insertStmt.setString(2, categoria.getNombre());

            insertStmt.executeUpdate();
            System.out.println("Categoría " + categoria.getNombre() + " (ID: " + nextId + ") guardada correctamente.");
            return true;
        } catch (SQLException e) {
            System.out.println("Error al guardar la categoría: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public Categoria leer(int id) throws SQLException {
        String sql = "SELECT * FROM Categoria WHERE id_categoria = ?";
        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Categoria(
                        rs.getInt("id_categoria"),
                        rs.getString("nombre")
                );
            }
        }
        return null;
    }

    @Override
    public List<Categoria> leerTodos() throws SQLException {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM Categoria";
        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Categoria categoria = new Categoria(
                        rs.getInt("id_categoria"),
                        rs.getString("nombre")
                );
                categorias.add(categoria);
            }
        }
        return categorias;
    }

    @Override
    public boolean actualizar(Categoria categoria) throws SQLException {
        String sql = "UPDATE Categoria SET nombre = ? WHERE id_categoria = ?";
        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNombre());
            stmt.setInt(3, categoria.getIdCategoria());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Categoria WHERE id_categoria = ?";
        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // Método adicional para verificar si una categoría existe en la base de datos por su nombre
    public boolean categoriaExiste(String nombre) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Categoria WHERE nombre = ?";
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
