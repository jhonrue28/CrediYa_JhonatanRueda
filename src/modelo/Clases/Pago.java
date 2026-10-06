package modelo.Clases;

import java.sql.Date;

public class Pago {
    private int id;
    private int idPrestamo;
    private Date fechaPago;
    private double monto;

    public Pago(int idPrestamo, double monto) {
        this.idPrestamo = idPrestamo;
        this.monto = monto;
    }

    public Pago(int id, int idPrestamo, Date fechaPago, double monto) {
        this.id = id;
        this.idPrestamo = idPrestamo;
        this.fechaPago = fechaPago;
        this.monto = monto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
}
