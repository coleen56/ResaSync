package fr.bts.sio.resasync.model.entity;

public class NiveauDroits {
    private int idNiveau;
    private String libelle;

    public NiveauDroits(int idNiveau, String libelle){
        this.idNiveau = idNiveau;
        this.libelle = libelle;
    }

    //Getters and Setters

    public int getIdNiveau() {
        return idNiveau;
    }

    public void setIdNiveau(int idNiveau) {
        this.idNiveau = idNiveau;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    //To String

    @Override
    public String toString() {
        return "NiveauDroits{" +
                "idNiveau=" + idNiveau +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
