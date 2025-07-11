package ClasesDAO;

import CampoCodigo.Producto;
import CampoCodigo.Usuario;
import java.sql.SQLException;
import java.util.List;

public class ProductoDAOProxy implements CrudDAO<Producto> {

    private final ProductoDAO productoDAO;
    private final Usuario usuarioActual;

    public ProductoDAOProxy(Usuario usuario) throws SQLException {
        this.productoDAO = new ProductoDAO() ;
        this.usuarioActual = usuario;
    }

    @Override
    public boolean crear(Producto producto) throws SQLException {
        log("crear");

        if (!esAdmin()) {
            throw new SecurityException("Acceso denegado: solo los administradores pueden crear productos.");
        }
        return productoDAO.crear(producto);
    }

    @Override
    public Producto leer(int id) throws SQLException {
        log("leer");
        return productoDAO.leer(id);
    }

    @Override
    public List<Producto> leerTodos() throws SQLException {
        log("leerTodos");
        return productoDAO.leerTodos();
    }

    @Override
    public boolean actualizar(Producto producto) throws SQLException {
        log("actualizar");

        if (!esAdmin()) {
            throw new SecurityException("Acceso denegado: solo los administradores pueden actualizar productos.");
        }
        return productoDAO.actualizar(producto);
    }

    @Override
    public boolean eliminar(int id) throws SQLException {
        log("eliminar");

        if (!esAdmin()) {
            throw new SecurityException("Acceso denegado: solo los administradores pueden eliminar productos.");
        }
        return productoDAO.eliminar(id);
    }

    // ---------- Métodos auxiliares ----------
    private void log(String metodo) {
        System.out.println("[LOG] Usuario: " + usuarioActual.getEmail() + " ejecutó: " + metodo);
    }

    private boolean esAdmin() {
        return usuarioActual != null && "administrador".equalsIgnoreCase(usuarioActual.getTipo());
    }
}
