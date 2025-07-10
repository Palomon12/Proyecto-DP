
package CampoCodigo;

import java.util.Date;

public class Pago {
    private int id_Pago;
    private double monto; // Monto total a pagar
    private Date fechaPago;
    
    // Constructor
    public Pago(int id_Pago, double monto) {
        this.id_Pago = id_Pago;
        this.monto = monto;
        this.fechaPago = new Date(); // Se establece la fecha de pago actual
    }

    // Getters y Setters
    public int getId_Pago() {
        return id_Pago;
    }

    public void setId_Pago(int id_Pago) {
        this.id_Pago = id_Pago;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    // Verificar si el pago es válido (en este caso, solo se verifica si el monto es positivo)
    public boolean verificarPago() {
        return monto > 0; 
    }

    @Override
    public String toString() {
        return "--Pago-- \n.ID: " + id_Pago 
                + "\n.Monto: $" + monto 
                + " \n.Fecha: " + fechaPago;
    }
}
