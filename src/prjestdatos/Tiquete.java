//Inicio
package prjestdatos;

//Librerias
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//Clase tiquete
public class Tiquete {
    //Atributos

    private String nombre;
    private int id;
    private int edad;
    private String horaCreacion;
    private String horaAtencion = "-1";
    private TipoTramite.tipoTramites tipoTramite;
    private TipoCliente.tipoClientes tipoCliente;

    //Constructores
    //-Default
    public Tiquete() {
    }

    //-Sobrecargado
    public Tiquete(String nombre, int id, int edad, String horaCreacion, TipoTramite.tipoTramites tipoTramite, TipoCliente.tipoClientes tipoCliente) {
        this.nombre = nombre;
        this.id = id;
        this.edad = edad;
        this.horaCreacion = horaCreacion;
        this.tipoTramite = tipoTramite;
        this.tipoCliente = tipoCliente;
    }

    //Getters And Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getHoraCreacion() {
        return horaCreacion;
    }

    public void setHoraCreacion(String horaCreacion) {
        this.horaCreacion = horaCreacion;
    }

    public String getHoraAtencion() {
        return horaAtencion;
    }

    public void setHoraAtencion(String horaAtencion) {
        this.horaAtencion = horaAtencion;
    }

    public TipoTramite.tipoTramites getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoTramite(TipoTramite.tipoTramites tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public TipoCliente.tipoClientes getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoCliente.tipoClientes tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    //To String
    @Override
    public String toString() {
        return "Información del cliente\n\n"
                + "Nombre: " + nombre + "\n"
                + "Cedula: " + id + "\n"
                + "Edad: " + edad + "\n"
                + "Hora de creación: " + horaCreacion + "\n"
                + "Hora de atención: " + horaAtencion + "\n"
                + "Tramite: " + tipoTramite + "\n"
                + "Tipo de cliente: " + tipoCliente;
    }

    //METODOS
    //Metodo para guardar la fecha y la hora de creación del tiquete
    public String horaCreacion() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        horaCreacion = now.format(formatter);
        return horaCreacion;
    }

    //Metodo para guardar la fecha y la hora de atención del tiquete
    public String horaAtencion() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        horaAtencion = now.format(formatter);
        return horaAtencion;
    }
}
