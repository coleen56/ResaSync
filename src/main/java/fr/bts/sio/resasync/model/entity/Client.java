package fr.bts.sio.resasync.model.entity;

import java.time.LocalDate;

public class Client {
    private int idClient;
    private String nom;
    private String prenom;
    private String tel;
    private String email;
    private LocalDate dateNaissance;
    private int idEntreprise;
    private int idAdresseFacturation;

    public Client (int idClient, String nom, String prenom, String tel, String email, LocalDate dateNaissance, int idEntreprise, int idAddresseFacturation){
        this.idClient = idClient;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
        this.dateNaissance = dateNaissance;
        this.idEntreprise = idEntreprise;
        this.idAdresseFacturation = idAddresseFacturation;
    }

    //getters and setters


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

    public int getIdEntreprise() {
        return idEntreprise;
    }

    public void setIdEntreprise(int idEntreprise) {
        this.idEntreprise = idEntreprise;
    }

    public int getIdAdresseFacturation() {
        return idAdresseFacturation;
    }

    public void setIdAdresseFacturation(int idAdresseFacturation) {
        this.idAdresseFacturation = idAdresseFacturation;
    }
    // To String

    @Override
    public String toString() {
        return "Client{" +
                "idClient=" + idClient +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", idEntreprise=" + idEntreprise +
                ", idAdresseFacturation=" + idAdresseFacturation +
                '}';
    }
}
