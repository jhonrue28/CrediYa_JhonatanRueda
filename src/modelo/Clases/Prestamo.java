package modelo.Clases;

import java.sql.Date;

public class Prestamo {
    private int id;
    private int idCliente;
    private double monto;
    private double interes;
    private int cuotas;
    private String estado; // "PENDIENTE", "PAGADO", "CANCELADO"
    private Date fecha;

    public Prestamo(int id, int idCliente, double monto, double interes, int cuotas, String estado, Date fecha) {
        this.id = id;
        this.idCliente = idCliente;
        this.monto = monto;
        this.interes = interes;
        this.cuotas = cuotas;
        this.estado = estado;
        this.fecha = fecha;
    }

    public Prestamo(int idCliente, double monto, double interes, int cuotas) {
        this.idCliente = idCliente;
        this.monto = monto;
        this.interes = interes;
        this.cuotas = cuotas;
        this.estado = "PENDIENTE";
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public double getInteres() { return interes; }
    public void setInteres(double interes) { this.interes = interes; }

    public int getCuotas() { return cuotas; }
    public void setCuotas(int cuotas) { this.cuotas = cuotas; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
}
