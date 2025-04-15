package fr.bts.sio.resasync.model.dao.interfaces;

import fr.bts.sio.resasync.model.entity.Reservation;

public interface ReservationDAO {
    Reservation findById(int idReservation);
    void save(Reservation reservation);
    void update(Reservation reservation);
    void delete(Reservation reservation);
}

