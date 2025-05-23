package fr.bts.sio.resasync.model.dao.implementations;

import fr.bts.sio.resasync.model.dao.interfaces.OptionReservationDAO;
import fr.bts.sio.resasync.model.entity.OptionReservation;
import fr.bts.sio.resasync.model.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OptionReservationDAOImpl implements OptionReservationDAO {

    @Override
    public OptionReservation findById(int id) {
        String sql = "SELECT * FROM OptionReservation WHERE idOption = ?";
        OptionReservation option = null;

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                option = new OptionReservation(rs.getDouble("prixUnitaire"), rs.getString("libelle"), rs.getInt("idOption"));
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
        return option;
    }

    @Override
    public void save(OptionReservation option) {
        String sql = "INSERT INTO optionreservation(prixUnitaire, libelle)" +
                "values (?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setDouble(1, option.getPrixUnitaire());
            stmt.setString(2, option.getLibelle());

            stmt.executeUpdate();
            System.out.println("option bien insérée en BDD");

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
    public void update(OptionReservation option) {
        String sql = "UPDATE optionreservation SET libelle = ?, prixUnitaire = ? where idOption = ?;";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, option.getLibelle());
            stmt.setDouble(2, option.getPrixUnitaire());

            stmt.setInt(3, option.getIdOption());

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
    public void delete(OptionReservation option) {
        String sql = "DELETE FROM optionreservation WHERE idoption = ?;";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, option.getIdOption());

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
    public List<OptionReservation> optionReservationsAll() {
        String sql = "SELECT * FROM optionreservation";
        List<OptionReservation> listeOptions = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                OptionReservation option = new OptionReservation(
                        rs.getDouble("prixUnitaire"),
                        rs.getString("libelle"),
                        rs.getInt("idOption")
                );
                listeOptions.add(option);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des options de réservation", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return listeOptions;
    }

    public OptionReservation findByLibelle(String libelle) {
        for (OptionReservation option : optionReservationsAll()) {
            if (option.getLibelle().equalsIgnoreCase(libelle)) {
                return option;
            }
        }
        return null;
    }
}
