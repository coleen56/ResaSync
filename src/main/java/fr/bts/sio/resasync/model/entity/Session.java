package fr.bts.sio.resasync.model.entity;

public class Session {

    private String login;
    private int niveau;

    public Session(String login, int niveau) {
        this.login = login;
        this.niveau = niveau;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    @Override
    public String toString() {
        return "Session{" +
                "login='" + login + '\'' +
                ", niveau=" + niveau +
                '}';
    }
}
