package modelo.DAO;

import modelo.Clases.Prestamo;
import modelo.Persistencia.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {

    public boolean guardar(Prestamo prestamo) {
        String sql = "INSERT INTO prestamos (id_cliente, monto, interes, cuotas, estado) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, prestamo.getIdCliente());
            ps.setDouble(2, prestamo.getMonto());
            ps.setDouble(3, prestamo.getInteres());
            ps.setInt(4, prestamo.getCuotas());
            ps.setString(5, prestamo.getEstado());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar préstamo: " + e.getMessage());
            return false;
        }
    }

    public List<Prestamo> obtenerTodos() {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT * FROM prestamos";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Prestamo(
                        rs.getInt("id"),
                        rs.getInt("id_cliente"),
                        rs.getDouble("monto"),
                        rs.getDouble("interes"),
                        rs.getInt("cuotas"),
                        rs.getString("estado"),
                        rs.getDate("fecha")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar préstamos: " + e.getMessage());
        }
        return lista;
    }
    public List<Prestamo> obtenerPorIdCliente(int idCliente) {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT * FROM prestamos WHERE id_cliente = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Prestamo(
                            rs.getInt("id"),
                            rs.getInt("id_cliente"),
                            rs.getDouble("monto"),
                            rs.getDouble("interes"),
                            rs.getInt("cuotas"),
                            rs.getString("estado"),
                            rs.getDate("fecha")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar préstamos por ID del cliente: " + e.getMessage());
        }
        return lista;
    }
    public boolean actualizarEstado(int id, String nuevoEstado) {
        String sql = "UPDATE prestamos SET estado = ? WHERE id = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar estado del préstamo: " + e.getMessage());
            return false;
        }
    }
    public Prestamo obtenerPorId(int id) {
        String sql = "SELECT * FROM prestamos WHERE id = ?";
        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Prestamo(
                            rs.getInt("id"),
                            rs.getInt("id_cliente"),
                            rs.getDouble("monto"),
                            rs.getDouble("interes"),
                            rs.getInt("cuotas"),
                            rs.getString("estado"),
                            rs.getDate("fecha")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar préstamo por ID: " + e.getMessage());
        }
        return null;
    }
}