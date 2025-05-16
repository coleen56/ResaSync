package fr.bts.sio.resasync.model.dao.implementations;

import fr.bts.sio.resasync.model.dao.interfaces.ClientDAO;
import fr.bts.sio.resasync.model.entity.AdresseFacturation;
import fr.bts.sio.resasync.model.entity.Client;
import fr.bts.sio.resasync.model.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ClientDAOImpl implements ClientDAO {

    @Override
    public Client findById(int id) {
        String sql = "SELECT * FROM Client WHERE idClient = ?";
        Client client = null;

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int idClient = rs.getInt("idclient");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String tel = rs.getString("tel");
                String email = rs.getString("email");
                LocalDate dateNaissance = rs.getDate("datenaissance").toLocalDate();
                int idAdresseFacturation = rs.getInt("idadressefacturation");

                AdresseFacturation adresseFacturation = new AdresseFacturationDAOImpl().findById(idAdresseFacturation);

                client = new Client(idClient, nom, prenom, tel, email, dateNaissance, adresseFacturation);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return client;
    }

    @Override
    public void save(Client client) {
        String sql = "INSERT INTO Client(nom, prenom, tel, email, datenaissance, idadressefacturation) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getPrenom());
            stmt.setString(3, client.getTel());
            stmt.setString(4, client.getEmail());
            stmt.setDate(5, java.sql.Date.valueOf(client.getDateNaissance()));
            stmt.setInt(6, client.getAdresseFacturation().getIdAdresseFacturation());

            stmt.executeUpdate();
            System.out.println("Client bien inséré en BDD");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(Client client) {
        String sql = "UPDATE Client SET nom = ?, prenom = ?, tel = ?, email = ?, datenaissance = ?, " +
                "idadressefacturation = ? WHERE idclient = ?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getPrenom());
            stmt.setString(3, client.getTel());
            stmt.setString(4, client.getEmail());
            stmt.setDate(5, java.sql.Date.valueOf(client.getDateNaissance()));
            stmt.setInt(6, client.getAdresseFacturation().getIdAdresseFacturation());
            stmt.setInt(7, client.getIdClient());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Client client) {
        String sql = "DELETE FROM Client WHERE idclient = ?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, client.getIdClient());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Client> findAll() {
        String sql = "SELECT c.idclient, c.nom, c.prenom, c.tel, c.email, c.datenaissance, " +
                "a.idadressefacturation, a.numero, a.voie, a.codepostal, a.ville, a.pays " +
                "FROM Client c " +
                "JOIN AdresseFacturation a ON c.idadressefacturation = a.idadressefacturation";

        ArrayList<Client> clients = new ArrayList<>();

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

                Client client = new Client(
                        rs.getInt("idclient"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("tel"),
                        rs.getString("email"),
                        rs.getDate("datenaissance").toLocalDate(),
                        adresseFacturation
                );

                clients.add(client);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des clients", e);
        }

        return clients;
    }

    public boolean mailExiste(String email) {
        String sql = "SELECT COUNT(*) FROM client WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();  // À remplacer par une vraie gestion d'erreur si besoin
        }
        return false;
    }

    public boolean telExiste(String tel) {
        String sql = "SELECT COUNT(*) FROM client WHERE tel = ?";
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
