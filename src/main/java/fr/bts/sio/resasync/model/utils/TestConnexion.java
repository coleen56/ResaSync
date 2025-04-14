package fr.bts.sio.resasync.model.utils;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConnexion {
    public static void main(String[] args) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Connexion à la base H2 réussie !");
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("❌ Erreur de connexion : " + e.getMessage());
            e.printStackTrace();
        }
    }
}