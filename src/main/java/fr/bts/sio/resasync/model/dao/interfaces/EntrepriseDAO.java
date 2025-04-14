package fr.bts.sio.resasync.model.dao.interfaces;

import fr.bts.sio.resasync.model.entity.Entreprise;

public interface EntrepriseDAO {
    Entreprise findById(int idEntreprise);
    void save(Entreprise entreprise);
    void update(Entreprise entreprise);
    void delete(Entreprise entreprise);
}
