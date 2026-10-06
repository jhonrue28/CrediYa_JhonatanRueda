package vista;

import com.sun.tools.jconsole.JConsoleContext;
import modelo.Clases.Empleado;
import modelo.DAO.EmpleadoDAO;

import static vista.ConsolUtils.seleccionarRol;

public class MenuEmpleado {
    public static void mostrar() {
        int opcion;
        do {
            System.out.println("\n===== MÓDULO DE EMPLEADOS =====");
            System.out.println("1. Registrar Empleado");
            System.out.println("2. Consultar Empleados");
            System.out.println("3. Volver al Menú Principal");

            opcion = ConsolUtils.leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    registrarEmpleado();
                    break;
                case 2:
                    consultarEmpleados();
                    break;
                case 3:
                    System.out.println("Regresando al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 3);
    }

    private static void registrarEmpleado() {
        System.out.println("\n--- Registrar Nuevo Empleado ---");
        String nombre = ConsolUtils.leerTexto("Nombre: ");
        String documento = ConsolUtils.leerTexto("Documento: ");
        String correo = ConsolUtils.leerTexto("Correo: ");
        String rol = ConsolUtils.seleccionarRol();

        double salario = ConsolUtils.leerDouble("Salario: ");

        Empleado nuevoEmpleado = new Empleado(nombre, documento, correo, rol, salario);

        if (EmpleadoDAO.guardar(nuevoEmpleado)) {
            System.out.println("¡Empleado registrado con éxito con el rol de '" + rol + "'!");
        } else {
            System.out.println("Error al guardar el empleado.");
        }
        // Aquí llamas a tu controlador/DAO:
        // empleadoController.guardar(new Empleado(nombre, documento, rol, ...));
        System.out.println("Empleado registrado con éxito.");
    }

    private static void consultarEmpleados() {
        System.out.println("\n--- Listado de Empleados ---");
        // Llamada al controlador para obtener y mostrar lista
    }
}
