package fr.bts.sio.resasync.model.dao.implementations;

import fr.bts.sio.resasync.model.dao.interfaces.AdresseFacturationDAO;
import fr.bts.sio.resasync.model.entity.AdresseFacturation;
import fr.bts.sio.resasync.model.entity.Utilisateur;
import fr.bts.sio.resasync.model.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdresseFacturationDAOImpl implements AdresseFacturationDAO {
    private Connection connection;

    public AdresseFacturationDAOImpl(Connection connection) {
        this.connection = connection;
    }

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
                "values (?, ?, ?, ?, ?)";

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

            stmt.executeUpdate();
            System.out.println("Client bien inséré en BDD");

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
}
