package fr.bts.sio.resasync.model.dao.implementations;

import fr.bts.sio.resasync.model.dao.interfaces.PayeDAO;
import fr.bts.sio.resasync.model.entity.Paye;
import fr.bts.sio.resasync.model.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PayeDAOImpl implements PayeDAO {
    private Connection connection;

    public PayeDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Paye findById(int idFacture, int idTypePaiement) {
        String sql = "SELECT * FROM paye WHERE idfacture = ? AND idtypepaiement = ?";
        Paye paye = null;

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, idFacture);
            stmt.setInt(2, idTypePaiement);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                paye = new Paye(
                        rs.getInt("idfacture"),
                        rs.getInt("idtypepaiement"),
                        rs.getDouble("montant")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return paye;
    }

    @Override
    public void save(Paye paye) {
        String sql = "INSERT INTO paye(idfacture, idtypepaiement, montant) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, paye.getIdFacture());
            stmt.setInt(2, paye.getIdTypePaiement());
            stmt.setDouble(3, paye.getMontant());

            stmt.executeUpdate();
            System.out.println("Paye bien inséré en BDD");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Paye paye) {
        String sql = "UPDATE paye SET montant = ? WHERE idfacture = ? AND idtypepaiement = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setDouble(1, paye.getMontant());
            stmt.setInt(2, paye.getIdFacture());
            stmt.setInt(3, paye.getIdTypePaiement());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Paye paye) {
        String sql = "DELETE FROM paye WHERE idfacture = ? AND idtypepaiement = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, paye.getIdFacture());
            stmt.setInt(2, paye.getIdTypePaiement());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}