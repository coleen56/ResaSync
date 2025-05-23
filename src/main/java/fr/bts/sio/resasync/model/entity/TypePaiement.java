package fr.bts.sio.resasync.model.entity;
/**
 * Représente un type de paiement (ex : Carte bancaire, Espèces, Chèque, etc.).
 * Cette classe contient un identifiant unique pour le type de paiement et un libellé descriptif.
 */
public class TypePaiement {

    private int idTypePaiement;
    private String libelle;

    public TypePaiement(int idTypePaiement, String libelle) {
        this.idTypePaiement = idTypePaiement;
        this.libelle = libelle;
    }

    public int getIdTypePaiement() {
        return idTypePaiement;
    }

    public void setIdTypePaiement(int idTypePaiement) {
        this.idTypePaiement = idTypePaiement;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "TypePaiement{" +
                "idTypePaiement=" + idTypePaiement +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
