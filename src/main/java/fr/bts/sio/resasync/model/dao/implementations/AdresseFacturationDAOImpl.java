package fr.bts.sio.resasync.model.dao.implementations;

import fr.bts.sio.resasync.model.dao.interfaces.AdresseFacturationDAO;
import fr.bts.sio.resasync.model.entity.AdresseFacturation;

import java.sql.Connection;

public class AdresseFacturationDAOImpl implements AdresseFacturationDAO {
    private Connection connection;

    public AdresseFacturationDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public AdresseFacturation findById(int id) {
        return null;
    }

    @Override
    public void save(AdresseFacturation adresseFacturation) {

    }

    @Override
    public void update(AdresseFacturation adresseFacturation) {

    }

    @Override
    public void delete(AdresseFacturation adresseFacturation) {

    }
}
