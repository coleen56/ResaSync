package fr.bts.sio.resasync.model.dao.implementations;

import fr.bts.sio.resasync.model.dao.interfaces.ChambreDAO;
import fr.bts.sio.resasync.model.entity.Chambre;
import fr.bts.sio.resasync.model.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChambreDAOImpl implements ChambreDAO {
    private Connection connection;


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

            stmt.setInt(4, chambre.getIdChambre());


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

    public void ajouterChambre(Chambre chambre) {
        String sql = "INSERT INTO chambre(numchambre, idtypechambre, idstatutchambre) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, chambre.getNumChambre());
            stmt.setInt(2, chambre.getIdTypeChambre());
            stmt.setInt(3, chambre.getIdStatutChambre());
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

    public ArrayList<Chambre> findAll() {
        String sql = "SELECT c.idchambre, c.numchambre, c.idtypechambre, c.idstatutchambre, s.libelle AS libelle_statut, t.libelle AS libelle_type "
                + "FROM chambre c "
                + "JOIN statutchambre s ON c.idstatutchambre = s.idstatutchambre "
                + "JOIN typechambre t ON c.idtypechambre = t.idtypechambre";
        ArrayList<Chambre> chambres = new ArrayList<>(); // Créer une ArrayList pour stocker les chambres
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idChambre = rs.getInt("idchambre");
                int numChambre = rs.getInt("numchambre");
                int idTypeChambre = rs.getInt("idtypechambre");
                int idStatutChambre = rs.getInt("idstatutchambre");
                String libelleType = rs.getString("libelle_type");
                String libelleStatut = rs.getString("libelle_statut");

                Chambre chambre = new Chambre(idChambre, numChambre, idTypeChambre, idStatutChambre);
                chambre.setTypeChambreLibelle(libelleType);
                chambre.setStatutChambreLibelle(libelleStatut);

                chambres.add(chambre);
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
        return chambres;
    }

    public boolean findByNumeroChambre(int numChambre) {
        String sql = "SELECT COUNT(*) FROM chambre WHERE numchambre = ?";
        boolean chambreExiste = false;
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, numChambre);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                chambreExiste = count > 0;
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
        return chambreExiste;
    }

    public void supprimerChambreById(int idChambre) {
        String sql = "DELETE FROM chambre WHERE idchambre = ?;";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, idChambre);

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
