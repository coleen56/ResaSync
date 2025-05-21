package fr.bts.sio.resasync.model.dao.implementations;

import fr.bts.sio.resasync.model.dao.interfaces.ReservationDAO;
import fr.bts.sio.resasync.model.entity.Reservation;
import fr.bts.sio.resasync.model.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAOImpl implements ReservationDAO {


    @Override
    public List<Reservation> findAll() {
        List<Reservation> reservations = new ArrayList<>();

        String sql = "SELECT " +
                "Reservation.idReservation, " +
                "Reservation.dateReservation, " +
                "Reservation.dateDebut, " +
                "Reservation.dateFin, " +
                "Reservation.nbrPersonnes, " +
                "Reservation.nbrChambre, " +
                "Reservation.idEntreprise, " +
                "Reservation.idStatutResa, " +
                "Reservation.idClient, " +
                "Reservation.idFacture, " +
                "StatutReservation.libelle AS libelleStatut, " +
                "Entreprise.raisonSociale AS raisonSociale, " +
                "Client.nom AS nomClient, " +
                "Client.prenom AS prenomClient " +
                "FROM Reservation " +
                "JOIN StatutReservation ON Reservation.idStatutResa = StatutReservation.idStatutResa " +
                "JOIN Entreprise ON Reservation.idEntreprise = Entreprise.idEntreprise " +
                "JOIN Client ON Reservation.idClient = Client.idClient";


        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet resultSet = stmt.executeQuery()
        ) {
            while (resultSet.next()) {
                Reservation reservation = new Reservation();

                reservation.setIdReservation(resultSet.getInt("idReservation"));
                reservation.setDateReservation(resultSet.getDate("dateReservation").toLocalDate());
                reservation.setDateDebut(resultSet.getDate("dateDebut").toLocalDate());
                reservation.setDateFin(resultSet.getDate("dateFin").toLocalDate());
                reservation.setNbrPersonnes(resultSet.getInt("nbrPersonnes"));
                reservation.setNbrChambre(resultSet.getInt("nbrChambre"));
                reservation.setIdEntreprise(resultSet.getInt("idEntreprise"));
                reservation.setIdStatutResa(resultSet.getInt("idStatutResa"));
                reservation.setIdClient(resultSet.getInt("idClient"));
                reservation.setIdFacture(resultSet.getInt("idFacture"));
                reservation.setLibelleStatut(resultSet.getString("libelleStatut"));
                reservation.setRaisonSociale(resultSet.getString("raisonSociale"));
                reservation.setNomClient(resultSet.getString("nomClient"));
                reservation.setPrenomClient(resultSet.getString("prenomClient"));

                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }


//----------------------------------------------------------------------------------------------------------------------
    @Override
    public void save(Reservation reservation) {
        String sql = "INSERT INTO reservation (dateReservation, dateDebut, dateFin, nbrPersonnes, nbrChambre, idEntreprise, idStatutResa, idClient, idFacture) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
             Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            stmt.setDate(1, Date.valueOf(reservation.getDateReservation()));
            stmt.setDate(2, Date.valueOf(reservation.getDateDebut()));
            stmt.setDate(3, Date.valueOf(reservation.getDateFin()));
            stmt.setInt(4, reservation.getNbrPersonnes());
            stmt.setInt(5, reservation.getNbrChambre());
            stmt.setInt(6, reservation.getIdEntreprise());
            stmt.setInt(7, reservation.getIdStatutResa());
            stmt.setInt(8, reservation.getIdClient());
            stmt.setInt(9, reservation.getIdFacture());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedId = generatedKeys.getInt(1);
                        reservation.setIdReservation(generatedId); // mise à jour de l'objet en mémoire
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


//----------------------------------------------------------------------------------------------------------------------
    @Override
    public void update(Reservation reservation) {
        String sql = "UPDATE reservation SET dateReservation = ?, dateDebut = ?, dateFin = ?, nbrPersonnes = ?, nbrChambre = ?, idEntreprise = ?, idStatutResa = ?, idClient = ?, idFacture = ? WHERE idReservation = ?";

        try (
             Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {

            stmt.setDate(1, Date.valueOf(reservation.getDateReservation()));
            stmt.setDate(2, Date.valueOf(reservation.getDateDebut()));
            stmt.setDate(3, Date.valueOf(reservation.getDateFin()));
            stmt.setInt(4, reservation.getNbrPersonnes());
            stmt.setInt(5, reservation.getNbrChambre());
            stmt.setInt(6, reservation.getIdEntreprise());
            stmt.setInt(7, reservation.getIdStatutResa());
            stmt.setInt(8, reservation.getIdClient());
            stmt.setInt(9, reservation.getIdFacture());
            stmt.setInt(10, reservation.getIdReservation());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Impossible de modifier la réservation : " + e.getMessage());
        }
    }


//----------------------------------------------------------------------------------------------------------------------
    @Override
    public void delete(int idReservation) {
        String sql = "DELETE FROM reservation WHERE idReservation = ?";

        try (
                Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql))
        {

            stmt.setInt(1, idReservation);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Suppression réussie de la réservation numéro " + idReservation);
            } else {
                System.out.println("Aucune réservation trouvée avec l'ID " + idReservation);
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la réservation numéro " + idReservation);
            e.printStackTrace();
        }
    }

}
