package vista;

import modelo.Clases.Pago;
import modelo.Clases.Prestamo;
import modelo.DAO.PagoDAO;
import modelo.DAO.PrestamoDAO;
import modelo.Servicios.PrestamoServicio;

import java.util.List;

public class MenuPago {

    private static final PagoDAO pagoDAO = new PagoDAO();
    private static final PrestamoDAO prestamoDAO = new PrestamoDAO();
    private static final PrestamoServicio prestamoServicio = new PrestamoServicio();

    public static void mostrar() {
        int opcion;
        do {
            System.out.println("\n===== MÓDULO DE PAGOS =====");
            System.out.println("1. Registrar pago");
            System.out.println("2. Listar pagos");
            System.out.println("3. Buscar pago por ID");
            System.out.println("4. Ver pagos de un préstamo");
            System.out.println("5. Actualizar pago");
            System.out.println("6. Eliminar pago");
            System.out.println("7. Volver al Menú Principal");

            opcion = ConsolUtils.leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    registrarPago();
                    break;
                case 2:
                    listarPagos();
                    break;
                case 3:
                    buscarPagoPorId();
                    break;
                case 4:
                    verPagosDePrestamo();
                    break;
                case 5:
                    actualizarPago();
                    break;
                case 6:
                    eliminarPago();
                    break;
                case 7:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida, intente de nuevo.");
            }
        } while (opcion != 7);
    }

    private static void registrarPago() {
        System.out.println("\n--- Registrar Nuevo Pago ---");
        int idPrestamo = ConsolUtils.leerEntero("Ingrese el ID del Préstamo: ");
        Prestamo prestamo = prestamoDAO.obtenerPorId(idPrestamo);

        if (prestamo == null) {
            System.out.println("No existe un préstamo con el ID " + idPrestamo);
            return;
        }

        double monto = ConsolUtils.leerDouble("Monto del pago: ");

        if (monto <= 0) {
            System.out.println("El monto del pago debe ser mayor que 0.");
            return;
        }

        if (monto > prestamo.getSaldoPendiente()) {
            System.out.printf(
                    "El pago no puede ser mayor al saldo pendiente: $%.2f%n",
                    prestamo.getSaldoPendiente()
            );
            return;
        }

        Pago nuevo = new Pago(idPrestamo, monto);

        if (pagoDAO.guardar(nuevo)) {
            List<Pago> pagos = pagoDAO.obtenerPorPrestamo(idPrestamo);
            prestamoServicio.calcularSaldoPendiente(prestamo, pagos);

            System.out.println("¡Pago registrado con éxito! ID generado: " + nuevo.getId());
            System.out.printf("Saldo pendiente: $%.2f%n", prestamo.getSaldoPendiente());
            System.out.println("Estado del préstamo: " + prestamo.getEstado());
        } else {
            System.out.println("Error al registrar el pago en la base de datos.");
        }
    }

    private static void listarPagos() {
        System.out.println("\n--- Listado General de Pagos ---");
        List<Pago> lista = pagoDAO.obtenerTodos();

        if (lista.isEmpty()) {
            System.out.println("No hay pagos registrados.");
        } else {
            for (Pago pago : lista) {
                mostrarPago(pago);
            }
        }
    }

    private static void buscarPagoPorId() {
        System.out.println("\n--- Buscar Pago por ID ---");
        int id = ConsolUtils.leerEntero("Ingrese el ID del Pago: ");
        Pago pago = pagoDAO.obtenerPorId(id);

        if (pago == null) {
            System.out.println("No existe un pago con el ID " + id);
            return;
        }

        mostrarPago(pago);
    }

    private static void verPagosDePrestamo() {
        System.out.println("\n--- Pagos de un Préstamo ---");
        int idPrestamo = ConsolUtils.leerEntero("Ingrese el ID del Préstamo: ");
        Prestamo prestamo = prestamoDAO.obtenerPorId(idPrestamo);

        if (prestamo == null) {
            System.out.println("No existe un préstamo con el ID " + idPrestamo);
            return;
        }

        List<Pago> lista = pagoDAO.obtenerPorPrestamo(idPrestamo);

        if (lista.isEmpty()) {
            System.out.println("El préstamo " + idPrestamo + " no tiene pagos asociados.");
        } else {
            System.out.println("\n--- Pagos del préstamo " + idPrestamo + " ---");
            for (Pago pago : lista) {
                mostrarPago(pago);
            }
        }
    }

    private static void actualizarPago() {
        System.out.println("\n--- Actualizar Pago ---");
        int id = ConsolUtils.leerEntero("Ingrese el ID del Pago a modificar: ");
        Pago pago = pagoDAO.obtenerPorId(id);

        if (pago == null) {
            System.out.println("No existe un pago con el ID " + id);
            return;
        }

        System.out.println("Datos actuales:");
        mostrarPago(pago);
        System.out.println("Deje el campo vacío para conservar el valor actual.");

        String idPrestamoTexto = ConsolUtils.leerTexto("Nuevo ID de préstamo (actual: " + pago.getIdPrestamo() + "): ");
        if (!idPrestamoTexto.isEmpty()) {
            try {
                int nuevoIdPrestamo = Integer.parseInt(idPrestamoTexto);
                Prestamo prestamo = prestamoDAO.obtenerPorId(nuevoIdPrestamo);
                if (prestamo == null) {
                    System.out.println("No existe un préstamo con el ID " + nuevoIdPrestamo
                            + ". Se conserva el préstamo actual.");
                } else {
                    pago.setIdPrestamo(nuevoIdPrestamo);
                }
            } catch (NumberFormatException e) {
                System.out.println("ID de préstamo inválido. Se conserva el valor actual.");
            }
        }

        String montoTexto = ConsolUtils.leerTexto("Nuevo monto (actual: " + pago.getMonto() + "): ");
        if (!montoTexto.isEmpty()) {
            try {
                pago.setMonto(Double.parseDouble(montoTexto));
            } catch (NumberFormatException e) {
                System.out.println("Monto inválido. Se conserva el valor actual.");
            }
        }

        if (pagoDAO.actualizar(pago)) {
            System.out.println("¡Pago actualizado exitosamente!");
        } else {
            System.out.println("Error al actualizar el pago.");
        }
    }

    private static void eliminarPago() {
        System.out.println("\n--- Eliminar Pago ---");
        int id = ConsolUtils.leerEntero("Ingrese el ID del Pago a eliminar: ");
        Pago pago = pagoDAO.obtenerPorId(id);

        if (pago == null) {
            System.out.println("No existe un pago con el ID " + id);
            return;
        }

        mostrarPago(pago);
        String confirmacion = ConsolUtils.leerTexto("¿Confirmar eliminación? (S/N): ");
        if (!confirmacion.equalsIgnoreCase("S")) {
            System.out.println("Eliminación cancelada.");
            return;
        }

        if (pagoDAO.eliminar(id)) {
            System.out.println("Pago eliminado correctamente.");
        } else {
            System.out.println("Error al eliminar el pago.");
        }
    }

    private static void mostrarPago(Pago pago) {
        System.out.printf(
                "ID Pago: %d | ID Préstamo: %d | Fecha: %s | Monto: $%.2f%n",
                pago.getId(),
                pago.getIdPrestamo(),
                pago.getFechaPago(),
                pago.getMonto()
        );
    }
}
