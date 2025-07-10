package CampoCodigo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CConexion {
    private static Connection conexion;
    private static boolean mensajeMostrado = false; // Nuevo indicador

    public static Connection getConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            inicializarConexion();
        }
        return conexion;
    }

    private static void inicializarConexion() throws SQLException {
        Properties props = new Properties();
        try (InputStream input = CConexion.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new SQLException("Error al cargar el archivo de configuración: config.properties no encontrado.");
            }
            props.load(input);
        } catch (IOException e) {
            throw new SQLException("No se pudo cargar la configuración de la base de datos: " + e.getMessage());
        }

        String url = props.getProperty("db.url");
        String username = props.getProperty("db.username");
        String password = props.getProperty("db.password");

        try {
            
            conexion = DriverManager.getConnection(url, username, password);

            // Solo imprimir el mensaje la primera vez
            if (!mensajeMostrado) {
                System.out.println("Conexión inicial establecida con la base de datos.");
                mensajeMostrado = true; // Evitar que el mensaje vuelva a mostrarse
            }
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
