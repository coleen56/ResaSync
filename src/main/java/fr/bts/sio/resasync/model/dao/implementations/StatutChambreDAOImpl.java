package fr.bts.sio.resasync.model.dao.implementations;

import fr.bts.sio.resasync.model.dao.interfaces.StatutChambreDAO;
import fr.bts.sio.resasync.model.entity.StatutChambre;
import fr.bts.sio.resasync.model.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatutChambreDAOImpl implements StatutChambreDAO {
    private Connection connection;

    public StatutChambreDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public StatutChambre findById(int idStatutChambre) {
        String sql = "SELECT * FROM statutchambre WHERE idstatutchambre = ?";
        StatutChambre statut = null;

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, idStatutChambre);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                statut = new StatutChambre(
                        rs.getInt("idstatutchambre"),
                        rs.getString("libelle")
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

        return statut;
    }

    @Override
    public void save(StatutChambre statutChambre) {
        String sql = "INSERT INTO statutchambre(libelle) VALUES (?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, statutChambre.getLibelle());

            stmt.executeUpdate();
            System.out.println("StatutChambre inséré en BDD");
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
    public void update(StatutChambre statutChambre) {
        String sql = "UPDATE statutchambre SET libelle = ? WHERE idstatutchambre = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, statutChambre.getLibelle());
            stmt.setInt(2, statutChambre.getIdStatutChambre());

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
    public void delete(StatutChambre statutChambre) {
        String sql = "DELETE FROM statutchambre WHERE idstatutchambre = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, statutChambre.getIdStatutChambre());

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
