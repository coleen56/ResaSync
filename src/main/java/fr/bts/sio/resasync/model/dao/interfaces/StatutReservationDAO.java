package fr.bts.sio.resasync.model.dao.interfaces;

import fr.bts.sio.resasync.model.entity.StatutReservation;

public interface StatutReservationDAO {
    StatutReservation findById(int id);
    void save(StatutReservation statut);
    void update(StatutReservation statut);
    void delete(StatutReservation statut);
}
