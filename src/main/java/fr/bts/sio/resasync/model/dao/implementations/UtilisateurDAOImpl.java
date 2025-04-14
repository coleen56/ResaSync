package fr.bts.sio.resasync.model.dao.implementations;

import fr.bts.sio.resasync.model.dao.interfaces.UtilisateurDAO;
import fr.bts.sio.resasync.model.entity.Utilisateur;

import java.sql.Connection;

public class UtilisateurDAOImpl implements UtilisateurDAO {
    private Connection connection;

    @Override
    public Utilisateur findById(int idUtilisateur) {
        return null;
    }

    @Override
    public void save(Utilisateur utilisateur) {

    }

    @Override
    public void update(Utilisateur utilisateur) {

    }

    @Override
    public void delete(Utilisateur utilisateur) {

    }
}
