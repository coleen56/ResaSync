package fr.bts.sio.resasync.model.dao.interfaces;

import fr.bts.sio.resasync.model.entity.StatutChambre;

public interface StatutChambreDAO {
    StatutChambre findById(int idStatutChambre);
    void save(StatutChambre statutChambre);
    void update(StatutChambre statutChambre);
    void delete(StatutChambre statutChambre);
}