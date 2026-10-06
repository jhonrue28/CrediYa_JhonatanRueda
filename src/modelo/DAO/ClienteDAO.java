package modelo.DAO;

import modelo.Clases.Cliente;
import modelo.Persistencia.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public static boolean guardar(Cliente cliente) {
        String sql = "INSERT INTO clientes (nombre, documento, correo, telefono) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getDocumento());
            stmt.setString(3, cliente.getCorreo());
            stmt.setString(4, cliente.getTelefono());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al guardar cliente en la BD: " + e.getMessage());
            return false;
        }
    }

    public List<Cliente> obtenerTodos() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, documento, correo, telefono FROM clientes";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cli = new Cliente(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("documento"),
                        rs.getString("correo"),
                        rs.getString("telefono")
                );
                lista.add(cli);
            }

        } catch (SQLException e) {
            System.err.println("Error al consultar clientes: " + e.getMessage());
        }

        return lista;
    }
    public void obtenerPrestamosPorCliente(String documento) {
        String sql = "SELECT c.nombre AS cliente, p.id AS prestamo_id, p.monto, p.interes, p.cuotas, p.estado " +
                "FROM clientes c " +
                "INNER JOIN prestamos p ON c.id = p.cliente_id " +
                "WHERE c.documento = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, documento);
            ResultSet rs = stmt.executeQuery();

            boolean tienePrestamos = false;
            System.out.println("\n--- Préstamos Registrados ---");

            while (rs.next()) {
                if (!tienePrestamos) {
                    System.out.println("Cliente: " + rs.getString("cliente"));
                    tienePrestamos = true;
                }
                System.out.println("Nº Préstamo: " + rs.getInt("prestamo_id") +
                        " | Monto: $" + rs.getDouble("monto") +
                        " | Interés: " + rs.getDouble("interes") + "%" +
                        " | Cuotas: " + rs.getInt("cuotas") +
                        " | Estado: " + rs.getString("estado"));
            }

            if (!tienePrestamos) {
                System.out.println("El cliente con documento " + documento + " no tiene préstamos asociados o no existe.");
            }

        } catch (SQLException e) {
            System.err.println("Error al consultar los préstamos del cliente: " + e.getMessage());
        }
    }
}
