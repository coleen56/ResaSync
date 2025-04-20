package fr.bts.sio.resasync.model.dao.interfaces;

import fr.bts.sio.resasync.model.entity.Paye;

public interface PayeDAO {
    Paye findById(int idFacture, int idTypePaiement);
    void save(Paye paye);
    void update(Paye paye);
    void delete(Paye paye);
}
