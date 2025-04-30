package fr.bts.sio.resasync.model.dao.implementations;

import fr.bts.sio.resasync.model.dao.interfaces.ClientDAO;
import fr.bts.sio.resasync.model.entity.Client;
import fr.bts.sio.resasync.model.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientDAOImpl implements ClientDAO {
    @Override
    public Client findById(int id) {
        String sql = "SELECT * FROM Client WHERE idClient = ?";
        Client client = null;

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                client = new Client(rs.getInt("idclient"), rs.getString("nom"),
                        rs.getString("prenom"), rs.getString("tel"), rs.getString("email"),
                        rs.getString("datenaissance"), rs.getInt("identreprise"),
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
        return client;
    }

        @Override
        public void save(Client client) {
            String sql = "INSERT INTO CLIENT(nom, prenom, tel, email, datenaissance, identreprise, idadressefacturation)" +
                    "values (?, ?, ?, ?, ?, ?, ?)";

            Connection conn = null;
            PreparedStatement stmt = null;

            try {
                conn = DatabaseConnection.getConnection();
                stmt = conn.prepareStatement(sql);

                stmt.setString(1, client.getNom());
                stmt.setString(2, client.getPrenom());
                stmt.setString(3, client.getTel());
                stmt.setString(4, client.getEmail());
                stmt.setString(5, client.getDateNaissance());
                stmt.setInt(6, client.getIdEntreprise());
                stmt.setInt(7, client.getIdAdresseFacturation());

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
    public void update(Client client) {
        String sql = "UPDATE client SET nom = ?, prenom = ?, tel = ?, email = ?, datenaissance = ?, identreprise = ?, idadressefacturation = ? WHERE idclient = ?;\n";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getPrenom());
            stmt.setString(3, client.getTel());
            stmt.setString(4, client.getEmail());
            stmt.setString(5, client.getDateNaissance());
            stmt.setInt(6, client.getIdEntreprise());
            stmt.setInt(7, client.getIdAdresseFacturation());

            stmt.setInt(8, client.getIdClient());

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
    public void delete(Client client) {
        String sql = "DELETE FROM client WHERE idclient = ?;";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, client.getIdClient());

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
