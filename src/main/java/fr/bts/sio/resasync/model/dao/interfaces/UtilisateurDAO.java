package fr.bts.sio.resasync.model.dao.interfaces;

import fr.bts.sio.resasync.model.entity.Utilisateur;

public interface UtilisateurDAO {
    Utilisateur findById(int idUtilisateur);
    void save(Utilisateur utilisateur);
    void update(Utilisateur utilisateur);
    void delete(Utilisateur utilisateur);

}
