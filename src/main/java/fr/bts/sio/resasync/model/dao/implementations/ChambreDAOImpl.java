package fr.bts.sio.resasync.model.dao.implementations;

import fr.bts.sio.resasync.model.dao.interfaces.ChambreDAO;
import fr.bts.sio.resasync.model.entity.Chambre;
import fr.bts.sio.resasync.model.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChambreDAOImpl implements ChambreDAO {
    private Connection connection;

    public ChambreDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Chambre findById(int id) {
        String sql = "SELECT * FROM chambre WHERE idchambre = ?";
        Chambre chambre = null;

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                chambre = new Chambre(rs.getInt("idchambre"), rs.getInt("numchambre"),
                        rs.getInt("idtypechambre"), rs.getInt("idstatutchambre"));
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
        return chambre;
    }

    @Override
    public void save(Chambre chambre) {
        String sql = "INSERT INTO chambre(numchambre, idtypechambre, idstatutchambre) " +
                "values (?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, chambre.getNumChambre());
            stmt.setInt(2, chambre.getIdTypeChambre());
            stmt.setInt(3, chambre.getIdStatutChambre());

            stmt.executeUpdate();
            System.out.println("chambre bien insérée en BDD");

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
    public void update(Chambre chambre) {
        String sql = "UPDATE chambre SET numchambre = ?, idtypechambre = ?, idstatutchambre = ? where idchambre = ?;";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, chambre.getNumChambre());
            stmt.setInt(2, chambre.getIdTypeChambre());
            stmt.setInt(3, chambre.getIdStatutChambre());

            stmt.setInt(5, chambre.getIdChambre());


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
    public void delete(Chambre chambre) {
        String sql = "DELETE FROM chambre WHERE idchambre = ?;";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, chambre.getIdChambre());

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
