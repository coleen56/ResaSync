package fr.bts.sio.resasync.model.dao.implementations;

import fr.bts.sio.resasync.model.dao.interfaces.ReservationDAO;
import fr.bts.sio.resasync.model.entity.Reservation;

import java.sql.*;

public class ReservationDAOImpl implements ReservationDAO {

    private Connection connection;

    public ReservationDAOImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Reservation findById(int idReservation) {

        Reservation reservation = null;
        String sql = "SELECT * FROM Reservation WHERE idReservation = ?";

        try (
                PreparedStatement stmt = connection.prepareStatement(sql)) {

                stmt.setInt(1, idReservation);

                ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                reservation = new Reservation(
                        resultSet.getInt("idReservation"),
                        resultSet.getString("statutReservation"),
                        resultSet.getDate("dateReservation").toLocalDate(),
                        resultSet.getDate("dateDebut").toLocalDate(),
                        resultSet.getDate("dateFin").toLocalDate(),
                        resultSet.getString("nbrPersonnes"),
                        resultSet.getInt("nbrChambre"),
                        resultSet.getInt("idStatutResa"),
                        resultSet.getInt("idClient"),
                        resultSet.getInt("idFacture"),
                        resultSet.getInt("idResp"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservation;
    }


    @Override
    public void save(Reservation reservation) {
        String sql = "INSERT INTO Reservation (statutReservation, dateReservation, dateDebut, dateFin, " +
                "nbrPersonnes, nbrChambre, idStatutResa, idClient, idFacture, idResp) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                PreparedStatement stmt = connection.prepareStatement(sql)) {

                stmt.setString(1, reservation.getStatusReservation());
                stmt.setDate(2, Date.valueOf(reservation.getDateReservation()));
                stmt.setDate(3, Date.valueOf(reservation.getDateDebut()));
                stmt.setDate(4, Date.valueOf(reservation.getDateFin()));
                stmt.setString(5, reservation.getNbrPersonnes());
                stmt.setInt(6, reservation.getNbrChambre());
                stmt.setInt(7, reservation.getIdStatusResa());
                stmt.setInt(8, reservation.getIdClient());
                stmt.setInt(9, reservation.getIdFacture());
                stmt.setInt(10, reservation.getIdResp());

                stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(Reservation reservation) {
        String sql = "UPDATE Reservation SET statutReservation = ?, dateReservation = ?, dateDebut = ?, dateFin = NULL, " +
                "nbrPersonnes = ?, nbrChambre = ?, idStatutResa = ?, idClient = ?, idFacture = ?, idResp = ? " +
                "WHERE idReservation = ?";

        try (
                PreparedStatement stmt = connection.prepareStatement(sql)) {

                stmt.setString(1, reservation.getStatusReservation());
                stmt.setDate(2, Date.valueOf(reservation.getDateReservation()));
                stmt.setString(4, reservation.getNbrPersonnes());
                stmt.setInt(5, reservation.getNbrChambre());
                stmt.setInt(6, reservation.getIdStatusResa());
                stmt.setInt(7, reservation.getIdClient());
                stmt.setInt(8, reservation.getIdFacture());
                stmt.setInt(9, reservation.getIdResp());
                stmt.setInt(10, reservation.getIdReservation());

                stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void delete(Reservation reservation) {
        String sql = "DELETE FROM Reservation WHERE idReservation = ?";

        try (
                PreparedStatement stmt = connection.prepareStatement(sql)) {

                stmt.setInt(1, reservation.getIdReservation());

                stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
