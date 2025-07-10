package CampoCodigo;

import java.util.Date;

public class Informe {
    private int idInforme;
    private double totalGanancias;
    private double igv;
    private int totalPedidos;
    private Date fechaGeneracion;

    // Constructor vacío
    public Informe() {}

    // Constructor completo
    public Informe(int idInforme, double totalGanancias, double igv, int totalPedidos, Date fechaGeneracion) {
        this.idInforme = idInforme;
        this.totalGanancias = totalGanancias;
        this.igv = igv;
        this.totalPedidos = totalPedidos;
        this.fechaGeneracion = fechaGeneracion;
    }

    // Getters y Setters
    public int getIdInforme() {
        return idInforme;
    }

    public void setIdInforme(int idInforme) {
        this.idInforme = idInforme;
    }

    public double getTotalGanancias() {
        return totalGanancias;
    }

    public void setTotalGanancias(double totalGanancias) {
        this.totalGanancias = totalGanancias;
    }

    public double getIgv() {
        return igv;
    }

    public void setIgv(double igv) {
        this.igv = igv;
    }

    public int getTotalPedidos() {
        return totalPedidos;
    }

    public void setTotalPedidos(int totalPedidos) {
        this.totalPedidos = totalPedidos;
    }

    public Date getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(Date fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }
}
