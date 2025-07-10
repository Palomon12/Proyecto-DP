package CampoCodigo.builders;

import CampoCodigo.Envio;
import java.time.LocalDate;

public class EnvioBuilder {
    private int id;
    private String estado;
    private LocalDate fecha;
    private String direccion;

    public EnvioBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public EnvioBuilder setEstado(String estado) {
        this.estado = estado;
        return this;
    }

    public EnvioBuilder setFecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public EnvioBuilder setDireccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public Envio build() {
        return new Envio(id, estado, fecha, direccion);
    }
}
