package fr.bts.sio.resasync.model.dao.interfaces;

import fr.bts.sio.resasync.model.entity.Entreprise;

import java.util.ArrayList;

public interface EntrepriseDAO {
    Entreprise findById(int idEntreprise);
    void save(Entreprise entreprise);
    void update(Entreprise entreprise);
    void delete(Entreprise entreprise);

    ArrayList<Entreprise> findAll();

    public class findAll extends ArrayList<Entreprise> {
    }
}
