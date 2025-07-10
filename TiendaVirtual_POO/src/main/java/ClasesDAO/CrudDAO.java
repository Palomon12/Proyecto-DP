package ClasesDAO;

import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T> {

    boolean crear(T entidad) throws SQLException;

    T leer(int id) throws SQLException;

    List<T> leerTodos() throws SQLException;

    boolean actualizar(T entidad) throws SQLException;

    boolean eliminar(int id) throws SQLException;
}
