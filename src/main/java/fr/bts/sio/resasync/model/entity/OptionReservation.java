package fr.bts.sio.resasync.model.entity;

public class OptionReservation {
    private int idOption;
    private String libelle;
    private Double prixUnitaire;

    public OptionReservation(Double prixUnitaire, String libelle, int idOption) {
        this.prixUnitaire = prixUnitaire;
        this.libelle = libelle;
        this.idOption = idOption;
    }

    public int getIdOption() {
        return idOption;
    }

    public void setIdOption(int idOption) {
        this.idOption = idOption;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    @Override
    public String toString() {
        return "OptionReservation{" +
                "idOption=" + idOption +
                ", libelle='" + libelle + '\'' +
                ", prixUnitaire=" + prixUnitaire +
                '}';
    }
}
