package fr.bts.sio.resasync.model.dao.interfaces;

import fr.bts.sio.resasync.model.entity.OptionReservation;

import java.util.List;

public interface OptionReservationDAO {
    OptionReservation findById(int id);
    void save(OptionReservation option);
    void update(OptionReservation option);
    void delete(OptionReservation option);

    List<OptionReservation> optionReservationsAll();
}
