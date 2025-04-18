package fr.bts.sio.resasync.model.entity;

import java.util.Date;

public class Constantes {

    private int idConstante;
    private String libelle;
    private String valeur;
    private Date DateDebut;
    private Date DateFin;

    public Constantes(int idConstante, String libelle, String valeur, Date dateDebut, Date dateFin) {
        this.idConstante = idConstante;
        this.libelle = libelle;
        this.valeur = valeur;
        DateDebut = dateDebut;
        DateFin = dateFin;
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

    public java.sql.Date getDateDebut() {
        return DateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        DateDebut = dateDebut;
    }

    public java.sql.Date getDateFin() {
        return DateFin;
    }

    public void setDateFin(Date dateFin) {
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
