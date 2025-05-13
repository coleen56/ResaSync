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
                "    StatutReservation.libelle AS statutReservation,\n" +
                "    Client.nom,\n" +
                "    Client.prenom,\n" +
                "    Reservation.idFacture,\n" +
                "    Reservation.idResp\n" +
                "FROM \n" +
                "    Reservation\n" +
                "JOIN \n" +
                "    StatutReservation ON Reservation.idStatutResa = StatutReservation.idStatutResa\n" +
                "JOIN \n" +
                "    Client ON Reservation.idClient = Client.idClient;\n";

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
                        resultSet.getString("statutReservation"),
                        resultSet.getString("nom") + " " + resultSet.getString("prenom"),
                        resultSet.getInt("idFacture"),
                        resultSet.getInt("idResp")
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
                        resultSet.getString("statutReservation"),
                        resultSet.getString("nom") + " " + resultSet.getString("prenom"),
                        resultSet.getInt("idFacture"),
                        resultSet.getInt("idResp"));
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
                "nbrPersonnes, nbrChambre, idStatutResa, idClient, idFacture, idResp) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setDate(2, new Date(valueOf(reservation.getDateReservation())));
            stmt.setDate(3, Date.valueOf(reservation.getDateDebut()));
            stmt.setDate(4, Date.valueOf(reservation.getDateFin()));
            stmt.setInt(5, reservation.getNbrPersonnes());
            stmt.setInt(6, reservation.getNbrChambre());
            stmt.setString(7, reservation.getStatutReservation());
            stmt.setString(8, reservation.getNomPrenomClient());
            stmt.setInt(9, reservation.getIdFacture());
            stmt.setInt(10, reservation.getIdResp());

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
        String sql = "UPDATE Reservation SET dateReservation = ?, dateDebut = ?, dateFin = NULL, " +
                "nbrPersonnes = ?, nbrChambre = ?, idStatutResa = ?, idClient = ?, idFacture = ?, idResp = ? " +
                "WHERE idReservation = ?";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);

            stmt.setDate(2, Date.valueOf(reservation.getDateReservation()));
            stmt.setInt(4, reservation.getNbrPersonnes());
            stmt.setInt(5, reservation.getNbrChambre());
            stmt.setString(6, reservation.getStatutReservation());
            stmt.setString(7, reservation.getNomPrenomClient());
            stmt.setInt(8, reservation.getIdFacture());
            stmt.setInt(9, reservation.getIdResp());
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
