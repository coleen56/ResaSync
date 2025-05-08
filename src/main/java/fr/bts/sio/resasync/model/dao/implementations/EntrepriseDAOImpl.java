package fr.bts.sio.resasync.model.dao.implementations;

import fr.bts.sio.resasync.model.dao.interfaces.EntrepriseDAO;
import fr.bts.sio.resasync.model.entity.Entreprise;
import fr.bts.sio.resasync.model.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EntrepriseDAOImpl implements EntrepriseDAO {
    private Connection connection;


    @Override
    public Entreprise findById(int idEntreprise) {
        String sql = "SELECT * FROM entreprise WHERE identreprise = ?";
        Entreprise entreprise = null;

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, idEntreprise);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                entreprise = new Entreprise(rs.getInt("identreprise"), rs.getString("raisonsociale"),
                        rs.getString("tel"), rs.getString("numsiret"), rs.getString("email"),
                        rs.getInt("idadressefacturation"));
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
        return entreprise;
    }

    @Override
    public void save(Entreprise entreprise) {
        String sql = "INSERT INTO entreprise(raisonsociale, tel, numsiret, email, idadressefacturation) " +
                "values (?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, entreprise.getRaisonSociale());
            stmt.setString(2, entreprise.getTel());
            stmt.setString(3, entreprise.getNumSiret());
            stmt.setString(4, entreprise.getEmail());
            stmt.setInt(5, entreprise.getIdAdresseFacturation());

            stmt.executeUpdate();
            System.out.println("Entreprise bien inséré en BDD");

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
    public void update(Entreprise entreprise) {
        String sql = "UPDATE entreprise SET raisonsociale = ?, tel = ?, numsiret = ?, email = ?, idadressefacturation = ? WHERE identreprise = ?;\n";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, entreprise.getRaisonSociale());
            stmt.setString(2, entreprise.getTel());
            stmt.setString(3, entreprise.getNumSiret());
            stmt.setString(4, entreprise.getEmail());
            stmt.setInt(5, entreprise.getIdAdresseFacturation());

            stmt.setInt(6, entreprise.getIdEntreprise());

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
    public void delete(Entreprise entreprise) {
        String sql = "DELETE FROM entreprise WHERE identreprise = ?;";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, entreprise.getIdEntreprise());

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
    public ArrayList<Entreprise> findAll() {
        // Requête SQL pour récupérer toutes les entreprises avec les informations supplémentaires
        String sql = "SELECT e.identreprise, e.raisonsociale, e.tel, e.numsiret, e.email "
                + "FROM entreprise e "
                + "JOIN adressefacturation a ON e.idadressefacturation = a.idadressefacturation";

        ArrayList<Entreprise> entreprises = new ArrayList<>();  // Liste pour stocker les entreprises
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();  // Connexion à la base de données
            stmt = conn.prepareStatement(sql);  // Préparer la requête
            ResultSet rs = stmt.executeQuery();  // Exécuter la requête

            // Parcours du résultat de la requête
            while (rs.next()) {
                int idEntreprise = rs.getInt("identreprise");
                String raisonSociale = rs.getString("raisonsociale");
                String tel = rs.getString("tel");
                String numSiret = rs.getString("numsiret");
                String email = rs.getString("email");


                // Créer un objet Entreprise avec les données récupérées
                Entreprise entreprise = new Entreprise(idEntreprise, raisonSociale, tel, numSiret, email);


                // Ajouter l'entreprise à la liste
                entreprises.add(entreprise);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des entreprises", e);
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

        return entreprises;
    }
}
