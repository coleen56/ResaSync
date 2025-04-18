package fr.bts.sio.resasync.model.dao.interfaces;

import fr.bts.sio.resasync.model.entity.Paye;

public interface PayeDAO {
    Paye findById(int id);
    void save(Paye paye);
    void update(Paye paye);
    void delete(Paye paye);
}
