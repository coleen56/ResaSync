package fr.bts.sio.resasync.model.entity;
/**
 * Représente une chambre au sein de l'établissement.
 * Une chambre est définie par son identifiant unique, son numéro, son type (ex: simple, double, suite...)
 * et son statut (ex: disponible, occupée, en maintenance...).
 */
public class Chambre {
    private int idChambre;
    private int numChambre;
    private TypeChambre typeChambre;
    private StatutChambre statutChambre;

    public Chambre(int idChambre, int numChambre, TypeChambre typeChambre, StatutChambre statutChambre) {
        if (typeChambre == null) {
            throw new IllegalArgumentException("Le type de chambre ne peut pas être nul.");
        }
        if (statutChambre == null) {
            throw new IllegalArgumentException("Le statut de chambre ne peut pas être nul.");
        }
        this.idChambre = idChambre;
        this.numChambre = numChambre;
        this.typeChambre = typeChambre;
        this.statutChambre = statutChambre;
    }

    public Chambre(int idChambre, int numChambre) {
        this.idChambre = idChambre;
        this.numChambre = numChambre;
    }

    // Constructeur sans ID de chambre (pour l'insertion d'une nouvelle chambre)
    public Chambre(int numChambre, TypeChambre typeChambre, StatutChambre statutChambre) {
        if (typeChambre == null) {
            throw new IllegalArgumentException("Le type de chambre ne peut pas être nul.");
        }
        if (statutChambre == null) {
            throw new IllegalArgumentException("Le statut de chambre ne peut pas être nul.");
        }
        this.numChambre = numChambre;
        this.typeChambre = typeChambre;
        this.statutChambre = statutChambre;
    }


    // Getters et setters
    public int getIdChambre() {
        return idChambre;
    }

    public void setIdChambre(int idChambre) {
        this.idChambre = idChambre;
    }

    public int getNumChambre() {
        return numChambre;
    }

    public void setNumChambre(int numChambre) {
        this.numChambre = numChambre;
    }

    public TypeChambre getTypeChambre() {
        return typeChambre;
    }

    public String getTypeChambreLibelle() {
        return this.typeChambre.getLibelle(); // Exemple
    }

    public void setTypeChambre(TypeChambre typeChambre) {
        this.typeChambre = typeChambre;
    }

    public StatutChambre getStatutChambre() {
        return statutChambre;
    }

    public String getStatutChambreLibelle() {
        return this.statutChambre.getLibelle(); // Exemple
    }

    public void setStatutChambre(StatutChambre statutChambre) {
        this.statutChambre = statutChambre;
    }

    @Override
    public String toString() {
        return "Chambre{" +
                "idChambre=" + idChambre +
                ", numChambre=" + numChambre +
                ", typeChambre=" + typeChambre +
                ", statutChambre=" + statutChambre +
                '}';
    }
}
