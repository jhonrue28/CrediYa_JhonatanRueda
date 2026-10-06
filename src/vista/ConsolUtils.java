package vista;

import java.util.Scanner;

public class ConsolUtils {
    private static final Scanner scanner = new Scanner(System.in);

    public static int leerEntero(String mensaje) {
        int numero = -1;
        boolean valido = false;
        while (!valido) {
            try {
                System.out.print(mensaje);
                numero = Integer.parseInt(scanner.nextLine());
                valido = true;
            } catch (NumberFormatException e) {
                System.out.println(" Error: Por favor ingrese un número entero válido.");
            }
        }
        return numero;
    }

    public static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    public static String seleccionarRol() {
        int opcionRol = 0;
        String rol = "";

        while (opcionRol < 1 || opcionRol > 3) {
            System.out.println("\nSeleccione el rol del empleado:");
            System.out.println("1. Cajero");
            System.out.println("2. Asesor de Crédito");
            System.out.println("3. Administrador");

            opcionRol = ConsolUtils.leerEntero("Ingrese una opción (1-3): ");

            switch (opcionRol) {
                case 1:
                    rol = "Cajero";
                    break;
                case 2:
                    rol = "Asesor de Crédito";
                    break;
                case 3:
                    rol = "Administrador";
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
        return rol;
    }

    public static double leerDouble(String mensaje) {
        double numero = 0;
        boolean valido = false;
        while (!valido) {
            try {
                System.out.print(mensaje);
                numero = Double.parseDouble(scanner.nextLine().trim());
                valido = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un valor numérico válido (ejemplo: 2500000.00).");
            }
        }
        return numero;
    }
}
