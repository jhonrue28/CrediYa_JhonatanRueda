package modelo.Servicios;

import java.util.List;
import modelo.Clases.Pago;
import modelo.Clases.Prestamo;

public class PrestamoServicio {

    public double calcularMontoTotal(Prestamo prestamo) {
        double montoTotal = prestamo.getMonto() + (prestamo.getMonto() * prestamo.getInteres() / 100);
        prestamo.setMontoTotal(montoTotal);
        return montoTotal;
    }

    public double calcularCuotaMensual(Prestamo prestamo) {
        if (prestamo.getCuotas() == 0) {
            prestamo.setCuotaMensual(0);
            return 0;
        }

        double cuotaMensual = prestamo.getMontoTotal() / prestamo.getCuotas();
        prestamo.setCuotaMensual(cuotaMensual);
        return cuotaMensual;
    }

    public double inicializarSaldoPendiente(Prestamo prestamo) {
        double saldoPendiente = prestamo.getMontoTotal();
        prestamo.setSaldoPendiente(saldoPendiente);
        return saldoPendiente;
    }
    public double calcularSaldoPendiente(Prestamo prestamo, List<Pago> pagos) {
        double totalPagado = 0;

        for (Pago pago : pagos) {
            totalPagado += pago.getMonto();
        }

        double saldoPendiente = prestamo.getMontoTotal() - totalPagado;

        if (saldoPendiente < 0) {
            saldoPendiente = 0;
        }

        prestamo.setSaldoPendiente(saldoPendiente);

        if (saldoPendiente == 0) {
            prestamo.setEstado("PAGADO");
        }

        return saldoPendiente;
    }
    public void calcularDatosPrestamo(Prestamo prestamo) {
        calcularMontoTotal(prestamo);
        calcularCuotaMensual(prestamo);
        inicializarSaldoPendiente(prestamo);
    }
}
