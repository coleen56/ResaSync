package fr.bts.sio.resasync.model.dao.implementations;

import fr.bts.sio.resasync.model.dao.interfaces.EntrepriseDAO;
import fr.bts.sio.resasync.model.entity.Entreprise;

import java.sql.Connection;

public class EntrepriseDAOImpl implements EntrepriseDAO {
    private Connection connection;

    public EntrepriseDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Entreprise findById(int idEntreprise) {
        return null;
    }

    @Override
    public void save(Entreprise entreprise) {

    }

    @Override
    public void update(Entreprise entreprise) {

    }

    @Override
    public void delete(Entreprise entreprise) {

    }
}
