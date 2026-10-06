package modelo.DAO;

import modelo.Clases.Pago;
import modelo.Persistencia.ConexionBD;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Persistencia.PersistenciaArchivo;

public class PagoDAO {

    public boolean guardar(Pago pago) {
        PersistenciaArchivo persistenciaArchivo = new PersistenciaArchivo();

        String sql = "INSERT INTO pagos (prestamo_id, fecha_pago, monto) VALUES (?, ?, ?)";

        try (Connection con = ConexionBD.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            Date fechaPago = pago.getFechaPago();
            if (fechaPago == null) {
                fechaPago = new Date(System.currentTimeMillis());
                pago.setFechaPago(fechaPago);
            }

            ps.setInt(1, pago.getIdPrestamo());
            ps.setDate(2, fechaPago);
            ps.setDouble(3, pago.getMonto());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        pago.setId(keys.getInt(1));
                    }
                }
                String linea = pago.getId()
                    + "|" + pago.getIdPrestamo()
                    + "|" + pago.getFechaPago()
                    + "|" + pago.getMonto();

                persistenciaArchivo.guardarPago(linea);
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error al registrar pago: " + e.getMessage());
            return false;
        }
    }

    public List<Pago> obtenerTodos() {
        List<Pago> lista = new ArrayList<>();
        String sql = "SELECT id, prestamo_id, fecha_pago, monto FROM pagos";

        try (Connection con = ConexionBD.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearPago(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar pagos: " + e.getMessage());
        }
        return lista;
    }

    public Pago obtenerPorId(int id) {
        String sql = "SELECT id, prestamo_id, fecha_pago, monto FROM pagos WHERE id = ?";

        try (Connection con = ConexionBD.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearPago(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar pago por ID: " + e.getMessage());
            return null;
        }
        return null;
    }

    public List<Pago> obtenerPorPrestamo(int idPrestamo) {
        List<Pago> lista = new ArrayList<>();
        String sql = "SELECT id, prestamo_id, fecha_pago, monto FROM pagos WHERE prestamo_id = ?";

        try (Connection con = ConexionBD.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPrestamo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearPago(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar pagos por préstamo: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizar(Pago pago) {
        String sql = "UPDATE pagos SET prestamo_id = ?, fecha_pago = ?, monto = ? WHERE id = ?";

        try (Connection con = ConexionBD.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {

            Date fechaPago = pago.getFechaPago();
            if (fechaPago == null) {
                fechaPago = new Date(System.currentTimeMillis());
            }

            ps.setInt(1, pago.getIdPrestamo());
            ps.setDate(2, fechaPago);
            ps.setDouble(3, pago.getMonto());
            ps.setInt(4, pago.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar pago: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM pagos WHERE id = ?";

        try (Connection con = ConexionBD.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar pago: " + e.getMessage());
            return false;
        }
    }

    private Pago mapearPago(ResultSet rs) throws SQLException {
        return new Pago(
                rs.getInt("id"),
                rs.getInt("prestamo_id"),
                rs.getDate("fecha_pago"),
                rs.getDouble("monto")
        );
    }
}
