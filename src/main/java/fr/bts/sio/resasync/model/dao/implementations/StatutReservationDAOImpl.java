package fr.bts.sio.resasync.model.dao.implementations;

import fr.bts.sio.resasync.model.dao.interfaces.StatutReservationDAO;
import fr.bts.sio.resasync.model.entity.StatutReservation;
import fr.bts.sio.resasync.model.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatutReservationDAOImpl implements StatutReservationDAO {
    @Override
    public StatutReservation findById(int id) {
        String sql = "SELECT * FROM statutreservation WHERE idStatutreservation = ?";
        StatutReservation statut = null;

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                statut = new StatutReservation(rs.getInt("idstatutreservation"), rs.getString("libelle"));
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
        return statut;
    }

    @Override
    public void save(StatutReservation statut) {
        String sql = "INSERT INTO statutReservation(libelle)" +
                "values (?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, statut.getLibelle());

            stmt.executeUpdate();
            System.out.println("statut reservation bien inséré en BDD");

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
    public void update(StatutReservation statut) {
        String sql = "UPDATE statutreservation SET libelle = ? where idstatutreservation = ?;";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, statut.getLibelle());

            stmt.setInt(2, statut.getIdStatutResa());

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
    public void delete(StatutReservation statut) {
        String sql = "DELETE FROM statutreservation WHERE idstatutreservation = ?;";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, statut.getIdStatutResa());

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
    public List<StatutReservation> findAll() {
        List<StatutReservation> statuts = new ArrayList<>();
        String sql = "SELECT idStatutResa, libelle FROM StatutReservation";

        try (
             Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery())
        {

            while (resultSet.next()) {
                StatutReservation statut = new StatutReservation(
                        resultSet.getInt("idStatutResa"),
                        resultSet.getString("libelle")
                );
                statuts.add(statut);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statuts;
    }

}
