package fr.bts.sio.resasync.model.entity;

import java.time.LocalDate;


public class Reservation {
    private int idReservation;
    private String statusReservation;
    private LocalDate dateReservation;
    private LocalDate dateDebut;
    private String nbrPersonnes;
    private int nbrChambre;
    private int idStatusResa;
    private int idClient;
    private int idFacture;
    private int idResp;

    public Reservation(int idReservation,String statusReservation, LocalDate dateReservation, LocalDate dateDebut,String nbrPersonnes,int nbrChambre,int idStatusResa,int idClient,int idFacture,int idResp){
        this.idReservation = idReservation;
        this.statusReservation = statusReservation;
        this.dateReservation = dateReservation;
        this.dateDebut = dateDebut;
        this.nbrPersonnes = nbrPersonnes;
        this.nbrChambre = nbrChambre;
        this.idStatusResa = idStatusResa;
        this.idClient = idClient;
        this.idFacture = idFacture;
        this.idResp = idResp;
    }

    //Getters and Setters

    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    public String getStatusReservation() {
        return statusReservation;
    }

    public void setStatusReservation(String statusReservation) {
        this.statusReservation = statusReservation;
    }

    public LocalDate getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDate dateReservation) {
        this.dateReservation = dateReservation;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getNbrPersonnes() {
        return nbrPersonnes;
    }

    public void setNbrPersonnes(String nbrPersonnes) {
        this.nbrPersonnes = nbrPersonnes;
    }

    public int getNbrChambre() {
        return nbrChambre;
    }

    public void setNbrChambre(int nbrChambre) {
        this.nbrChambre = nbrChambre;
    }

    public int getIdStatusResa() {
        return idStatusResa;
    }

    public void setIdStatusResa(int idStatusResa) {
        this.idStatusResa = idStatusResa;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdFacture() {
        return idFacture;
    }

    public void setIdFacture(int idFacture) {
        this.idFacture = idFacture;
    }

    public int getIdResp() {
        return idResp;
    }

    public void setIdResp(int idResp) {
        this.idResp = idResp;
    }


    //ToString

    @Override
    public String toString() {
        return "Reservation{" +
                "idReservation=" + idReservation +
                ", statusReservation='" + statusReservation + '\'' +
                ", dateReservation=" + dateReservation +
                ", dateDebut=" + dateDebut +
                ", nbrPersonnes='" + nbrPersonnes + '\'' +
                ", nbrChambre=" + nbrChambre +
                ", idStatusResa=" + idStatusResa +
                ", idClient=" + idClient +
                ", idFacture=" + idFacture +
                ", idResp=" + idResp +
                '}';
    }
}
