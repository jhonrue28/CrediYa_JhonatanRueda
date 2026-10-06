package modelo.Clases;

public class Empleado extends Persona{

    protected String Rol;
    protected Double Salario;

    public Empleado(int id, String nombre, String documento, String correo, String Rol, Double Salario) {
        super(id, nombre, documento, correo);
        this.Rol = Rol;
        this.Salario = Salario;
    }

    public Empleado(String nombre, String documento, String correo, String Rol, Double Salario) {
        super(nombre, documento, correo);
        this.Rol = Rol;
        this.Salario = Salario;
    }

    public String getRol() {
        return Rol;
    }

    public void setRol(String rol) {
        Rol = rol;
    }

    public Double getSalario() {
        return Salario;
    }

    public void setSalario(Double salario) {
        Salario = salario;
    }

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public void setId(int id) {
        super.setId(id);
    }

    @Override
    public String getNombre() {
        return super.getNombre();
    }

    @Override
    public void setNombre(String nombre) {
        super.setNombre(nombre);
    }

    @Override
    public String getDocumento() {
        return super.getDocumento();
    }

    @Override
    public void setDocumento(String documento) {
        super.setDocumento(documento);
    }

    @Override
    public String getCorreo() {
        return super.getCorreo();
    }

    @Override
    public void setCorreo(String correo) {
        super.setCorreo(correo);
    }



}
