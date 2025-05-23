package fr.bts.sio.resasync.model.dao.interfaces;

import fr.bts.sio.resasync.model.entity.Constantes;

public interface ConstantesDAO {

    Constantes findById(int idConstantes);
    void save(Constantes constantes);
    void update(Constantes constantes);
    void delete(Constantes constantes);
    void updateLastConstante(String libelle);
}
