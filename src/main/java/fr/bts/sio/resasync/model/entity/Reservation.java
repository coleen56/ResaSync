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
    private int idEntreprise;
    private String raisonSociale;
    private int idStatutResa;
    private String libelleStatut;
    private int idClient;
    private String nomClient;
    private String prenomClient;
    private int idFacture;


    public Reservation(int idReservation, LocalDate dateReservation, LocalDate dateDebut, LocalDate dateFin, int nbrPersonnes, int nbrChambre, int idEntreprise, int idStatutResa, int idClient, int idFacture) {
        this.idReservation = idReservation;
        this.dateReservation = dateReservation;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.nbrPersonnes = nbrPersonnes;
        this.nbrChambre = nbrChambre;
        this.idEntreprise = idEntreprise;
        this.idStatutResa = idStatutResa;
        this.idClient = idClient;
        this.idFacture = idFacture;
    }

    // Constructeur vide
    public Reservation() {
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

    public int getIdEntreprise() {
        return idEntreprise;
    }

    public void setIdEntreprise(int idEntreprise) {
        this.idEntreprise = idEntreprise;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public int getIdStatutResa() {
        return idStatutResa;
    }

    public void setIdStatutResa(int idStatutResa) {
        this.idStatutResa = idStatutResa;
    }

    public String getLibelleStatut() {
        return libelleStatut;
    }

    public void setLibelleStatut(String libelleStatut) {
        this.libelleStatut = libelleStatut;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getPrenomClient() {
        return prenomClient;
    }

    public void setPrenomClient(String prenomClient) {
        this.prenomClient = prenomClient;
    }

    public int getIdFacture() {
        return idFacture;
    }

    public void setIdFacture(int idFacture) {
        this.idFacture = idFacture;
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
                ", idEntreprise=" + idEntreprise +
                ", idStatutResa=" + idStatutResa +
                ", idClient=" + idClient +
                ", idFacture=" + idFacture +
                '}';
    }


    private String format(LocalDate date, DateTimeFormatter formatter) {
        return (date != null) ? date.format(formatter) : "non renseignée";
    }
}
