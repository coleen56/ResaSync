package fr.bts.sio.resasync.model.dao.implementations;

import fr.bts.sio.resasync.model.dao.interfaces.EntrepriseDAO;
import fr.bts.sio.resasync.model.entity.AdresseFacturation;
import fr.bts.sio.resasync.model.entity.Entreprise;
import fr.bts.sio.resasync.model.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EntrepriseDAOImpl implements EntrepriseDAO {
    private Connection connection;

    public EntrepriseDAOImpl(Connection connection) {
        this.connection = connection;
    }

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
}
