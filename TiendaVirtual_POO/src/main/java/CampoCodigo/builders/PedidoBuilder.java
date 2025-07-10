package CampoCodigo.builders;

import CampoCodigo.Pedido;
import CampoCodigo.Usuario;
import java.util.Date;

public class PedidoBuilder {
    private Usuario usuario;
    private String estado;
    private Date fechaPedido;
    private double total;

    public PedidoBuilder setUsuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public PedidoBuilder setEstado(String estado) {
        this.estado = estado;
        return this;
    }

    public PedidoBuilder setFecha(Date fecha) {
        this.fechaPedido = fecha;
        return this;
    }

    public PedidoBuilder setTotal(double total) {
        this.total = total;
        return this;
    }

    public Pedido build() {
        return new Pedido(usuario, estado, fechaPedido, total);
    }
}
