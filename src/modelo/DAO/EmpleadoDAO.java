package modelo.DAO;

import modelo.Clases.Empleado;
import modelo.Persistencia.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Persistencia.PersistenciaArchivo;

public class EmpleadoDAO {

    public static boolean guardar(Empleado empleado) {
        PersistenciaArchivo persistenciaArchivo = new PersistenciaArchivo();
        String sql = "INSERT INTO empleados (nombre, documento, correo, rol, salario) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.obtenerConexion();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, empleado.getNombre());
            stmt.setString(2, empleado.getDocumento());
            stmt.setString(3, empleado.getCorreo());
            stmt.setString(4, empleado.getRol());
            stmt.setDouble(5, empleado.getSalario());

            int filas = stmt.executeUpdate();
            if (filas > 0) {
                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        empleado.setId(keys.getInt(1));
                    }
                }

                String linea = empleado.getId()
                        + "|" + empleado.getNombre()
                        + "|" + empleado.getDocumento()
                        + "|" + empleado.getCorreo()
                        + "|" + empleado.getRol()
                        + "|" + empleado.getSalario();

                persistenciaArchivo.guardarEmpleado(linea);

                return true;
            }
            return false;
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
                lista.add(mapearEmpleado(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar empleados: " + e.getMessage());
        }

        return lista;
    }

    public Empleado obtenerPorId(int id) {
        String sql = "SELECT id, nombre, documento, correo, rol, salario FROM empleados WHERE id = ?";

        try (Connection con = ConexionBD.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearEmpleado(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar empleado por ID: " + e.getMessage());
        }
        return null;
    }

    public Empleado obtenerPorDocumento(String documento) {
        String sql = "SELECT id, nombre, documento, correo, rol, salario FROM empleados WHERE documento = ?";

        try (Connection con = ConexionBD.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, documento);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearEmpleado(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar empleado por documento: " + e.getMessage());
        }
        return null;
    }

    public boolean actualizar(Empleado empleado) {
        String sql = "UPDATE empleados SET nombre = ?, documento = ?, correo = ?, rol = ?, salario = ? WHERE id = ?";

        try (Connection con = ConexionBD.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, empleado.getNombre());
            ps.setString(2, empleado.getDocumento());
            ps.setString(3, empleado.getCorreo());
            ps.setString(4, empleado.getRol());
            ps.setDouble(5, empleado.getSalario());
            ps.setInt(6, empleado.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar empleado: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM empleados WHERE id = ?";

        try (Connection con = ConexionBD.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar empleado: " + e.getMessage());
            return false;
        }
    }

    private Empleado mapearEmpleado(ResultSet rs) throws SQLException {
        return new Empleado(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("documento"),
                rs.getString("correo"),
                rs.getString("rol"),
                rs.getDouble("salario")
        );
    }
}
