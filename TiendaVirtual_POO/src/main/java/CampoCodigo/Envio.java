
package CampoCodigo;

import java.time.LocalDate;


public class Envio {
    private int id_envio;
    private String estado;
    private LocalDate fechaEnvio;
    private String direccionEnvio;

    public Envio(int id_envio, String estado, LocalDate fechaEnvio, String direccionEnvio) {
        this.id_envio = id_envio;
        this.estado = estado;
        this.fechaEnvio = fechaEnvio;
        this.direccionEnvio = direccionEnvio;
    }

    public int getId_envio() {
        return id_envio;
    }

    public void setId_envio(int id_envio) {
        this.id_envio = id_envio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDate fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getDireccionEnvio() {
        return direccionEnvio;
    }

    public void setDireccionEnvio(String direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
    }
    
    public void generarEnvio(){
        System.out.println("Generando envio a: "+direccionEnvio);
        this.estado="En proceso";
        this.fechaEnvio = LocalDate.now();
        System.out.println("Envio generado con ID: "+id_envio);
    }
    public void verEstadoEnvio(){
        System.out.println("Estado del envio con ID "+id_envio+": "+estado);
    }
    @Override
    public String toString() {
        return "Envio ID: " + id_envio + ", Estado: " + estado + ", Fecha de Envío: " + fechaEnvio + ", Dirección: " + direccionEnvio;
    }
}
