package vista;

public class MenuPrincipal {
    public void iniciar() {
        int opcion;
        do {
            System.out.println("\n=================================");
            System.out.println("    SISTEMA CREDIYA S.A.S.      ");
            System.out.println("=================================");
            System.out.println("1. Gestión de Empleados");
            System.out.println("2. Gestión de Clientes");
            System.out.println("3. Gestión de Préstamos");
            System.out.println("4. Gestión de Pagos");
            System.out.println("5. Reportes");
            System.out.println("6. Salir");

            opcion = ConsolUtils.leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    MenuEmpleado.mostrar();
                    break;
                case 2:
                    MenuCliente.mostrar();
                    break;
                case 3:
                    MenuPrestamos.mostrar();
                    break;
                case 4:
                    MenuPago.mostrar();
                    break;
                case 5:
                    MenuReportes.mostrar();
                    break;
                case 6:
                    System.out.println("¡Gracias por usar CrediYa! Hasta luego.");
                    break;
                default:
                    System.out.println("Opción no válida, intente de nuevo.");
            }
        } while (opcion != 6);
    }
}
