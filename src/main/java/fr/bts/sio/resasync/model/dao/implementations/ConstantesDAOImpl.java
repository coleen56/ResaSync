package fr.bts.sio.resasync.model.dao.implementations;

import fr.bts.sio.resasync.model.dao.interfaces.ConstantesDAO;
import fr.bts.sio.resasync.model.entity.Constantes;
import fr.bts.sio.resasync.model.utils.DatabaseConnection;
import java.sql.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import fr.bts.sio.resasync.util.Methods;

public class ConstantesDAOImpl implements ConstantesDAO {
    private Connection connection;

    public ConstantesDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Constantes findById(int idConstantes) {
        String sql = "SELECT * FROM constantes WHERE idconstantes = ?";
        Constantes constantes = null;

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, idConstantes);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                constantes = new Constantes(rs.getInt("idconstantes"), rs.getString("libelle"),
                        rs.getString("valeur"), rs.getDate("datedebut").toLocalDate(), rs.getDate("datefin").toLocalDate());
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
        return constantes;
    }

    @Override
    public void save(Constantes constantes) {
        String sql = "INSERT INTO constantes(libelle, valeur, datedebut,datefin) " +
                "values (?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, constantes.getLibelle());
            stmt.setString(2, constantes.getValeur());
            stmt.setDate(3, Date.valueOf(constantes.getDateDebut()));
            stmt.setDate(4, Date.valueOf(constantes.getDateFin()));


            stmt.executeUpdate();
            System.out.println("Constantes bien insérées en BDD");

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
    public void update(Constantes constantes) {
        String sql = "UPDATE constantes SET libelle = ?, valeur = ?, datedebut = ?, datefin = ? WHERE idconstantes = ?;\n";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, constantes.getLibelle());
            stmt.setString(2, constantes.getValeur());
            stmt.setDate(3, Date.valueOf(constantes.getDateDebut()));
            stmt.setDate(4, Date.valueOf(constantes.getDateFin()));
            stmt.setInt(5, constantes.getIdConstante());

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
    public void delete(Constantes constantes) {
        String sql = "DELETE FROM constantes WHERE idconstantes = ?;";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, constantes.getIdConstante());

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


