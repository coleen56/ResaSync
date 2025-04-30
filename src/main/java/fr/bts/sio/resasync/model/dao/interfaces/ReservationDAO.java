package fr.bts.sio.resasync.model.dao.interfaces;

import fr.bts.sio.resasync.model.entity.Reservation;

import java.util.List;

public interface ReservationDAO {
    List<Reservation> findAll();
    Reservation findById(int idReservation);
    void save(Reservation reservation);
    void update(Reservation reservation);
    void delete(Reservation reservation);
}

