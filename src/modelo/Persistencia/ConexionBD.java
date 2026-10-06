package modelo.Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    // Parámetros de conexión a MariaDB/MySQL
    private static final String URL = "jdbc:mariadb://localhost:3306/crediya_db?serverTimezone=UTC";
    private static final String USUARIO = "admin";
    private static final String CLAVE = "1234";

    // Método estático para obtener la conexión
    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }

    public static void main(String[] args) {
        try (Connection conn = obtenerConexion()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("¡Conexión exitosa a la base de datos crediya_db!");
            }
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
            System.err.println("Revisa el usuario, la contraseña o que el servicio de MariaDB/MySQL esté activo.");
        }
    }
}