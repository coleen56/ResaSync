package fr.bts.sio.resasync.model.dao.implementations;

import fr.bts.sio.resasync.model.dao.interfaces.RelieDAO;
import fr.bts.sio.resasync.model.entity.Relie;
import fr.bts.sio.resasync.model.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RelieDAOImpl implements RelieDAO {
    private Connection connection;


    @Override
    public Relie findById(int idChambre, int idReservation) {
        String sql = "SELECT * FROM relie WHERE idchambre = ? AND idreservation = ?";
        Relie relie = null;

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, idChambre);
            stmt.setInt(2, idReservation);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                relie = new Relie(
                        rs.getInt("idchambre"),
                        rs.getInt("idreservation")
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

        return relie;
    }

    @Override
    public void save(Relie relie) {
        String sql = "INSERT INTO relie(idchambre, idreservation) VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, relie.getIdChambre());
            stmt.setInt(2, relie.getIdReservation());

            stmt.executeUpdate();
            System.out.println("Liaison Relie bien insérée en BDD");
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
    public void delete(Relie relie) {
        String sql = "DELETE FROM relie WHERE idchambre = ? AND idreservation = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, relie.getIdChambre());
            stmt.setInt(2, relie.getIdReservation());

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
