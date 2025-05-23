package fr.bts.sio.resasync.model.dao.implementations;

import fr.bts.sio.resasync.model.dao.interfaces.ChambreDAO;
import fr.bts.sio.resasync.model.entity.Chambre;
import fr.bts.sio.resasync.model.entity.StatutChambre;
import fr.bts.sio.resasync.model.entity.TypeChambre;
import fr.bts.sio.resasync.model.utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ChambreDAOImpl implements ChambreDAO {
    private Connection connection;

    @Override
    public Chambre findById(int id) {
        String sql = "SELECT * FROM chambre c " +
                "JOIN statutchambre s ON c.idstatutchambre = s.idstatutchambre " +
                "JOIN typechambre t ON c.idtypechambre = t.idtypechambre " +
                "WHERE c.idchambre = ?";
        Chambre chambre = null;

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Création des objets StatutChambre et TypeChambre
                StatutChambre statutChambre = new StatutChambre(rs.getInt("idstatutchambre"), rs.getString("libelle_statut"));
                TypeChambre typeChambre = new TypeChambre(rs.getInt("idtypechambre"), rs.getString("libelle_type"));

                chambre = new Chambre(rs.getInt("idchambre"), rs.getInt("numchambre"), typeChambre, statutChambre);
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
        return chambre;
    }

    @Override
    public void save(Chambre chambre) {
        String sql = "INSERT INTO chambre (numchambre, idtypechambre, idstatutchambre) VALUES (?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, chambre.getNumChambre());
            stmt.setInt(2, chambre.getTypeChambre().getIdTypeChambre());  // Utilisation de l'objet TypeChambre
            stmt.setInt(3, chambre.getStatutChambre().getIdStatutChambre());  // Utilisation de l'objet StatutChambre

            stmt.executeUpdate();
            System.out.println("Chambre bien insérée en BDD");

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
    public void update(Chambre chambre) {
        String sql = "UPDATE chambre SET numchambre = ?, idtypechambre = ?, idstatutchambre = ? WHERE idchambre = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, chambre.getNumChambre());
            stmt.setInt(2, chambre.getTypeChambre().getIdTypeChambre());  // Utilisation de l'objet TypeChambre
            stmt.setInt(3, chambre.getStatutChambre().getIdStatutChambre());  // Utilisation de l'objet StatutChambre

            stmt.setInt(4, chambre.getIdChambre());

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
    public void delete(Chambre chambre) {
        String sql = "DELETE FROM chambre WHERE idchambre = ?";

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
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Chambre> findAll() {
        String sql = "SELECT c.idchambre, c.numchambre, c.idtypechambre, c.idstatutchambre, s.libelle AS libelle_statut, t.libelle AS libelle_type " +
                "FROM chambre c " +
                "JOIN statutchambre s ON c.idstatutchambre = s.idstatutchambre " +
                "JOIN typechambre t ON c.idtypechambre = t.idtypechambre";
        ArrayList<Chambre> chambres = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Création des objets StatutChambre et TypeChambre
                StatutChambre statutChambre = new StatutChambre(rs.getInt("idstatutchambre"), rs.getString("libelle_statut"));
                TypeChambre typeChambre = new TypeChambre(rs.getInt("idtypechambre"), rs.getString("libelle_type"));

                // Création de la chambre avec les objets StatutChambre et TypeChambre
                Chambre chambre = new Chambre(rs.getInt("idchambre"), rs.getInt("numchambre"), typeChambre, statutChambre);

                chambres.add(chambre);
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
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return chambreExiste;
    }

    public void supprimerChambreById(int idChambre) {
        String sql = "DELETE FROM chambre WHERE idchambre = ?";

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
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
//
    public ArrayList<Chambre> getChambreDisponibles(LocalDate startDate, LocalDate endDate, int typeChambre) {
        // la requête exclut toutes les chambres appartenant à une resa dont soit la date de fin, la date de debut ou les deux
        // se trouvent entre les bornes de la nouvelle réservation + celles dont les dates englobent celles de la nouvelle resa
        String sql = "SELECT * FROM chambre WHERE chambre.idchambre NOT IN (" +
                "SELECT chambre.idchambre FROM chambre " +
                "JOIN relie ON relie.idchambre = chambre.idchambre " +
                "JOIN reservation ON reservation.idreservation = relie.idreservation " +
                "WHERE (" +
                "  (reservation.datedebut >= ? AND reservation.datedebut <= ?) OR " + // commence dans la période
                "  (reservation.datefin > ? AND reservation.datefin < ?) OR " +   // finit dans la période
                "  (reservation.datedebut <= ? AND reservation.datefin > ?)" + // englobe toute la période
                ")) AND idtypechambre = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        ArrayList<Chambre> listeChambres = new ArrayList<>();
        Chambre chambre = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setDate(1, Date.valueOf(startDate));
            stmt.setDate(2, Date.valueOf(endDate));
            stmt.setDate(3, Date.valueOf(startDate));
            stmt.setDate(4, Date.valueOf(endDate));
            stmt.setDate(5, Date.valueOf(startDate));
            stmt.setDate(6, Date.valueOf(endDate));
            stmt.setInt(7, typeChambre);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                chambre = new Chambre(rs.getInt("idchambre"), rs.getInt("numchambre"));
                listeChambres.add(chambre);
            }
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
        return listeChambres;
    }

    public int countAllChambres() {
        String sql = "SELECT COUNT (chambre.idchambre) FROM chambre";

        int count = 0;
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
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
        return count;
    }
}
