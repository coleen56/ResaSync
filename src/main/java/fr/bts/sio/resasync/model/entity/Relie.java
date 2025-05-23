package fr.bts.sio.resasync.model.entity;
/**
 * Représente la relation entre une chambre et une réservation.
 * Cette classe relie une chambre à une réservation donnée, permettant d'indiquer
 * quelles chambres sont affectées à quelles réservations.
 */

public class Relie {

    private int idChambre;
    private int idReservation;

    public Relie(int idChambre, int idReservation) {
        this.idChambre = idChambre;
        this.idReservation = idReservation;
    }

    public int getIdChambre() {
        return idChambre;
    }

    public void setIdChambre(int idChambre) {
        this.idChambre = idChambre;
    }

    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    @Override
    public String toString() {
        return "Relie{" +
                "idChambre=" + idChambre +
                ", idReservation=" + idReservation +
                '}';
    }
}
