package fr.bts.sio.resasync.model.dao.implementations;

import fr.bts.sio.resasync.model.dao.interfaces.TypePaiementDAO;
import fr.bts.sio.resasync.model.entity.TypePaiement;
import fr.bts.sio.resasync.model.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TypePaiementDAOImpl implements TypePaiementDAO {
    @Override
    public TypePaiement findById(int idTypePaiement) {
        String sql = "SELECT * FROM TypePaiement WHERE idTypePaiement = ?";
        TypePaiement tp = null;

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, idTypePaiement);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                tp = new TypePaiement(rs.getInt("idTypePaiement"), rs.getString("libelle"));
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
        return tp;
    }

    @Override
    public void save(TypePaiement typePaiement) {
        String sql = "INSERT INTO typepaiement(libelle)" +
                "values (?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, typePaiement.getLibelle());

            stmt.executeUpdate();
            System.out.println("type paiement bien inséré en BDD");

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
    public void update(TypePaiement typePaiement) {
        String sql = "UPDATE typepaiement SET libelle = ? where idtypepaiement = ?;";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, typePaiement.getLibelle());

            stmt.setInt(2, typePaiement.getIdTypePaiement());

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
    public void delete(TypePaiement typePaiement) {
        String sql = "DELETE FROM typepaiement WHERE idtypepaiement = ?;";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, typePaiement.getIdTypePaiement());

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
