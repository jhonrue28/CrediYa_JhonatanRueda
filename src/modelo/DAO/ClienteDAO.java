package modelo.DAO;

import modelo.Clases.Cliente;
import modelo.Persistencia.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Persistencia.PersistenciaArchivo;

public class ClienteDAO {

    public static boolean guardar(Cliente cliente) {
        PersistenciaArchivo persistenciaArchivo = new PersistenciaArchivo();
        String sql = "INSERT INTO clientes (nombre, documento, correo, telefono) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionBD.obtenerConexion();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getDocumento());
            stmt.setString(3, cliente.getCorreo());
            stmt.setString(4, cliente.getTelefono());

            int filas = stmt.executeUpdate();
            if (filas > 0) {
                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        cliente.setId(keys.getInt(1));
                    }
                }

                String linea = cliente.getId()
                        + "|" + cliente.getNombre()
                        + "|" + cliente.getDocumento()
                        + "|" + cliente.getCorreo()
                        + "|" + cliente.getTelefono();

                persistenciaArchivo.guardarCliente(linea);

                return true;
            }
            return false;
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
                lista.add(mapearCliente(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar clientes: " + e.getMessage());
        }

        return lista;
    }

    public Cliente obtenerPorId(int id) {
        String sql = "SELECT id, nombre, documento, correo, telefono FROM clientes WHERE id = ?";

        try (Connection con = ConexionBD.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente por ID: " + e.getMessage());
        }
        return null;
    }

    public Cliente obtenerPorDocumento(String documento) {
        String sql = "SELECT id, nombre, documento, correo, telefono FROM clientes WHERE documento = ?";

        try (Connection con = ConexionBD.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, documento);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente por documento: " + e.getMessage());
        }
        return null;
    }

    public boolean actualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET nombre = ?, documento = ?, correo = ?, telefono = ? WHERE id = ?";

        try (Connection con = ConexionBD.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getDocumento());
            ps.setString(3, cliente.getCorreo());
            ps.setString(4, cliente.getTelefono());
            ps.setInt(5, cliente.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";

        try (Connection con = ConexionBD.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }

    public void obtenerPrestamosPorCliente(String documento) {
        String sql = "SELECT c.nombre AS cliente, p.id AS prestamo_id, p.monto, p.interes, p.cuotas, p.estado "
                + "FROM clientes c "
                + "INNER JOIN prestamos p ON c.id = p.cliente_id "
                + "WHERE c.documento = ?";

        try (Connection conn = ConexionBD.obtenerConexion();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, documento);
            try (ResultSet rs = stmt.executeQuery()) {
                boolean tienePrestamos = false;
                System.out.println("\n--- Préstamos Registrados ---");

                while (rs.next()) {
                    if (!tienePrestamos) {
                        System.out.println("Cliente: " + rs.getString("cliente"));
                        tienePrestamos = true;
                    }
                    System.out.println("Nº Préstamo: " + rs.getInt("prestamo_id")
                            + " | Monto: $" + rs.getDouble("monto")
                            + " | Interés: " + rs.getDouble("interes") + "%"
                            + " | Cuotas: " + rs.getInt("cuotas")
                            + " | Estado: " + rs.getString("estado"));
                }

                if (!tienePrestamos) {
                    System.out.println("El cliente con documento " + documento
                            + " no tiene préstamos asociados o no existe.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar los préstamos del cliente: " + e.getMessage());
        }
    }

    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        return new Cliente(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("documento"),
                rs.getString("correo"),
                rs.getString("telefono")
        );
    }
}
