package fr.bts.sio.resasync.model.entity;

public class Session {

    private static String login; // Déclaré comme static
    private static int niveau;   // Déclaré comme static

    public Session(String login, int niveau) {
        Session.login = login; // Utilisation de la variable statique
        Session.niveau = niveau; // Utilisation de la variable statique
    }

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String login) {
        Session.login = login; // Utilisation de la variable statique
    }

    public static int getNiveau() {
        return niveau;
    }

    public static void setNiveau(int niveau) {
        Session.niveau = niveau; // Utilisation de la variable statique
    }

    @Override
    public String toString() {
        return "Session{" +
                "login='" + login + '\'' +
                ", niveau=" + niveau +
                '}';
    }
}