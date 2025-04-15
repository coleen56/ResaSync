package fr.bts.sio.resasync.model.entity;

public class Chambre {
    private int idChambre;
    private int numChambre;
    private int idTypeChambre;
    private int idStatutChambre;

    public Chambre (int idChambre, int numChambre, int idTypeChambre, int idStatutChambre){
        this.idChambre = idChambre;
        this.numChambre = numChambre;
       //rob
        this.idTypeChambre = idTypeChambre;
        this.idStatutChambre = idStatutChambre;
    }

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

    public int getIdTypeChambre() {
        return idTypeChambre;
    }

    public void setIdTypeChambre(int idTypeChambre) {
        this.idTypeChambre = idTypeChambre;
    }

    public int getIdStatutChambre() {
        return idStatutChambre;
    }

    public void setIdStatutChambre(int idStatusChambre) {
        this.idStatutChambre = idStatusChambre;
    }
}
