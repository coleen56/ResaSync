package fr.bts.sio.resasync.model.dao.interfaces;

import fr.bts.sio.resasync.model.entity.AdresseFacturation;
import fr.bts.sio.resasync.model.entity.Chambre;

import java.util.ArrayList;

public interface ChambreDAO {
    Chambre findById(int id);
    void save(Chambre chambre);
    void update(Chambre chambre);
    void delete(Chambre chambre);
    void ajouterChambre(Chambre chambre);
    ArrayList<Chambre> findAll();
    boolean findByNumeroChambre(int numChambre);
}
