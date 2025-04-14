package fr.bts.sio.resasync.model.entity;

import java.util.Date;

public class Facturation {

    private int idFacture;
    private Double totalFacture;
    private Date dateFacturation;
    private int idStatutFacture;
    private int idClient;

    public Facturation(int idFacture, Double totalFacture, Date dateFacturation, int idStatutFacture, int idClient) {
        this.idFacture = idFacture;
        this.totalFacture = totalFacture;
        this.dateFacturation = dateFacturation;
        this.idStatutFacture = idStatutFacture;
        this.idClient = idClient;
    }

    public int getIdFacture() {
        return idFacture;
    }

    public void setIdFacture(int idFacture) {
        this.idFacture = idFacture;
    }

    public Double getTotalFacture() {
        return totalFacture;
    }

    public void setTotalFacture(Double totalFacture) {
        this.totalFacture = totalFacture;
    }

    public Date getDateFacturation() {
        return dateFacturation;
    }

    public void setDateFacturation(Date dateFacturation) {
        this.dateFacturation = dateFacturation;
    }

    public int getIdStatutFacture() {
        return idStatutFacture;
    }

    public void setIdStatutFacture(int idStatutFacture) {
        this.idStatutFacture = idStatutFacture;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    @Override
    public String toString() {
        return "Facturation{" +
                "idFacture=" + idFacture +
                ", totalFacture=" + totalFacture +
                ", dateFacturation=" + dateFacturation +
                ", idStatutFacture=" + idStatutFacture +
                ", idClient=" + idClient +
                '}';
    }
}
