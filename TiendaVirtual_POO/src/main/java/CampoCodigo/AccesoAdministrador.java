package CampoCodigo;

import java.sql.SQLException;

public interface AccesoAdministrador {
    void gestionarUsuarios() throws SQLException;
    void gestionarProductos() throws SQLException;
    void gestionarCategorias() throws SQLException;
    void generarInforme() throws SQLException;
}
