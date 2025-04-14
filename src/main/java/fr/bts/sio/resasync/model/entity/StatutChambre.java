package fr.bts.sio.resasync.model.entity;

public class StatutChambre {
    private int idStatutChambre;
    private String libelle;

    public int getIdStatutChambre() {
        return idStatutChambre;
    }

    public void setIdStatutChambre(int idStatutChambre) {
        this.idStatutChambre = idStatutChambre;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public StatutChambre (int idStatutChambre, String libelle){
        this.idStatutChambre = idStatutChambre;
        this.libelle = libelle;

        // Getters and setters

    }
}
