package fr.bts.sio.resasync.model.entity;

import javafx.beans.property.*;

public class OptionTableau {

    private final SimpleStringProperty description;
    private final SimpleIntegerProperty quantite;
    private final SimpleDoubleProperty prixUnitaire;

    public OptionTableau(String description, int quantite, double prixUnitaire) {
        this.description = new SimpleStringProperty(description);
        this.quantite = new SimpleIntegerProperty(quantite);
        this.prixUnitaire = new SimpleDoubleProperty(prixUnitaire);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public int getQuantite() {
        return quantite.get();
    }

    public void setQuantite(int quantite) {
        this.quantite.set(quantite);
    }

    public SimpleIntegerProperty quantiteProperty() {
        return quantite;
    }

    public double getPrixUnitaire() {
        return prixUnitaire.get();
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire.set(prixUnitaire);
    }

    public SimpleDoubleProperty prixUnitaireProperty() {
        return prixUnitaire;
    }
}