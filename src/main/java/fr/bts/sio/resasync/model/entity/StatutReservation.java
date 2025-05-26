package fr.bts.sio.resasync.model.entity;
/**
 * Représente le statut d'une réservation (ex : Confirmée, Annulée, En attente, etc.).
 * Cette classe contient un identifiant unique et un libellé descriptif du statut.
 */
public class StatutReservation {

    private int idStatutResa;
    private String libelle;

    public StatutReservation(int idStatutResa, String libelle) {
        this.idStatutResa = idStatutResa;
        this.libelle = libelle;
    }

    public int getIdStatutResa() {
        return idStatutResa;
    }

    public void setIdStatutResa(int idStatutResa) {
        this.idStatutResa = idStatutResa;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return libelle;
    }
}
