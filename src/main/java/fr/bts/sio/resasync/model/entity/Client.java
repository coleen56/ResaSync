package fr.bts.sio.resasync.model.entity;

import java.sql.Date;

public class Client {
    private int idClient;
    private String nom;
    private String prenom;
    private String tel;
    private String email;
    private String dateNaissance;
    private Entreprise Entreprise;
    private AdresseFacturation AdresseFacturation;

    public Client (int idClient, String nom, String prenom, String tel, String email,String dateNaissance, Entreprise Entreprise, AdresseFacturation AdresseFacturation){
        this.idClient = idClient;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
        this.dateNaissance = dateNaissance;
        this.Entreprise = Entreprise;
        this.AdresseFacturation = AdresseFacturation;
    }

    public Client(int idClient,String nom, String prenom, String tel, String email, String dateNaissance) {
        this.idClient = idClient;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
        this.dateNaissance = dateNaissance;
    }

    public Client(String nom, String prenom, String tel, String email, String dateNaissance,Entreprise entreprise, AdresseFacturation adresseFacturation) {
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
        this.dateNaissance = dateNaissance;
        this.Entreprise = entreprise;
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

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public fr.bts.sio.resasync.model.entity.Entreprise getEntreprise() {
        return Entreprise;
    }

    public void setEntreprise(fr.bts.sio.resasync.model.entity.Entreprise entreprise) {
        Entreprise = entreprise;
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
                ", Entreprise=" + Entreprise +
                ", AdresseFacturation=" + AdresseFacturation +
                '}';
    }
}
