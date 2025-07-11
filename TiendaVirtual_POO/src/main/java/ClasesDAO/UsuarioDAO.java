package ClasesDAO;

import CampoCodigo.CConexion;
import CampoCodigo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements CrudDAO<Usuario> {

    private final CConexion conexion;

    public UsuarioDAO() throws SQLException {
        this.conexion = CConexion.getInstancia();   // ← Singleton
    }

    @Override
    public boolean crear(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO Usuario (nombre, email, contraseña, tipo, direccion, telefono) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getContraseña());
            stmt.setString(4, usuario.getTipo());
            stmt.setString(5, usuario.getDireccion());
            stmt.setInt(6, usuario.getTelefono());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Usuario " + usuario.getNombre() + " guardado correctamente.");
                return true;
            } else {
                System.out.println("Error al guardar el usuario.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error al guardar el usuario: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public Usuario leer(int id) throws SQLException {
        String sql = "SELECT * FROM Usuario WHERE id_usuario = ?";
        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("contraseña"),
                        rs.getString("tipo"),
                        rs.getString("direccion"),
                        rs.getInt("telefono")
                );
            }
        }
        return null;
    }

    public Usuario leerPorCredenciales(String correo, String password) throws SQLException {
        String sql = "SELECT * FROM Usuario WHERE email = ? AND contraseña = ?";
        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, correo);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Usamos un constructor si existe
                Usuario usuario = new Usuario(
                        rs.getInt("id_usuario"), // ID del usuario
                        rs.getString("nombre"), // Nombre
                        rs.getString("email"), // Correo
                        rs.getString("contraseña"), // Contraseña
                        rs.getString("tipo"), // Contraseña
                        rs.getString("direccion"), // Contraseña
                        rs.getInt("telefono") // Teléfono
                );
                return usuario;
            }
        }
        return null; // Si no se encuentra el usuario
    }

    @Override
    public List<Usuario> leerTodos() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuario";
        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("contraseña"),
                        rs.getString("tipo"),
                        rs.getString("direccion"),
                        rs.getInt("telefono")
                );
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    @Override
    public boolean actualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE Usuario SET nombre = ?, email = ?, contraseña = ? , tipo = ? , direccion = ? , telefono = ? WHERE id_usuario = ?";
        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuario.getId_usuario());
            stmt.setString(2, usuario.getNombre());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getContraseña());
            stmt.setString(5, usuario.getTipo());
            stmt.setString(6, usuario.getDireccion());
            stmt.setInt(7, usuario.getTelefono());
            return stmt.executeUpdate() > 0;
        }
    }

    public Usuario obtenerUsuarioPorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM Usuario WHERE email = ?";
        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("contraseña"),
                        rs.getString("tipo"),
                        rs.getString("direccion"),
                        rs.getInt("telefono")
                );
            }
        }
        return null; // Usuario no encontrado
    }

    @Override
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Usuario WHERE id_usuario = ?";
        try (Connection conn = conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // Método adicional para verificar si un usuario existe en la base de datos por su correo electrónico
    public boolean usuarioExiste(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Usuario WHERE email = ?";
        try (Connection conn = conexion.getConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;  // Devuelve true si ya existe
            }
        }
        return false;
    }

}
