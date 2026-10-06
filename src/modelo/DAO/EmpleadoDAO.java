package modelo.DAO;

import modelo.Clases.Empleado;
import modelo.Persistencia.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    public static boolean guardar(Empleado empleado) {
        String sql = "INSERT INTO empleados (nombre, documento, correo, rol, salario) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, empleado.getNombre());
            stmt.setString(2, empleado.getDocumento());
            stmt.setString(3, empleado.getCorreo());
            stmt.setString(4, empleado.getRol());
            stmt.setDouble(5, empleado.getSalario());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al guardar el empleado en la BD: " + e.getMessage());
            return false;
        }
    }

    public List<Empleado> obtenerTodos() {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, documento, correo, rol, salario FROM empleados";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Empleado emp = new Empleado(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("documento"),
                        rs.getString("correo"),
                        rs.getString("rol"),
                        rs.getDouble("salario")
                );
                lista.add(emp);
            }

        } catch (SQLException e) {
            System.err.println("Error al consultar empleados: " + e.getMessage());
        }

        return lista;
    }
}