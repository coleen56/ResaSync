package fr.bts.sio.resasync.model.entity;

import java.sql.Date;
import java.time.LocalDate;
/**
 * Représente un client du système de réservation.
 * Un client possède des informations personnelles ainsi qu'une adresse de facturation associée.
 * Les informations stockées incluent l'identifiant du client, le nom, le prénom, le numéro de téléphone,
 * l'adresse email, la date de naissance et une adresse de facturation.
 */
public class Client {
    private int idClient;
    private String nom;
    private String prenom;
    private String tel;
    private String email;
    private LocalDate dateNaissance;
    private AdresseFacturation AdresseFacturation;

    public Client (int idClient, String nom, String prenom, String tel, String email,LocalDate dateNaissance, AdresseFacturation AdresseFacturation){
        this.idClient = idClient;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
        this.dateNaissance = dateNaissance;
        this.AdresseFacturation = AdresseFacturation;
    }

    public Client(int idClient,String nom, String prenom, String tel, String email, LocalDate dateNaissance) {
        this.idClient = idClient;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
        this.dateNaissance = dateNaissance;
    }

    public Client(String nom, String prenom, String tel, String email, LocalDate dateNaissance,AdresseFacturation adresseFacturation) {
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
        this.dateNaissance = dateNaissance;
        this.AdresseFacturation = adresseFacturation ;
    }


    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public fr.bts.sio.resasync.model.entity.AdresseFacturation getAdresseFacturation() {
        return AdresseFacturation;
    }

    public void setAdresseFacturation(fr.bts.sio.resasync.model.entity.AdresseFacturation adresseFacturation) {
        AdresseFacturation = adresseFacturation;
    }

    @Override
    public String toString() {
        return "Client{" +
                "idClient=" + idClient +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", dateNaissance='" + dateNaissance + '\'' +
                ", AdresseFacturation=" + AdresseFacturation +
                '}';
    }
}
