package fr.bts.sio.resasync.model.entity;

import java.time.LocalDateTime;

public class Session {
    private static Session instance;

    private String login;
    private int niveau;
    private LocalDateTime dateConnexion;

    private Session(String login, int niveau, LocalDateTime dateConnexion) {
        this.login = login;
        this.niveau = niveau;
        this.dateConnexion = dateConnexion;
    }

    // Factory method pour créer une session
    public static void creerSession(String login, int niveau) {
        instance = new Session(login, niveau, LocalDateTime.now());
    }

    public static Session getInstance() {
        return instance;
    }

    // Getters
    public String getLogin() {
        return login;
    }

    public int getNiveau() {
        return niveau;
    }

    public LocalDateTime getDateConnexion() {
        return dateConnexion;
    }

    public static void reset() {
        instance = null;
    }

    public boolean isAdmin() {
        return niveau > 0;
    }

    @Override
    public String toString() {
        return "Session{" +
                "login='" + login + '\'' +
                ", niveau=" + niveau +
                ", dateConnexion=" + dateConnexion +
                '}';
    }
}
