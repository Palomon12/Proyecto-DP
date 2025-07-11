package CampoCodigo;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Conexión única y reutilizable a la BD (Patrón Singleton).
 */
public final class CConexion {

    private static volatile CConexion instancia;      // 1) única instancia
    private Connection conexion;                      // 2) estado interno
    private static final Object lock = new Object();  // 3) para threads
    private static boolean mensajeMostrado = false;   // Nuevo indicador

    /**
     * Constructor privado: evita new desde fuera
     */
    private CConexion() throws SQLException {
        inicializarConexion();
    }

    /**
     * Punto de acceso global
     */
    public static CConexion getInstancia() throws SQLException {
        if (instancia == null) {          // 1ᵉʳ chequeo (rápido)
            synchronized (lock) {         // bloqueamos solo si es necesario
                if (instancia == null) {  // 2ᵈᵒ chequeo (seguro)
                    instancia = new CConexion();
                }
            }
        }
        return instancia;
    }

    /**
     * Devuelve la conexión viva; la abre si se cerró.
     */
    public Connection getConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            inicializarConexion();
        }
        return conexion;
    }

    /* ------------------ helpers privados ------------------ */
    /**
     * Carga config.properties y abre la conexión
     */
    private void inicializarConexion() throws SQLException {
        Properties props = new Properties();
        try (InputStream input
                = CConexion.class.getClassLoader().getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new SQLException("config.properties no encontrado.");
            }
            props.load(input);

            String url = props.getProperty("db.url");
            String username = props.getProperty("db.username");
            String password = props.getProperty("db.password");

            conexion = DriverManager.getConnection(url, username, password);

            if (!mensajeMostrado) {
                System.out.println("Conexión BD establecida (Singleton).");
                mensajeMostrado = true;
            }

        } catch (IOException e) {
            throw new SQLException("No se pudo leer config.properties", e);
        }
    }
}
