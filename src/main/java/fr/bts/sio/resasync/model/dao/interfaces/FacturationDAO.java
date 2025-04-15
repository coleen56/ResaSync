package fr.bts.sio.resasync.model.dao.interfaces;

import fr.bts.sio.resasync.model.entity.Facturation;

public interface FacturationDAO {
    Facturation findById(int id);
    void save(Facturation facturation);
    void update(Facturation facturation);
    void delete(Facturation facturation);
}
