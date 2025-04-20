package fr.bts.sio.resasync.model.dao.interfaces;

import fr.bts.sio.resasync.model.entity.TypeChambre;

public interface TypeChambreDAO {
    TypeChambre findById(int IdTypeChambre);

    void save(TypeChambre typeChambre);

    void update(TypeChambre typeChambre);

    void delete(TypeChambre typeChambre);
}
