package CampoCodigo;

import java.sql.SQLException;

public class AdministradorProxy implements AccesoAdministrador {

    private final Usuario usuario;

    public AdministradorProxy(Usuario usuario) {
        this.usuario = usuario;
    }

    private boolean esAdmin() {
        return usuario != null && usuario.getTipo().equalsIgnoreCase("Admin");
    }

    @Override
    public void gestionarUsuarios() throws SQLException {
        if (esAdmin()) {
            Administrador.gestionarUsuarios();  // Método estático
        } else {
            System.out.println("Acceso denegado. Solo para administradores.");
        }
    }

    @Override
    public void gestionarProductos() throws SQLException {
        if (esAdmin()) {
            Administrador.gestionarProductos();
        } else {
            System.out.println("Acceso denegado. Solo para administradores.");
        }
    }

    @Override
    public void gestionarCategorias() throws SQLException {
        if (esAdmin()) {
            Administrador.gestionarCategorias();
        } else {
            System.out.println("Acceso denegado. Solo para administradores.");
        }
    }

    @Override
    public void generarInforme() throws SQLException {
        if (esAdmin()) {
            Administrador.generarInforme();
        } else {
            System.out.println("Acceso denegado. Solo para administradores.");
        }
    }
}
