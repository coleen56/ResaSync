package fr.bts.sio.resasync.model.dao.implementations;

import fr.bts.sio.resasync.model.dao.interfaces.NiveauDroitsDAO;
import fr.bts.sio.resasync.model.entity.Comprend;
import fr.bts.sio.resasync.model.entity.NiveauDroits;
import fr.bts.sio.resasync.model.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NiveauDroitsDAOImpl implements NiveauDroitsDAO {
    private Connection connection;


    @Override
    public NiveauDroits findById(int idniveau) {
        String sql = "SELECT * FROM niveaudroit WHERE idniveau = ?;";
        NiveauDroits niveau = null;

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, idniveau);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                niveau = new NiveauDroits(rs.getInt("idniveau"), rs.getString("libelle"));
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
        return niveau;
    }

    @Override
    public void save(NiveauDroits niveau) {
        String sql = "INSERT INTO niveaudroit(idniveau, libelle) " +
                "values (?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, niveau.getIdNiveau());
            stmt.setString(2, niveau.getLibelle());

            stmt.executeUpdate();
            System.out.println("Niveau de droits bien inséré en BDD");

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
    public void update(NiveauDroits niveau) {
        String sql = "UPDATE niveaudroit SET libelle = ? where idniveau = ?;";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, niveau.getLibelle());

            stmt.setInt(2, niveau.getIdNiveau());


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
    public void delete(NiveauDroits niveau) {
        String sql = "DELETE FROM niveaudroit WHERE idniveau = ?;";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, niveau.getIdNiveau());

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
