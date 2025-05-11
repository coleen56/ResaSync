package fr.bts.sio.resasync.model.entity;

public class TypeChambre {
    private int idTypeChambre;
    private String libelle;

    public TypeChambre (int idTypeChambre, String libelle){
        this.idTypeChambre = idTypeChambre;
        this.libelle = libelle;
    }

    public TypeChambre() {

    }

    public int getIdTypeChambre() {
        return idTypeChambre;
    }

    public void setIdTypeChambre(int idTypeChambre) {
        this.idTypeChambre = idTypeChambre;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "TypeChambre{" +
                "idTypeChambre=" + idTypeChambre +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
