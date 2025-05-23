package fr.bts.sio.resasync.model.dao.implementations;

import fr.bts.sio.resasync.model.dao.interfaces.AdresseFacturationDAO;
import fr.bts.sio.resasync.model.entity.AdresseFacturation;
import fr.bts.sio.resasync.model.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class AdresseFacturationDAOImpl implements AdresseFacturationDAO {
    private Connection connection;

    @Override
    public AdresseFacturation findById(int id) {
        String sql = "SELECT * FROM adressefacturation WHERE idadressefacturation = ?";
        AdresseFacturation adresseFact = null;

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                adresseFact = new AdresseFacturation(rs.getInt("idadressefacturation"), rs.getString("numero"),
                        rs.getString("voie"), rs.getString("codepostal"), rs.getString("ville"),
                        rs.getString("pays"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Ferme les ressources
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return adresseFact;
    }

    @Override
    public void save(AdresseFacturation adresseFacturation) {
        String sql = "INSERT INTO adressefacturation(numero, voie, codepostal, ville, pays) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, adresseFacturation.getNumero());
            stmt.setString(2, adresseFacturation.getVoie());
            stmt.setString(3, adresseFacturation.getCodePostal());
            stmt.setString(4, adresseFacturation.getVille());
            stmt.setString(5, adresseFacturation.getPays());

            stmt.executeUpdate();

            // Récupérer la clé générée (idAdresseFacturation)
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    adresseFacturation.setIdAdresseFacturation(generatedId); // ← Mise à jour de l’objet
                    System.out.println("Adresse insérée avec ID : " + generatedId);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(AdresseFacturation adresseFacturation) {
        String sql = "UPDATE adressefacturation SET numero = ?, voie = ?, codepostal = ?, ville = ?, pays = ? WHERE idadressefacturation = ?;\n";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, adresseFacturation.getNumero());
            stmt.setString(2, adresseFacturation.getVoie());
            stmt.setString(3, adresseFacturation.getCodePostal());
            stmt.setString(4, adresseFacturation.getVille());
            stmt.setString(5, adresseFacturation.getPays());

            stmt.setInt(6, adresseFacturation.getIdAdresseFacturation());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Ferme les ressources
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(AdresseFacturation adresseFacturation) {
        String sql = "DELETE FROM adressefacturation WHERE idadressefacturation = ?;";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, adresseFacturation.getIdAdresseFacturation());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Ferme les ressources
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<AdresseFacturation> findAll() {
        // Requête SQL pour récupérer toutes les adresses de facturation
        String sql = "SELECT * FROM adressefacturation";

        ArrayList<AdresseFacturation> adresses = new ArrayList<>();  // Liste pour stocker les adresses
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();  // Connexion à la base de données
            stmt = conn.prepareStatement(sql);  // Préparer la requête
            ResultSet rs = stmt.executeQuery();  // Exécuter la requête

            // Parcours du résultat de la requête
            while (rs.next()) {
                int idAdresseFacturation = rs.getInt("idadressefacturation");
                String numero = rs.getString("numero");
                String voie = rs.getString("voie");
                String codePostal = rs.getString("codepostal");
                String ville = rs.getString("ville");
                String pays = rs.getString("pays");

                // Créer un objet AdresseFacturation avec les données récupérées
                AdresseFacturation adresseFacturation = new AdresseFacturation(idAdresseFacturation, numero, voie, codePostal, ville, pays);

                // Ajouter l'adresse à la liste
                adresses.add(adresseFacturation);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des adresses de facturation", e);
        } finally {
            // Fermeture des ressources
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return adresses;
    }

}
