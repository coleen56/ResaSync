package fr.bts.sio.resasync.model.dao.interfaces;

import fr.bts.sio.resasync.model.entity.NiveauDroits;

public interface NiveauDroitsDAO {
    NiveauDroits findById(int idniveau);
    void save(NiveauDroits niveau);
    void update(NiveauDroits niveau);
    void delete(NiveauDroits niveau);
}
