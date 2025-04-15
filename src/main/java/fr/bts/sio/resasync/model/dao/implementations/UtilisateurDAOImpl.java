package fr.bts.sio.resasync.model.dao.implementations;

import fr.bts.sio.resasync.model.dao.interfaces.UtilisateurDAO;
import fr.bts.sio.resasync.model.entity.Utilisateur;
import fr.bts.sio.resasync.model.utils.DatabaseConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilisateurDAOImpl implements UtilisateurDAO {
    private Connection connection;

    @Override
    public Utilisateur findById(int idUtilisateur) {
        String sql = "SELECT * FROM Client WHERE idClient = ?";
        Utilisateur user = null;

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, idUtilisateur);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new Utilisateur(rs.getInt("idutilisateur"), rs.getString("login"),
                        rs.getString("pwd"), rs.getString("nom"), rs.getString("prenom"),
                        rs.getInt("idniveau"));
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
        return user;
    }

    @Override
    public void save(Utilisateur utilisateur) {
        String sql = "INSERT INTO UTILISATEUR(login, pwd, nom, prenom, idniveau)" +
                "values (?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, utilisateur.getLogin());
            stmt.setString(2, BCrypt.hashpw(utilisateur.getPwd(), BCrypt.gensalt()));
            stmt.setString(3, utilisateur.getNom());
            stmt.setString(4, utilisateur.getPrenom());
            stmt.setInt(5, utilisateur.getIdNiveau());

            stmt.executeUpdate();
            System.out.println("user bien inséré en BDD");

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
    public void update(Utilisateur utilisateur) {
        String sql = "UPDATE utilisateur SET login = ?, pwd = ?, nom = ?, prenom = ?, idniveau = ? where idutilisateur = ?;";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, utilisateur.getLogin());
            stmt.setString(2, utilisateur.getPwd());
            stmt.setString(3, utilisateur.getNom());
            stmt.setString(4, utilisateur.getPrenom());
            stmt.setInt(5, utilisateur.getIdNiveau());

            stmt.setInt(6, utilisateur.getId());

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
    public void delete(Utilisateur utilisateur) {
        String sql = "DELETE FROM utilisateur WHERE idutilisateur = ?;";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, utilisateur.getId());

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
    public Utilisateur findByLogin(String login) {
        String sql = "SELECT * FROM UTILISATEUR where login = ?";

        Utilisateur user = null;

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new Utilisateur(rs.getInt("idutilisateur"), rs.getString("login"),
                        rs.getString("pwd"), rs.getString("nom"), rs.getString("prenom"),
                        rs.getInt("idniveau"));
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
        return user;
    }
}
