package fr.bts.sio.resasync.model.dao.interfaces;

import fr.bts.sio.resasync.model.entity.TypePaiement;

public interface TypePaiementDAO {
    TypePaiement findById(int idTypePaiement);
    void save(TypePaiement typePaiement);
    void update(TypePaiement typePaiement);
    void delete(TypePaiement typePaiement);
}
