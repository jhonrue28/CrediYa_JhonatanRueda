package modelo.Clases;

public class Cliente extends Persona{
    protected String Telefono;

    public Cliente(int id, String nombre, String documento, String correo, String telefono) {
        super(id, nombre, documento, correo);
        Telefono = telefono;
    }

    public Cliente(String nombre, String documento, String correo, String telefono) {
        super(nombre, documento, correo);
        this.Telefono = telefono;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
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
