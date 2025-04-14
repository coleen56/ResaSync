package fr.bts.sio.resasync.model.entity;

public class StatutFacture {

    private int idStatutFacture;
    private String libelle;

    public StatutFacture(int idStatutFacture, String libelle) {
        this.idStatutFacture = idStatutFacture;
        this.libelle = libelle;
    }

    public int getIdStatutFacture() {
        return idStatutFacture;
    }

    public void setIdStatutFacture(int idStatutFacture) {
        this.idStatutFacture = idStatutFacture;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "StatutFacture{" +
                "idStatutFacture=" + idStatutFacture +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
