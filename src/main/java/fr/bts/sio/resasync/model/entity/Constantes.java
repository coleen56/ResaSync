package fr.bts.sio.resasync.model.entity;

import java.time.LocalDate;
import java.util.Date;

public class Constantes {

    private int idConstante;
    private String libelle;
    private String valeur;
    private LocalDate DateDebut;
    private LocalDate DateFin;

    public Constantes() { // pour jackson
    }

    public Constantes(String libelle, String valeur, LocalDate dateDebut, LocalDate dateFin) {
        this.libelle = libelle;
        this.valeur = valeur;
        this.DateDebut = dateDebut;
        this.DateFin = dateFin;
    }

    public Constantes(int idConstante, String libelle, String valeur, LocalDate dateDebut, LocalDate dateFin) {
        this.idConstante = idConstante;
        this.libelle = libelle;
        this.valeur = valeur;
        this.DateDebut = dateDebut;
        this.DateFin = dateFin;
    }

    public int getIdConstante() {
        return idConstante;
    }

    public void setIdConstante(int idConstante) {
        this.idConstante = idConstante;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public LocalDate getDateDebut() {
        return DateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        DateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return DateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        DateFin = dateFin;
    }

    @Override
    public String toString() {
        return "Constantes{" +
                "idConstante=" + idConstante +
                ", libelle='" + libelle + '\'' +
                ", valeur='" + valeur + '\'' +
                ", DateDebut=" + DateDebut +
                ", DateFin=" + DateFin +
                '}';
    }
}