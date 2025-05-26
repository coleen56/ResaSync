package fr.bts.sio.resasync.model.entity;
/**
 * Représente l'association entre une réservation et une option sélectionnée,
 * incluant la quantité choisie pour cette option dans le cadre de la réservation.
 * Cette classe fait le lien entre une réservation et les options ajoutées à celle-ci.
 */
public class Comprend {
    private int idReservation;
    private int idOption;
    private int quantite;

    public Comprend(int idReservation, int idOption, int quantite) {
        this.idReservation = idReservation;
        this.idOption = idOption;
        this.quantite = quantite;
    }

    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    public int getIdOption() {
        return idOption;
    }

    public void setIdOption(int idOption) {
        this.idOption = idOption;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public String toString() {
        return "Comprend{" +
                "idReservation=" + idReservation +
                ", idOption=" + idOption +
                ", quantite=" + quantite +
                '}';
    }
}
