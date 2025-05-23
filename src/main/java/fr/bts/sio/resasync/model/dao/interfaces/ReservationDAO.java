package fr.bts.sio.resasync.model.dao.interfaces;

import fr.bts.sio.resasync.model.entity.Reservation;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

public interface ReservationDAO {
    Reservation findById(int id);

    List<Reservation> findAll();
    int save(Reservation reservation);
    void update(Reservation reservation);
    void delete(int idReservation);

    void updateIdFacture(int idReservation, int idFacture);

    ArrayList<Integer> getNumChambresByReservation(Reservation reservation);
}

