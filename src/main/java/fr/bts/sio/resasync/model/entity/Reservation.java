package fr.bts.sio.resasync.model.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Reservation {
    private int idReservation;
    private LocalDate dateReservation;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String nbrPersonnes;
    private int nbrChambre;
    private int idStatutResa;
    private int idClient;
    private int idFacture;
    private int idResp;

    public Reservation(int idReservation, LocalDate dateReservation, LocalDate dateDebut, LocalDate dateFin, String nbrPersonnes, int nbrChambre, int idStatusResa, int idClient, int idFacture, int idResp) {
        this.idReservation = idReservation;
        this.dateReservation = dateReservation;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nbrPersonnes = nbrPersonnes;
        this.nbrChambre = nbrChambre;
        this.idStatutResa = idStatutResa;
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

    public int getIdStatutResa() {
        return idStatutResa;
    }

    public void setIdStatutResa(int idStatutResa) {
        this.idStatutResa = idStatutResa;
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
                ", dateReservation=" + format(dateReservation, formatter) +
                ", dateDebut=" + format(dateDebut, formatter) +
                ", dateFin=" + format(dateFin, formatter) +
                ", nbrPersonnes='" + nbrPersonnes + '\'' +
                ", nbrChambre=" + nbrChambre +
                ", idStatutResa=" + idStatutResa +
                ", idClient=" + idClient +
                ", idFacture=" + idFacture +
                ", idResp=" + idResp +
                '}';
    }

    private String format(LocalDate date, DateTimeFormatter formatter) {
        return (date != null) ? date.format(formatter) : "non renseignée";
    }
}
