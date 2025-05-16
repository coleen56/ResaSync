package fr.bts.sio.resasync.model.dao.implementations;

import fr.bts.sio.resasync.model.dao.interfaces.ReservationDAO;
import fr.bts.sio.resasync.model.entity.Reservation;
import fr.bts.sio.resasync.model.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.h2.engine.GeneratedKeysMode.valueOf;

public class ReservationDAOImpl implements ReservationDAO {


    @Override
    public List<Reservation> findAll() {

        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT \n" +
                "    Reservation.idReservation,\n" +
                "    Reservation.dateReservation,\n" +
                "    Reservation.dateDebut,\n" +
                "    Reservation.dateFin,\n" +
                "    Reservation.nbrPersonnes,\n" +
                "    Reservation.nbrChambre,\n" +
                "    Reservation.idEntreprise,\n" +
                "    Reservation.idStatutResa,\n" +
                "    Reservation.idClient,\n" +
                "    Reservation.idFacture,\n" +
                "FROM Reservation\n";

        Reservation reservation = null;
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Reservation res = new Reservation(
                        resultSet.getInt("idReservation"),
                        resultSet.getDate("dateReservation").toLocalDate(),
                        resultSet.getDate("dateDebut").toLocalDate(),
                        resultSet.getDate("dateFin").toLocalDate(),
                        resultSet.getInt("nbrPersonnes"),
                        resultSet.getInt("nbrChambre"),
                        resultSet.getInt("idEntreprise"),
                        resultSet.getInt("idStatutResa"),
                        resultSet.getInt("idClient"),
                        resultSet.getInt("idFacture")
                );
                reservations.add(res);
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
        return reservations;
    }


    @Override
    public Reservation findById(int idReservation) {

        String sql = "SELECT * FROM Reservation WHERE idReservation = ?";

        Reservation reservation = null;
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idReservation);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                reservation = new Reservation(
                        resultSet.getInt("idReservation"),
                        resultSet.getDate("dateReservation").toLocalDate(),
                        resultSet.getDate("dateDebut").toLocalDate(),
                        resultSet.getDate("dateFin").toLocalDate(),
                        resultSet.getInt("nbrPersonnes"),
                        resultSet.getInt("nbrChambre"),
                        resultSet.getInt("idEntreprise"),
                        resultSet.getInt("idStatutResa"),
                        resultSet.getInt("idClient"),
                        resultSet.getInt("idFacture"));
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
        return reservation;
    }


    @Override
    public void save(Reservation reservation) {
        String sql = "INSERT INTO Reservation (dateReservation, dateDebut, dateFin, " +
                "nbrPersonnes, nbrChambre, idEntreprise, idStatutResa, idClient, idFacture) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setDate(1, Date.valueOf(reservation.getDateReservation()));
            stmt.setDate(2, Date.valueOf(reservation.getDateDebut()));
            stmt.setDate(3, Date.valueOf(reservation.getDateFin()));
            stmt.setInt(4, reservation.getNbrPersonnes());
            stmt.setInt(5, reservation.getNbrChambre());
            stmt.setInt(6, reservation.getIdEntreprise());
            stmt.setInt(7, reservation.getIdStatutResa());
            stmt.setInt(8, reservation.getIdClient());
            stmt.setInt(9, reservation.getIdFacture());

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
    public void update(Reservation reservation) {
        String sql = "UPDATE Reservation SET dateReservation = ?, dateDebut = ?, dateFin = ?, " +
                "nbrPersonnes = ?, nbrChambre = ?, idEntreprise = ?, idStatutResa = ?, idClient = ?, idFacture = ?" +
                "WHERE idReservation = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

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
    public void delete(Reservation reservation) {
        String sql = "DELETE FROM Reservation WHERE idReservation = ?";

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, reservation.getIdReservation());

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
