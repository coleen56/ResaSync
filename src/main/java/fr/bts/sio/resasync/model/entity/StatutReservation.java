package fr.bts.sio.resasync.model.entity;

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
        return "StatutReservation{" +
                "idStatutResa=" + idStatutResa +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
