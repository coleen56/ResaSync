package fr.bts.sio.resasync.model.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Reservation {
    private int idReservation;
    private String statusReservation;
    private LocalDate dateReservation;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String nbrPersonnes;
    private int nbrChambre;
    private int idStatusResa;
    private int idClient;
    private int idFacture;
    private int idResp;

    public Reservation(int idReservation, String statusReservation, LocalDate dateReservation, LocalDate dateDebut, LocalDate dateFin, String nbrPersonnes, int nbrChambre, int idStatusResa, int idClient, int idFacture, int idResp) {
        this.idReservation = idReservation;
        this.statusReservation = statusReservation;
        this.dateReservation = dateReservation;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nbrPersonnes = nbrPersonnes;
        this.nbrChambre = nbrChambre;
        this.idStatusResa = idStatusResa;
        this.idClient = idClient;
        this.idFacture = idFacture;
        this.idResp = idResp;
    }

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

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
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

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        return "Reservation{" +
                "idReservation=" + idReservation +
                ", statusReservation='" + statusReservation + '\'' +
                ", dateReservation=" + format(dateReservation, formatter) +
                ", dateDebut=" + format(dateDebut, formatter) +
                ", dateFin=" + format(dateFin, formatter) +
                ", nbrPersonnes='" + nbrPersonnes + '\'' +
                ", nbrChambre=" + nbrChambre +
                ", idStatusResa=" + idStatusResa +
                ", idClient=" + idClient +
                ", idFacture=" + idFacture +
                ", idResp=" + idResp +
                '}';
    }

    private String format(LocalDate date, DateTimeFormatter formatter) {
        return (date != null) ? date.format(formatter) : "non renseignée";
    }
}
