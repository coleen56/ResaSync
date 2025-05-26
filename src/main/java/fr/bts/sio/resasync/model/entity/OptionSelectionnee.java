package fr.bts.sio.resasync.model.entity;

/**
 * Représente une option sélectionnée lors d'une réservation.
 * Cette classe contient le libellé de l'option, la quantité choisie, ainsi qu'un nombre de jours
 * (pouvant être nul si non applicable) pour lesquels l'option est sélectionnée.
 */
public class OptionSelectionnee {
    private String libelle;
    private int quantite;
    private Integer nbJours;

    // Vous pouvez aussi créer un constructeur sans le paramètre nbJours :
    public OptionSelectionnee(String libelle, int quantite) {
        this.libelle = libelle;
        this.quantite = quantite;
        this.nbJours = null; // explicitement null, optionnel car c'est la valeur par défaut
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

}