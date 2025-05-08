package fr.bts.sio.resasync.model.dao.implementations;

import fr.bts.sio.resasync.model.dao.interfaces.ClientDAO;
import fr.bts.sio.resasync.model.entity.AdresseFacturation;
import fr.bts.sio.resasync.model.entity.Client;
import fr.bts.sio.resasync.model.entity.Entreprise;
import fr.bts.sio.resasync.model.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientDAOImpl implements ClientDAO {
    @Override
    public Client findById(int id) {
        String sql = "SELECT * FROM Client WHERE idClient = ?";
        Client client = null;

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Récupération des valeurs du client
                int idClient = rs.getInt("idclient");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String tel = rs.getString("tel");
                String email = rs.getString("email");
                String dateNaissance = rs.getString("datenaissance");

                int idEntreprise = rs.getInt("identreprise");
                int idAdresseFacturation = rs.getInt("idadressefacturation");

                // Récupération des objets liés
                Entreprise entreprise = new EntrepriseDAOImpl().findById(idEntreprise);
                AdresseFacturation adresseFacturation = new AdresseFacturationDAOImpl().findById(idAdresseFacturation);

                // Création du client avec objets liés
                client = new Client(idClient, nom, prenom, tel, email, dateNaissance, entreprise, adresseFacturation);
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

        return client;
    }

    @Override
    public void save(Client client) {
        String sql = "INSERT INTO CLIENT(nom, prenom, tel, email, datenaissance, identreprise, idadressefacturation)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getPrenom());
            stmt.setString(3, client.getTel());
            stmt.setString(4, client.getEmail());
            stmt.setString(5, client.getDateNaissance());

            // Récupère les IDs des objets liés
            stmt.setInt(6, client.getEntreprise().getIdEntreprise());
            stmt.setInt(7, client.getAdresseFacturation().getIdAdresseFacturation());

            stmt.executeUpdate();
            System.out.println("Client bien inséré en BDD");

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
    public void update(Client client) {
        String sql = "UPDATE client SET nom = ?, prenom = ?, tel = ?, email = ?, datenaissance = ?, identreprise = ?, idadressefacturation = ? WHERE idclient = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getPrenom());
            stmt.setString(3, client.getTel());
            stmt.setString(4, client.getEmail());
            stmt.setString(5, client.getDateNaissance());

            // Récupération via les objets liés
            stmt.setInt(6, client.getEntreprise().getIdEntreprise());
            stmt.setInt(7, client.getAdresseFacturation().getIdAdresseFacturation());

            stmt.setInt(8, client.getIdClient());

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
    public void delete(Client client) {
        String sql = "DELETE FROM client WHERE idclient = ?;";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, client.getIdClient());

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

    public ArrayList<Client> findAll() {
        String sql = "SELECT c.idclient, c.nom, c.prenom, c.tel, c.email, c.datenaissance, " +
                "e.identreprise, e.raisonsociale, e.tel AS tel_entreprise, e.numsiret, e.email AS email_entreprise, e.idadressefacturation AS id_adr_ent, " +
                "a.idadressefacturation, a.numero, a.voie, a.codepostal, a.ville, a.pays " +
                "FROM client c " +
                "JOIN entreprise e ON c.identreprise = e.identreprise " +
                "JOIN adressefacturation a ON c.idadressefacturation = a.idadressefacturation";

        ArrayList<Client> clients = new ArrayList<>();
        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                // Données du client
                int idClient = rs.getInt("idclient");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String tel = rs.getString("tel");
                String email = rs.getString("email");
                String dateNaissance = rs.getString("datenaissance");

                // Données de l'entreprise
                int idEntreprise = rs.getInt("identreprise");
                String raisonSociale = rs.getString("raisonsociale");
                String telEntreprise = rs.getString("tel_entreprise");
                String numSiret = rs.getString("numsiret");
                String emailEntreprise = rs.getString("email_entreprise");
                int idAdresseFactEnt = rs.getInt("id_adr_ent");

                Entreprise entreprise = new Entreprise(idEntreprise, raisonSociale, telEntreprise, numSiret, emailEntreprise, idAdresseFactEnt);

                // Données de l'adresse de facturation
                int idAdresse = rs.getInt("idadressefacturation");
                String numero = rs.getString("numero");
                String voie = rs.getString("voie");
                String codePostal = rs.getString("codepostal");
                String ville = rs.getString("ville");
                String pays = rs.getString("pays");

                AdresseFacturation adresse = new AdresseFacturation(idAdresse, numero, voie, codePostal, ville, pays);

                // Création du client avec les objets complets
                Client client = new Client(idClient, nom, prenom, tel, email, dateNaissance, entreprise, adresse);

                clients.add(client);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des clients", e);
        }

        return clients;
    }


}
