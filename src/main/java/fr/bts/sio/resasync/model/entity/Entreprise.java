package fr.bts.sio.resasync.model.entity;

public class Entreprise {
    private int idEntreprise;
    private String raisonSociale;
    private String tel;
    private String numSiret;
    private String email;
    private int idAdresseFacturation;

    public Entreprise() {
    }

    public Entreprise(int idEntreprise, String raisonSociale, String tel, String numSiret, String email, int idAdresseFacturation) {
        this.idEntreprise = idEntreprise;
        this.raisonSociale = raisonSociale;
        this.tel = tel;
        this.numSiret = numSiret;
        this.email = email;
        this.idAdresseFacturation = idAdresseFacturation;
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

    public int getIdAdresseFacturation() {
        return idAdresseFacturation;
    }

    public void setIdAdresseFacturation(int idAdresseFacturation) {
        this.idAdresseFacturation = idAdresseFacturation;
    }

    @Override
    public String toString() {
        return "Entreprise{" +
                "idEntreprise=" + idEntreprise +
                ", raisonSociale='" + raisonSociale + '\'' +
                ", tel='" + tel + '\'' +
                ", numSiret='" + numSiret + '\'' +
                ", email='" + email + '\'' +
                ", idAdresseFacturation=" + idAdresseFacturation +
                '}';
    }
}
