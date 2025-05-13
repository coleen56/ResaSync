package fr.bts.sio.resasync.model.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Reservation {
    private int idReservation;
    private LocalDate dateReservation;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private int nbrPersonnes;
    private int nbrChambre;
    private String statutReservation;
    private String nomPrenomClient;
    private int idFacture;
    private int idResp;


    public Reservation(int idReservation, LocalDate dateReservation, LocalDate dateDebut, LocalDate dateFin, int nbrPersonnes, int nbrChambre, String statutReservation, String nomPrenomClient, int idFacture, int idResp) {
        this.idReservation = idReservation;
        this.dateReservation = dateReservation;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nbrPersonnes = nbrPersonnes;
        this.nbrChambre = nbrChambre;
        this.statutReservation = statutReservation;
        this.nomPrenomClient = nomPrenomClient;
        this.idFacture = idFacture;
        this.idResp = idResp;
    }


    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
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

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public int getNbrPersonnes() {
        return nbrPersonnes;
    }

    public void setNbrPersonnes(int nbrPersonnes) {
        this.nbrPersonnes = nbrPersonnes;
    }

    public int getNbrChambre() {
        return nbrChambre;
    }

    public void setNbrChambre(int nbrChambre) {
        this.nbrChambre = nbrChambre;
    }

    public String getStatutReservation() {
        return statutReservation;
    }

    public void setStatutReservation(String statutReservation) {
        this.statutReservation = statutReservation;
    }

    public String getNomPrenomClient() {
        return nomPrenomClient;
    }

    public void setNomPrenomClient(String nomPrenomClient) {
        this.nomPrenomClient = nomPrenomClient;
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


    @Override
    public String toString() {
        return "Reservation{" +
                "idReservation=" + idReservation +
                ", dateReservation=" + dateReservation +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", nbrPersonnes=" + nbrPersonnes +
                ", nbrChambre=" + nbrChambre +
                ", statutReservation='" + statutReservation + '\'' +
                ", nomPrenomClient='" + nomPrenomClient + '\'' +
                ", idFacture=" + idFacture +
                ", idResp=" + idResp +
                '}';
    }

    private String format(LocalDate date, DateTimeFormatter formatter) {
        return (date != null) ? date.format(formatter) : "non renseignée";
    }
}
