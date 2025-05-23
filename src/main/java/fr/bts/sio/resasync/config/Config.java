package fr.bts.sio.resasync.config;

import fr.bts.sio.resasync.model.entity.Constantes;

import java.util.List;

public class Config {
    private List<Constantes> constantes;

    public List<Constantes> getConstantes() {
        return constantes;
    }

    public void setConstantes(List<Constantes> constantes) {
        this.constantes = constantes;
    }
}
