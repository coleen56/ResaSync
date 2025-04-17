package fr.bts.sio.resasync.model.dao.interfaces;

import fr.bts.sio.resasync.model.entity.AdresseFacturation;
import fr.bts.sio.resasync.model.entity.Chambre;

public interface ChambreDAO {
    Chambre findById(int id);
    void save(Chambre chambre);
    void update(Chambre chambre);
    void delete(Chambre chambre);
}
