package fr.bts.sio.resasync.model.dao.interfaces;

import fr.bts.sio.resasync.model.entity.Client;

import java.util.List;

public interface ClientDAO {
    Client findById(int id);
    void save(Client client);
    void update(Client client);
    void delete(Client client);

    List<Client> findAll();
}
