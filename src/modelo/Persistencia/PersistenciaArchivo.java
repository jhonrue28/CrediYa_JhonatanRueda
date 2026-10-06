package modelo.Persistencia;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaArchivo {

    private static final Path CARPETA_DATA = Paths.get("data");
    private static final Path ARCHIVO_EMPLEADOS = CARPETA_DATA.resolve("empleados.txt");
    private static final Path ARCHIVO_CLIENTES = CARPETA_DATA.resolve("clientes.txt");
    private static final Path ARCHIVO_PRESTAMOS = CARPETA_DATA.resolve("prestamos.txt");
    private static final Path ARCHIVO_PAGOS = CARPETA_DATA.resolve("pagos.txt");

    public void guardarEmpleado(String linea) {
        agregarLinea(ARCHIVO_EMPLEADOS, linea);
    }

    public void guardarCliente(String linea) {
        agregarLinea(ARCHIVO_CLIENTES, linea);
    }

    public void guardarPrestamo(String linea) {
        agregarLinea(ARCHIVO_PRESTAMOS, linea);
    }

    public void guardarPago(String linea) {
        agregarLinea(ARCHIVO_PAGOS, linea);
    }

    public List<String> leerEmpleados() {
        return leerLineas(ARCHIVO_EMPLEADOS);
    }

    public List<String> leerClientes() {
        return leerLineas(ARCHIVO_CLIENTES);
    }

    public List<String> leerPrestamos() {
        return leerLineas(ARCHIVO_PRESTAMOS);
    }

    public List<String> leerPagos() {
        return leerLineas(ARCHIVO_PAGOS);
    }

    private void agregarLinea(Path archivo, String linea) {
        try {
            asegurarArchivo(archivo);
            Files.writeString(
                    archivo,
                    linea + System.lineSeparator(),
                    StandardCharsets.UTF_8,
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            System.err.println("Error al guardar en " + archivo + ": " + e.getMessage());
        }
    }

    private List<String> leerLineas(Path archivo) {
        try {
            asegurarArchivo(archivo);
            return Files.readAllLines(archivo, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Error al leer " + archivo + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void asegurarArchivo(Path archivo) throws IOException {
        if (Files.notExists(CARPETA_DATA)) {
            Files.createDirectories(CARPETA_DATA);
        }
        if (Files.notExists(archivo)) {
            Files.createFile(archivo);
        }
    }
}
