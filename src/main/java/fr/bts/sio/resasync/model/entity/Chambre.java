package fr.bts.sio.resasync.model.entity;

public class Chambre {
    private int idChambre;
    private int numChambre;
    private String statutChambre;
    private int idTypeChambre;
    private int idStatusChambre;

    public Chambre (int idChambre, int numChambre, String statutChambre, int idTypeChambre, int idStatusChambre){
        this.idChambre = idChambre;
        this.numChambre = numChambre;
       //rob
        this.statutChambre = statutChambre;
        this.idTypeChambre = idTypeChambre;
        this.idStatusChambre = idStatusChambre;

    }
}
