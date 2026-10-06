package vista;

import modelo.Clases.Cliente;
import modelo.Clases.Prestamo;
import modelo.DAO.ClienteDAO;
import modelo.DAO.PrestamoDAO;

import java.util.List;
import java.util.Scanner;

public class MenuPrestamos {

    private static final PrestamoDAO prestamoDAO = new PrestamoDAO();
    private static final ClienteDAO clienteDAO = new ClienteDAO();
    private static final Scanner scanner = new Scanner(System.in);

    public static void mostrar() {
        int opcion = 0;
        do {
            System.out.println("\n===== MÓDULO DE PRÉSTAMOS =====");
            System.out.println("1. Crear préstamo");
            System.out.println("2. Listar préstamos");
            System.out.println("3. Consultar préstamos de un cliente");
            System.out.println("4. Cambiar estado de un préstamo");
            System.out.println("5. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcion = 0;
            }

            switch (opcion) {
                case 1 -> crearPrestamo();
                case 2 -> listarPrestamos();
                case 3 -> consultarPrestamosCliente();
                case 4 -> cambiarEstado();
                case 5 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida, intente de nuevo.");
            }
        } while (opcion != 5);
    }

    // Submenú reutilizable para seleccionar la búsqueda del cliente por ID (1) o Documento (2)
    private static Cliente seleccionarCliente() {
        System.out.println("\n--- Buscar Cliente ---");
        System.out.println("1. Buscar por ID del Cliente");
        System.out.println("2. Buscar por Documento del Cliente");
        System.out.print("Seleccione una opción (1 o 2): ");

        int opcionBusqueda;
        try {
            opcionBusqueda = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Opción inválida.");
            return null;
        }

        Cliente cliente = null;
        if (opcionBusqueda == 1) {
            System.out.print("Ingrese el ID del Cliente: ");
            try {
                int idCliente = Integer.parseInt(scanner.nextLine());
                cliente = clienteDAO.obtenerPorId(idCliente);
            } catch (NumberFormatException e) {
                System.out.println("El ID debe ser un número entero.");
            }
        } else if (opcionBusqueda == 2) {
            System.out.print("Ingrese el Documento del Cliente: ");
            String documento = scanner.nextLine();
            cliente = clienteDAO.obtenerPorDocumento(documento);
        } else {
            System.out.println("Opción de búsqueda no válida.");
        }

        if (cliente == null) {
            System.out.println("Cliente no encontrado en el sistema.");
        }

        return cliente;
    }

    private static void crearPrestamo() {
        System.out.println("\n--- Crear Nuevo Préstamo ---");
        Cliente cliente = seleccionarCliente();

        if (cliente == null) return;

        try {
            System.out.print("Monto: ");
            double monto = Double.parseDouble(scanner.nextLine());

            System.out.print("Interés (%): ");
            double interes = Double.parseDouble(scanner.nextLine());

            System.out.print("Número de cuotas: ");
            int cuotas = Integer.parseInt(scanner.nextLine());

            Prestamo nuevo = new Prestamo(cliente.getId(), monto, interes, cuotas);

            if (prestamoDAO.guardar(nuevo)) {
                System.out.println("¡Préstamo creado con éxito para " + cliente.getNombre() + "!");
            } else {
                System.out.println("Error al registrar el préstamo en la base de datos.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingrese valores numéricos válidos.");
        }
    }

    private static void listarPrestamos() {
        System.out.println("\n--- Listado General de Préstamos ---");
        List<Prestamo> lista = prestamoDAO.obtenerTodos();

        if (lista.isEmpty()) {
            System.out.println("No hay préstamos registrados.");
        } else {
            for (Prestamo p : lista) {
                System.out.printf("ID Préstamo: %d | ID Cliente: %d | Monto: $%.2f | Interés: %.1f%% | Cuotas: %d | Estado: %s | Fecha: %s\n",
                        p.getId(), p.getIdCliente(), p.getMonto(), p.getInteres(), p.getCuotas(), p.getEstado(), p.getFecha());
            }
        }
    }

    private static void consultarPrestamosCliente() {
        Cliente cliente = seleccionarCliente();
        if (cliente == null) return;

        List<Prestamo> lista = prestamoDAO.obtenerPorIdCliente(cliente.getId());

        if (lista.isEmpty()) {
            System.out.println("El cliente " + cliente.getNombre() + " no tiene préstamos asociados.");
        } else {
            System.out.println("\n--- Préstamos de " + cliente.getNombre() + " ---");
            for (Prestamo p : lista) {
                System.out.printf("ID Préstamo: %d | Monto: $%.2f | Interés: %.1f%% | Cuotas: %d | Estado: %s | Fecha: %s\n",
                        p.getId(), p.getMonto(), p.getInteres(), p.getCuotas(), p.getEstado(), p.getFecha());
            }
        }
    }

    private static void cambiarEstado() {
        System.out.println("\n--- Cambiar Estado de Préstamo ---");
        System.out.print("Ingrese el ID del Préstamo a modificar: ");

        try {
            int idPrestamo = Integer.parseInt(scanner.nextLine());
            Prestamo p = prestamoDAO.obtenerPorId(idPrestamo);

            if (p == null) {
                System.out.println("No existe un préstamo con el ID " + idPrestamo);
                return;
            }

            System.out.println("Estado actual: " + p.getEstado());
            System.out.println("Seleccione el nuevo estado:");
            System.out.println("1. PENDIENTE");
            System.out.println("2. PAGADO");
            System.out.println("3. CANCELADO");
            System.out.print("Opción: ");

            int op = Integer.parseInt(scanner.nextLine());
            String nuevoEstado = switch (op) {
                case 1 -> "PENDIENTE";
                case 2 -> "PAGADO";
                case 3 -> "CANCELADO";
                default -> null;
            };

            if (nuevoEstado != null) {
                if (prestamoDAO.actualizarEstado(idPrestamo, nuevoEstado)) {
                    System.out.println("¡Estado actualizado exitosamente a " + nuevoEstado + "!");
                } else {
                    System.out.println("Error al actualizar el estado.");
                }
            } else {
                System.out.println("Opción de estado inválida.");
            }
        } catch (NumberFormatException e) {
            System.out.println("El ID ingresado debe ser entero.");
        }
    }
}