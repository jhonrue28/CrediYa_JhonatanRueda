package vista;
import java.util.List;
import modelo.Clases.Cliente;
import modelo.DAO.ClienteDAO;

public class MenuCliente {
    private static final ClienteDAO clienteDAO = new ClienteDAO();

    public static void mostrar() {
        int opcion = 0;
        do {
            System.out.println("\n===== MÓDULO DE CLIENTES =====");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Consultar préstamos del cliente");
            System.out.println("4. Volver al Menú Principal");

            opcion = ConsolUtils.leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    registrarCliente();
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    consultarPrestamosCliente();
                    break;
                case 4:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 4);
    }

    private static void registrarCliente() {
        System.out.println("\n--- Registrar Nuevo Cliente ---");
        String nombre = ConsolUtils.leerTexto("Nombre: ");
        String documento = ConsolUtils.leerTexto("Documento: ");
        String correo = ConsolUtils.leerTexto("Correo: ");
        String telefono = ConsolUtils.leerTexto("Teléfono: ");

        Cliente nuevoCliente = new Cliente(nombre, documento, correo, telefono);

        if (ClienteDAO.guardar(nuevoCliente)) {
            System.out.println("¡Cliente registrado con éxito!");
        } else {
            System.out.println("Error al guardar el cliente.");
        }
    }

    private static void listarClientes() {
        System.out.println("\n--- Listado de Clientes ---");
        List<Cliente> clientes = clienteDAO.obtenerTodos();

        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados en la base de datos.");
        } else {
            for (Cliente cli : clientes) {
                System.out.println("ID: " + cli.getId() +
                        " | Nombre: " + cli.getNombre() +
                        " | Doc: " + cli.getDocumento() +
                        " | Correo: " + cli.getCorreo() +
                        " | Teléfono: " + cli.getTelefono());
            }
        }
    }

    private static void consultarPrestamosCliente() {
        System.out.println("\n--- Consultar Préstamos de un Cliente ---");
        String documento = ConsolUtils.leerTexto("Ingrese el documento del cliente: ");

        // Método que implementaremos en ClienteDAO para la consulta por documento/préstamos
        clienteDAO.obtenerPrestamosPorCliente(documento);
    }
}
