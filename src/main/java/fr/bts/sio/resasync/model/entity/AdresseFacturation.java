package fr.bts.sio.resasync.model.entity;

/**
 * Représente une adresse de facturation pour un client ou une entité.
 * Cette classe contient les informations nécessaires pour identifier une adresse utilisée lors de la facturation,
 * telles que le numéro, la voie, le code postal, la ville et le pays.
 */
public class AdresseFacturation {
    private int idAdresseFacturation;
    private String numero;
    private String voie;
    private String codePostal;
    private String ville;
    private String pays;

    public AdresseFacturation(int idAdresseFacturation, String numero, String voie, String codePostal, String ville, String pays) {
        this.idAdresseFacturation = idAdresseFacturation;
        this.numero = numero;
        this.voie = voie;
        this.codePostal = codePostal;
        this.ville = ville;
        this.pays = pays;
    }

    public int getIdAdresseFacturation() {
        return idAdresseFacturation;
    }

    public void setIdAdresseFacturation(int idAdresseFacturation) {
        this.idAdresseFacturation = idAdresseFacturation;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getVoie() {
        return voie;
    }

    public void setVoie(String voie) {
        this.voie = voie;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    @Override
    public String toString() {
        return numero +' '+ voie + ' ' +
                codePostal + ' ' + ville + ' ' +
                pays;
    }
}
