package fr.bts.sio.resasync.model.entity;

public class Chambre {
    private int idChambre;
    private int numChambre;
    private int idTypeChambre;
    private int idStatutChambre;
    private String typeChambreLibelle;
    private String statutChambreLibelle;

    public Chambre (int idChambre, int numChambre, int idTypeChambre, int idStatutChambre){
        this.idChambre = idChambre;
        this.numChambre = numChambre;
        this.idTypeChambre = idTypeChambre;
        this.idStatutChambre = idStatutChambre;
    }

    public Chambre (int numChambre, int idTypeChambre,int idStatutChambre){
        this.numChambre = numChambre;
        this.idTypeChambre = idTypeChambre;
        this.idStatutChambre = idStatutChambre;
    }

    public Chambre (int numChambre, String typeChambrelibelle, String statutChambreLibelle){
        this.numChambre = numChambre;
        this.typeChambreLibelle = typeChambrelibelle;
        this.statutChambreLibelle = statutChambreLibelle;
    }

    public Chambre (int idChambre,int numChambre, String typeChambrelibelle, String statutChambreLibelle){
        this.idChambre = idChambre;
        this.numChambre = numChambre;
        this.typeChambreLibelle = typeChambrelibelle;
        this.statutChambreLibelle = statutChambreLibelle;
    }

    public String getTypeChambreLibelle() {
        return typeChambreLibelle;
    }

    public void setTypeChambreLibelle(String typeChambreLibelle) {
        this.typeChambreLibelle = typeChambreLibelle;
    }

    public String getStatutChambreLibelle() {
        return statutChambreLibelle;
    }

    public void setStatutChambreLibelle(String statutChambreLibelle) {
        this.statutChambreLibelle = statutChambreLibelle;
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
