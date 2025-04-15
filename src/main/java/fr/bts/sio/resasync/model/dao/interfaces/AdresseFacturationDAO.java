package fr.bts.sio.resasync.model.dao.interfaces;

import fr.bts.sio.resasync.model.entity.AdresseFacturation;

public interface AdresseFacturationDAO {
    AdresseFacturation findById(int id);
    void save(AdresseFacturation adresseFacturation);
    void update(AdresseFacturation adresseFacturation);
    void delete(AdresseFacturation adresseFacturation);
}
