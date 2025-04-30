package fr.bts.sio.resasync.model.entity;

public class Utilisateur {
    private int idUtilisateur;
    private String login;
    private String pwd;
    private String nom;
    private String prenom;
    private int idNiveau;

    public Utilisateur (int idutilisateur, String login, String pwd, String nom, String prenom, int idNiveau){
        this.idUtilisateur = idutilisateur;
        this.login = login;
        this.pwd = pwd;
        this.nom = nom;
        this.prenom = prenom;
        this.idNiveau = idNiveau;
    }

    public Utilisateur(String login, String pwd, String nom, String prenom, int idNiveau) {
        this.login = login;
        this.pwd = pwd;
        this.nom = nom;
        this.prenom = prenom;
        this.idNiveau = idNiveau;
    }

    // Getters and Setters

    public int getId() {
        return idUtilisateur;
    }

    public void setId(int id) {
        this.idUtilisateur = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
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

    public int getIdNiveau() {
        return idNiveau;
    }

    public void setIdNiveau(int idNiveau) {
        this.idNiveau = idNiveau;
    }


    @Override
    public String toString() {
        return "Utilisateur{" +
                "idUtilisateur=" + idUtilisateur +
                ", login='" + login + '\'' +
                ", pwd='" + pwd + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", idNiveau=" + idNiveau +
                '}';
    }
}
