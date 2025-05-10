package fr.bts.sio.resasync.model.entity;

public class Entreprise {
    private int idEntreprise;
    private String raisonSociale;
    private String tel;
    private String numSiret;
    private String email;
    private AdresseFacturation adresseFacturationEntreprise;

    public Entreprise() {
    }

    public Entreprise(int idEntreprise, String raisonSociale, String tel, String numSiret, String email, AdresseFacturation adresseFacturationEntreprise) {
        this.idEntreprise = idEntreprise;
        this.raisonSociale = raisonSociale;
        this.tel = tel;
        this.numSiret = numSiret;
        this.email = email;
        this.adresseFacturationEntreprise = adresseFacturationEntreprise;
    }

    public Entreprise(int idEntreprise, String raisonSociale, String tel, String numSiret, String email) {
        this.idEntreprise = idEntreprise;
        this.raisonSociale = raisonSociale;
        this.tel = tel;
        this.numSiret = numSiret;
        this.email = email;

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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getNumSiret() {
        return numSiret;
    }

    public void setNumSiret(String numSiret) {
        this.numSiret = numSiret;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AdresseFacturation getAdresseFacturationEntreprise() {
        return adresseFacturationEntreprise;
    }

    public void setAdresseFacturationEntreprise(AdresseFacturation adresseFacturationEntreprise) {
        this.adresseFacturationEntreprise = adresseFacturationEntreprise;
    }

    @Override
    public String toString() {
        return "Entreprise{" +
                "idEntreprise=" + idEntreprise +
                ", raisonSociale='" + raisonSociale + '\'' +
                ", tel='" + tel + '\'' +
                ", numSiret='" + numSiret + '\'' +
                ", email='" + email + '\'' +
                ", idAdresseFacturation=" + adresseFacturationEntreprise +
                '}';
    }
}
