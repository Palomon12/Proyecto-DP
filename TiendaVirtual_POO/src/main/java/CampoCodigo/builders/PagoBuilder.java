package CampoCodigo.builders;

import CampoCodigo.Pago;

public class PagoBuilder {
    private int id;
    private double monto;

    public PagoBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public PagoBuilder setMonto(double monto) {
        this.monto = monto;
        return this;
    }

    public Pago build() {
        return new Pago(id, monto);
    }
}
