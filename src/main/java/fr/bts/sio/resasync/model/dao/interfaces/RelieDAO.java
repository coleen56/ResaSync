package fr.bts.sio.resasync.model.dao.interfaces;

import fr.bts.sio.resasync.model.entity.Relie;

public interface RelieDAO {
    Relie findById(int idChambre, int idReservation);
    void save(Relie relie);
    void delete(Relie relie);
    void deleteAllFromIdReservation(int idreservation);
}