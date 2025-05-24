package fr.bts.sio.resasync.model.dao.interfaces;

import fr.bts.sio.resasync.model.entity.Comprend;

import java.util.List;

public interface ComprendDAO {
    Comprend findById(int idreservation, int idoption);
    void save(Comprend compr);
    void update(Comprend compr);
    void delete(Comprend compr);
    List<Comprend> findOptionsByReservationId(int idReservation);

    void deleteAllFromIdReservation(int idreservation);
}

