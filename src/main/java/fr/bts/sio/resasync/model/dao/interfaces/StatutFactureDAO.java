package fr.bts.sio.resasync.model.dao.interfaces;

import fr.bts.sio.resasync.model.entity.StatutFacture;

public interface StatutFactureDAO {
    StatutFacture findById(int id);
    void save(StatutFacture statutFacture);
    void update(StatutFacture statutFacture);
    void delete(StatutFacture statutFacture);
}
