package fr.bts.sio.resasync.model.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    //private static final String URL = "jdbc:h2:./src/main/java/fr/bts/sio/resasync/storage/resa"; // fichier .mv.db à la racine ou dans un dossier /data
    private static final String URL = "jdbc:h2:./resa";
    private static final String USER = "";
    private static final String PASSWORD = "";

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }
}