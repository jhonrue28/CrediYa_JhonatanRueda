package vista;

import modelo.Clases.Prestamo;
import modelo.DAO.PrestamoDAO;
import java.sql.Date;
import java.util.List;
import modelo.Clases.Cliente;
import modelo.DAO.ClienteDAO;

public class MenuReportes {
    private static final PrestamoDAO prestamoDAO = new PrestamoDAO();
    private static final ClienteDAO clienteDAO = new ClienteDAO();

    public static void mostrar() {
        int opcion;

        do {
            System.out.println("\n=================================");
            System.out.println("          REPORTES CREDIYA       ");
            System.out.println("=================================");
            System.out.println("1. Préstamos activos");
            System.out.println("2. Préstamos vencidos");
            System.out.println("3. Clientes morosos");
            System.out.println("4. Volver");

            opcion = ConsolUtils.leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    mostrarPrestamosActivos();
                    break;
                case 2:
                    mostrarPrestamosVencidos();
                    break;
                case 3:
                    mostrarClientesMorosos();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Opción no válida, intente de nuevo.");
            }

        } while (opcion != 4);
    }

    private static void mostrarPrestamosActivos() {
        System.out.println("\n--- Préstamos Activos ---");

        List<Prestamo> prestamos = prestamoDAO.obtenerTodos();

        prestamos.stream()
                .filter(p -> p.getEstado().equals("PENDIENTE"))
                .forEach(p -> System.out.printf(
                        "ID: %d | Cliente: %d | Saldo pendiente: $%.2f%n",
                        p.getId(),
                        p.getIdCliente(),
                        p.getSaldoPendiente()
                ));
    }

    private static void mostrarPrestamosVencidos() {
        System.out.println("\n--- Préstamos Vencidos ---");

        List<Prestamo> prestamos = prestamoDAO.obtenerTodos();
        Date hoy = new Date(System.currentTimeMillis());
        System.out.println("Fecha actual " + hoy + " todos aquellos prestamos antes de esta fecha son vencidos");

        prestamos.stream()
                .filter(p -> p.getEstado().equals("PENDIENTE"))
                .filter(p -> p.getFechaInicio().toString().compareTo(hoy.toString()) < 0)
                .forEach(p -> System.out.printf(
                        "ID: %d | Cliente: %d | Fecha inicio: %s | Saldo pendiente: $%.2f%n",
                        p.getId(),
                        p.getIdCliente(),
                        p.getFechaInicio(),
                        p.getSaldoPendiente()
                ));
    }

    private static void mostrarClientesMorosos() {
        System.out.println("\n--- Clientes Morosos ---");

        List<Prestamo> prestamos = prestamoDAO.obtenerTodos();
        List<Cliente> clientes = clienteDAO.obtenerTodos();

        prestamos.stream()
                .filter(p -> p.getEstado().equals("PENDIENTE"))
                .filter(p -> p.getFechaInicio().toString().compareTo(
                        new Date(System.currentTimeMillis()).toString()
                ) < 0)
                .forEach(p -> clientes.stream()
                        .filter(c -> c.getId() == p.getIdCliente())
                        .forEach(c -> System.out.printf(
                                "ID: %d | Nombre: %s | Documento: %s | Préstamo vencido: %d%n",
                                c.getId(),
                                c.getNombre(),
                                c.getDocumento(),
                                p.getId()
                        ))
                );
    }
}