package fr.bts.sio.resasync.model.dao.implementations;

import fr.bts.sio.resasync.model.dao.interfaces.FacturationDAO;
import fr.bts.sio.resasync.model.entity.Facturation;
import fr.bts.sio.resasync.model.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static fr.bts.sio.resasync.util.Methods.javaDateToSqlDate;

public class FacturationDAOImpl implements FacturationDAO {
    private Connection connection;

    public FacturationDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Facturation findById(int id) {
        String sql = "SELECT * FROM facturation WHERE idfacturation = ?";
        Facturation facturation = null;

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                facturation = new Facturation(rs.getInt("idfacture"), rs.getDouble("totalfacture"), rs.getDate("datefacturation"),
                        rs.getInt("idstatutfacturation"), rs.getInt("idclient"));
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
        return facturation;
    }

    @Override
    public void save(Facturation facturation) {
        String sql = "INSERT INTO facturation(totalfacture, datefacturation, idstatutfacture, idclient) " +
                "values (?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setDouble(1, facturation.getTotalFacture());

            stmt.setDate(2, javaDateToSqlDate(facturation.getDateFacturation()));// conversion de java date a sql date
            stmt.setInt(3, facturation.getIdStatutFacture());
            stmt.setInt(4, facturation.getIdClient());

            stmt.executeUpdate();
            System.out.println("facture bien inséré en BDD");

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
    public void update(Facturation facturation) {
        String sql = "UPDATE facturation SET totalfacture = ?, datefacturation = ?, idstatutfacture = ?, idclient = ? WHERE idfacture = ?;\n";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setDouble(1, facturation.getTotalFacture());
            stmt.setDate(2, javaDateToSqlDate(facturation.getDateFacturation()));
            stmt.setInt(3, facturation.getIdStatutFacture());
            stmt.setInt(4, facturation.getIdClient());

            stmt.setInt(5, facturation.getIdFacture());

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
    public void delete(Facturation facturation) {
        String sql = "DELETE FROM facturation WHERE idfacture = ?;";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, facturation.getIdFacture());

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
