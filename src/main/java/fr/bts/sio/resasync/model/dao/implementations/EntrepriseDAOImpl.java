package fr.bts.sio.resasync.model.dao.implementations;

import fr.bts.sio.resasync.model.dao.interfaces.AdresseFacturationDAO;
import fr.bts.sio.resasync.model.dao.interfaces.EntrepriseDAO;
import fr.bts.sio.resasync.model.entity.AdresseFacturation;
import fr.bts.sio.resasync.model.entity.Entreprise;
import fr.bts.sio.resasync.model.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntrepriseDAOImpl implements EntrepriseDAO {
    private Connection connection;

    @Override
    public Entreprise findById(int idEntreprise) {
        String sql = "SELECT * FROM entreprise WHERE identreprise = ?";
        Entreprise entreprise = null;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idEntreprise);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int idAdresse = rs.getInt("idadressefacturation");

                // Utilise le DAO pour récupérer l'adresse
                AdresseFacturation adresseFacturation = null;
                if (idAdresse > 0) {
                    AdresseFacturationDAO adresseFacturationDao = new AdresseFacturationDAOImpl();
                    adresseFacturation = adresseFacturationDao.findById(idAdresse);
                }

                entreprise = new Entreprise(
                        rs.getInt("identreprise"),
                        rs.getString("raisonsociale"),
                        rs.getString("tel"),
                        rs.getString("numsiret"),
                        rs.getString("email"),
                        adresseFacturation
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return entreprise;
    }

    @Override
    public void save(Entreprise entreprise) {
        String sql = "INSERT INTO entreprise(raisonsociale, tel, numsiret, email, idadressefacturation) " +
                "VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, entreprise.getRaisonSociale());
            stmt.setString(2, entreprise.getTel());
            stmt.setString(3, entreprise.getNumSiret());
            stmt.setString(4, entreprise.getEmail());

            AdresseFacturation adresse = entreprise.getAdresseFacturationEntreprise();
            if (adresse != null) {
                stmt.setInt(5, adresse.getIdAdresseFacturation());
            } else {
                stmt.setNull(5, java.sql.Types.INTEGER); // ou SQLException si adresse obligatoire
            }

            stmt.executeUpdate();
            System.out.println("Entreprise bien insérée en BDD");

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
    public void update(Entreprise entreprise) {
        String sql = "UPDATE entreprise SET raisonsociale = ?, tel = ?, numsiret = ?, email = ?, idadressefacturation = ? WHERE identreprise = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, entreprise.getRaisonSociale());
            stmt.setString(2, entreprise.getTel());
            stmt.setString(3, entreprise.getNumSiret());
            stmt.setString(4, entreprise.getEmail());

            AdresseFacturation adresse = entreprise.getAdresseFacturationEntreprise();
            if (adresse != null) {
                stmt.setInt(5, adresse.getIdAdresseFacturation());
            } else {
                stmt.setNull(5, java.sql.Types.INTEGER); // ou tu peux gérer l'erreur si adresse est obligatoire
            }

            stmt.setInt(6, entreprise.getIdEntreprise());

            stmt.executeUpdate();
            System.out.println("Entreprise mise à jour en BDD");

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
    public void delete(Entreprise entreprise) {
        String sql = "DELETE FROM entreprise WHERE identreprise = ?;";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, entreprise.getIdEntreprise());

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
    public ArrayList<Entreprise> findAll() {
        String sql = "SELECT e.identreprise, e.raisonsociale, e.tel, e.numsiret, e.email, "
                + "a.idadressefacturation, a.numero, a.voie, a.codepostal, a.ville, a.pays "
                + "FROM entreprise e "
                + "JOIN adressefacturation a ON e.idadressefacturation = a.idadressefacturation";

        ArrayList<Entreprise> entreprises = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                AdresseFacturation adresseFacturation = new AdresseFacturation(
                        rs.getInt("idadressefacturation"),
                        rs.getString("numero"),
                        rs.getString("voie"),
                        rs.getString("codepostal"),
                        rs.getString("ville"),
                        rs.getString("pays")
                );

                Entreprise entreprise = new Entreprise(
                        rs.getInt("identreprise"),
                        rs.getString("raisonsociale"),
                        rs.getString("tel"),
                        rs.getString("numsiret"),
                        rs.getString("email"),
                        adresseFacturation
                );

                entreprises.add(entreprise);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des entreprises", e);
        }

        return entreprises;
    }

    public boolean numSiretExiste(String numSiret) {
        String sql = "SELECT COUNT(*) FROM entreprise WHERE numsiret = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, numSiret);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean mailExiste(String email) {
        String sql = "SELECT COUNT(*) FROM entreprise WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean telExiste(String tel) {
        String sql = "SELECT COUNT(*) FROM entrepise WHERE tel = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tel);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
