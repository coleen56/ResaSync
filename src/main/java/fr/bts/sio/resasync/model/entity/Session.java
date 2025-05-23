package fr.bts.sio.resasync.model.entity;

import java.time.LocalDateTime;
/**
 * Singleton représentant la session courante d'un utilisateur connecté.
 * Stocke le login, le niveau de droits et la date de connexion.
 * Permet de vérifier si l'utilisateur est admin et de réinitialiser la session.
 */
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

    // méthode qui permet la création de la session + stockage dans instance
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
