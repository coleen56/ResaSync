package fr.bts.sio.resasync.model.entity;

public class Paye {

    private int idFacture;
    private int idTypePaiement;
    private Double montant;

    public Paye(int idFacture, int idTypePaiement, Double montant) {
        this.idFacture = idFacture;
        this.idTypePaiement = idTypePaiement;
        this.montant = montant;
    }

    public int getIdFacture() {
        return idFacture;
    }

    public void setIdFacture(int idFacture) {
        this.idFacture = idFacture;
    }

    public int getIdTypePaiement() {
        return idTypePaiement;
    }

    public void setIdTypePaiement(int idTypePaiement) {
        this.idTypePaiement = idTypePaiement;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    @Override
    public String toString() {
        return "Paye{" +
                "idFacture=" + idFacture +
                ", idTypePaiement=" + idTypePaiement +
                ", montant=" + montant +
                '}';
    }
}
