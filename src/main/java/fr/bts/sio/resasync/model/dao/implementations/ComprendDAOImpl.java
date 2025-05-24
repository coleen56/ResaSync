package fr.bts.sio.resasync.model.dao.implementations;

import fr.bts.sio.resasync.model.dao.interfaces.ComprendDAO;
import fr.bts.sio.resasync.model.entity.Comprend;
import fr.bts.sio.resasync.model.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComprendDAOImpl implements ComprendDAO {
    private Connection connection;


    @Override
    public Comprend findById(int idreservation, int idoption) {
        String sql = "SELECT * FROM comprend WHERE idreservation = ? and idoption = ?;";
        Comprend compr = null;

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, idreservation);
            stmt.setInt(2, idoption);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                compr = new Comprend(rs.getInt("idreservation"), rs.getInt("idoption"), rs.getInt("quantite"));
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
        return compr;
    }

    @Override
    public void save(Comprend compr) {
        String sql = "INSERT INTO comprend(idreservation, idoption, quantite) " +
                "values (?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, compr.getIdReservation());
            stmt.setInt(2, compr.getIdOption());
            stmt.setInt(3, compr.getQuantite());

            stmt.executeUpdate();
            System.out.println("lien entre option et resa bien inséré en BDD");

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
    public void update(Comprend compr) {
        String sql = "UPDATE comprend SET quantite = ? where idreservation = ? and idoption = ?;";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, compr.getQuantite());

            stmt.setInt(2, compr.getIdReservation());
            stmt.setInt(3, compr.getIdOption());


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
    public void delete(Comprend compr) {
        String sql = "DELETE FROM comprend WHERE idreservation = ? and idoption = ?;";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, compr.getIdReservation());
            stmt.setInt(2, compr.getIdOption());

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
    public List<Comprend> findOptionsByReservationId(int idReservation) {
        List<Comprend> listeOptions = new ArrayList<>();
        String sql = "SELECT * FROM comprend WHERE idreservation = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idReservation);

            rs = stmt.executeQuery();

            while (rs.next()) {
                int idOption = rs.getInt("idoption");
                int quantite = rs.getInt("quantite");

                Comprend option = new Comprend(idReservation, idOption, quantite);
                listeOptions.add(option);
            }

        } catch (SQLException e) {
            e.printStackTrace();
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

    @Override
    public void deleteAllFromIdReservation(int idreservation) {
        String sql = "DELETE FROM comprend WHERE idreservation = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, idreservation);

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

