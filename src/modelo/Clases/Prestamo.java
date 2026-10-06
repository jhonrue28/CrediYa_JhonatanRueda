package modelo.Clases;

import java.sql.Date;

public class Prestamo {
    private int id;
    private int idCliente;
    private int idEmpleado;
    private double monto;
    private double interes;
    private int cuotas;
    private String estado;
    private Date fechaInicio;
    private double montoTotal;
    private double cuotaMensual;
    private double saldoPendiente;

    public Prestamo(int idCliente, int idEmpleado, double monto, double interes, int cuotas) {
        this.idCliente = idCliente;
        this.idEmpleado = idEmpleado;
        this.monto = monto;
        this.interes = interes;
        this.cuotas = cuotas;
        this.estado = "PENDIENTE";
    }

    public Prestamo(int id, int idCliente, int idEmpleado, double monto, double interes, int cuotas,
                    String estado, Date fechaInicio, double montoTotal, double cuotaMensual,
                    double saldoPendiente) {
        this.id = id;
        this.idCliente = idCliente;
        this.idEmpleado = idEmpleado;
        this.monto = monto;
        this.interes = interes;
        this.cuotas = cuotas;
        this.estado = estado;
        this.fechaInicio = fechaInicio;
        this.montoTotal = montoTotal;
        this.cuotaMensual = cuotaMensual;
        this.saldoPendiente = saldoPendiente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public double getInteres() {
        return interes;
    }

    public void setInteres(double interes) {
        this.interes = interes;
    }

    public int getCuotas() {
        return cuotas;
    }

    public void setCuotas(int cuotas) {
        this.cuotas = cuotas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public double getCuotaMensual() {
        return cuotaMensual;
    }

    public void setCuotaMensual(double cuotaMensual) {
        this.cuotaMensual = cuotaMensual;
    }

    public double getSaldoPendiente() {
        return saldoPendiente;
    }

    public void setSaldoPendiente(double saldoPendiente) {
        this.saldoPendiente = saldoPendiente;
    }
}
